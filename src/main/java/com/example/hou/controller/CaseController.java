package com.example.hou.controller;

import com.example.hou.entity.*;
import com.example.hou.handler.*;
import com.example.hou.result.Result;
import com.example.hou.service.CaseService;
import com.example.hou.service.DiseaseService;
import com.example.hou.util.FileUtil;
import com.example.hou.util.ResultUtil;
import com.example.hou.validator.CaseTypeCreateConstraint;
import com.example.hou.validator.CaseTypeUpdateConstraint;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/case")
@Validated
public class CaseController {
    @Autowired
    private CaseService caseService;

    @Autowired
    private DiseaseService diseaseService;

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

//    @PostMapping
//    public Result createCase(
//            @NotBlank @Size(max = 30, message = "name不合法") @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "name为中文、英文、数字、下划线组合")
//            @RequestParam("name") String name,
//            @Size(max = 200, message = "description不合法")
//            @RequestParam(value = "description", required = false) String description,
//            @RequestParam(value = "descriptionImg", required = false) MultipartFile descriptionImg,
//            @RequestParam(value = "descriptionVideo", required = false) MultipartFile descriptionVideo,
//            @Size(max = 200, message = "checkItem不合法")
//            @RequestParam(value = "checkItem", required = false) String checkItem,
//            @RequestParam(value = "checkItemImg", required = false) MultipartFile checkItemImg,
//            @RequestParam(value = "checkItemVideo", required = false) MultipartFile checkItemVideo,
//            @Size(max = 200, message = "diagnosis不合法")
//            @RequestParam(value = "diagnosis", required = false) String diagnosis,
//            @RequestParam(value = "diagnosisImg", required = false) MultipartFile diagnosisImg,
//            @RequestParam(value = "diagnosisVideo", required = false) MultipartFile diagnosisVideo,
//            @Size(max = 200, message = "remedy不合法")
//            @RequestParam(value = "remedy", required = false) String remedy,
//            @RequestParam(value = "remedyImg", required = false) MultipartFile remedyImg,
//            @RequestParam(value = "remedyVideo", required = false) MultipartFile remedyVideo,
//            @CaseTypeCreateConstraint @RequestParam(value = "type") List<String> types) {
//
//        System.out.println("name: " + name);
//        System.out.println("description: " + description);
//
//
////        检查type是否全部合法，若有一个不合法string就返回错误响应
//        for(String s : types){
//            System.out.println(s);
//            long existNum = diseaseService.existName(s);
//            System.out.println(existNum);
//            if(existNum <= 0)return ResultUtil.error("存在不合法病名");
//        }
//
//        System.out.println("==============");
//
//        Case cur = new Case();
//        cur.setName(name)
//                .setDescription(description)
//                .setCheckItem(checkItem)
//                .setDiagnosis(diagnosis)
//                .setRemedy(remedy)
//                .setType(types);
//
//
//
//        //以下是单个文件上传样例  使用file工具类的方法上传 要结合下面的存在检测逻辑
////        if(descriptionImg!=null){
////            String feedback= FileUtil.fileUpload(descriptionImg);
////            if (feedback!=null) {// 把文件路径存入数据库的对应位置
////                //cur.setDescriptionImg(feedback);   后续逻辑需要按照业务添加
////                System.out.println("测试上传成功: "+feedback);
////                return ResultUtil.success("临时文件上传成功 后续先不做");
////            }
////        }
//
//
//
//
//
////        上传文件，并设置case的文件路径字段
//        List<FileHandler<Case>> handlers = new ArrayList<>();
//        if(descriptionImg != null)handlers.add(new DescImgHandler(descriptionImg, cur));
//        if(descriptionVideo != null)handlers.add(new DescVdoHandler(descriptionVideo, cur));
//
//        if(checkItemImg != null)handlers.add(new CheckImgHandler(checkItemImg, cur));
//        if(checkItemVideo != null)handlers.add(new CheckVdoHandler(checkItemVideo, cur));
//
//        if(diagnosisImg != null)handlers.add(new DiagImgHandler(diagnosisImg, cur));
//        if(diagnosisVideo != null)handlers.add(new DiagVdoHandler(diagnosisVideo, cur));
//
//        if(remedyImg != null)handlers.add(new RemedyImgHandler(remedyImg, cur));
//        if(remedyVideo != null)handlers.add(new RemedyVdoHandler(remedyVideo, cur));
//
//        try {
//            for(FileHandler<Case> handler : handlers){
//                handler.handleFile();
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResultUtil.error("文件上传失败");
//        }
//
//        System.out.println("process ok");
//        System.out.println(cur);
//
////        将所有字段set的case添加到数据库
//        Case created = caseService.createCase(cur);
//        if(created == null)return ResultUtil.error(null);
//        return ResultUtil.success(created);
//
////        List<MultipartFile> multFiles = new ArrayList<>();
////        if (descriptionImg != null)multFiles.add(descriptionImg);
////        if (descriptionVideo != null)multFiles.add(descriptionVideo);
////
////        String prefix = System.getProperty("user.dir") + System.getProperty("file.separator") +
////                "media" + System.getProperty("file.separator");
////        for(MultipartFile f : multFiles){
////            String dst = prefix + f.getOriginalFilename();
////            System.out.println("dst: " + dst);
//
////            try{
////                File dest = new File(dst);
////                System.out.println(dest.getParentFile());
////
////                if(!dest.getParentFile().exists()){
////                    System.out.println("make file");
////                    dest.getParentFile().mkdirs();
////                }
////                f.transferTo(dest);
////
////            }catch (Exception e){
////                e.printStackTrace();
////            }
////        }
//
////        return ResultUtil.success();
//    }

//    formdata接收不能用requestbody，使用ModelAttribute
    @PostMapping
    public Result createCase(@NonNull @ModelAttribute @Valid CaseCreateVo createVo) {
        System.out.println("vo:" + createVo);

        String name = createVo.getName();
        String description = createVo.getDescription();
        MultipartFile descriptionImg = createVo.getDescriptionImg();
        MultipartFile descriptionVideo = createVo.getDescriptionVideo();

        String checkItem = createVo.getCheckItem();
        MultipartFile checkItemImg = createVo.getCheckItemImg();
        MultipartFile checkItemVideo = createVo.getCheckItemVideo();

        String diagnosis = createVo.getDiagnosis();
        MultipartFile diagnosisImg = createVo.getDiagnosisImg();
        MultipartFile diagnosisVideo = createVo.getDiagnosisVideo();

        String remedy = createVo.getRemedy();
        MultipartFile remedyImg = createVo.getRemedyImg();
        MultipartFile remedyVideo = createVo.getRemedyVideo();

        List<String> types = createVo.getType();

        System.out.println("name: " + name);
//        System.out.println("description: " + description);
//        System.out.println(types);

//        检查type是否全部合法，若有一个不合法string就返回错误响应
        for(String s : types){
            System.out.println(s);
            long existNum = diseaseService.existName(s);
//            System.out.println(existNum);
            if(existNum <= 0)return ResultUtil.error("存在不合法病名");
        }

//        if(descriptionImg != null) System.out.println(descriptionImg.getOriginalFilename());
//        if(descriptionVideo != null)System.out.println(descriptionVideo.getOriginalFilename());
//
//        if(checkItemImg != null)System.out.println(checkItemImg.getOriginalFilename());
//        if(checkItemVideo != null)System.out.println(checkItemVideo.getOriginalFilename());
//
//        if(diagnosisImg != null)System.out.println(diagnosisImg.getOriginalFilename());
//        if(diagnosisVideo != null)System.out.println(diagnosisVideo.getOriginalFilename());
//
//        if(remedyImg != null)System.out.println(remedyImg.getOriginalFilename());
//        if(remedyVideo != null)System.out.println(remedyVideo.getOriginalFilename());

        System.out.println("==============");

        Case cur = new Case();
        cur.setName(name)
                .setDescription(description)
                .setCheckItem(checkItem)
                .setDiagnosis(diagnosis)
                .setRemedy(remedy)
                .setType(types)
                .setMdText(createVo.getMdText());


//        上传文件，并设置case的文件路径字段
        List<FileHandler<Case>> handlers = new ArrayList<>();
        if(descriptionImg != null)handlers.add(new DescImgHandler(descriptionImg, cur));
        if(descriptionVideo != null)handlers.add(new DescVdoHandler(descriptionVideo, cur));

        if(checkItemImg != null)handlers.add(new CheckImgHandler(checkItemImg, cur));
        if(checkItemVideo != null)handlers.add(new CheckVdoHandler(checkItemVideo, cur));

        if(diagnosisImg != null)handlers.add(new DiagImgHandler(diagnosisImg, cur));
        if(diagnosisVideo != null)handlers.add(new DiagVdoHandler(diagnosisVideo, cur));

        if(remedyImg != null)handlers.add(new RemedyImgHandler(remedyImg, cur));
        if(remedyVideo != null)handlers.add(new RemedyVdoHandler(remedyVideo, cur));

        try {
            for(FileHandler<Case> handler : handlers){
                handler.handleFile();
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("文件上传失败");
        }

        System.out.println("process ok");
        System.out.println(cur);
//        return ResultUtil.success();

//        将所有字段set的case添加到数据库
        Case created = caseService.createCase(cur);
        if(created == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @PutMapping
    public Result updateCaseById(@NonNull @ModelAttribute @Valid CaseUpdateVo updateVo){
        System.out.println("vo:" + updateVo);

        String id = updateVo.getId();
        String name = updateVo.getName();
        String description = updateVo.getDescription();
        MultipartFile descriptionImg = updateVo.getDescriptionImg();
        MultipartFile descriptionVideo = updateVo.getDescriptionVideo();

        String checkItem = updateVo.getCheckItem();
        MultipartFile checkItemImg = updateVo.getCheckItemImg();
        MultipartFile checkItemVideo = updateVo.getCheckItemVideo();

        String diagnosis = updateVo.getDiagnosis();
        MultipartFile diagnosisImg = updateVo.getDiagnosisImg();
        MultipartFile diagnosisVideo = updateVo.getDiagnosisVideo();

        String remedy = updateVo.getRemedy();
        MultipartFile remedyImg = updateVo.getRemedyImg();
        MultipartFile remedyVideo = updateVo.getRemedyVideo();

        List<String> types = updateVo.getType();

        System.out.println("id: " + id);

//        if(descriptionImg != null) System.out.println(descriptionImg.getOriginalFilename());
//        if(descriptionVideo != null)System.out.println(descriptionVideo.getOriginalFilename());
//
//        if(checkItemImg != null)System.out.println(checkItemImg.getOriginalFilename());
//        if(checkItemVideo != null)System.out.println(checkItemVideo.getOriginalFilename());
//
//        if(diagnosisImg != null)System.out.println(diagnosisImg.getOriginalFilename());
//        if(diagnosisVideo != null)System.out.println(diagnosisVideo.getOriginalFilename());
//
//        if(remedyImg != null)System.out.println(remedyImg.getOriginalFilename());
//        if(remedyVideo != null)System.out.println(remedyVideo.getOriginalFilename());

        if (types != null){
            for(String s : types){
                System.out.println(s);
                long existNum = diseaseService.existName(s);
                if(existNum <= 0)return ResultUtil.error("存在不合法病名");
            }
        }
        System.out.println("==============");

        Case cur = new Case();
        cur.setId(id)
                .setName(name)
                .setDescription(description)
                .setCheckItem(checkItem)
                .setDiagnosis(diagnosis)
                .setRemedy(remedy)
                .setType(types)
                .setMdText(updateVo.getMdText());


//        上传文件，并设置case的文件路径字段
        List<FileHandler<Case>> handlers = new ArrayList<>();
        if(descriptionImg != null)handlers.add(new DescImgHandler(descriptionImg, cur));
        if(descriptionVideo != null)handlers.add(new DescVdoHandler(descriptionVideo, cur));

        if(checkItemImg != null)handlers.add(new CheckImgHandler(checkItemImg, cur));
        if(checkItemVideo != null)handlers.add(new CheckVdoHandler(checkItemVideo, cur));

        if(diagnosisImg != null)handlers.add(new DiagImgHandler(diagnosisImg, cur));
        if(diagnosisVideo != null)handlers.add(new DiagVdoHandler(diagnosisVideo, cur));

        if(remedyImg != null)handlers.add(new RemedyImgHandler(remedyImg, cur));
        if(remedyVideo != null)handlers.add(new RemedyVdoHandler(remedyVideo, cur));

        try {
            for(FileHandler<Case> handler : handlers){
                handler.handleFile();
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("文件上传失败");
        }

        System.out.println("process ok");
        System.out.println(cur);
        if(cur.nullFieldsExceptId())return ResultUtil.error("未填写任何需要更新的信息");

//        数据库更新
        Long res = caseService.updateCaseById(cur);
        if (res == null || res == 0)return ResultUtil.error(null);
        else return ResultUtil.success(res);
    }


//    @PutMapping
//    public Result updateCaseById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
//                                 @RequestParam("id") String id,
//                                 @Size(max = 30, message = "name不合法") @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "name为中文、英文、数字、下划线组合")
//                                 @RequestParam(value = "name", required = false) String name,
//                                 @Size(max = 200, message = "description不合法")
//                                 @RequestParam(value = "description", required = false) String description,
//                                 @RequestParam(value = "descriptionImg", required = false) MultipartFile descriptionImg,
//                                 @RequestParam(value = "descriptionVideo", required = false) MultipartFile descriptionVideo,
//                                 @Size(max = 200, message = "checkItem不合法")
//                                 @RequestParam(value = "checkItem", required = false) String checkItem,
//                                 @RequestParam(value = "checkItemImg", required = false) MultipartFile checkItemImg,
//                                 @RequestParam(value = "checkItemVideo", required = false) MultipartFile checkItemVideo,
//                                 @Size(max = 200, message = "diagnosis不合法")
//                                 @RequestParam(value = "diagnosis", required = false) String diagnosis,
//                                 @RequestParam(value = "diagnosisImg", required = false) MultipartFile diagnosisImg,
//                                 @RequestParam(value = "diagnosisVideo", required = false) MultipartFile diagnosisVideo,
//                                 @Size(max = 200, message = "remedy不合法")
//                                 @RequestParam(value = "remedy", required = false) String remedy,
//                                 @RequestParam(value = "remedyImg", required = false) MultipartFile remedyImg,
//                                 @RequestParam(value = "remedyVideo", required = false) MultipartFile remedyVideo,
//                                 @CaseTypeUpdateConstraint @RequestParam(value = "type", required = false) List<String> types){
////        if(StringUtils.isBlank(id))return ResultUtil.error("id不能是空串或只有空格");
//        System.out.println("id: " + id);
//
//        if (types != null){
//            for(String s : types){
//                System.out.println(s);
//                long existNum = diseaseService.existName(s);
//                if(existNum <= 0)return ResultUtil.error("存在不合法病名");
//            }
//        }
//        System.out.println("==============");
//
//        Case cur = new Case();
//        cur.setId(id)
//                .setName(name)
//                .setDescription(description)
//                .setCheckItem(checkItem)
//                .setDiagnosis(diagnosis)
//                .setRemedy(remedy)
//                .setType(types);
//
////        上传文件，并设置case的文件路径字段
//        List<FileHandler<Case>> handlers = new ArrayList<>();
//        if(descriptionImg != null)handlers.add(new DescImgHandler(descriptionImg, cur));
//        if(descriptionVideo != null)handlers.add(new DescVdoHandler(descriptionVideo, cur));
//
//        if(checkItemImg != null)handlers.add(new CheckImgHandler(checkItemImg, cur));
//        if(checkItemVideo != null)handlers.add(new CheckVdoHandler(checkItemVideo, cur));
//
//        if(diagnosisImg != null)handlers.add(new DiagImgHandler(diagnosisImg, cur));
//        if(diagnosisVideo != null)handlers.add(new DiagVdoHandler(diagnosisVideo, cur));
//
//        if(remedyImg != null)handlers.add(new RemedyImgHandler(remedyImg, cur));
//        if(remedyVideo != null)handlers.add(new RemedyVdoHandler(remedyVideo, cur));
//
//        for(FileHandler<Case> handler : handlers){
//            handler.handleFile();
//        }
//
//        System.out.println("process ok");
//        System.out.println(cur);
//        if(cur.nullFieldsExceptId())return ResultUtil.error("未填写任何需要更新的信息");
//
////        数据库更新
//        Long res = caseService.updateCaseById(cur);
//        if (res == null || res == 0)return ResultUtil.error(null);
//        else return ResultUtil.success(res);
//    }

    @DeleteMapping
    public Result deleteCaseById(@NotBlank(message = "id不能是空串或只有空格") @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                 @RequestParam("id") String id){
        try {
            int flag = caseService.deleteCaseById(id);
            if(flag < 0)return ResultUtil.error("id不存在");
            System.out.println("delete:"+id);
            return ResultUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除异常");
        }
    }

    @GetMapping
    public Result getCaseById(@NotBlank(message = "id不能是空串或只有空格") @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                @RequestParam("id") String id){
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

        PageSupport<Case> respPage = new PageSupport<>();
        respPage.setListData(res.getContent())
                .setTotalPages(res.getTotalPages());

        return ResultUtil.success(respPage);

//        return ResultUtil.success(res.getContent());
    }

    @GetMapping("/group")
    public Result getCaseByCombinedName(@NonNull @RequestParam("pageNum") Integer pageNum,
                                        @NonNull @RequestParam("pageSize") Integer pageSize,
                                        @NotBlank @Size(max = 100, message = "diseases不合法") @RequestParam("diseases") String diseases){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        List<Case> res = caseService.getCaseByCombinedName(pageNum, pageSize, diseases);
        if(res == null)return ResultUtil.error(null);

        long total = (caseService.getCaseByCombinedNameCount(pageNum, pageSize, diseases) + pageSize - 1) / pageSize;

        PageSupport<Case> respPage = new PageSupport<>();
        respPage.setListData(res)
                .setTotalPages((int)total);

        return ResultUtil.success(respPage);

//        return ResultUtil.success(res);
    }



}
