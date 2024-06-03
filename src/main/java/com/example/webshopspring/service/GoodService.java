package com.example.webshopspring.service;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.DTO.mapper.GoodWithPriceMapper;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.repo.GoodRepository;
import com.example.webshopspring.repo.PriceRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class GoodService {
    private final GoodRepository  goodRepository;
    private final GoodWithPriceMapper goodWithPriceMapper;
    private final PriceService priceService;
    private final PriceRepository priceRepository;

    public GoodService(GoodRepository goodRepository, GoodWithPriceMapper goodWithPriceMapper, PriceService priceService, PriceRepository priceRepository) {
        this.goodRepository = goodRepository;
        this.goodWithPriceMapper = goodWithPriceMapper;
        this.priceService = priceService;
        this.priceRepository = priceRepository;
    }

    public  GoodWithPriceDTO  getGoodWithPriceById(Long id) {
        Good good = goodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Good not found"));
        Price price = priceRepository.findPriceForGoodId(id).orElseThrow(() -> new ResourceNotFoundException("Price not found for Good id: " + id));
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return GoodWithPriceMapper.toDTO(good, price, baseUrl);
    }


    public List<Good> getGoodsWitOutPrice(){
        return goodRepository.findGoodsWithoutPrice();
    }

    public List<GoodWithPriceDTO> getAllGoodsWithPrices() {
        List<Good> goods = goodRepository.findAll();
        Map<Long, Price> priceMap = priceService.getPricesMappedByGoodId();
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        List<Good> goodsWithPrices = goods.stream()
                .filter(good -> priceMap.containsKey(good.getId()))
                .collect(Collectors.toList());

        return GoodWithPriceMapper.toDTOList(goodsWithPrices, priceMap, baseUrl);
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



//    public  boolean deleteGood(Good good){
//        return  true;
//    }
//    public boolean updateGood(Long id,Good good){
//        Good existingGood = getGoodById(id);
//        if(existingGood != null){
//
//
//            return  true;
//        }
//        return false;
//    }


}
