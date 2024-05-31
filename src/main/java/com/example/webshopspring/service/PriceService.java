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
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    public Price getPriceById(Long id) {
        return priceRepository.finById(id).orElse(null);
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    public void addPrice(Price price) {
        priceRepository.save(price);
    }

    public Price getPriceForGoodId(Long id) {
        return priceRepository.findPriceForGoodId(id).orElse(null);
    }

    public List<Price> getAllPriceForGood(Good good) {
        return  priceRepository.findByGood(good);
    }

    public  boolean updatePrice(Long id, Price updatedPrice) {
        Price existingPrice = getPriceById(id);
        if (existingPrice != null) {
            existingPrice.setSupplier_price(updatedPrice.getSupplier_price());
            existingPrice.setClient_price(updatedPrice.getClient_price());
            existingPrice.setBought_amount(updatedPrice.getBought_amount());
            existingPrice.setGood_id(updatedPrice.getGood_id());
            priceRepository.save(existingPrice);
            return  true;
        }
        return  false;
    }


}
