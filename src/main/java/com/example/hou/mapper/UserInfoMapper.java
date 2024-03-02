package com.example.hou.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hou.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
//@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")


public interface UserInfoMapper extends BaseMapper<UserInfo> {

   // @Select("select * from user_info where username = #{un}")
    UserInfo searchByUsername(String un);

/*  注意 用mybatis plus的最大不同是可以自动生成CRUD   但是要extend
    UserInfo getUserInfo(int id);

   int save(UserInfo userInfo);

     int update(UserInfo userInfo);

    int deleteById(int id);

    static List<UserInfo> selectAll() {
        return null;
    }
*/



}
