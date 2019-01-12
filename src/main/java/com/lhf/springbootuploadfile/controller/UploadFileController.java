package com.lhf.springbootuploadfile.controller;

import com.lhf.springbootuploadfile.utils.FileUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * @ClassName: UploadFileController
 * @Desc:  上传文件
 * swagger: http://localhost:8015/swagger-ui.html#/upload45file45controller
 * http://localhost:8015/upload
 *
 * @Author: liuhefei
 * @Date: 2019/1/12 10:59
 */
@Controller
public class UploadFileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${web.uploadPath}")
    private String path;

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadFileController(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @ApiOperation(value = "上传文件",notes = "")
    @RequestMapping(value = "/upload")
    public String toUpload(){
        return "upload";
    }

    @ApiOperation(value = "上传文件",notes = "")
    @RequestMapping(value = "/fileUpload")
    public String upload(@RequestParam("fileName")MultipartFile file, Map<String, Object> map){
       //要上传的目标文件存放的路径
        String localPath = path;

        //上传成功或失败的提示信息
        String msg = "";

        logger.info("文件名：{}", file.getOriginalFilename());
        String realPath = path + File.separator + file.getOriginalFilename();  //原图地址
        String resultPath = path + File.separator + "thumbnail" + File.separator + file.getOriginalFilename();  //缩略图地址

        if(FileUtils.upload(file, localPath, file.getOriginalFilename(), map)){
             //上传成功，给出页面提示
            msg = "文件上传成功，文件路径：" + realPath + "<br>缩略图地址：" + resultPath;
        }else {
            msg = "文件上传失败";
        }

        //显示图片
        map.put("msg", msg);
        map.put("fileName", file.getOriginalFilename());
        return "forward:/upload";
    }

    @ApiOperation(value = "显示图片",notes = "")
    @RequestMapping(value = "/show")
    public ResponseEntity showPhotos(String fileName){
        try{
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + File.separator + fileName));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
