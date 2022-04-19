package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Gold;
import com.sda.currencyexchangeapi.model.GoldDto;
import org.springframework.stereotype.Component;

@Component
public class GoldMapper {

    public GoldDto map(Gold gold) {
        return new GoldDto(gold.getPrice());
    }
}
