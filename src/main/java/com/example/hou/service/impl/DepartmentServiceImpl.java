package com.example.hou.service.impl;

import com.example.hou.entity.Case;
import com.example.hou.entity.Department;
import com.example.hou.entity.Position;
import com.example.hou.entity.Staff;
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
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author:
 * @create: 2024-03-29 10:49
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //ok
    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public String updateDepartmentById(Department department) {
        // 检查department是否包含id属性
        if (department.getId() == null) {
            throw new IllegalArgumentException("Department ID must not be null.");
        }

        // 尝试从数据库中获取具有相同id的department
        Optional<Department> existingDepartment = departmentRepository.findById(department.getId());

        // 如果找不到具有该id的department，返回错误
        if (!existingDepartment.isPresent()) {
            throw new IllegalArgumentException("Department with ID " + department.getId() + " does not exist.");
        }

        // 如果找到了，就更新department并保存   注意需求是不覆盖原有内容
        Department exist=existingDepartment.get();
        //URL优先覆盖
        exist.setPic(department.getPic());


        if (StringUtils.hasText(department.getDepartmentName()) ) {
            exist.setDepartmentName(department.getDepartmentName());
        }
        if (StringUtils.hasText(department.getIntroduction()) ) {
            exist.setIntroduction(department.getIntroduction());
        }

        Position pos=department.getPosition();
        if (pos != null) {
            exist.setPosition(pos);
        }

        //列表元素去重判断   注意类型匹配问题的报错
       // Set<String> newConnectIDSet = new HashSet<>(department.getConnectID());
      //  Set<String> existingConnectIDSet = new HashSet<>(exist.getConnectID());
        if (department.getConnectID()!=null && department.getConnectID().size()>0) {
            exist.setConnectID(department.getConnectID());
        }

        if (department.getStaffList()!=null && department.getStaffList().size()>0) {
            exist.setStaffList(department.getStaffList());
        }

        //save是直接覆盖
        Department updatedDepartment = departmentRepository.save(exist);

        // 返回更新后的department的id
        return updatedDepartment.getId();
    }

    @Override
    public void deleteDepartmentById(String id) {

        // 尝试从数据库中获取具有相同id的department
        Optional<Department> existingDepartment = departmentRepository.findById(id);

        // 如果找不到具有该id的department，返回错误
        if (!existingDepartment.isPresent()) {
            throw new IllegalArgumentException("Department with ID " + id + " does not exist.");
        }

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
    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> getDepartmentByCombinedName(Integer pageNum, Integer pageSize, String searchName) {
        // 使用正则表达式进行模糊查询
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(searchName) + ".*", Pattern.CASE_INSENSITIVE);

        // 创建对departmentName和introduction字段的模糊匹配条件
        Criteria departmentNameCriteria = Criteria.where("departmentName").regex(pattern);
        Criteria introductionCriteria = Criteria.where("introduction").regex(pattern);
        Criteria staffNameCriteria = Criteria.where("staffList.name").regex(pattern);
        Criteria staffPositionCriteria = Criteria.where("staffList.position").regex(pattern);
        Criteria staffPhoneCriteria = Criteria.where("staffList.phone").regex(pattern);
        //还要别的字段可以直接新建 Criteria然后在下面一行的orOperator参数里塞即可。

        // 使用orOperator创建一个逻辑或条件
        Criteria orCriteria = new Criteria().orOperator(departmentNameCriteria,
                introductionCriteria,
                staffNameCriteria,
                staffPositionCriteria,
                staffPhoneCriteria

        );

        // 构建查询并添加分页逻辑
        Query query = new Query(orCriteria);
        query.skip((pageNum - 1) * pageSize).limit(pageSize);

        List<Department> departments = mongoTemplate.find(query, Department.class);
        //departments.stream().forEach(System.out::println);

        return departments;
    }


    // TODO: 根据需求添加修改员工和连接等功能的实现
    // 例如，修改员工可以是一个方法，它接收部门ID和员工列表，然后更新对应部门的员工
    // 修改连接可能涉及到与其他系统或服务的交互，具体实现会依赖于这些系统的API或协议

    //  修改部门的员工列表  并未使用
    public ResponseEntity<Department> updateDepartmentStaff(String departmentId, List<Staff> newStaffList) {
        // 检查部门ID和新的员工列表是否为空
        if (departmentId.trim().isEmpty() || newStaffList == null || newStaffList.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            // 获取部门实体
            Department department = getDepartmentById(departmentId);
            if (department != null) {
                // 更新员工列表
                department.setStaffList(newStaffList);
                // 保存更新后的部门
                Department updatedDepartment = departmentRepository.save(department);
                return ResponseEntity.ok(updatedDepartment);
            } else {
                // 部门不存在
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常，例如记录日志等
            // 根据实际情况决定是否需要抛出异常或者返回错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
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