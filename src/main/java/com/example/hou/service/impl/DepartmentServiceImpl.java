package com.example.hou.service.impl;

import com.example.hou.entity.Department;
import com.example.hou.mapper.DepartmentRepository;
import com.example.hou.service.DepartmentService;
import com.mongodb.connection.ConnectionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author:                todo  整个类方法需要修改
 * @create: 2024-03-29 10:49
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public String updateDepartmentById(Department department) {
        return departmentRepository.save(department).getId();
    }

    @Override
    public void deleteDepartmentById(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Department> getDepartmentByPage(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return departmentRepository.findAll(pageable);
    }

    @Override
    public List<Department> getDepartmentByCombinedName(Integer pageNum, Integer pageSize, String searchName) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Criteria criteria = new Criteria("name").regex(searchName, "i"); // 使用正则表达式进行模糊查询，不区分大小写
        Query query = new Query(criteria).with(pageable);
        return mongoTemplate.find(query, Department.class);
    }

    // TODO: 根据需求添加修改员工和连接等功能的实现
    // 例如，修改员工可以是一个方法，它接收部门ID和员工列表，然后更新对应部门的员工
    // 修改连接可能涉及到与其他系统或服务的交互，具体实现会依赖于这些系统的API或协议

    // 示例：修改部门下的员工列表
    public void updateDepartmentStaff(String departmentId, List<Department.Staff> newStaffList) {
        // 获取部门实体
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            // 更新员工列表，这里假设Department实体有一个员工列表的属性
            //department.setStaffList(newStaffList);
            // 保存更新后的部门
            departmentRepository.save(department);
        } else {
            // 处理部门不存在的逻辑
        }
    }

    // 示例：修改部门连接信息（这只是一个示例，实际实现会依赖于你的业务逻辑）
    public void updateDepartmentConnection(String departmentId, ConnectionId newConnection) {
        // 获取部门实体
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            // 更新连接信息，这里假设Department实体有一个连接信息的属性
            //department.setConnectionID(newConnection);
            // 保存更新后的部门
            departmentRepository.save(department);
        } else {
            // 处理部门不存在的逻辑
        }
    }

    // 其他自定义方法实现
    // ...
}