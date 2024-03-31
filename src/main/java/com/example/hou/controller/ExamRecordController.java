package com.example.hou.controller;

import com.example.hou.service.ExamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examrecord")
@Validated
public class ExamRecordController {
    @Autowired
    private ExamRecordService examRecordService;
}
