package com.example.webshopspring.DTO;

public record GoodWithPriceDTO(
        Long id,
        String goodName,
        String goodDescription,
        String goodBrand,
        String[] goodPhoto,
        int goodLikes,
        Long priceId,
        double clientPrice
) {
}
