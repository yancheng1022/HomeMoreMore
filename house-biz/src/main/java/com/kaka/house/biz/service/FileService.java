package com.kaka.house.biz.service;

import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    @Value("${file.path}")
    private String filePath;

    public List<String> getImagePath(List<MultipartFile>files){
        ArrayList<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            File localFile = null;
            if (!file.isEmpty()){
                try {
                    saveToLocal(file,filePath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath );
                    paths.add(path);
                }catch (Exception e){
                    //字面非法参数 通常情况为找不到文件等
                    throw new IllegalArgumentException();
                }

            }
        }
        return paths;
    }

    private File saveToLocal(MultipartFile file,String filePath2) throws IOException {
        File newFile = new File(filePath + "/"+ Instant.now().getEpochSecond()+"/"+file.getOriginalFilename());
        if (!newFile.exists()){
            //创建上级目录 存在的情况什么都不做
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(), newFile);
        return null;
    }
}
