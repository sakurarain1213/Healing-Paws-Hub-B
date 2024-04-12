package com.example.hou.service;

import com.example.hou.entity.AffairRecord;

import java.util.List;

public interface AffairRecordService {

    AffairRecord createAffairRecord(String affairId, Integer userId);

    List<AffairRecord> getByLateSortedPage(Integer pageNum, Integer pageSize);

    int deleteById(String id);

    long getByLateSortedPageCount(Integer pageNum, Integer pageSize);
}
