package com.digger.app.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllStocks {

    private ArrayList<Stock> crypto;
    private ArrayList<Stock> stockMarketIndices;
    private ArrayList<Stock> rawMaterials;

}
