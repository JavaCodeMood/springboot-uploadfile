package com.lhf.springbootuploadfile.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: FileUtils
 * @Desc:  上传文件工具类
 * @Author: liuhefei
 * @Date: 2019/1/12 11:30
 */
public class FileUtils {

    /**
     * 上传文件
     * @param file  文件
     * @param path  文件路径
     * @param fileName  文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName, Map<String, Object> map){

        //文件路径
        String realPath = path + File.separator + fileName;
        System.out.println("realPath = " + realPath);

        File dest = new File(realPath);

        //判断文件目录是否存在
        if( !dest.getParentFile().exists()){
            dest.getParentFile().mkdir();   //不存在就创建
        }

        try{
            //保存文件
            file.transferTo(dest);
            ImageIcon image = new ImageIcon(dest.getAbsolutePath());
            map.put("height", image.getIconHeight());
            map.put("width", image.getIconWidth());
            String thumbnailImg = path + File.separator + "thumbnail" + File.separator + fileName;
            File thumbNail = new File(thumbnailImg);
            if(!thumbNail.getParentFile().exists()){
                thumbNail.getParentFile().mkdir();
            }
            Thumbnails.of(realPath)
                    .size(200,200)
                    .toFile(thumbnailImg);
            return true;
        }catch (IllegalStateException  e){
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
