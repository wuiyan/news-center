package com.example.controller;

import com.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${file.upload.path:D:/uploads/}")
    private String uploadPath;

    @PostMapping("/image")
    public Result uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.fail("请选择文件");
        }

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + ext;

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fullPath = uploadPath + datePath + "/";
        File dir = new File(fullPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File dest = new File(fullPath + filename);
        file.transferTo(dest);

        String url = "/uploads/" + datePath + "/" + filename;
        return Result.ok(url);
    }
}
