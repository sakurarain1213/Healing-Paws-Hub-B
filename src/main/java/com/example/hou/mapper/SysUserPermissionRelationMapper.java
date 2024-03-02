package com.example.hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hou.entity.SysUserPermissionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface SysUserPermissionRelationMapper extends BaseMapper<SysUserPermissionRelation> {

}