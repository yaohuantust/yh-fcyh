package com.yh.utils.http;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/05/21 21:59
 **/
@Component
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private PoolingHttpClientConnectionManager cm;

    public HttpClientUtil() {
        this.cm = new PoolingHttpClientConnectionManager();
        this.cm.setDefaultMaxPerRoute(20); // 每个主机的最大连接数
        this.cm.setMaxTotal(100); //设置最大连接数
    }

    public String postMethod(String baseUrl, List<NameValuePair> paramList, List<Map<String, String>> headerList) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpPost httpPost = new HttpPost(baseUrl);
        //httpPost.setConfig(getConfig());
        if (CollectionUtils.isNotEmpty(headerList)) {
            for (Map<String, String> header : headerList) {
                httpPost.setHeader(header.get("name"), header.get("value"));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode() && response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            logger.info("postMethod*****UrlEncodedFormEntity*****" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.info("postMethod*****response.close*****" + e);
            }
        }
        return null;
    }


    public String getMethod(String uri) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(uri);
        //httpGet.setConfig(getConfig());
        try {
            response = httpClient.execute(httpGet);
            if (200 == response.getStatusLine().getStatusCode() && response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            logger.info("getMethod------->>execute:" + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.info("getMethod------->>response.close():" + e);
                }
            }
        }
        return null;
    }

//    private RequestConfig getConfig(){
//        return RequestConfig.custom()
//                .setConnectTimeout(5000) // 创建连接的最长时间
//                .setConnectionRequestTimeout(500) // 获取连接的最长时间
//                .setSocketTimeout(10000) // 数据传输的最长时间
//                .build();
//    }
}
