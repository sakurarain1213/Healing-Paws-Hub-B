package com.example.hou.controller;

import com.example.hou.entity.Case;
import com.example.hou.handler.*;
import com.example.hou.result.Result;
import com.example.hou.service.CaseService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/case")
public class CaseController {
    @Autowired
    private CaseService caseService;

//    @PostMapping
//    public Result createCase(@RequestBody Case req){
//        if (req.getName() == null || req.getType() == null)return ResultUtil.error(0);
////        Integer num = caseService.createCase(req);
////        System.out.println(num);
////        if (num == 0)return ResultUtil.error(num);
////        return ResultUtil.success(num);
//        Case created = caseService.createCase(req);
//        if(created == null)return ResultUtil.error(null);
//        return ResultUtil.success(created);
//    }

    @PostMapping
    public Result createCase(
            @NonNull @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "descriptionImg", required = false) MultipartFile descriptionImg,
            @RequestParam(value = "descriptionVideo", required = false) MultipartFile descriptionVideo,
            @RequestParam(value = "checkItem", required = false) String checkItem,
            @RequestParam(value = "checkItemImg", required = false) MultipartFile checkItemImg,
            @RequestParam(value = "checkItemVideo", required = false) MultipartFile checkItemVideo,
            @RequestParam(value = "diagnosis", required = false) String diagnosis,
            @RequestParam(value = "diagnosisImg", required = false) MultipartFile diagnosisImg,
            @RequestParam(value = "diagnosisVideo", required = false) MultipartFile diagnosisVideo,
            @RequestParam(value = "remedy", required = false) String remedy,
            @RequestParam(value = "remedyImg", required = false) MultipartFile remedyImg,
            @RequestParam(value = "remedyVideo", required = false) MultipartFile remedyVideo,
            @NonNull @RequestParam(value = "type") List<String> types) {

        if(StringUtils.isBlank(name))return ResultUtil.error("name不能是空串或只有空格");
        System.out.println("name: " + name);
        System.out.println("description: " + description);

        for(String s : types) System.out.println(s);
        System.out.println("==============");

        Case cur = new Case();
        cur.setName(name)
                .setDescription(description)
                .setCheckItem(checkItem)
                .setDiagnosis(diagnosis)
                .setRemedy(remedy)
                .setType(types);

//        上传文件，并设置case的文件路径字段
        List<CaseFileHandler> handlers = new ArrayList<>();
        if(descriptionImg != null)handlers.add(CaseDescImgHandler.getInstance(descriptionImg, cur));
        if(descriptionVideo != null)handlers.add(CaseDescVdoHandler.getInstance(descriptionVideo, cur));

        if(checkItemImg != null)handlers.add(CaseCheckImgHandler.getInstance(checkItemImg, cur));
        if(checkItemVideo != null)handlers.add(CaseCheckVdoHandler.getInstance(checkItemVideo, cur));

        if(diagnosisImg != null)handlers.add(CaseDiagImgHandler.getInstance(diagnosisImg, cur));
        if(diagnosisVideo != null)handlers.add(CaseDiagVdoHandler.getInstance(diagnosisVideo, cur));

        if(remedyImg != null)handlers.add(CaseRemedyImgHandler.getInstance(remedyImg, cur));
        if(remedyVideo != null)handlers.add(CaseRemedyVdoHandler.getInstance(remedyVideo, cur));

        for(CaseFileHandler handler : handlers){
            handler.handleFile();
        }


        System.out.println("process ok");
        System.out.println(cur);

//        将所有字段set的case添加到数据库
        Case created = caseService.createCase(cur);
        if(created == null)return ResultUtil.error(null);
        return ResultUtil.success(created);

//        List<MultipartFile> multFiles = new ArrayList<>();
//        if (descriptionImg != null)multFiles.add(descriptionImg);
//        if (descriptionVideo != null)multFiles.add(descriptionVideo);
//
//        String prefix = System.getProperty("user.dir") + System.getProperty("file.separator") +
//                "media" + System.getProperty("file.separator");
//        for(MultipartFile f : multFiles){
//            String dst = prefix + f.getOriginalFilename();
//            System.out.println("dst: " + dst);

//            try{
//                File dest = new File(dst);
//                System.out.println(dest.getParentFile());
//
//                if(!dest.getParentFile().exists()){
//                    System.out.println("make file");
//                    dest.getParentFile().mkdirs();
//                }
//                f.transferTo(dest);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

//        return ResultUtil.success();
    }

    @PutMapping
    public Result updateCaseById(@NonNull @RequestParam("id") String id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam(value = "descriptionImg", required = false) MultipartFile descriptionImg,
                                 @RequestParam(value = "descriptionVideo", required = false) MultipartFile descriptionVideo,
                                 @RequestParam(value = "checkItem", required = false) String checkItem,
                                 @RequestParam(value = "checkItemImg", required = false) MultipartFile checkItemImg,
                                 @RequestParam(value = "checkItemVideo", required = false) MultipartFile checkItemVideo,
                                 @RequestParam(value = "diagnosis", required = false) String diagnosis,
                                 @RequestParam(value = "diagnosisImg", required = false) MultipartFile diagnosisImg,
                                 @RequestParam(value = "diagnosisVideo", required = false) MultipartFile diagnosisVideo,
                                 @RequestParam(value = "remedy", required = false) String remedy,
                                 @RequestParam(value = "remedyImg", required = false) MultipartFile remedyImg,
                                 @RequestParam(value = "remedyVideo", required = false) MultipartFile remedyVideo,
                                 @RequestParam(value = "type", required = false) List<String> types){
        if(StringUtils.isBlank(id))return ResultUtil.error("id不能是空串或只有空格");
        System.out.println("id: " + id);
        System.out.println("==============");

        Case cur = new Case();
        cur.setId(id)
                .setName(name)
                .setDescription(description)
                .setCheckItem(checkItem)
                .setDiagnosis(diagnosis)
                .setRemedy(remedy)
                .setType(types);

//        上传文件，并设置case的文件路径字段
        List<CaseFileHandler> handlers = new ArrayList<>();
        if(descriptionImg != null)handlers.add(CaseDescImgHandler.getInstance(descriptionImg, cur));
        if(descriptionVideo != null)handlers.add(CaseDescVdoHandler.getInstance(descriptionVideo, cur));

        if(checkItemImg != null)handlers.add(CaseCheckImgHandler.getInstance(checkItemImg, cur));
        if(checkItemVideo != null)handlers.add(CaseCheckVdoHandler.getInstance(checkItemVideo, cur));

        if(diagnosisImg != null)handlers.add(CaseDiagImgHandler.getInstance(diagnosisImg, cur));
        if(diagnosisVideo != null)handlers.add(CaseDiagVdoHandler.getInstance(diagnosisVideo, cur));

        if(remedyImg != null)handlers.add(CaseRemedyImgHandler.getInstance(remedyImg, cur));
        if(remedyVideo != null)handlers.add(CaseRemedyVdoHandler.getInstance(remedyVideo, cur));

        for(CaseFileHandler handler : handlers){
            handler.handleFile();
        }

        System.out.println("process ok");
        System.out.println(cur);

//        数据库更新
        Long res = caseService.updateCaseById(cur);
        if (res == null || res == 0)return ResultUtil.error(null);
        else return ResultUtil.success(res);
//        System.out.println(num);
//        if (num == 0)return ResultUtil.error(num);
//        if(updated == null)return ResultUtil.error(null);
//        return ResultUtil.success(updated);
    }

    @DeleteMapping
    public Result deleteCaseById(@NonNull @RequestParam("id") String id){
        if(StringUtils.isBlank(id))return ResultUtil.error("id不能是空串或只有空格");
        caseService.deleteCaseById(id);
        return ResultUtil.success();
    }

    @GetMapping
    public Result getCaseById(@NonNull @RequestParam("id") String id){
        if(StringUtils.isBlank(id))return ResultUtil.error("id不能是空串或只有空格");

        Case res = caseService.getCaseById(id);
        System.out.println(res);
        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @GetMapping("/page")
    public Result getCaseByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                                @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Page<Case> res = caseService.getCaseByPage(pageNum, pageSize);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }

    @GetMapping("/group")
    public Result getCaseByCombinedName(@NonNull @RequestParam("pageNum") Integer pageNum,
                                        @NonNull @RequestParam("pageSize") Integer pageSize,
                                        @NonNull @RequestParam("diseases") String diseases){
        if(pageNum < 1 || pageSize < 1 || StringUtils.isBlank(diseases))return ResultUtil.error("请求参数不合法");

        List<Case> res = caseService.getCaseByCombinedName(pageNum, pageSize, diseases);
        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }



}
