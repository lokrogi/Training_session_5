package com.sda.currencyexchangeapi.repository;

import com.sda.currencyexchangeapi.model.Gold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface GoldRepository extends JpaRepository<Gold, Integer> {

    Gold findByDate(Date date);
}
