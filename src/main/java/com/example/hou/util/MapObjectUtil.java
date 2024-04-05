package com.example.hou.util;

import com.example.hou.entity.Favorite;
import com.example.hou.entity.FavoriteDTO;

public class MapObjectUtil {
    public static FavoriteDTO favoriteToDTO(Favorite favorite){
        FavoriteDTO res = new FavoriteDTO();
        res.setId(favorite.getId())
                .setUserId(favorite.getUserId())
                .setObjectId(favorite.getObjectId())
                .setObjectType(favorite.getObjectType())
                .setCreatedAt(favorite.getCreatedAt());
        return res;
    }
}
