package com.example.webshopspring.DTO.mapper;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GoodWithPriceMapper {

    public static GoodWithPriceDTO toDTO(Good good, Price price) {
        return new GoodWithPriceDTO(
                good.getId(),
                good.getGoodName(),
                good.getGoodDescription(),
                good.getGoodBrand(),
                good.getGoodPhoto(),
                good.getGoodLikes(),
                price.getClient_price()
        );
    }

    public static List<GoodWithPriceDTO> toDTOList(List<Good> goods, Map<Long, Price> priceMap) {
        return goods.stream()
                .map(good -> toDTO(good, priceMap.get(good.getId())))
                .collect(Collectors.toList());
    }

}
