package com.example.hou.controller;

import com.example.hou.entity.AffairNode;
import com.example.hou.entity.AffairNodeCreateVo;
import com.example.hou.entity.AffairNodeUpdateVo;
import com.example.hou.entity.PageSupport;
import com.example.hou.handler.AffairNodeImgHandler;
import com.example.hou.handler.AffairNodeVdoHandler;
import com.example.hou.handler.FileHandler;
import com.example.hou.result.Result;
import com.example.hou.service.AffairNodeService;
import com.example.hou.util.FileUtil;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@RestController
@RequestMapping("/affairnode")
@Validated
public class AffairNodeController {
    @Autowired
    private AffairNodeService affairNodeService;

    @PostMapping
    public Result createAffairNode(@NonNull @ModelAttribute @Valid AffairNodeCreateVo createVo){
        AffairNode affairNode = new AffairNode();
        affairNode.setContent(createVo.getContent());
        affairNode.setName(createVo.getName());
        affairNode.setPositionX(createVo.getPositionX());
        affairNode.setPositionY(createVo.getPositionY());

        List<FileHandler<AffairNode>> handlers = new ArrayList<>();
        if (createVo.getContentImg() != null)handlers.add(new AffairNodeImgHandler(createVo.getContentImg(), affairNode));
        if (createVo.getContentVideo() != null)handlers.add(new AffairNodeVdoHandler(createVo.getContentVideo(), affairNode));

        for (FileHandler<AffairNode> handler : handlers){
            handler.handleFile();
        }
        System.out.println(affairNode);

        AffairNode created = affairNodeService.createAffairNode(affairNode);
        System.out.println(created.getId());
        if (created.getId() == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @DeleteMapping
    public Result deleteById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                             @RequestParam("id") String id){
        try {
            int flag = affairNodeService.deleteById(id);
            if(flag < 0)return ResultUtil.error("id不存在");
            System.out.println("delete:"+id);
            return ResultUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除异常");
        }
    }

    @PutMapping
    public Result updateById(@NonNull @ModelAttribute @Valid AffairNodeUpdateVo updateVo){
        AffairNode affairNode = new AffairNode();

        affairNode.setId(updateVo.getId())
                .setContent(updateVo.getContent())
                .setName(updateVo.getName())
                .setPositionX(updateVo.getPositionX())
                .setPositionY(updateVo.getPositionY());

        List<FileHandler<AffairNode>> handlers = new ArrayList<>();
        if (updateVo.getContentImg() != null)handlers.add(new AffairNodeImgHandler(updateVo.getContentImg(), affairNode));
        if (updateVo.getContentVideo() != null)handlers.add(new AffairNodeVdoHandler(updateVo.getContentVideo(), affairNode));

        for (FileHandler<AffairNode> handler : handlers){
            handler.handleFile();
        }
        System.out.println(affairNode);

        if (affairNode.nullFieldsExceptId())return ResultUtil.error("未填写任何需要更新的信息");

        long res = affairNodeService.updateById(affairNode);
        if (res <= 0)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }


    @GetMapping
    public Result getById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                          @RequestParam("id") String id){
        AffairNode res = affairNodeService.getById(id);
        System.out.println(res);
        if (res == null)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @GetMapping("/page")
    public Result getByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                            @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Page<AffairNode> page = affairNodeService.getByPage(pageNum, pageSize);
        if(page == null)return ResultUtil.error(null);
        System.out.println(page.getContent());

        PageSupport<AffairNode> respPage = new PageSupport<>();
        respPage.setListData(page.getContent())
                .setTotalPages(page.getTotalPages());

        return ResultUtil.success(respPage);

//        return ResultUtil.success(page.getContent());
    }

    /**
     * 上传图片文件
     */
    @PostMapping("/upload")
    public Result uploadMultipartFile(@NonNull @RequestParam("upload") MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1){
            return ResultUtil.error("文件不合法");
        }

       try {
           String url = FileUtil.fileUpload(file);
           return ResultUtil.success(url);
       }catch (Exception e){
           e.printStackTrace();
           return ResultUtil.error("文件上传失败");
       }

    }




}
