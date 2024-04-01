package com.example.hou.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Like;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.hou.entity.*;
import com.example.hou.mapper.AffairNodeRepository;
import com.example.hou.mapper.AffairRepository;
import com.example.hou.service.AffairService;
import com.example.hou.util.ESUtils;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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


    @Override
    public Affair createAffair(Affair affair) {
        return affairRepository.insert(affair);
    }

    @Override
    public void deleteById(String id) {
        affairRepository.deleteById(id);
    }

    @Override
    public long updateById(Affair affair) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(affair.getId()));

        Update upt = new Update();
        Optional.ofNullable(affair.getRole()).ifPresent(c -> upt.set("role", c));
        Optional.ofNullable(affair.getDescription()).ifPresent(c -> upt.set("description", c));
        Optional.ofNullable(affair.getAffairs()).ifPresent(c -> upt.set("affairs", c));

        UpdateResult updateResult = mongoTemplate.updateFirst(query, upt, Affair.class);
        return updateResult.getModifiedCount();
    }

    @Override
    public Page<Affair> getByPage(Integer pageNum, Integer pageSize) {
        return affairRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    @Override
    public List<AffairNode> getAllNodesByAffairid(String affairId) {
        Optional<Affair> cur = affairRepository.findById(affairId);
        Affair affair = cur.orElse(null);
        if (affair == null)return null;

//        List<AffairNode> res = new ArrayList<>();
        List<AffairNode> res = (List<AffairNode>)affairNodeRepository.findAllById(affair.getAffairs());
        return res;
    }

    @Override
    public List<Affair> getRecommendAffairs(LogUser user, Integer count) {
        Integer userId = user.getUser().getUserId();
//        Integer userId = 1;
        List<String> permissions = user.getPermissions();
        if (permissions == null || permissions.size() == 0)return null;

//        TODO 未确定role，测试用admin
//        String userRole = permissions.get(0);
        String userRole = "admin";
        System.out.println("userRole: " + userRole);

        try {
            CountResponse totalTarAffairResp = esClient.count(s -> s
                    .index("test.affair")
                    .query(q -> q.term(
                            t -> t.field("role").value(userRole)
                    ))
            );

            long totalAffairCnt = totalTarAffairResp.count();
            System.out.println("totalAffairCnt:" + totalAffairCnt);
            //            检查符合用户角色的affair总数,不足return
            if(totalAffairCnt <= count){
                System.out.println("totalAffairCnt <= count");

                SearchResponse<HashMap> totalResp = esClient.search(s -> s
                                .index("test.affair")
                                .query(q -> q.term(
                                        t -> t.field("role").value(userRole)
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
                        .query(q -> q.term(
                                t -> t.field("role").value(userRole)
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
                                        m -> m.term(t -> t.field("role").value(userRole))
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
                                    .must(m -> m.term(t -> t.field("role").value(userRole)))
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


}