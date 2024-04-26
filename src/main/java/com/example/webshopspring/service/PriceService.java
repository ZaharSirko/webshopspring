package com.example.webshopspring.service;

import com.example.webshopspring.model.Price;
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
    public  Price addPrice(Price price) {
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
}
