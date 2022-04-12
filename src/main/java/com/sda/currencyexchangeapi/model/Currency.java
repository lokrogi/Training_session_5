package com.sda.currencyexchangeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String baseCurrency;
    private String wantedCurrency;
    private double value;
    private Date date;

}
