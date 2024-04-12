package com.example.hou.service.impl;

import com.example.hou.entity.Affair;
import com.example.hou.entity.AffairNode;
import com.example.hou.entity.AffairRecord;
import com.example.hou.entity.AffairDTO;
import com.example.hou.mapper.AffairNodeRepository;
import com.example.hou.mapper.AffairRecordRepository;
import com.example.hou.mapper.AffairRepository;
import com.example.hou.service.AffairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AffairRecordServiceImpl implements AffairRecordService {
    @Autowired
    private AffairRecordRepository affairRecordRepository;

    @Autowired
    private AffairRepository affairRepository;

    @Autowired
    private AffairNodeRepository affairNodeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AffairRecord createAffairRecord(String affairId, Integer userId) {
        Affair affair = affairRepository.findById(affairId).orElse(null);
        if (affair == null)return null;

        List<AffairNode> nodes = (List<AffairNode>) affairNodeRepository.findAllById(affair.getAffairs());
        AffairDTO affairDTO = new AffairDTO().setId(affair.getId())
                .setName(affair.getName())
                .setDescription(affair.getDescription())
                .setRole(affair.getRole())
                .setAffairs(nodes);

        AffairRecord affairRecord = new AffairRecord();

        affairRecord.setAffairInfo(affairDTO)
                .setUserId(userId)
                .setFinishTime(new Date());

//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        System.out.println(formatter.format());

        return affairRecordRepository.insert(affairRecord);
    }

    @Override
    public List<AffairRecord> getByLateSortedPage(Integer pageNum, Integer pageSize) {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("finishTime")))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize);


        return mongoTemplate.find(query, AffairRecord.class);
    }

    @Override
    public int deleteById(String id) {
        AffairRecord cur = affairRecordRepository.findById(id).orElse(null);
        if(cur == null)return -1;
        affairRecordRepository.deleteById(id);
        return 0;
    }

    @Override
    public long getByLateSortedPageCount(Integer pageNum, Integer pageSize) {
        Query query = new Query();
//        query.with(Sort.by(Sort.Order.desc("finishTime")))
//                .skip((pageNum - 1) * pageSize)
//                .limit(pageSize);

        long res = mongoTemplate.count(query, AffairRecord.class);
        System.out.println("res: "+res);
        return res;
//        return mongoTemplate.count(query, AffairRecord.class);
    }
}
