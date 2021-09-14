package com.yh.service.impl;

import com.yh.domain.entity.FgwProject;
import com.yh.domain.entity.FgwProjectDtl;
import com.yh.domain.entity.NewHouse;
import com.yh.domain.entity.NewHouseDtl;
import com.yh.service.FangChanYaoHaoService;
import com.yh.utils.http.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yaohuan
 * @version 1.0
 **/
@Service
public class FangChanYaoHaoImpl implements FangChanYaoHaoService {
    public final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
    public final Logger logger = LoggerFactory.getLogger(FangChanYaoHaoImpl.class);

    @Autowired
    HttpClientUtil httpClientUtil;

    @Override
    public Map<String, Object> getMsg(String fgjUrl, String fgwUrl, int date, String status) {
        String tomorrowStr = getDate(date);


        Map<String, Object> map = new HashMap<>();

        // 获取明日和未来几日的房产登记列表
        Map<String, Object> tableMap = getFgjList(tomorrowStr, fgjUrl, status);
        List<NewHouse> newHouses = (List<NewHouse>) tableMap.get("tomorrowList");
        // 获取项目详情
        List<Map<String, Object>> dtlList = getFgjAndFgwDtl(newHouses, fgwUrl);

        Map<String, String> titleAndSummary = getRegionAndEntry(newHouses);
//        //获取标题
//        String title = getTitle(tomorrowStr, newHouses);
//        //获取摘要
//        String summary = getSummary(tomorrowStr, newHouses);
        String title = tomorrowStr + "期合肥房产登记打新啦，" + titleAndSummary.get("entryName");
        if (CollectionUtils.isNotEmpty(newHouses) && newHouses.size() == 1) {
            title += "是你心仪的房产吗？";
        } else {
            title += "有你心仪的房产吗？";
        }

        String summary = tomorrowStr + "期房产上新，涉及区域：" + titleAndSummary.get("remark") + "，涉及房产：" + titleAndSummary.get("entryName") + "。";

        logger.info("newHouses:" + newHouses);
        logger.info("dtlList:" + dtlList);
        logger.info("title:" + title);
        logger.info("summary:" + summary);

        map.put("tableMap", tableMap);
        map.put("dtlList", dtlList);
        map.put("title", title);
        map.put("summary", summary);

        return map;
    }

    @Override
    public Map<String, Object> getFgwByProjectName(String fgwUrl, String txtspname) {
        Map<String, Object> dtlMap = new HashMap<>();
        //发改委Page
        Map<String, Object> fgwMap = getFgwPage(txtspname, fgwUrl);
        //发改委详情
        FgwProjectDtl fgwProjectDtl = new FgwProjectDtl();
        String projectUrl = fgwMap.get("projectUrl") + "";
        if (!"-".equals(projectUrl)) {
            fgwProjectDtl = getFgwDtl(projectUrl);
        }
        dtlMap.put("fgwPage", fgwMap);
        dtlMap.put("fgwDtl", fgwProjectDtl);
        return dtlMap;
    }

