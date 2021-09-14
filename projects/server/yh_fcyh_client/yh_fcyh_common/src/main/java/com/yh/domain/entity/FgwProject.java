package com.yh.domain.entity;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/05/20 21:33
 **/
public class FgwProject {
    private String projectName; //项目名称
    private String projectUrl; // 项目链接

    @Override
    public String toString() {
        return "FgwProject{" +
                "projectName='" + projectName + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                ", recordNum='" + recordNum + '\'' +
                ", buildingNo='" + buildingNo + '\'' +
                ", buildUpArea='" + buildUpArea + '\'' +
                ", setNum='" + setNum + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                '}';
    }

    private String recordNum; // 备案号

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getBuildUpArea() {
        return buildUpArea;
    }

    public void setBuildUpArea(String buildUpArea) {
        this.buildUpArea = buildUpArea;
    }

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    private String buildingNo; //楼号
    private String buildUpArea; // 建筑面积
    private String setNum; //套数
    private String averagePrice; // 均价
}
