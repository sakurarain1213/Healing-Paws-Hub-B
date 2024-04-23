package com.example.hou.util;

import com.example.hou.entity.Favorite;
import com.example.hou.entity.FavoriteDTO;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * pre：edges.size == 2
     * @param edges
     * @return
     */
    public static List<String[]> convertEdges(List<String[]> edges){
        int pairLen1 = edges.get(0).length;
        int pairLen2 = edges.get(1).length;

        if (pairLen1 == 1 && pairLen2 == 1){
            String x = edges.get(0)[0];
            String y = edges.get(1)[0];

            edges = new ArrayList<>();
            String[] newpair = {x, y};
            edges.add(newpair);
        }

        return edges;
    }
}
