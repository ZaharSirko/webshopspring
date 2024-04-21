package com.example.webshopspring.service;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.repo.GoodRepository;
import com.example.webshopspring.repo.PriceRepository;
import com.example.webshopspring.repo.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {
private final GoodRepository  goodRepository;
private final PriceRepository priceRepository;
private final TypesRepository typesRepository;

@Autowired
public GoodService(GoodRepository goodRepository, PriceRepository priceRepository, TypesRepository typesRepository) {
    this.goodRepository = goodRepository;
    this.priceRepository = priceRepository;
    this.typesRepository = typesRepository;
}

public  Good getById(long id) {
    return goodRepository.findById(id);
}

public Good getGoodByName(String name){
 return   goodRepository.findByGoodName(name);
}

public List<Good> getAllGoods(){
    return goodRepository.findAll();
}

public Good addGood(Good good){
    return goodRepository.save(good);
}

public  boolean deleteGood(Good good){
    return  true;
}
public boolean updateGood(Good good){
    return true;
}


}
