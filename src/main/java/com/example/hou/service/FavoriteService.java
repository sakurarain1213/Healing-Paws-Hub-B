package com.example.hou.service;

import com.example.hou.entity.Favorite;
import com.example.hou.entity.FavoriteDTO;
import com.example.hou.result.Result;

import java.util.List;

public interface FavoriteService {
    Result createFavorite(Favorite createVo);

    int deleteById(String id);

    List<FavoriteDTO> getByPage(Integer userId, Integer pageNum, Integer pageSize);

    boolean judgeFavored(Integer userId, String objectType, String objectId);

    long getTotalPageCount(Integer userId, Integer pageNum, Integer pageSize);
}
