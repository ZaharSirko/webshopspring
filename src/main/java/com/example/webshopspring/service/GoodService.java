package com.example.webshopspring.service;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.DTO.mapper.GoodWithPriceMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GoodService {
    private final GoodRepository  goodRepository;
    private final GoodWithPriceMapper goodWithPriceMapper;
    private final PriceService priceService;

    @Value("${upload.dir}")
    private String uploadDir;


    public GoodService(GoodRepository goodRepository, GoodWithPriceMapper goodWithPriceMapper, PriceService priceService) {
        this.goodRepository = goodRepository;
        this.goodWithPriceMapper = goodWithPriceMapper;
        this.priceService = priceService;
    }

    public  Good getGoodById(Long id) {
        return goodRepository.findById(id).orElse(null);
    }

    public Good getGoodByName(String name){
        return   goodRepository.findByGoodName(name).orElse(null);
    }

    public List<GoodWithPriceDTO> getAllGoodsWithPrices() {
        List<Good> goods = goodRepository.findAll();
        Map<Long, Price> priceMap = priceService.getPricesMappedByGoodId();
        return GoodWithPriceMapper.toDTOList(goods, priceMap);
    }

    public Good addGood(String goodName, String goodDescription, String goodBrand, String[] goodPhoto){
        Good newGood = new Good();
        newGood.setGoodName(goodName);
        newGood.setGoodDescription(goodDescription);
        newGood.setGoodBrand(goodBrand);
        newGood.setGoodPhoto(goodPhoto);
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