    public List<Map<String, Object>> getFgjAndFgwDtl(List<NewHouse> newHouses, String fgwUrl) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(newHouses)) {
            for (NewHouse newHouse : newHouses) {
                Map<String, Object> dtlMap = new HashMap<>();
                //房管局详情
                NewHouseDtl newHouseDtl = getFgjDtl(newHouse);
                //发改委Page
                Map<String, Object> fgwMap = getFgwPage(newHouse.getEntryName(), fgwUrl);
                //发改委详情
                FgwProjectDtl fgwProjectDtl = new FgwProjectDtl();
                String projectUrl = fgwMap.get("projectUrl") + "";
                if (!"-".equals(projectUrl)) {
                    fgwProjectDtl = getFgwDtl(projectUrl);
                }
                dtlMap.put("fgjDtl", newHouseDtl);
                dtlMap.put("fgwPage", fgwMap);
                dtlMap.put("fgwDtl", fgwProjectDtl);
                list.add(dtlMap);
            }
        }
        return list;
    }


    /**
     * 获取房产局明日、未来几日房产上新列表
     *
     * @param tomorrowStr
     * @return
     */
    public Map<String, Object> getFgjList(String tomorrowStr, String fgjUrl, String status) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("djzt", status));
        String html = httpClientUtil.postMethod(fgjUrl, paramList, null);

        Map<String, Object> tableMap = new HashMap<>();
        List<NewHouse> tomorrowList = new ArrayList<>();
        List<NewHouse> futureList = new ArrayList<>();
        if (StringUtils.isNotEmpty(html)) {
            Document doc = Jsoup.parse(html);
            Elements trs = doc.select("table").select("tr");
            if (!"暂无数据".equals(trs.get(1).select("td").get(0).text().trim())) {
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    NewHouse newHouse = new NewHouse();
                    newHouse.setEntryName(tds.get(0).select("a").text().trim());
                    newHouse.setEntryUrl(fgjUrl + tds.get(0).select("a").attr("href"));
                    newHouse.setBuilding(tds.get(1).text().trim());
                    newHouse.setEnterpriseName(tds.get(2).text().trim());
                    newHouse.setRegion(tds.get(3).text().trim());
                    String time = tds.get(4).select("div").eq(0).text().trim() + tds.get(4).select("div").eq(1).text().replace("&nbsp", "").trim();
                    newHouse.setStartAndEndTime(time);
                    newHouse.setSuppliedTotal(tds.get(5).text().trim());
                    if (time.contains(tomorrowStr)) {
                        tomorrowList.add(newHouse);
                    } else {
//                        if (date == 1) {
                        futureList.add(newHouse);
//                        }
                    }
                }
            }
            tableMap.put("tomorrowList", tomorrowList);
            tableMap.put("futureList", futureList);
        }
        return tableMap;
    }


    /**
     * 获取房管局明细
     *
     * @return
     */
    public NewHouseDtl getFgjDtl(NewHouse newHouse) {
        NewHouseDtl newHouseDtl = new NewHouseDtl();
        String html = httpClientUtil.getMethod(newHouse.getEntryUrl());
        assert html != null;
        Document doc = Jsoup.parse(html);
        Elements dls = doc.getElementsByClass("info").select("dl");
        //获取基本信息
        Elements jbxx = dls.get(0).select("dd");
        String company = jbxx.get(0).text();
        String region = jbxx.get(1).text();
        String total = jbxx.get(2).text();
        String needTotal = jbxx.get(3).text();
        String entryName = jbxx.get(4).text();
        String phone = jbxx.get(5).text();
        String salesAddress = jbxx.get(6).text();

        //获取登记信息
        String djdz = dls.get(1).select("a").eq(0).attr("href");

        //获取二维码
        String erwm = dls.get(2).select("img").eq(0).attr("src");

        //获取楼幢
//        List<Building> buildings = new ArrayList<>();
//        Elements lz = doc.getElementsByTag("table").eq(0).select("a");
//        for (Element buildingUrl : lz) {
//            Building building = new Building();
//            building.setBuildingUrl(baseUrl + buildingUrl.attr("href"));
//            buildings.add(building);
//        }

        newHouseDtl.setBuilding(newHouse.getBuilding());
        newHouseDtl.setCompany(company.substring(company.indexOf("：") + 1).trim());
        newHouseDtl.setRegion(region.substring(region.indexOf("：") + 1).trim());
        newHouseDtl.setTotal(total.substring(total.indexOf("：") + 1).trim());
        newHouseDtl.setNeedTotal(needTotal.substring(needTotal.indexOf("：") + 1).trim());
        newHouseDtl.setEntryName(entryName.substring(entryName.indexOf("：") + 1).trim());
        newHouseDtl.setPhone(phone.substring(phone.indexOf("：") + 1).trim());
        newHouseDtl.setSalesAddress(salesAddress.substring(salesAddress.indexOf("：") + 1).trim());
        newHouseDtl.setRegisteredAddress(djdz);
        newHouseDtl.setErWeiMa(erwm);
//        newHouseDtl.setBuildings(buildings);
        return newHouseDtl;
    }

    /**
     * 获取发改委列表\价格区间
     * txtspname 房产名称
     *
     * @return
     */
    public Map<String, Object> getFgwPage(String txtspname, String fgwUrl) {
        Map<String, Object> fgwPage = new HashMap<>();

        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("txtspname", txtspname));
        paramsList.add(new BasicNameValuePair("Button1", "查询"));
        paramsList.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwULLTE5MjcwMjg3NzgPZBYCAgMPZBYEAgsPFgIeC18hSXRlbUNvdW50AgoWFGYPZBYCZg8VCAQ3ODE0CTIwMjEwNTA0MwQ3ODE0DOeGmeWyuOmbhei9qRRZMSxZMixZMyxZNSxZNixZNyxZOAgzNjc1OC42NQMzMjYJMTUsNzY5LjkxZAIBD2QWAmYPFQgENzgxNQkyMDIxMDUwNDQENzgxNQnlh6/kuZDovakBMQgxOTA1Ny4yNwMyMjEJMjEsMzk5LjcyZAICD2QWAmYPFQgENzgxMwkyMDIxMDUwNDAENzgxMwzpm43lrrnpm4Xoi5EFQTgsQTkHNzE1Ny4yMAI0OAkyMyw1MjcuMzdkAgMPZBYCZg8VCAQ3ODA3CTIwMjEwNTAzNAQ3ODA3EuaWh+S4gOWhmOa6qua0pemXqAIxMwgxMDg1NS42MQI2OQkyNCw0OTkuMjhkAgQPZBYCZg8VCAQ3NzYxCTIwMjEwNTAzNgQ3NzYxD+WFieW5tOS4lue6quWfjgM1LDcIMjQ3MTYuNTQDMTk2CTE5LDY5OS45N2QCBQ9kFgJmDxUIBDc3NjMJMjAyMTA1MDM3BDc3NjMP5YWJ5bm05LiW57qq5Z+OCDEsOCw5LDEyCDM1MTI0LjQ5AzIxMgkyMiwxOTcuMjdkAgYPZBYCZg8VCAQ3Nzk1CTIwMjEwNTAzNQQ3Nzk1Eua5luWyuOa+nOW6reS6jOacnxdWMjMsVjUyLFY1MyxWNTUsVjU2LFY1NwgxMTI0Ny43NwIzOAkyMSwyMDguOTdkAgcPZBYCZg8VCAQ3NzY1CTIwMjEwNDA1MQQ3NzY1DOS4nOacm+mbheiLkQ5HMDMjLEcwNSMsRzExIwgzMTgyNS4wNAMzNDIJMjAsNzQ5LjkwZAIID2QWAmYPFQgENzczMQkyMDIxMDQwNDUENzczMQnpm4Xlraboi5ECMTEHNzU1Ny40OQI2NgkxNCwzNjYuMTFkAgkPZBYCZg8VCAQ3Nzc4CTIwMjEwNDA0NgQ3Nzc4DOiKuOi+ieiKseWbrQUxMSwxOQgxMDkwNS4wMwMxMTIJMTQsOTk5LjkyZAINDw8WBB4IUGFnZVNpemUCCh4LUmVjb3JkY291bnQCqhpkZGQtVB3ezohHwDrGIV6kbpk1zquXdg=="));
        paramsList.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWBgKH466ACgK38b7MBgLKwJ/YAQKq7uP1CgKt7uP1CgKM54rGBiygyQe6e5eBPemeNFUTZb1RBDaA"));

        List<Map<String, String>> headerList = new ArrayList<Map<String, String>>();
        headerList.add(new HashMap<String, String>() {{
            put("name", "Content-Type");
            put("value", "application/x-www-form-urlencoded");
        }});

        String html = httpClientUtil.postMethod(fgwUrl + "/fangjia/ws/DefaultList.aspx", paramsList, headerList);

        assert html != null;

        Document doc = Jsoup.parse(html);
        Elements trs = doc.getElementById("top").select("tr");
        if (trs.size() > 3) {
            List<FgwProject> list = new ArrayList<>();
            Double minPrice = Double.parseDouble(trs.get(2).select("td").get(5).text().replace(",", "").replace("&nbsp", "").replace(";", "").trim());
            Double maxPrice = Double.parseDouble(trs.get(2).select("td").get(5).text().replace(",", "").replace("&nbsp", "").replace(";", "").trim());

            for (int i = 2; i < trs.size() - 1; i++) {
                FgwProject fgwProject = new FgwProject();
                Elements element = trs.get(i).select("td");
                String recordNum = element.get(0).select("a").eq(0).text().trim();
                String projectUrl = element.get(1).select("a").eq(0).attr("href");
                String projectName = element.get(1).select("a").eq(0).text().trim();
                String buildingNo = element.get(2).text().trim();
                String buildUpArea = element.get(3).text().trim();
                String setNum = element.get(4).text().trim();
                Double averagePrice = Double.parseDouble(element.get(5).text().replace(",", "").replace("&nbsp", "").replace(";", "").trim());
                fgwProject.setProjectName(projectName);
                fgwProject.setProjectUrl(fgwUrl + "/fangjia/ws/" + projectUrl);
                fgwProject.setAveragePrice(averagePrice + "");
                fgwProject.setBuildingNo(buildingNo);
                fgwProject.setRecordNum(recordNum);
                fgwProject.setSetNum(setNum);
                fgwProject.setBuildUpArea(buildUpArea);
                list.add(fgwProject);

                if (minPrice > averagePrice) {
                    minPrice = averagePrice;
                }
                if (maxPrice < averagePrice) {
                    maxPrice = averagePrice;
                }
            }

            if (CollectionUtils.isNotEmpty(list)) {
                fgwPage.put("projectName", list.get(0).getProjectName());
                fgwPage.put("projectUrl", list.get(0).getProjectUrl());
                String avgPrice = minPrice + "-" + maxPrice + "(元/m²)";
                fgwPage.put("avgPrice", avgPrice);
            }
        } else {
            fgwPage.put("projectName", "-");
            fgwPage.put("projectUrl", "-");
            fgwPage.put("avgPrice", "-");
        }
        return fgwPage;
    }


    /**
     * 获取发改委详情
     *
     * @return
     */
    public FgwProjectDtl getFgwDtl(String projectUrl) {
        String str = httpClientUtil.getMethod(projectUrl);
        assert str != null;
        Document document = Jsoup.parse(str);
        String zlwz = document.getElementById("txtLpLocation").text();
        String szqy = document.getElementById("txtLpArea").text();
        String wylb = document.getElementById("txtLpwyType").text();
        String jzlx = document.getElementById("txtLpBuildType").text();
        String kfqy = document.getElementById("txtLpkfEnterprise").text();
        String sjdw = document.getElementById("txtLpsjEnterprise").text();
        String wygs = document.getElementById("txtLpwyEnterprise").text();
        String zbpt = document.getElementById("txtLpRim").text().replace("\"", "");
        String rjl = document.getElementById("txtLpFloorAreaRatio").text();
        String lhl = document.getElementById("txtLpGreeningRate").text();
        String jdcw = document.getElementById("txtCDistancePrice").text();
        String jtzk = document.getElementById("txtLpTraffic").text().replace("\"", "");
        FgwProjectDtl fgwProjectDtl = new FgwProjectDtl();
        fgwProjectDtl.setJtzk(jtzk);
        fgwProjectDtl.setJzlx(jzlx);
        fgwProjectDtl.setKfqy(kfqy);
        fgwProjectDtl.setSjdw(sjdw);
        fgwProjectDtl.setSzqy(szqy);
        fgwProjectDtl.setWygs(wygs);
        fgwProjectDtl.setWylb(wylb);
        fgwProjectDtl.setJzlx(jzlx);
        fgwProjectDtl.setZlwz(zlwz);
        fgwProjectDtl.setZbpt(zbpt);
        fgwProjectDtl.setRjl(rjl);
        fgwProjectDtl.setLhl(lhl + "%");
        fgwProjectDtl.setJdcw(jdcw + "%");
        return fgwProjectDtl;
    }

    public String getDate(int x) {
        //获取明天的时间
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.DATE, x);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 标题
     *
     * @param tomorrowStr
     * @param newHouses
     * @return
     */
    public String getTitle(String tomorrowStr, List<NewHouse> newHouses) {
        String title = tomorrowStr + "期合肥房产登记打新啦，";
        if (CollectionUtils.isNotEmpty(newHouses)) {
            if (newHouses.size() == 1) {
                title += "[" + newHouses.get(0).getEntryName() + "]是你心仪的房产吗？";
            } else {
                title += "明日或许有大肉~";
            }
        }
        return title;
    }


    /**
     * 摘要
     *
     * @param tomorrowStr
     * @param newHouses
     * @return
     */
    public String getSummary(String tomorrowStr, List<NewHouse> newHouses) {
        StringBuilder remark = new StringBuilder();
        String region0 = "";
        StringBuilder entryName = new StringBuilder();
        String entryName0 = "";
        String summary = tomorrowStr + "期房产上新，";
        if (CollectionUtils.isNotEmpty(newHouses)) {
            region0 = newHouses.get(0).getRegion();
            remark = new StringBuilder(region0);

            entryName0 = newHouses.get(0).getEntryName();
            entryName = new StringBuilder(entryName0);
            for (NewHouse newHouse : newHouses) {
                if (!region0.equals(newHouse.getRegion())) {
                    remark.append("、").append(newHouse.getRegion());
                    region0 = newHouse.getRegion();
                }
                if (!entryName0.equals(newHouse.getEntryName())) {
                    entryName.append("、").append(newHouse.getEntryName());
                    entryName0 = newHouse.getEntryName();
                }
            }
            summary += "涉及区域有：" + remark + "，涉及房产有：" + entryName + "。";
        }
        return summary;
    }

    public Map<String, String> getRegionAndEntry(List<NewHouse> newHouses) {
        Map<String, String> returnMap = new HashMap<>();
        StringBuilder remark = new StringBuilder();
        String region0 = "";
        StringBuilder entryName = new StringBuilder();
        String entryName0 = "";
        if (CollectionUtils.isNotEmpty(newHouses)) {
            region0 = newHouses.get(0).getRegion();
            remark = new StringBuilder(region0);

            entryName0 = newHouses.get(0).getEntryName();
            entryName = new StringBuilder(entryName0);
            for (NewHouse newHouse : newHouses) {
                if (!region0.equals(newHouse.getRegion())) {
                    remark.append("、").append(newHouse.getRegion());
                    region0 = newHouse.getRegion();
                }
                if (!entryName0.equals(newHouse.getEntryName())) {
                    entryName.append("、").append(newHouse.getEntryName());
                    entryName0 = newHouse.getEntryName();
                }
            }
        }
        returnMap.put("remark", remark.toString());
        returnMap.put("entryName", entryName.toString());
        return returnMap;
    }
}
