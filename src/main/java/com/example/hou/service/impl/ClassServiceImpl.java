package com.example.hou.service.impl;

import com.example.hou.mapper.ClassMapper;
import com.example.hou.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: juanBao
 * @description:
 * @author: 作者
 * @create: 2024-01-05 05:36
 */

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classMapper;

    public int maxClassID() {
            Integer maxId = classMapper.selectMaxId();
            return maxId != null ? maxId : 0; // 如果没有记录，返回0
    }

}
