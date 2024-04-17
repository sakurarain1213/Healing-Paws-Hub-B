package com.example.hou.service.impl;

import com.example.hou.entity.*;
import com.example.hou.mapper.FavoriteRepository;
import com.example.hou.result.Result;
import com.example.hou.service.FavoriteService;
import com.example.hou.util.MapObjectUtil;
import com.example.hou.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Result createFavorite(Favorite createVo) {
//        检查objectId是否在对应的collection中存在，已存在才能插入
        String objType = createVo.getObjectType();
        Query query = new Query().addCriteria(Criteria.where("id").is(createVo.getObjectId()));
        boolean flag = false;

        if (objType.equals("affair")){
            flag = mongoTemplate.exists(query, Affair.class);

        }else if(objType.equals("affairNode")){
            flag = mongoTemplate.exists(query, AffairNode.class);

        }else if(objType.equals("item")){
//            TODO 设置item

        }

        if (!flag)return ResultUtil.error("objectId不存在");

        //        检查是否已收藏
        flag = judgeFavored(createVo.getUserId(), createVo.getObjectType(), createVo.getObjectId());
        if (flag)return ResultUtil.error("用户已收藏");

        Favorite created = favoriteRepository.insert(createVo);

        if (created.getId() == null)return ResultUtil.error(null);
        System.out.println(created.getId());
        return ResultUtil.success(created);
    }

    @Override
    public int deleteById(String id) {
        Favorite cur = favoriteRepository.findById(id).orElse(null);
        if(cur == null)return -1;
        favoriteRepository.deleteById(id);
        return 0;
    }

    @Override
    public List<FavoriteDTO> getByPage(Integer userId, Integer pageNum, Integer pageSize) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId))
                .with(Sort.by(Sort.Order.desc("createdAt")))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize);
        List<Favorite> favorites = mongoTemplate.find(query, Favorite.class);

        List<FavoriteDTO> res = new ArrayList<>();
        for(Favorite f : favorites){
            FavoriteDTO dto = MapObjectUtil.favoriteToDTO(f);

            String objType = f.getObjectType();
            String objId = f.getObjectId();
            FavoriteInfo info = null;

//            根据objType,objId 查询完整信息
            if (objType.equals("affair")){
                info = mongoTemplate.findById(objId, Affair.class);

            }else if(objType.equals("affairNode")){
                info = mongoTemplate.findById(objId, AffairNode.class);

            }else if(objType.equals("item")){
//            TODO 设置item

            }

            dto.setInfo(info);

            res.add(dto);
        }

        return res;
    }




    @Override
    public boolean judgeFavored(Integer userId, String objectType, String objectId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId)
                .and("objectType").is(objectType)
                .and("objectId").is(objectId));

        return mongoTemplate.exists(query, Favorite.class);
    }

    @Override
    public long getTotalPageCount(Integer userId, Integer pageNum, Integer pageSize) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));

        return mongoTemplate.count(query, Favorite.class);
    }


}
