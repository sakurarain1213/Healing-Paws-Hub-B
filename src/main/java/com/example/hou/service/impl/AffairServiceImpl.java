package com.example.hou.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Like;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hou.entity.*;
import com.example.hou.mapper.AffairNodeRepository;
import com.example.hou.mapper.AffairRepository;
import com.example.hou.mapper.SysPermissionMapper;
import com.example.hou.mapper.SysUserPermissionRelationMapper;
import com.example.hou.result.Result;
import com.example.hou.service.AffairService;
import com.example.hou.util.ESUtils;
import com.example.hou.util.ResultUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AffairServiceImpl implements AffairService {
    @Autowired
    private AffairRepository affairRepository;

    @Autowired
    private AffairNodeRepository affairNodeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticsearchClient esClient;

    @Autowired
    private ESUtils<Affair> esAffairUtils;

    @Autowired
    private ESUtils<AffairRecord> esAffairRecordUtils;

    @Autowired
    SysUserPermissionRelationMapper relationMapper;

    @Autowired
    SysPermissionMapper permissionMapper;


    @Override
    public Affair createAffair(Affair affair) {
        return affairRepository.insert(affair);
    }

    @Override
    public Affair getById(String id){
        return affairRepository.findById(id).orElse(null);
    }

    @Override
    public int deleteById(String id) {
        Affair cur = affairRepository.findById(id).orElse(null);
        if(cur == null)return -1;
        affairRepository.deleteById(id);
        return 0;
    }

    @Override
    public long updateById(Affair affair) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(affair.getId()));

        Update upt = new Update();
        Optional.ofNullable(affair.getRole()).ifPresent(c -> upt.set("role", c));
        Optional.ofNullable(affair.getName()).ifPresent(c -> upt.set("name", c));
        Optional.ofNullable(affair.getDescription()).ifPresent(c -> upt.set("description", c));
        Optional.ofNullable(affair.getPic()).ifPresent(c -> upt.set("pic", c));
        Optional.ofNullable(affair.getPicSize()).ifPresent(c -> upt.set("picSize", c));

        upt.set("affairs", affair.getAffairs());
        upt.set("edges", affair.getEdges());
