package com.digger.app.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {

    private Integer mId;

    private String mName;

    private Double mCurrentValue;

    private Double mOpen;

    private  Double mPercentage;

    private String mDaysRange;

}
