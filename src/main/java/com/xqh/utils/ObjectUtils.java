package com.xqh.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 对象工具类
 */
public class ObjectUtils {


    public static <T> T parseObject(String json,Class<T> clz){
        if(StringUtils.isNotEmpty(json)){
            return JSON.parseObject(json,clz);
        }
        return null;
    }

    /**
     * Copy对象
     * 经测试 通过fastJson 序列化 比 该方法高效
     * @param resourceObj
     * @param targetObject
     * @param <R>
     * @param <T>
     * @return
     */
    @Deprecated
    public static <R,T> T copyObject(R resourceObj,T targetObject){
        BeanUtils.copyProperties(resourceObj,targetObject);
        return targetObject;
    }


    /**
     * 通过fastJson copy对象
     * @param resourceObj
     * @param clz
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R,T> T copyObject(R resourceObj,Class<T> clz){
        return JSON.parseObject(JSON.toJSONString(resourceObj),clz);
    }

    /**
     * copy list
     * @param rList
     * @param clz
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R,T> List<T> copyListObject(List<R> rList,Class<T> clz){
        return JSONArray.parseArray(JSON.toJSONString(rList),clz);
    }

    /**
     * 将对象转map 用于dto转查询参数
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<String,Object> changeToMap(T obj){
        if(null != obj){
            Map<String,Object> params=copyObject(obj,Map.class);
            /**
             * 字符串去空格符
             */
            for(String key:params.keySet()){
                Object object=params.get(key);
                if(object instanceof String){
                    object=((String) object).trim();
                    params.put(key,object);
                }
            }
            return params;
        }
        return null;
    }

    /**
     * 根据key获取value 处理 key为 obj.objItem.key 这种情况
     * @param map
     * @param key
     * @return
     */
    public static String getMapValue(Map map,String key){
        if(map.containsKey(key)){
            return map.get(key).toString();
        }
        if(key.contains(".")){
            String subKey=key.substring(0,key.indexOf("."));
            if(null != map && map.containsKey(subKey)){
                Map subMap= changeToMap(map.get(subKey));
                key=key.substring(key.indexOf(".")+1);//去除 第一个key
                return getMapValue(subMap,key);
            }
        }else{
            if(null != map && map.containsKey(key)){
                return map.get(key).toString();
            }
        }
        return "";
    }

    /**
     * 对时间格式化 (时间戳格式)
     * @param map
     * @param key
     */
    public static void mapFormatDate(Map<String,Object> map,String key){
        String dateVal=getMapValue(map,key);
        if(org.springframework.util.StringUtils.hasText(dateVal)){
            Date date=new Date(Long.valueOf(dateVal));
            if(null != date){
                map.put(key, DateUtil.formatDate(date,DateUtil.DATE_PATTERN_COMPLEX));
            }
        }
    }

    /**
     * 对集合 进行分组操作
     *   用于 数据量较大 分批处理
     * @param list
     * @param periodSize  分组长度
     * @param eachIndex   当前循环序号
     * @param <T>
     * @return
     */
    public static <T> List<T> getPeriodList(List<T> list,int periodSize, int eachIndex){
        //集合为空 或者 长度 小于 分段长度 不处理
        if(CollectionUtils.isEmpty(list) || list.size() <= periodSize){
            return list;
        }

        /**
         * 循环 序号 小于0 或者 大于 最大 值
         */
        if(eachIndex < 0 || eachIndex > list.size() / periodSize){
            return null;
        }

        if(eachIndex != list.size()/periodSize){
            int start = eachIndex * periodSize;
            int end = (eachIndex+1)*periodSize - 1;
            return getListByBetweenSize(list,start,end);
        }else{
            if(list.size() % periodSize == 0){
                //数组长度 是 分段长度 的整数倍 最后一次循环不做处理
                return null;
            }else{
                int start = eachIndex * periodSize;
                int end = list.size() - 1;
                return getListByBetweenSize(list,start,end);
            }
        }

    }

    public static <T> List<T> getListByBetweenSize(List<T> list, int start, int end){
        if(!CollectionUtils.isEmpty(list) && list.size() >= end + 1 && end >= start ){
            return list.subList(start,end+1);
        }
        return null;
    }

}
