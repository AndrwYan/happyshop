package com.imooc.ecommerce.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * @author yfk
 * @since 2022-05-28
 */
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private LocalDateTime deletedAt;

    private Boolean isDeleted;

    private Integer categoryId;

    private Integer brandsId;

    private Boolean onSale;

    private Boolean shipFree;

    private Boolean isNew;

    private Boolean isHot;

    private String name;

    private String goodsSn;

    private Integer clickNum;

    private Integer soldNum;

    private Integer favNum;

    private Float marketPrice;

    private Float shopPrice;

    private String goodsBrief;

    private String images;

    private String descImages;

    private String goodsFrontImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandsId() {
        return brandsId;
    }

    public void setBrandsId(Integer brandsId) {
        this.brandsId = brandsId;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Boolean getShipFree() {
        return shipFree;
    }

    public void setShipFree(Boolean shipFree) {
        this.shipFree = shipFree;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public Integer getFavNum() {
        return favNum;
    }

    public void setFavNum(Integer favNum) {
        this.favNum = favNum;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Float getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Float shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescImages() {
        return descImages;
    }

    public void setDescImages(String descImages) {
        this.descImages = descImages;
    }

    public String getGoodsFrontImage() {
        return goodsFrontImage;
    }

    public void setGoodsFrontImage(String goodsFrontImage) {
        this.goodsFrontImage = goodsFrontImage;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Goods{" +
        ", id=" + id +
        ", addTime=" + addTime +
        ", updateTime=" + updateTime +
        ", deletedAt=" + deletedAt +
        ", isDeleted=" + isDeleted +
        ", categoryId=" + categoryId +
        ", brandsId=" + brandsId +
        ", onSale=" + onSale +
        ", shipFree=" + shipFree +
        ", isNew=" + isNew +
        ", isHot=" + isHot +
        ", name=" + name +
        ", goodsSn=" + goodsSn +
        ", clickNum=" + clickNum +
        ", soldNum=" + soldNum +
        ", favNum=" + favNum +
        ", marketPrice=" + marketPrice +
        ", shopPrice=" + shopPrice +
        ", goodsBrief=" + goodsBrief +
        ", images=" + images +
        ", descImages=" + descImages +
        ", goodsFrontImage=" + goodsFrontImage +
        "}";
    }
}
