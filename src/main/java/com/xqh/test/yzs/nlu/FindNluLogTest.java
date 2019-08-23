package com.xqh.test.yzs.nlu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xqh.utils.DateUtil;
import com.xqh.utils.ExcelExportUtil;
import com.xqh.utils.ReadTxtFileUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import scala.util.matching.Regex;
import scala.util.matching.Regex.Match;

import java.awt.datatransfer.FlavorListener;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName FindNluLogTest
 * @Description 提取nlu日志信息
 * @Author xuqianghui
 * @Date 2019/8/22 11:13
 * @Version 1.0
 */
@Slf4j
public class FindNluLogTest {

    private static List<String> matchList = Arrays.asList("NLU url:", "nlu result:");

    private static String pattern = "yyyy-MM-dd HH:mm:ss";

    private static Date start = DateUtil.parseDate("2019-08-22 22:00:00", pattern);
    private static Date end = DateUtil.parseDate("2019-08-23 01:30:00", pattern);

    private static Map<String, MatchResult> logMap = Maps.newConcurrentMap();

    private static List<String> udidList = Arrays.asList(
            "LTE3Nzg0MjcyMTUwMEVDRTE1NEYwMjZCRA",
            "LTY2OTg5Mjk4NTAwRUNFMTU0RjAyNkM1",
            "LTY2OTg5Mjk4NTAwRUNFMTU0RjAyNkJE",
            "LTE3Nzg0MjcyMTUwMEVDRTE1NEYwMjZDNQ");

    private final static String regex = "(\\[).*?(\\])";

    public static void main(String[] args) {
//        filterLogByTime();
        exportExcel();

    }

    public static String getRequestId(String log){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(log);
        String ret = null;
        while (m.find()){
            ret = m.group().substring(1, m.group().length()-1);
            break;
        }
        return  ret;
    }



    public static void filterLogByTime(){
        List<String> list = ReadTxtFileUtils.readTxt(new File("E:\\secureCRT_file\\download\\webapi_debug.log"));
        List<String> writeList = Lists.newArrayList();
        for(String str:list){
            if(StringUtils.hasText(str) && str.length() > 19){
                String time = str.substring(0, 19);
                Date d = null;
                try {
                    d = DateUtil.parseDate(time, pattern);
                }catch (Exception e){

                }
                if(Objects.nonNull(d) && d.compareTo(start) >=0 && d.compareTo(end) <=0){
                    writeList.add(str);
                }
            }
        }
        String outTxt = "E:\\secureCRT_file\\download\\out.txt";
        ReadTxtFileUtils.writeToTxt(writeList, outTxt);
    }


    /**
     * 导出日志分析结果
     */
    public static void exportExcel(){
        List<String> list = ReadTxtFileUtils.readTxt(new File("E:\\secureCRT_file\\download\\out.txt"));
        List<MatchResult> retList = Lists.newArrayList();
        for(String str:list){
            if(str.indexOf(matchList.get(0)) != -1){
                String time = str.substring(0, 19);
                String nluUrl = str.substring(str.indexOf(matchList.get(0))+matchList.get(0).length());
                String reqId = getRequestId(str);
                logMap.put(reqId, MatchResult.builder()
                        .time(DateUtil.parseDate(time, pattern))
                        .requestId(reqId)
                        .nluUrl(nluUrl).build());
            }
            if(str.indexOf(matchList.get(1)) != -1){
                String nluRet = str.substring(str.indexOf(matchList.get(1))+matchList.get(1).length());
                String reqId = getRequestId(str);
                if(logMap.containsKey(reqId)){
                    MatchResult last = logMap.get(reqId);
                    JSONObject nluJson = JSON.parseObject(nluRet);
                    last.setNluRet(nluRet);
                    if(Objects.nonNull(nluJson) && nluJson.containsKey("rc")){
                        last.setSuccess("成功");
                        if(nluJson.containsKey("text")){
                            last.setReqTxt(nluJson.getString("text"));
                        }
                    }
                }
            }
        }
        //遍历 map
        for(Entry<String, MatchResult> entry:logMap.entrySet()){
            retList.add(entry.getValue());
        }

        retList.sort((a,b)-> a.getTime().compareTo(b.getTime()));// 时间倒序

        // 根据udid过滤数据

        retList = retList.stream().filter(r-> {
            boolean flag = false;
            for(String udid:udidList){
                if(r.getNluUrl().contains(udid)){
                    flag = true;
                    break;
                }
            }
            return flag;
        }).collect(Collectors.toList());

        String[] headers = {"时间", "请求文本", "requestId", "是否成功", "nlu请求", "nlu结果"};
        String[] properties = {"timeStr", "reqTxt", "requestId", "success", "nluUrl", "nluRet"};
        String filePath = "E:\\secureCRT_file\\download\\out4";

//        List<MatchResult> filterList = retList.stream().filter(r-> {
//            Date d = DateUtil.parseDate(r.getTime(), pattern);
//            return d.compareTo(start) >=0 && d.compareTo(end) <=0;
//        }).collect(Collectors.toList());
        ExcelExportUtil.export(headers, properties, retList, false, filePath);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MatchResult{

        private String requestId;

        private Date time;

        private String timeStr;

        private String success;

        private String reqTxt;

        private String nluUrl;

        private String nluRet;

        public String getTimeStr() {
            return DateUtil.formatDate(time, pattern);
        }
    }

}
