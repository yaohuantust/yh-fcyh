package com.yh.domain.entity;

import java.util.List;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/05/15 15:37
 **/
public class NewHouseDtl {
    private String company; //开发公司
    private String region; // 所在区域

    @Override
    public String toString() {
        return "NewHouseDtl{" +
                "company='" + company + '\'' +
                ", region='" + region + '\'' +
                ", building='" + building + '\'' +
                ", total='" + total + '\'' +
                ", buildings=" + buildings +
                ", needTotal='" + needTotal + '\'' +
                ", entryName='" + entryName + '\'' +
                ", entryCode='" + entryCode + '\'' +
                ", phone='" + phone + '\'' +
                ", salesAddress='" + salesAddress + '\'' +
                ", registeredAddress='" + registeredAddress + '\'' +
                ", erWeiMa='" + erWeiMa + '\'' +
                '}';
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    private String building; // 所在区域


    private String total; // 供应住房总数

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    private List<Building> buildings; // 供应住房总数

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNeedTotal() {
        return needTotal;
    }

    public void setNeedTotal(String needTotal) {
        this.needTotal = needTotal;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalesAddress() {
        return salesAddress;
    }

    public void setSalesAddress(String salesAddress) {
        this.salesAddress = salesAddress;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getErWeiMa() {
        return erWeiMa;
    }

    public void setErWeiMa(String erWeiMa) {
        this.erWeiMa = erWeiMa;
    }

    private String needTotal; //刚需供应数
    private String entryName; //项目名称
    private String entryCode; // 项目代码
    private String phone; //联系电话
    private String salesAddress; //售楼部地址
    private String registeredAddress; //登记地址
    private String erWeiMa; //二维码
}
