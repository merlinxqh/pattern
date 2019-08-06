package com.xqh.test.yzs;

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

    private final static String tmp_path = "E:\\secureCRT_file\\download\\世茂一个月数据\\";

    private final static String filter_str = "----";

    public static void main(String[] args) {

        List<String> errorData = Lists.newArrayList();
//
        wrapperMethod("2019-08-05", errorData);


    }

    public static void wrapperMethod(String date, List<String> errorData){
        findNotExistsAudio(date+"\\mp3",
                date+"\\audio.xlsx", errorData, date+"\\"+date+"_new");
    }

    public static void findNotExistsAudio(String filePath, String excelPath, List<String> errorData, String newExcelName){
        List<String> fileList = getFileList(tmp_path.concat(filePath));

        boolean flag = false; //标识 是否需要重新生成excel
        List<String[]> excelData = ExcelReader.getExcelData(new File(tmp_path.concat(excelPath)), 1);

        List<Map<String, Object>> newExcel = Lists.newArrayList();
        for(String[] array:excelData){
            if(null != array && array.length >= 5 && StringUtils.hasText(array[3])){
                Map<String, Object> map = Maps.newHashMap();
                String text = array[0];
                map.put("text", text);
                map.put("udid", array[1]);
                map.put("mp3", array[2]);
                map.put("date", array[3]);
                if(!fileList.contains(array[2])){
                    errorData.add(array[2]);
                    flag = true;
                }else{
                    newExcel.add(map);
                    if(!text.equals(filter_str)){
                        FutureTask<String> future = new FutureTask<>(new ReqNluThread(text));
                        ThreadPoolUtils.submit(future);
                        try {
                            map.put("nluRet", future.get());
                        } catch (InterruptedException e) {
                        } catch (ExecutionException e) {
                        }
                    }else {
                        map.put("nluRet", filter_str);
                    }
                }
            }
        }

        if(flag){
            // 重新生成excel
            String[] headers = new String[]{"文本", "nlu结果", "udid", "mp3名称", "时间"};
            String[] properties = new String[]{"text", "nluRet", "udid", "mp3", "date"};
            String newFile = tmp_path.concat(newExcelName);
            ExcelExportUtil.export(headers, properties, newExcel, true, newFile);
        }

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
