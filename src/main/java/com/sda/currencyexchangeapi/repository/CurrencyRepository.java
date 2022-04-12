package com.sda.currencyexchangeapi.repository;

import com.sda.currencyexchangeapi.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
