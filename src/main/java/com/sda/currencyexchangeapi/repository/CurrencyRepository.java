package com.sda.currencyexchangeapi.repository;

import com.sda.currencyexchangeapi.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Currency findByBaseAndTargetAndDate(String base, String target, Date date);
}
