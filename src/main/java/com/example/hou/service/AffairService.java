package com.example.hou.service;

import com.example.hou.entity.Affair;
import com.example.hou.entity.AffairNode;
import com.example.hou.entity.LogUser;
import com.example.hou.result.Result;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AffairService {

    Affair createAffair(Affair affair);

    Affair getById(String id);

    int deleteById(String id);

    long updateById(Affair affair);

    Page<Affair> getByPage(Integer pageNum, Integer pageSize);

    List<AffairNode> getAllNodesByAffairid(String affairId);

    List<Affair> getRecommendAffairs(LogUser user, Integer count);

    List<Affair> getFuzzyMatchedAffairs(String input, Integer pageNum, Integer pageSize);

    Result addNodeToAffair(String affairId, String nodeId);

    boolean validateAffairs(List<String> affairs);

}
