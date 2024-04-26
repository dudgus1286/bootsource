package com.example.movie.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/upload")
@RequiredArgsConstructor
@Log4j2
@Controller
// fetch ==> @RestController
public class UploadController {
    // application.properties 에 설정한 변수 가져오기
    @Value("${com.example.upload.path}")
    private String uploadPath;

    @GetMapping("/ex1")
    public void getMethodName() {
        log.info("upload form 요청");
    }

    @PostMapping("/uploadAjax")
    public void postMethodName(MultipartFile[] uploadFiles) {

        for (MultipartFile multipartFile : uploadFiles) {
            String oriName = multipartFile.getOriginalFilename();
            String fileName = oriName.substring(oriName.lastIndexOf("\\") + 1);
            log.info("파일정보 - 전체경로 : {}", oriName);
            log.info("파일 정보 - 파일명 : {}", fileName);

            // 폴더 생성
            String saveFolderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + fileName;
            // java.nio.Path
            Path savePath = Paths.get(saveName);

            try {
                multipartFile.transferTo(savePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String makeFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderStr = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderStr);
        if (!uploadPathFolder.exists())
            uploadPathFolder.mkdirs();
        return folderStr;
    }

}
