package com.example.webshopspring.DTO.mapper;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodWithPriceMapper {

    public static GoodWithPriceDTO toDTO(Good good, Price price) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
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
                price.getId(),
                price.getClient_price()
        );
    }

    public static List<GoodWithPriceDTO> toDTOList(List<Good> goods, Map<Long, Price> priceMap) {
        return goods.stream()
                .map(good -> toDTO(good, priceMap.get(good.getId())))
                .collect(Collectors.toList());
    }

    public static Good toGoodId(GoodWithPriceDTO dto) {
        Good good = new Good();
        good.setId(dto.id());
        return good;
    }

    public static Price toPriceId(GoodWithPriceDTO dto) {
        Price price = new Price();
        price.setId(dto.priceId());
        return price;
    }

}