package com.example.webshopspring.DTO.mapper;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GoodWithPriceMapper {

    public static GoodWithPriceDTO toDTO(Good good, Price price, String baseUrl) {
        String[] fullPhotoUrls = Arrays.stream(good.getGoodPhoto())
                .map(photo -> baseUrl + "/static/" + photo)
                .toArray(String[]::new);

        return new GoodWithPriceDTO(
                good.getId(),
                good.getGoodName(),
                good.getGoodDescription(),
                good.getGoodBrand(),
                fullPhotoUrls,
                good.getGoodLikes(),
                price.getClient_price()
        );
    }

    public static List<GoodWithPriceDTO> toDTOList(List<Good> goods, Map<Long, Price> priceMap, String baseUrl) {
        return goods.stream()
                .map(good -> toDTO(good, priceMap.get(good.getId()), baseUrl))
                .collect(Collectors.toList());
    }

}