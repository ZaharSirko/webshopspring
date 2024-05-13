package com.example.webshopspring.service;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.repo.GoodRepository;
import com.example.webshopspring.repo.PriceRepository;
import com.example.webshopspring.repo.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class GoodService {
    private final GoodRepository  goodRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    public  Good getGoodById(Long id) {
        return goodRepository.findById(id).orElse(null);
    }

    public Good getGoodByName(String name){
        return   goodRepository.findByGoodName(name).orElse(null);
    }

    public List<Good> getAllGoods(){
        return goodRepository.findAll();
    }

    public Good addGood(String goodName, String goodDescription, String goodBrand, String[] goodPhoto){
//    Price goodPrice
        Good newGood = new Good();
        newGood.setGoodName(goodName);
        newGood.setGoodDescription(goodDescription);
        newGood.setGoodBrand(goodBrand);
        newGood.setGoodPhoto(goodPhoto);
//    newGood.setGoodPrice(goodPrice);
        newGood.setGoodLikes(0);

        return goodRepository.save(newGood);
    }

    public Good addGood(Good good){
        return goodRepository.save(good);
    }

    public  boolean deleteGood(Good good){
        return  true;
    }
    public boolean updateGood(Long id,Good good){
        Good existingGood = getGoodById(id);
        if(existingGood != null){


            return  true;
        }
        return false;
    }

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
