package com.example.hou.util;

import com.example.hou.entity.LogUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;


//自定义security权限校验方法

//返回格式问题
@Component("syex")
public class SecurityPermissionsExpression {
    public boolean hasAuthority(String authority){


        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LogUser loginUser = (LogUser) authentication.getPrincipal();
        //getPrincipal这个方法不可靠
        //System.out.println(loginUser);
        //重大debug，loginUser的 permissions=[] authorities=[] 会找不到 权限
        //这边根据数据库临时补写join查询
        List<String> permissions = loginUser.getPermissions();
        // List permissions  已经保存mysql里特定用户的权限名的全体 可以任意使用hasAuthority方法
        //System.out.println( permissions);
        //System.out.println("-----------------------------");
        //判断用户权限集合中是否存在authority
        return permissions.contains(authority);


        /*
                // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            //System.out.println( "noooooooooooooo");
            return false;
        }
        System.out.println( authentication+"noooooooooooooo");
        // 检查是否具有指定的权限
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        */
    }

}

