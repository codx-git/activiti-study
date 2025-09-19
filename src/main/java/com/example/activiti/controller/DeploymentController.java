package com.example.activiti.controller;

import com.example.activiti.common.ApiResponse;
import com.example.activiti.utils.ZipUtil;
import org.activiti.engine.RepositoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/deploy")
public class DeploymentController {
    private final RepositoryService repositoryService;
    DeploymentController(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
    }

    @PostMapping("/deployByZip")
    public ApiResponse<Void> deployByZip(@RequestParam MultipartFile file) throws IOException {
        if(ZipUtil.isValidZip(file)) return  ApiResponse.fail(500,null);
        ZipInputStream zip = new ZipInputStream(file.getInputStream());
        repositoryService.createDeployment().addZipInputStream(zip);
        return ApiResponse.success(null);
    }
}
