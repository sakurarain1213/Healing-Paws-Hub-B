package com.example.hou.controller;

import com.example.hou.entity.AffairNode;
import com.example.hou.entity.Department;
import com.example.hou.entity.Item;
import com.example.hou.entity.PageSupport;
import com.example.hou.mapper.DepartmentRepository;
import com.example.hou.result.Result;
import com.example.hou.service.DepartmentService;
import com.example.hou.service.ItemService;
import com.example.hou.util.FileUtil;
import com.example.hou.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者      todo  需要根据token验证管理员权限才能改   删除需要删除关联的连接关系
 * @create: 2024-04-06 11:56
 */
@RestController
@RequestMapping("/department")
@Validated
//校验方面  请求体的校验 通常用框架自带的 @Valid 或 @Validated 注解  相似，但 @Validated 提供了更多的灵活性，比如支持分组校验
//  notnull是第三方库的校验功能
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private DepartmentRepository departmentRepository;

    // 创建部门
    @PostMapping
    public Result createDepartment(@Validated @RequestPart Department department, @RequestPart(value = "pic", required = false) MultipartFile pic) {
        //考虑直接在控制层判断文件
        String url = "";//默认物品文件
        if (pic != null && !pic.isEmpty()) {
            url= FileUtil.fileUpload(pic);
        }
        department.setPic(url);
        Department createdDepartment = departmentService.createDepartment(department);
        // 框架默认的返回体
        //return ResponseEntity.created(URI.create("/department/" + createdDepartment.getId())).body(createdDepartment);
        if(createdDepartment==null) return new Result(-100,"error", "DepartmentName参数必要,或文件传输失败");
        else return new Result(200,"success",createdDepartment);
    }


    // 更新部门（通过ID）    文件也要改
    @PutMapping
    public Result updateDepartmentById(@Validated @RequestPart Department department, @RequestPart(value = "pic", required = false) MultipartFile pic) {
        try {
            // 设置ID，确保ID被正确设置
            if(department.getId()==null ||department.getId()=="") return new Result(-100, "error", "缺少id字段") ;


            //为了先得到数据库里的pic 的URL
            Optional<Department> existingDepartment = departmentRepository.findById(department.getId());
            // 如果找不到具有该id的department，返回错误
            if (!existingDepartment.isPresent()) {
                throw new IllegalArgumentException("Department with ID " + department.getId() + " does not exist.");
            }
            //文件部分
            String url = existingDepartment.get().getPic();//默认物品文件
            if (pic != null && !pic.isEmpty()) {
                url= FileUtil.fileUpload(pic);
            }
            department.setPic(url);


            // 调用服务层方法更新部门
            String updatedId = departmentService.updateDepartmentById(department);
            // 验证返回的ID是否与传入的ID匹配，并返回结果
            if (Objects.equals(updatedId, String.valueOf(department.getId()))) {
                return  new Result(200, "success", "更新成功") ;
            } else {
                // 如果不匹配，可以抛出异常或者返回错误结果
                return  new Result(-100, "error", "更新后的ID与传入的ID不匹配");
            }
        } catch (IllegalArgumentException e) {
            // 捕获IllegalArgumentException异常，并返回错误响应
            return new Result(-100, "error", e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常，并返回通用错误响应
            return new Result(-100, "error", "更新部门时发生错误") ;
        }

}

    // 删除部门（通过ID）
    @DeleteMapping("/{id}")
    public Result deleteDepartmentById(@PathVariable String id) {
        try {
            departmentService.deleteDepartmentById(id);
            return  new Result(200, "success", "删除成功") ;
        } catch (Exception e) {
            // 没用到
            return  new Result(-100, "error", "id不存在或数据库异常") ;
        }
}

    // 根据ID获取部门
    @GetMapping("/{id}")
    public Result getDepartmentById(@PathVariable String id) {
        Department department = departmentService.getDepartmentById(id);
        return new Result(200, "success", department) ;
    }

    //查询特定部门下的所有item
    @GetMapping("/getItem/{DepartmentId}")
    public Result getItemsInDepartment(@PathVariable String DepartmentId) {
        List<Item> items = itemService.searchItemByDepartment(DepartmentId);
        if(items==null || items.size()==0) return new Result(-100, "error", "部门内无物品或查询失败") ;
        return new Result(200, "success", items) ;
    }

    // 分页获取部门列表
    @GetMapping("/page")
    public Result getDepartmentByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Department> departments = departmentService.getDepartmentByPage(pageNum, pageSize);
        //System.out.println(departments);
        //封装一个分页标准返回
        PageSupport<Department> respPage = new PageSupport<>();
        respPage.setListData(departments.getContent())
                .setTotalPages(departments.getTotalPages());

        return ResultUtil.success(respPage);
    }

    // 根据部门名称组合查询部门（这里需要定义具体的查询逻辑）
    @GetMapping("/search")
    public Result getDepartmentByCombinedName(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                                                        @RequestParam("name") String name) {
        List<Department> departments = departmentService.getDepartmentByCombinedName(pageNum, pageSize,name); // 假设服务层有这个方法
        return ResultUtil.success(departments);
    }
}
