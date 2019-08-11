package com.miaoshaproject.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品的领域模型
 */
public class ItemModel {
    
    private Integer id;
    // 商品名称
    @NotBlank(message = "商品名称不能为空")
    private String title;
    // 商品价格
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;
    // 商品的库存
    @NotNull(message = "库存不能不填")
    private Integer stock;
    // 商品的描述
    @NotBlank(message = "描述信息不能为空")
    private String descpription;
    // 商品的销量
    private Integer sales;
    // 商品描述图片的URl
    @NotBlank(message = "图片信息不能为空")
    private String ImgUrl;
    
    // 使用聚合模型, 如果promoModel不为空，则表示其拥有还未结束的秒杀活动
    private PromoModel promoModel;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescpription() {
        return descpription;
    }

    public void setDescpription(String description) {
        this.descpription = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + descpription + '\'' +
                ", sales=" + sales +
                ", ImgUrl='" + ImgUrl + '\'' +
                '}';
    }
}
