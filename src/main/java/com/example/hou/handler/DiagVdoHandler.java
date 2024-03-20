package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

public class DiagVdoHandler extends FileHandler<Case> {
    public DiagVdoHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseDiagVdoHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setDiagnosisVideo(filename);
        System.out.println(cse);
    }

}
