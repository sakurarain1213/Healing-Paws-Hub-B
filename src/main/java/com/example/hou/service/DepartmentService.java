package com.example.hou.service;

import com.example.hou.entity.Department;
import org.springframework.data.domain.Page;

import java.util.List;


//todo   需要  添加部门连接属性   在删除部门的时候删除连接属性（查询开销大）

public interface DepartmentService {
    Department createDepartment(Department req);

    String updateDepartmentById(Department req);

    void deleteDepartmentById(String id);

    Department getDepartmentById(String id);

    List<Department>  getDepartments();

    Page<Department> getDepartmentByPage(Integer pageNum, Integer pageSize);

    List<Department> getDepartmentByCombinedName(Integer pageNum, Integer pageSize, String searchName);

    // 可以按需再加   todo  包括改staff  改connect
}