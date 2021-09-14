package com.yh.service;

import java.util.Map;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/07/31 07:50
 **/
public interface FangChanYaoHaoService {

    Map<String, Object> getMsg(String fgjUrl, String fgwUrl, int date, String status);

    Map<String, Object> getFgwByProjectName(String fgwUrl, String txtspname);
}
