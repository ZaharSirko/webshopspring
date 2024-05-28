package com.example.webshopspring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = generateUniqueFileName(imageFile.getOriginalFilename());
            Path filePath = Paths.get(uploadDir + File.separator + fileName);

            Files.copy(imageFile.getInputStream(), filePath);


            return  fileName;
        }
        return null;
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }


}
