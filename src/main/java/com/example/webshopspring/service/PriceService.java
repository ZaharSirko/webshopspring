package com.example.webshopspring.service;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.repo.GoodRepository;
import com.example.webshopspring.repo.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    final private PriceRepository priceRepository;
    final private GoodRepository goodRepository;
    final private GoodService goodService;
    public PriceService(PriceRepository priceRepository, GoodRepository goodRepository, GoodService goodService) {
        this.priceRepository = priceRepository;
        this.goodRepository = goodRepository;
        this.goodService = goodService;
    }
    public Price getPriceById(Long id) {
        return priceRepository.finById(id).orElse(null);
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }
    public  Price addPrice(Price price) {
        setGoodPrice(price);
        return priceRepository.save(price);
    }
    public  boolean savePrice(Price price) {
        Price priceFromDb = priceRepository.findById(price.getId()).orElse(null);
        if (priceFromDb == null) {
            return false;
        }
         priceRepository.save(price);
        return true;
    }
    public  boolean updatePrice(Long id, Price updatedPrice) {
        Price existingPrice = getPriceById(id);
        if (existingPrice != null) {
            existingPrice.setSupplier_price(updatedPrice.getSupplier_price());
            existingPrice.setBought_amount(updatedPrice.getBought_amount());
            existingPrice.setGood_id(updatedPrice.getGood_id());
            priceRepository.save(existingPrice);
            setGoodPrice(existingPrice);
            return  true;
        }
        return  false;
    }

    public void setGoodPrice(Price price){
        Good good =  goodService.getGoodById(price.getGood_id().getId());
        good.setGoodPrice(price);
        goodRepository.save(good);
    }

}
