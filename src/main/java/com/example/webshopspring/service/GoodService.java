package com.example.webshopspring.service;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.DTO.mapper.GoodWithPriceMapper;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.repo.GoodRepository;
import com.example.webshopspring.repo.PriceRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class GoodService {
    private final GoodRepository  goodRepository;
    private final PriceService priceService;
    private final PriceRepository priceRepository;

    public GoodService(GoodRepository goodRepository, PriceService priceService, PriceRepository priceRepository) {
        this.goodRepository = goodRepository;
        this.priceService = priceService;
        this.priceRepository = priceRepository;
    }

    public  GoodWithPriceDTO  getGoodWithPriceById(Long id) {
        Good good = goodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Good not found"));
        Price price = priceRepository.findPriceForGoodId(id).orElseThrow(() -> new ResourceNotFoundException("Price not found for Good id: " + id));
        return GoodWithPriceMapper.toDTO(good, price);
    }


    public List<Good> getGoodsWitOutPrice(){
        return goodRepository.findGoodsWithoutPrice();
    }

    public List<GoodWithPriceDTO> getAllGoodsWithPrices() {
        List<Good> goods = goodRepository.findAll();
        Map<Long, Price> priceMap = priceService.getPricesMappedByGoodId();

        List<Good> goodsWithPrices = goods.stream()
                .filter(good -> priceMap.containsKey(good.getId()))
                .collect(Collectors.toList());

        return GoodWithPriceMapper.toDTOList(goodsWithPrices, priceMap);
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


    public List<String> getSearchSuggestions(String query) {
        return goodRepository.findTop10ByGoodNameContainingIgnoreCase(query)
                .stream()
                .map(Good::getGoodName)
                .collect(Collectors.toList());
    }

    public List<Long> searchGoods(String query) {
        List<Good> goods = goodRepository.findByGoodNameContainingIgnoreCase(query);
        return goods.stream()
                .map(Good::getId)
                .collect(Collectors.toList());
    }


}
