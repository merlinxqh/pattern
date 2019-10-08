package com.xqh.test.yzs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.media.jfxmedia.logging.Logger;
import com.xqh.test.yzs.nlu.NLURequestUtils.ReqNluThread;
import com.xqh.utils.ExcelExportUtil;
import com.xqh.utils.ExcelReader;
import com.xqh.utils.ThreadPoolUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName CheckAudioTest
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/7/12 9:13
 * @Version 1.0
 */
public class CheckAudioTest {

    private final static String tmp_path = "E:\\secureCRT_file\\download\\数据分析\\";

    private final static String filter_str = "----";

    public static void main(String[] args) {
//
//        List<String> errorData = Lists.newArrayList();
////
//        wrapperMethod("2019-09-07", errorData);
//        wrapperMethod("2019-09-08", errorData);
//        wrapperMethod("2019-09-09", errorData);
//        wrapperMethod("2019-09-10", errorData);


//        wrapperMethod("2019-08-07", errorData);
//        wrapperMethod("2019-08-08", errorData);
//        wrapperMethod("2019-08-09", errorData);
//        wrapperMethod("2019-08-10", errorData);
//        wrapperMethod("2019-08-11", errorData);
//        wrapperMethod("2019-08-12", errorData);
//        wrapperMethod("2019-08-13", errorData);
//        wrapperMethod("2019-08-14", errorData);
//        wrapperMethod("2019-08-15", errorData);
//        wrapperMethod("2019-08-06", errorData);
//        wrapperMethod("2019-08-16", errorData);
//        wrapperMethod("2019-08-17", errorData);
//        wrapperMethod("2019-08-18", errorData);
//        wrapperMethod("2019-08-19", errorData);
//        wrapperMethod("2019-08-20", errorData);
//        wrapperMethod("2019-08-21", errorData);
//        wrapperMethod("2019-08-22", errorData);
//        wrapperMethod("2019-08-23", errorData);
//        wrapperMethod("2019-08-24", errorData);
//        wrapperMethod("2019-08-25", errorData);
//        wrapperMethod("2019-08-26", errorData);
//        wrapperMethod("2019-08-27", errorData);

//        System.out.println("error data "+ JSON.toJSONString(errorData));
        String abc = "{\"name\":\"ddd\"}";
        System.out.println(JSONPath.read(abc,"$.nfame").toString());
    }

    public static void wrapperMethod(String date, List<String> errorData){
        findNotExistsAudio(date+"\\mp3",
                date+"\\"+date+".xlsx", errorData, date+"\\"+date+"_new");
    }

    public static void findNotExistsAudio(String filePath, String excelPath, List<String> errorData, String newExcelName){
        List<String> fileList = getFileList(tmp_path.concat(filePath));

        List<String[]> excelData = ExcelReader.getExcelData(new File(tmp_path.concat(excelPath)), 1);

        List<Map<String, Object>> newExcel = Lists.newArrayList();
        for(String[] array:excelData){
            if(null != array && array.length >= 5 && StringUtils.hasText(array[3])){
                Map<String, Object> map = Maps.newHashMap();
                String text = array[0];
                map.put("text", text);
                map.put("udid", array[2]);
                map.put("mp3", array[3]);
                map.put("date", array[4]);
                if(!fileList.contains(array[3])){
                    errorData.add(array[3]);
                }else{
                    newExcel.add(map);
                    if(!text.equals(filter_str)){
                        String reqText = text.startsWith("小茂小茂")?(text.substring(4)):text;
                        FutureTask<String> future = new FutureTask<>(new ReqNluThread(reqText));
                        ThreadPoolUtils.submit(future);
                        map.put("future", future);
                    }else {
                        map.put("nluRet", filter_str);
                    }
                }
            }
        }

        for(Map<String, Object> map:newExcel){
            if(map.containsKey("future")){
                FutureTask<String> future = (FutureTask<String>) map.get("future");
                try {
                    map.put("nluRet", future.get());
                } catch (Exception e) {
                    System.out.println("error"+e.getMessage());
                }
            }
        }

        // 重新生成excel
        String[] headers = new String[]{"文本", "nlu结果", "udid", "mp3名称", "时间"};
        String[] properties = new String[]{"text", "nluRet", "udid", "mp3", "date"};
        String newFile = tmp_path.concat(newExcelName);
        ExcelExportUtil.export(headers, properties, newExcel, true, newFile);

    }

    public static List<String> getFileList(String filePath){
        List<String> resList = Lists.newArrayList();
        File file = new File(filePath);
        for(File i:file.listFiles()){
            resList.add(i.getName());
        }
        return resList;
    }
}
