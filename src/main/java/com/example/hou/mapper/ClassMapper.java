package com.example.hou.mapper;

import com.example.hou.entity.Class;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository//注解
@Mapper
public interface ClassMapper extends BaseMapper<Class> {


    @Select("SELECT MAX(id) FROM class")
    Integer selectMaxId();


}
