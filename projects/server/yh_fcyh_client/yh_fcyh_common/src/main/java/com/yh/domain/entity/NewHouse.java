package com.yh.domain.entity;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/05/15 11:42
 **/
public class NewHouse {
    private String entryName; //项目名称
    private String entryUrl; // 项目链接

    @Override
    public String toString() {
        return "NewHouse{" +
                "entryName='" + entryName + '\'' +
                ", entryUrl='" + entryUrl + '\'' +
                ", building='" + building + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", region='" + region + '\'' +
                ", startAndEndTime='" + startAndEndTime + '\'' +
                ", suppliedTotal='" + suppliedTotal + '\'' +
                '}';
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStartAndEndTime() {
        return startAndEndTime;
    }

    public void setStartAndEndTime(String startAndEndTime) {
        this.startAndEndTime = startAndEndTime;
    }

    public String getSuppliedTotal() {
        return suppliedTotal;
    }

    public void setSuppliedTotal(String suppliedTotal) {
        this.suppliedTotal = suppliedTotal;
    }

    private String building; // 楼幢

    private String enterpriseName; //企业名称
    private String region; //区域
    private String startAndEndTime; //登记起止时间
    private String suppliedTotal; // 供应总套数





}