//        Optional.ofNullable(affair.getAffairs()).ifPresent(c -> upt.set("affairs", c));
//        Optional.ofNullable(affair.getEdges()).ifPresent(c -> upt.set("edges", c));

        UpdateResult updateResult = mongoTemplate.updateFirst(query, upt, Affair.class);
        return updateResult.getModifiedCount();
    }

    @Override
    public Page<AffairAndFavoriteDTO> getByPage(Integer pageNum, Integer pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId=-1;
        // 检查用户是否已经登录
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                userId = logUser.getUser().getUserId();
            }
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Affair> pageAffairs =affairRepository.findAll(pageable);


        //List<Affair> affairs=affairRepository.findAll();
        List <AffairAndFavoriteDTO> isFavoriteAffairs = new ArrayList<>();
        // 遍历Affair列表，为每个Affair查询Favorite状态
        for (Affair affair : pageAffairs.getContent()) {
            String affairId = affair.getId(); // 假设Affair有一个getId()方法返回用于查询的ID
            Query query = new Query();
            query.addCriteria(Criteria.where("objectId").is(affairId)
                    .and("userId").is(userId));
            // 执行查询，检查是否有匹配的Favorite对象
            Favorite favorite = mongoTemplate.findOne(query, Favorite.class);
            // 创建AffairAndFavoriteDTO对象
            AffairAndFavoriteDTO affairAndFavoriteDTO = new AffairAndFavoriteDTO(affair, favorite != null);
            // 添加到结果列表中
            isFavoriteAffairs.add(affairAndFavoriteDTO);
        }

        //    //封装成分页创建新的Page对象，包含转换后的DTO列表

        //System.out.println(departments);
        //封装一个分页标准返回
        //PageSupport<AffairAndFavoriteDTO> respPage = new PageSupport<>();
       // respPage.setListData(dtos.getContent())
        //        .setTotalPages(dtos.getTotalPages());

        return new PageImpl<>(isFavoriteAffairs, pageable, pageAffairs.getTotalElements());
        // 返回结果列表

}

    @Override
    public NodeFlowDia getGraphByAffairid(String affairId) {
        Optional<Affair> cur = affairRepository.findById(affairId);
        Affair affair = cur.orElse(null);
        if (affair == null)return null;

//        List<AffairNode> res = new ArrayList<>();
        List<AffairNode> nodes = (List<AffairNode>)affairNodeRepository.findAllById(affair.getAffairs());

        NodeFlowDia nodeFlowDia = new NodeFlowDia()
                .setNodes(nodes)
                .setEdges(affair.getEdges());
        return nodeFlowDia;
    }

    @Override
    public List<Affair> getRecommendAffairs(LogUser user, Integer count) {
        Integer userId = user.getUser().getUserId();
//        Integer userId = 1;
//        List<String> permissions = user.getPermissions();
//        if (permissions == null || permissions.size() == 0)return null;


//        String userRole = permissions.get(0);
        QueryWrapper<SysUserPermissionRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        SysUserPermissionRelation permissionRelation = relationMapper.selectOne(wrapper);
        if (permissionRelation == null)return null;
        Integer pId = permissionRelation.getPermissionId();
        if (pId == null)return null;

        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("permission_id", pId);
        SysPermission sysPermission = permissionMapper.selectOne(permissionQueryWrapper);
        if (sysPermission == null)return null;

        String userRole = sysPermission.getPermissionCode();
        if (userRole == null)return null;

//        String userRole = "admin";
        System.out.println("userRole: " + userRole);

        try {
            CountResponse totalTarAffairResp = esClient.count(s -> s
                    .index("test.affair")
                    .query(q -> q.match(
                            t -> t.field("role").query(userRole)
                    ))
            );

            long totalAffairCnt = totalTarAffairResp.count();
            System.out.println("totalAffairCnt:" + totalAffairCnt);
            //            检查符合用户角色的affair总数,不足return
            if(totalAffairCnt <= count){
                System.out.println("totalAffairCnt <= count");

                SearchResponse<HashMap> totalResp = esClient.search(s -> s
                                .index("test.affair")
                                .query(q -> q.match(
                                        t -> t.field("role").query(userRole)
                                ))
                                .from(0)
                                .size((int)totalAffairCnt)
                        , HashMap.class
                );

                return esAffairUtils.hitToTarget(totalResp.hits().hits(), Affair.class);
            }

            //检查该用户是否有record，有则根据record作推荐，否则分页查询count个affair
            CountResponse cntResp = esClient.count(
                s -> s.index("test.affair_record")
                        .query(q -> q.term(
                            t -> t.field("userId").value(userId)
                        ))

            );


            long existNum = cntResp.count();
            System.out.println("existNum:"+existNum);
            if(existNum == 0){
                System.out.println("existNum == 0");

                SearchResponse<HashMap> search = esClient.search(s -> s
                        .index("test.affair")
                        .query(q -> q.match(
                                t -> t.field("role").query(userRole)
                        ))
                        .from(0)
                        .size(count),
                        HashMap.class
                );

                return esAffairUtils.hitToTarget(search.hits().hits(), Affair.class);
            }

//            获取record数量
            long fetchNum = existNum > 10 ? 10 : existNum;
            System.out.println("fetchNum:"+fetchNum);

            SearchResponse<HashMap> recordSearchResponse = esClient.search(s -> s
                            .index("test.affair_record")
                            .query(q -> q.term(
                                    t -> t.field("userId").value(userId)
                            ))
                            .from(0)
                            .size((int)fetchNum)
                            .sort(f -> f.field(o -> o.field("finishTime").order(SortOrder.Desc)))
                    , HashMap.class
            );

            //抽取用户记录
            List<AffairRecord> existRecords = esAffairRecordUtils.hitToTarget(recordSearchResponse.hits().hits(), AffairRecord.class);
            System.out.println("existRecords.size: " + existRecords.size());
            if (existRecords.size() > 5) {
                Collections.shuffle(existRecords);
                existRecords = existRecords.subList(0, 5);
            }

//            抽取用作搜索的id list
            List<String> usefulIds = existRecords.stream()
                    .map(AffairRecord::getAffairInfo)
                    .map(AffairDTO::getId)
                    .collect(Collectors.toList());
            System.out.println("usefulIds.size: " + usefulIds.size());


            List<Like> likes = usefulIds.stream()
                    .map(e -> Like.of(t -> t.document(d -> d.id(e))))
                    .collect(Collectors.toList());


//            Like.Builder.text(e.getId()).build()

            SearchResponse<HashMap> targetResp = esClient.search(s -> s
                            .index("test.affair")
                            .query(q -> q.bool(
                                    b -> b.must(
                                        m -> m.moreLikeThis(
                                                t -> t.fields("description")
                                                        .like(likes)
                                                        .minTermFreq(2)
                                                        .maxQueryTerms(12)
                                        )
                                    ).must(
                                        m -> m.match(t -> t.field("role").query(userRole))
                                    )
                            ))
//                            .query(q -> q.moreLikeThis(
//                                    t -> t.fields("description")
//                                            .like(likes)
//                                            .minTermFreq(1)
//                                            .maxQueryTerms(12)
//                                    )
//                            )
//                            .query(q -> q.term(t -> t.field("role").value(userRole)))
                            .from(0)
                            .size(count)
                    , HashMap.class
            );
            List<Affair> candidates = esAffairUtils.hitToTarget(targetResp.hits().hits(), Affair.class);
            //            candidates.size 一定 <= count
            System.out.println("candidates.size: " + candidates.size());
            if(candidates.size() == count) return candidates;

            int fillNum = count - candidates.size();
            System.out.println("fillNum: " + fillNum);
            List<String> excludeIds = candidates.stream().map(Affair::getId).collect(Collectors.toList());
//            excludeIds.stream().forEach(System.out::println);

            SearchResponse<HashMap> fillResp = esClient.search(s -> s
                            .index("test.affair")
                            .query(q -> q
                                .bool(b -> b
                                    .mustNot(
                                            m -> m.ids(e -> e.values(excludeIds))
                                    )
                                    .must(m -> m.match(t -> t.field("role").query(userRole)))
                                )
                            )
                            .from(0)
                            .size(fillNum)
                    , HashMap.class
            );

            List<Affair> toFill = esAffairUtils.hitToTarget(fillResp.hits().hits(), Affair.class);
            candidates.addAll(toFill);
            return candidates;


//            SearchResponse<HashMap> recordSearchResponse = esClient.search(s -> s
//                            .index("test.affair_record")
//                            .query(q -> q.term(
//                                    t -> t.field("userId").value(userId)
//                            ))
//                            .from(0)
//                            .size(2)
//                            .sort(f -> f.field(o -> o.field("finishTime").order(SortOrder.Desc))),
//                    HashMap.class
//            );
//
//
//            List<Hit<HashMap>> hits = recordSearchResponse.hits().hits();
//            for(Hit<HashMap> hit : hits){
//                String curId = hit.id();
//                System.out.println("curId: "+ curId);
//
//                Double score = hit.score();
//                System.out.println("score: "+ score);
//
//                Map<String,Object> docMap = hit.source();
//                String json = JSON.toJSONString(docMap);
//                AffairRecord record  = JSON.parseObject(json, AffairRecord.class);
//                System.out.println(record);
//            }
//
//            System.out.println("=================");


//            return null;

        }catch (Exception e){
            e.printStackTrace();
            return null;
//            throw new RuntimeException("getRecommendAffairs: " + e.getMessage());
        }

    }

    @Override
    public List<Affair> getFuzzyMatchedAffairs(String input, Integer pageNum, Integer pageSize) {
        try {
            List<String> fields = new ArrayList<>();
            fields.add("name");
            fields.add("description");

            return esAffairUtils.fuzzyQueryByPage("test.affair", input, fields, pageNum, pageSize, Affair.class);
//            SearchResponse<HashMap> resp = esClient.search(s -> s
//                .index("test.affair")
//                .query(q -> q.bool(
//                    b -> b.should(
//                        h -> h.fuzzy(
//                            f -> f.field("description")
//                                  .value(input)
//                                  .fuzziness("1")
//                        )
//                    ).should(
//                        h -> h.fuzzy(
//                            f -> f.field("name")
//                                    .value(input)
//                                    .fuzziness("1")
//                        )
//                    ))
//                )
//                .from(pageNum)
//                .size(pageSize)
//            , HashMap.class);
//            return esAffairUtils.hitToTarget(resp.hits().hits(), Affair.class);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Result addNodeToAffair(String affairId, String nodeId) {
        Affair affair = affairRepository.findById(affairId).orElse(null);
        if (affair == null) {
            System.out.println("affairId invalid");
            return ResultUtil.error("affairId不合法");
        }

        AffairNode affairNode = affairNodeRepository.findById(nodeId).orElse(null);
        if (affairNode == null){
            System.out.println("nodeId invalid");
            return ResultUtil.error("nodeId不合法");
        }

        List<String> affairs = affair.getAffairs();

//        判断nodeId是否已经添加在affairs
        Set<String> set = affairs.stream().collect(Collectors.toSet());
        if (set.contains(nodeId))return ResultUtil.error("nodeId已存在");

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(affairId));

        Update update = new Update();
        update.push("affairs", nodeId);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Affair.class);
        System.out.println(updateResult.getModifiedCount());

        affairs.add(nodeId);

        return ResultUtil.success(affair);
    }

    @Override
    public boolean validateAffairs(List<String> affairs) {
//        affairNodeRepository.existsById()
        List<AffairNode> nodes = (List<AffairNode>) affairNodeRepository.findAllById(affairs);
        System.out.println("validateAffairs exist nodes.size: " + nodes.size());

        return nodes.size() == affairs.size();
    }

    @Override
    public boolean validateEdges(List<String[]> edges, List<String> affairs) {
        HashSet<String> st = new HashSet<>(affairs);

        for(String[] e : edges){
            if(e.length != 2)return false;
            if(!st.contains(e[0]))return false;
            if(!st.contains(e[1]))return false;
        }

        return true;
//        List<AffairNode> nodes = (List<AffairNode>) affairNodeRepository.findAllById(st);
//        return nodes.size() == st.size();
    }


}
