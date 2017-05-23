package com.wfy.server.model;

import java.math.BigDecimal;

/**
 * Created by weifeiyu on 2017/5/4.
 */
public class Computer extends BaseModel{

    private String computerId;

    private String computerName;

    private String computerDesc;

    private String computerType;

    private String computerCountry;

    private Integer computerCount;

    private BigDecimal computerPrice;

    private String computerSize;

    private String computerColor;

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerDesc() {
        return computerDesc;
    }

    public void setComputerDesc(String computerDesc) {
        this.computerDesc = computerDesc;
    }

    public String getComputerType() {
        return computerType;
    }

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }

    public String getComputerCountry() {
        return computerCountry;
    }

    public void setComputerCountry(String computerCountry) {
        this.computerCountry = computerCountry;
    }

    public Integer getComputerCount() {
        return computerCount;
    }

    public void setComputerCount(Integer computerCount) {
        this.computerCount = computerCount;
    }

    public BigDecimal getComputerPrice() {
        return computerPrice;
    }

    public void setComputerPrice(BigDecimal computerPrice) {
        this.computerPrice = computerPrice;
    }

    public String getComputerSize() {
        return computerSize;
    }

    public void setComputerSize(String computerSize) {
        this.computerSize = computerSize;
    }

    public String getComputerColor() {
        return computerColor;
    }

    public void setComputerColor(String computerColor) {
        this.computerColor = computerColor;
    }
}
