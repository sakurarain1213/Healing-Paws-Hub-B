package com.example.hou.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor


/*
重大debug：  登录时候获得的token在其它活动接口中无效：
Could not read JSON: Unrecognized field “enabled”，
在json序列化时，不仅是根据get方法来序列化的，而是实体类中所有的有返回值的方法都会将返回的值序列化，
但是反序列化时是根据set方法来实现的，
所以当实体类中有非get，set方法的方法有返回值时，反序列化时就会出错

解决方法一：
实体类中只放get，set方法或返回值为空的方法。
解决方法二：
必须要有有返回值的其他方法，例如在整合security时自定义登录认证和权限认证时需要继承UserDetail，
就必须有其他方法，这时候可以在  RedisConfig   的  RedisTemplate的设置里加
om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

*/



//我们自定义一个用户信息，实现security 自带的  UserDetails
public class LogUser implements UserDetails {

    //用户信息
    private SysUser user;

    //用户权限
    private List<String> permissions;

    //存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LogUser(SysUser user,List<String> permissions){

        this.user = user;
        this.permissions = permissions;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将权限信息封装成 SimpleGrantedAuthority
        if (authorities != null) {
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = this.permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return authorities;

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNotExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNotExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
