package com.miaoshaproject.controller.viewObject;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class ItemVo {
    
    private Integer id;

    private String title;
    
    private Double price;

    private String descpription;
    
    private Integer sales;

    private String imgUrl;

    private Integer stock;
    
    // 记录商品是否在秒杀活动中，以及对应的状态0：没有秒杀活动，1：表示待开始，2：表示秒杀活动进行中
    private Integer promoStatus;
    
    // 秒杀活动价格
    private BigDecimal promoPrice;
    
    // 秒杀活动id
    private Integer promoId;
    
    // 秒杀活动的开始时间
    private String startDate;

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescpription() {
        return descpription;
    }

    public void setDescpription(String descpription) {
        this.descpription = descpription;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
