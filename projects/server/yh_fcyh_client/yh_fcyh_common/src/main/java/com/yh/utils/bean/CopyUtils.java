
package com.yh.utils.bean;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CopyUtils {

    private static final Log logger = LogFactory.getLog(CopyUtils.class);

    private static ConvertUtilsBean convert;

    private static BeanUtilsBean beanUtils;

    static{
        convert = new ConvertUtilsBean();
        convert.register(new Converter(){
            public Object convert(Class type, Object value) {
                if(value == null)
                    return null;
                if(value instanceof String){
                    if(StringUtils.isBlank((String) value)){
                        return null;
                    }
                    if(StringUtils.equalsIgnoreCase((String) value, "null")){
                        return null;
                    }
                    try{
                        // 各种日期转换
                        return DateUtils.parseDate(String.valueOf(value), new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH" });
                    }catch(IllegalArgumentException e){
                        throw new ConversionException("ill date patten:" + value);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                }
                return value;
            }
        }, Date.class);

        convert.register(new Converter(){
            public Object convert(Class type, Object value) {
                if(value == null)
                    return null;
                if(value instanceof String){
                    if(StringUtils.isBlank((String) value)){
                        return null;
                    }
                    if(StringUtils.equalsIgnoreCase((String) value, "null")){
                        return null;
                    }
                    try{
                        return new BigDecimal(String.valueOf(value));
                    }catch(Exception e){
                        return null;
                    }
                }
                return value;
            }
        }, BigDecimal.class);

        beanUtils = new BeanUtilsBean(convert, new PropertyUtilsBean());

    }

    /**
     * 将orig对象Copy到dest对象
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig) {
        try{
            beanUtils.copyProperties(dest, orig);
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(InvocationTargetException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据前缀从Request中创建对象
     * @param <T>
     * @param clz
     * @param request
     * @param var   前缀   user. 例： user.username,user.sex
     * @return
     */
    public static <T> T getBean(Class<T> clz, HttpServletRequest request, String var) {
        T t = null;
        try{
            t = clz.newInstance();
            copyToObject(t, request, var);
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 拷贝request的内容到指定的对象<br>
     * per.name,per.sex,per.age Person person = new Person();<br>
     * this.copyToObject(person,request,"per");<br>
     * @param obj
     * @param request
     * @param var person.name ----> var = person
     */
    public static void copyToObject(Object obj, HttpServletRequest request, String var) {
        try{
            Map<String, String> map = convertString(request, var);
            beanUtils.copyProperties(obj, map);
        }catch(IllegalAccessException e){
            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }catch(InvocationTargetException e){
            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }
    }

    /**
     * 将request的内容，copy到对象
     * @param <T>
     * @param clz
     * @param request
     * @return
     */
    public static <T> T getBean(Class<T> clz, HttpServletRequest request) {
        T t = null;
        try{
            t = clz.newInstance();
            copyToObject(t, request);
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 拷贝request的内容到指定的对象<br>
     * Person person = new Person();<br>
     * this.copyToObject(person,request);<br>
     * @param obj <br>
     * @param request <br>
     */
    public static void copyToObject(Object obj, HttpServletRequest request) {
        try{
            Map<String, String> map = convertString(request);

            beanUtils.copyProperties(obj, map);
        }catch(IllegalAccessException e){

            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }catch(InvocationTargetException e){

            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }
    }

    public static void copySmapToObject(Object obj, Map<String, String> map) {

        try{
            beanUtils.copyProperties(obj, map);
        }catch(IllegalAccessException e){

            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }catch(InvocationTargetException e){

            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }

    }

    /**
     * 拷贝map中的值到指定的对象<br>
     * Person person = new Person();<br>
     * map.put("name","xxxxx");<br>
     * copyToObject(person,map)<br>
     * @param obj <br>
     * @param map <br>
     */
    public static void copyToObject(Object obj, Map<String, Object> map) {

        try{
            beanUtils.copyProperties(obj, map);
        }catch(IllegalAccessException e){

            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }catch(InvocationTargetException e){

            logger.debug("Map中的数据Copy到对象出错误！");
            throw new RuntimeException("Map中的数据Copy到对象出错误！", e);
        }

    }

    /**
     * 将Map<String,String> 转换象 Map<String,Object><br>
     * @param qm <br>
     * @return
     */
    public static Map<String, Object> copyMap(Map<String, String> qm) {
        Map<String, Object> params = new HashMap<String, Object>();
        for(Iterator<Entry<String, String>> iter = qm.entrySet().iterator(); iter.hasNext();){
            Entry<String, String> e = (Entry<String, String>) iter.next();
            params.put(e.getKey(), e.getValue());
        }
        return params;
    }

    /**
     * Map<String,String> 转换成 Map
     * @param qm
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map copyToNewMap(Map qm) {
        Map params = new HashMap();
        for(Iterator iter = qm.entrySet().iterator(); iter.hasNext();){
            Entry<String, String> e = (Entry<String, String>) iter.next();
            params.put(e.getKey(), e.getValue());
        }
        return params;
    }

    /**
     * 将request接受的参数转换Map形式
     * @param request
     * @return
     */
    public static Map<String, Object> copyMap(HttpServletRequest request) {
        return copyMap(convertString(request));
    }

    /**
     * 将request中以var为前缀的单独转换成Map
     * @param request
     * @param var
     * @return
     */
    public static Map<String, Object> copyMap(HttpServletRequest request, String var) {
        return copyMap(convertString(request, var));
    }

    /**
     * 取request中的内容，以键值对返回Map<br>
     * 以"per"开头的name的value<br>
     * Map map = convertString(request,"per");<br>
     * @param request <br>
     * @param var <br>
     * @return<br>
     */
    public static Map<String, String> convertString(HttpServletRequest request, String var) {
        Map<String, String> map = new HashMap<String, String>();
        var = var.trim() + ".";
        for(Iterator iter = request.getParameterMap().entrySet().iterator(); iter.hasNext();){
            Entry e = (Entry) iter.next();
            String key = String.valueOf(e.getKey());
            String value = ServletRequestUtils.getStringParameter(request, key, "");
            if(StringUtils.isNotBlank(key) && StringUtils.startsWith(key, var) && !StringUtils.isBlank(value)){
                key = StringUtils.substringAfter(key, var);
                map.put(key, value);
            }
        }
        return map;

    }

    /**
     * @param obj
     * @return
     */
    public static Map convertToMap(Object obj) {
        try{
            return beanUtils.describe(obj);
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(InvocationTargetException e){
            e.printStackTrace();
        }catch(NoSuchMethodException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取request中的内容，以键值对返回Map<br>
     * Map map = convertString(request);<br>
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> convertString(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        for(Iterator iter = request.getParameterMap().entrySet().iterator(); iter.hasNext();){
            Entry e = (Entry) iter.next();
            String key = String.valueOf(e.getKey());
            String value = ServletRequestUtils.getStringParameter(request, key, "");

            if(!StringUtils.isBlank(value)){
                map.put(key, value);
            }
        }
        return map;
    }

}
