package com.yh.domain.entity;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/05/15 17:04
 **/
public class Building {
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingUrl() {
        return buildingUrl;
    }

    public void setBuildingUrl(String buildingUrl) {
        this.buildingUrl = buildingUrl;
    }

    public String getDesignInstitute() {
        return designInstitute;
    }

    public void setDesignInstitute(String designInstitute) {
        this.designInstitute = designInstitute;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    private String buildingName; // 楼幢名称
    private String buildingUrl; // 链接url
    private String designInstitute; // 设计单位

    public String getPreSalePermit() {
        return preSalePermit;
    }

    public void setPreSalePermit(String preSalePermit) {
        this.preSalePermit = preSalePermit;
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingName='" + buildingName + '\'' +
                ", buildingUrl='" + buildingUrl + '\'' +
                ", designInstitute='" + designInstitute + '\'' +
                ", completionDate='" + completionDate + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", preSalePermit='" + preSalePermit + '\'' +
                '}';
    }

    private String completionDate; // 竣工日期
    private String deliveryDate; // 交付日期
    private String preSalePermit; // 预售许可证




}
