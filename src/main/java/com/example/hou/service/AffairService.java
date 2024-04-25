package com.example.hou.service;

import com.example.hou.entity.*;
import com.example.hou.result.Result;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AffairService {

    Affair createAffair(Affair affair);

    Affair getById(String id);

    int deleteById(String id);

    long updateById(Affair affair);

    Page<AffairAndFavoriteDTO> getByPage(Integer pageNum, Integer pageSize);

    NodeFlowDia getGraphByAffairid(String affairId);

    List<Affair> getRecommendAffairs(LogUser user, Integer count);

    List<Affair> getFuzzyMatchedAffairs(String input, Integer pageNum, Integer pageSize);

    Result addNodeToAffair(String affairId, String nodeId);

    boolean validateAffairs(List<String> affairs);

    boolean validateEdges(List<String[]> edges, List<String> affairs);

}
