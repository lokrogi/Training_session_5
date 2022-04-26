package com.sda.currencyexchangeapi.domain;

import com.sda.currencyexchangeapi.model.Gold;
import com.sda.currencyexchangeapi.model.GoldDto;
import com.sda.currencyexchangeapi.repository.GoldRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@Service
public class GoldService {

    private final GoldRepository goldRepository;
    private final GoldConnection goldConnection;
    private final GoldMapper goldMapper;


    @Autowired
    public GoldService(GoldRepository goldRepository, GoldConnection goldConnection, GoldMapper goldMapper) {
        this.goldRepository = goldRepository;
        this.goldConnection = goldConnection;
        this.goldMapper = goldMapper;
    }

    public GoldDto getGoldPrice(String date) {

        if(date == null) {
            date = String.valueOf(LocalDate.now());
        }

        Date validDate;
        try{
            validDate = Date.valueOf(date);
        }catch (IllegalArgumentException e) {
            throw new DateFormatException("Invalid date format");
        }

        Gold requestedGold = goldRepository
                .findByDate(validDate);

        if (requestedGold != null) {
            log.info("Gold price loaded from data base.");
            return goldMapper.map(requestedGold);
        }

        Gold goldFromApi = goldConnection.getGold(date);

        if(goldFromApi != null) {
            log.info("Gold price loaded from external api.");
            return goldMapper.map(goldRepository.save(goldFromApi));
        }

        return null;
    }

}
