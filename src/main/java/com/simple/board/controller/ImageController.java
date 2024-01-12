package com.simple.board.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final ResourceLoader resourceLoader;

    @PostMapping("/image/upload")
    public Map<String,Object> upload(@RequestParam("upload") MultipartFile uploadFile) throws IOException {
        log.info("호출됨");
        Map<String,Object> map = new HashMap<>();

        //검증
        if (uploadFile == null){
            //기타검증 필요
            map.put("uploaded",0);
            return map;
        }
        
        //기존 파일 이름
        String originalFileName = uploadFile.getOriginalFilename();
        //UUID로 새 이름 생성
        String newFileName = UUID.randomUUID()+ originalFileName.substring(originalFileName.indexOf("."));

        //storage 절대 경로 찾아오기
        String path = Paths.get("storage/upload").toAbsolutePath().toString();
        log.info("path = {}",path);

        //저장
        File file = new File(path+"/"+newFileName);
        uploadFile.transferTo(file);

        //리턴값
        map.put("filename",originalFileName);
        map.put("uploaded", 1);
        map.put("url", "/image/"+newFileName);
        return map;
    }

}
