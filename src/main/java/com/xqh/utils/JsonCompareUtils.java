package com.xqh.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName JsonCompareUtils
 * @Description json内容对比工具
 * @Author xuqianghui
 * @Date 2019/5/6 11:26
 * @Version 1.0
 */
@Slf4j
public class JsonCompareUtils {

    // 不对比的 key
    private static final List<String> filterKeys = Arrays.asList("history", "responseId", "totalTime", "sessionId", "timeCosts", "updateTime");

    // 集合结果size 对比
    private static final List<String> arrayMatchKeys = Arrays.asList("musicinfo","news", "playlist");

    /**
     * 对比 json 内容差异
     * @param json 对比Json对象
     * @param prefixKey json key
     * @param compareStr 被对比 字符
     * @param difMsg 差异内容
     */
    public static void compareJsonObj(JSONObject json, String prefixKey, String compareStr, StringBuilder difMsg){
        for(String key:json.keySet()){
            String currentKey = prefixKey.concat(".").concat(key);
            if(StringUtils.isEmpty(prefixKey)){
                currentKey = key;
            }
            Object obj = json.get(key);
            if (obj.getClass().equals(JSONObject.class)) {

                compareJsonObj(json.getJSONObject(key), currentKey, compareStr, difMsg);

            } else if (obj.getClass().equals(JSONArray.class)) {

                JSONArray array = json.getJSONArray(key);

                if(arrayMatchKeys.contains(key)){
                    // 只对比数组长度
                    compareArraySize(array.size(), compareStr, currentKey, difMsg);
                    continue;
                }

                for(int i=0;i<array.size();i++){
                    Object o = array.get(i);
                    String tmpKey = currentKey.concat("["+i+"]");
                    if(JSONObject.class.equals(o.getClass())){
                        JSONObject jsonObject = (JSONObject) o;
                        compareJsonObj(jsonObject, tmpKey, compareStr, difMsg);
                    }else{
                        // 其他数据类型
                        compareBaseType(o,  compareStr, tmpKey, difMsg);
                    }
                }
            } else {
                //其他类型 数据
                compareBaseType(obj, compareStr, currentKey, difMsg);
            }
        }
    }

    /**
     * 对比 数组长度
     * @param size
     * @param compareStr
     * @param jsonPath
     * @param difMsg
     */
    public static void compareArraySize(int size, String compareStr, String jsonPath, StringBuilder difMsg){
        int compareSize = 0;
        Object compareVal = JSONPath.read(compareStr, jsonPath);
        if(null != compareVal){
            JSONArray parseArray = null;
            try {
                parseArray = JSONArray.parseArray(String.valueOf(compareVal));
            }catch (Exception e){
                log.error("", e);
            }
            if(null != parseArray){
                compareSize = parseArray.size();
            }
        }
        if(size != compareSize){
            difMsg.append("差异key: "+jsonPath+" ,公有云数组长度: "+ size+" , 私有云数组长度: "+compareSize+" .'\n");
        }
    }

    /**
     * 对比 基础类型 内容差异
     * @param obj
     * @param compareStr
     * @param jsonPath
     * @param difMsg
     */
    public static void compareBaseType(Object obj, String compareStr, String jsonPath, StringBuilder difMsg){
        if(checkFilterKey(jsonPath)){
            return;
        }
        String val = String.valueOf(obj);
        Object compareVal = JSONPath.read(compareStr, jsonPath);
        String targetVal = String.valueOf(compareVal);
        if(!Objects.equal(val, targetVal)){
            difMsg.append("差异key: "+jsonPath+", 公有云内容: "+ val +", 私有云内容: "+targetVal+".\n");
        }
    }

    public static boolean checkFilterKey(String key){
        for(String s:key.split("\\.")){
            if(s.endsWith("]")){
                s = s.substring(0, s.indexOf("["));
            }
            if(filterKeys.contains(s)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String text1 = "{\"rc\":0,\"text\":\"开灯\",\"service\":\"cn.yunzhisheng.setting\",\"code\":\"SETTING_EXEC\",\"semantic\":{\"intent\":{\"operations\":[{\"operator\":\"ACT_OPEN_ALL\",\"deviceType\":\"OBJ_LIGHT\",\"deviceExpr\":\"灯\"}]}},\"general\":{\"quitDialog\":\"true\",\"type\":\"T\",\"text\":\"好的，正在为您打开所有灯\"},\"history\":\"cn.yunzhisheng.setting\",\"responseId\":\"861b5345349a46ce93dd344d2396d9dc\"}";
        String text2 = "{\"rc\":0,\"text\":\"打开卧室壁灯\",\"service\":\"cn.yunzhisheng.setting\",\"code\":\"SETTING_EXEC\",\"semantic\":{\"intent\":{\"operations\":[{\"operator\":\"ACT_OPEN\",\"roomType\":\"BED_ROOM\",\"roomExpr\":\"卧室\",\"deviceType\":\"OBJ_LIGHT\",\"deviceExpr\":\"壁灯\"}]}},\"general\":{\"quitDialog\":\"true\",\"type\":\"T\",\"text\":\"好的，正在为您打开卧室壁灯\"},\"history\":\"cn.yunzhisheng.setting\",\"responseId\":\"4a7fa1d119114c5c92bfb4f425c8cc2f\"}";
        JSONObject json = JSON.parseObject(text1);
        StringBuilder diffMsg = new StringBuilder();
        compareJsonObj(json, "", text2, diffMsg);
        System.out.println("diff msg==>"+ diffMsg.toString());
    }
}
