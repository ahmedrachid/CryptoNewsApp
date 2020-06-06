package com.digger.app.model;

public class Alert {

    private String stock;
    private  boolean more ;
    private  Double value;
    private String deviceId;

    private int id ;


    public Alert(String stock, boolean more, Double value) {
        this.stock = stock;
        this.more = more;
        this.value = value;
    }


    public Alert(String stock, boolean more, Double value,int id) {
        this.stock = stock;
        this.more = more;
        this.value = value;
        this.id = id;
    }


    public Alert() {
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }
}
