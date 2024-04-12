package com.example.hou.service.impl;

import com.example.hou.entity.Case;
import com.example.hou.mapper.CaseRepository;
import com.example.hou.service.CaseService;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private MongoTemplate template;

    @Override
    public Case createCase(Case req) {
        Case saved = caseRepository.insert(req);
        return saved;
//        if (saved == null || StringUtils.isBlank(saved.getId()))return 0;
//        else return 1;
//        return saved == null ? 0 : 1;
    }

    @Override
    public Long updateCaseById(Case req) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(req.getId());
        query.addCriteria(criteria);

        Update upt = new Update();
        if(req.getName() != null){
//            System.out.println(req.getName());
            upt.set("name", req.getName());
        }
        if(req.getDescription() != null){
//            System.out.println(req.getDescription());
            upt.set("description", req.getDescription());
        }
        if(req.getDescriptionImg() != null)upt.set("descriptionImg", req.getDescriptionImg());
        if(req.getDescriptionVideo() != null)upt.set("descriptionVideo", req.getDescriptionVideo());

        if(req.getCheckItem() != null)upt.set("checkItem", req.getCheckItem());
        if(req.getCheckItemImg() != null)upt.set("checkItemImg", req.getCheckItemImg());
        if(req.getCheckItemVideo() != null)upt.set("checkItemVideo", req.getCheckItemVideo());

        if(req.getDiagnosis() != null)upt.set("diagnosis", req.getDiagnosis());
        if(req.getDiagnosisImg() != null)upt.set("diagnosisImg", req.getDiagnosisImg());
        if(req.getDiagnosisVideo() != null)upt.set("diagnosisVideo", req.getDiagnosisVideo());

        if(req.getRemedy() != null)upt.set("remedy", req.getRemedy());
        if(req.getRemedyImg() != null)upt.set("remedyImg", req.getRemedyImg());
        if(req.getRemedyVideo() != null)upt.set("remedyVideo", req.getRemedyVideo());

        if(req.getType() != null)upt.set("type", req.getType());

        if (req.getMdText() != null)upt.set("mdText", req.getMdText());

//        修改图片size list
        Optional.ofNullable(req.getDescImgSize()).ifPresent(c -> upt.set("descImgSize", c));
        Optional.ofNullable(req.getCheckImgSize()).ifPresent(c -> upt.set("checkImgSize", c));
        Optional.ofNullable(req.getDiagImgSize()).ifPresent(c -> upt.set("diagImgSize", c));
        Optional.ofNullable(req.getRemedyImgSize()).ifPresent(c -> upt.set("remedyImgSize", c));

        UpdateResult updateResult = template.updateFirst(query, upt, Case.class);
        // 是否执行成功
//        System.out.println(updateResult.wasAcknowledged());
        // 匹配到的数量
//        System.out.println(updateResult.getMatchedCount());
        // 更新数量
        System.out.println(updateResult.getModifiedCount());

        return updateResult.getModifiedCount();

//        Case saved = caseRepository.save(req);
//        if (saved == null)return 0;
//        else return 1;
    }

    @Override
    public int deleteCaseById(String id) {
        Case cur = caseRepository.findById(id).orElse(null);
        if(cur == null)return -1;
        caseRepository.deleteById(id);
        return 0;
    }

    @Override
    public Case getCaseById(String id) {
        Optional<Case> res = caseRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public Page<Case> getCaseByPage(Integer pageNum, Integer pageSize) {
        Page<Case> page = caseRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        return page;
    }

    /**
     * 使用mongo特性：文本索引检索
     * @param pageNum
     * @param pageSize
     * @param diseases
     * @return
     */
    @Override
    public List<Case> getCaseByCombinedName(Integer pageNum, Integer pageSize, String diseases) {
        // 构建查询条件
        Query query = new Query();

//        精确检索
        TextCriteria matching = TextCriteria.forDefaultLanguage().matching(diseases);
//         模糊检索
//        TextCriteria matching = TextCriteria.forDefaultLanguage().matchingAny(req);

        query.addCriteria(matching);
        query.skip((pageNum - 1) * pageSize).limit(pageSize);

        List<Case> cases = template.find(query, Case.class);
        cases.stream().forEach(System.out::println);

        return cases;
    }

    @Override
    public long getCaseByCombinedNameCount(Integer pageNum, Integer pageSize, String diseases) {
        Query query = new Query();

        TextCriteria matching = TextCriteria.forDefaultLanguage().matching(diseases);
        query.addCriteria(matching);

        return template.count(query, Case.class);
    }
}
