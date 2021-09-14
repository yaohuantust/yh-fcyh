
package com.yh.utils.collection;

import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Date;
import java.util.*;

import static com.alibaba.fastjson.util.TypeUtils.*;

@SuppressWarnings("unchecked")
public class MapView {

    private Map map;

    public MapView(Map map) {
        if(null == map){
            this.map = new LinkedHashMap();
        }
        this.map = map;
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object obj = map.get(key);
        return TypeUtils.castToJavaBean(obj, clazz);
    }

    public Boolean getBoolean(String key, Boolean def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        Boolean b = castToBoolean(value);
        if(null == b){
            return def;
        }
        return b;
    }

    public byte[] getBytes(String key) {
        Object value = map.get(key);

        if(value == null){
            return null;
        }

        return castToBytes(value);
    }

    public boolean getBooleanValue(String key, boolean def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }
        return castToBoolean(value).booleanValue();
    }

    public Byte getByte(String key) {
        Object value = map.get(key);

        return castToByte(value);
    }

    public byte getByteValue(String key) {
        Object value = map.get(key);

        if(value == null){
            return 0;
        }

        return castToByte(value).byteValue();
    }

    public Short getShort(String key, Short def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        Short s = castToShort(value);
        if(null == s){
            return def;
        }
        return s;
    }

    public short getShortValue(String key, short def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        return castToShort(value).shortValue();
    }

    public Integer getInteger(String key, Integer def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        Integer i = castToInt(value);
        if(null == i){
            return def;
        }
        return i;
    }

    public int getIntValue(String key, int def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        return castToInt(value).intValue();
    }

    public Long getLong(String key, Long def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        Long l = castToLong(value);
        if(null == l){
            return def;
        }
        return l;
    }

    public long getLongValue(String key, long def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        return castToLong(value).longValue();
    }

    public Float getFloat(String key, Float def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        Float f = castToFloat(value);
        if(null == f){
            return def;
        }
        return f;
    }

    public float getFloatValue(String key, float def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        return castToFloat(value).floatValue();
    }

    public Double getDouble(String key, Double def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        Double d = castToDouble(value);
        if(null == d){
            return def;
        }
        return d;
    }

    public double getDoubleValue(String key, double def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        return castToDouble(value).floatValue();
    }

    public BigDecimal getBigDecimal(String key, BigDecimal def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        BigDecimal b = castToBigDecimal(value);
        if(null == b){
            return def;
        }
        return b;
    }

    public BigInteger getBigInteger(String key, BigInteger def) {
        Object value = map.get(key);
        if(null == value){
            return def;
        }
        BigInteger b = castToBigInteger(value);
        if(null == b){
            return def;
        }
        return b;
    }

    public String getString(String key, String def) {
        Object value = map.get(key);

        if(value == null){
            return def;
        }

        return value.toString();
    }

    public Date getDate(String key, Date def) {
        Object value = map.get(key);
        Date d = castToDate(value);
        if(null == d){
            return def;
        }
        return d;
    }

    public java.sql.Date getSqlDate(String key, java.sql.Date def) {
        Object value = map.get(key);

        java.sql.Date d = castToSqlDate(value);
        if(null == d){
            return def;
        }
        return d;
    }

    public Timestamp getTimestamp(String key, Timestamp def) {
        Object value = map.get(key);

        Timestamp t = castToTimestamp(value);
        if(null == t){
            return def;
        }
        return t;
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set entrySet() {
        return map.entrySet();
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set keySet() {
        return map.keySet();
    }

    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    public void putAll(Map t) {
        map.putAll(t);
    }

    public Object remove(Object key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Collection values() {
        return map.values();
    }

    public static List<Map<String, Object>> toListMap(ResultSet rs, boolean isAttr, boolean isClose) throws SQLException {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapOfColValues = null;
        if(null != rs){
            try{
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while(rs.next()){
                    mapOfColValues = new HashMap<String, Object>();
                    for(int i = 1; i <= columnCount; i++){
                        String key = getColumnKey(rsmd, i);
                        if(isAttr){
                            key = convertUnderscoreNameToPropertyName(key);
                        }
                        Object obj = getResultSetValue(rs, i);
                        mapOfColValues.put(key, obj);
                    }
                    dataList.add(mapOfColValues);
                }
            }finally{
                if(isClose){
                    rs.close();
                }
            }
        }
        return dataList;
    }

    protected static String convertUnderscoreNameToPropertyName(String name) {
        StringBuffer result = new StringBuffer();
        boolean nextIsUpper = false;
        if(name != null && name.length() > 0){
            if(name.length() > 1 && name.substring(1, 2).equals("_")){
                result.append(name.substring(0, 1).toUpperCase());
            }else{
                result.append(name.substring(0, 1).toLowerCase());
            }
            for(int i = 1; i < name.length(); i++){
                String s = name.substring(i, i + 1);
                if(s.equals("_")){
                    nextIsUpper = true;
                }else{
                    if(nextIsUpper){
                        result.append(s.toUpperCase());
                        nextIsUpper = false;
                    }else{
                        result.append(s.toLowerCase());
                    }
                }
            }
        }
        return result.toString();
    }

    protected static String getAttrName(String columnName, String split) {
        if(StringUtils.isEmpty(columnName)){
            return null;
        }

        String[] strs = StringUtils.splitByWholeSeparator(columnName, split);
        StringBuffer sbf = new StringBuffer();
        for(int i = 0; i < strs.length; i++){
            if(i == 0){
                sbf.append(StringUtils.lowerCase(strs[i]));
            }else{
                sbf.append(StringUtils.capitalize(StringUtils.lowerCase(strs[i])));
            }
        }
        return sbf.toString();
    }

    protected static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
        Object obj = rs.getObject(index);
        String className = null;
        if(obj != null){
            className = obj.getClass().getName();
        }
        if(obj instanceof Blob){
            obj = rs.getBytes(index);
        }else if(obj instanceof Clob){
            obj = rs.getString(index);
        }else if(className != null && ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className))){
            obj = rs.getTimestamp(index);
        }else if(className != null && className.startsWith("oracle.sql.DATE")){
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            if("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)){
                obj = rs.getTimestamp(index);
            }else{
                obj = rs.getDate(index);
            }
        }else if(obj != null && obj instanceof java.sql.Date){
            if("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))){
                obj = rs.getTimestamp(index);
            }
        }
        return obj;
    }

    protected static String getColumnKey(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
        String name = resultSetMetaData.getColumnLabel(columnIndex);
        if(name == null || name.length() < 1){
            name = resultSetMetaData.getColumnName(columnIndex);
        }
        return name;
    }

}
