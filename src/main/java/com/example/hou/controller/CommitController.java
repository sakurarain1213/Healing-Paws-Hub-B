package com.example.hou.controller;

import com.example.hou.result.Result;
import com.example.hou.util.ResultUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exam")
@Validated
public class CommitController {
    @PostMapping("/commit")
    public Result commitAnswer(@RequestBody List<String > req) {
        return ResultUtil.success();
    }
}
