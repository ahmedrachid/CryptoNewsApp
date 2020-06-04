package com.digger.app.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {

    private String id;


    private Double marketOpen;

    private Double previousClose;

    private  Double regularMarketChangePercent;

    private Double regularMarketPrice;

    private String symbol;

    private  String urlImage;

}
