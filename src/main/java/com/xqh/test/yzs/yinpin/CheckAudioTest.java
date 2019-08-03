package com.xqh.test.yzs.yinpin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xqh.utils.ExcelExportUtil;
import com.xqh.utils.ExcelReader;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckAudioTest
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/7/12 9:13
 * @Version 1.0
 */
public class CheckAudioTest {

    public static void main(String[] args) {

        List<String> errorData = Lists.newArrayList();

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-03\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-03\\2019-07-03.xlsx", errorData);

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-04\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-04\\2019-07-04.xlsx", errorData);

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-05\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-05\\2019-07-05.xlsx", errorData);

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-06\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-06\\2019-07-06.xlsx", errorData);

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-07\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-07\\2019-07-07.xlsx", errorData);

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-08\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-08\\2019-07-08.xlsx", errorData);

        findNotExistsAudio("E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-09\\mp3",
                "E:\\secureCRT_file\\download\\云茂一周数据\\2019-07-09\\2019-07-09.xlsx", errorData);


//        String id = String.valueOf(IdWorker.getId());
//        System.out.println(id);
//        System.out.println(EncryptUtils.getSha1(id));
//        System.out.println(IdWorker.getIdStr());
    }

    public static void findNotExistsAudio(String filePath, String excelPath, List<String> errorData){
        List<String> fileList = getFileList(filePath);

        boolean flag = false; //标识 是否需要重新生成excel
        List<String[]> excelData = ExcelReader.getExcelData(new File(excelPath), 1);

        List<Map<String, Object>> newExcel = Lists.newArrayList();
        for(String[] array:excelData){
            if(null != array && array.length >= 5 && StringUtils.hasText(array[3])){
                Map<String, Object> map = Maps.newHashMap();
                map.put("text", array[0]);
                map.put("nluRet", array[1]);
                map.put("udid", array[2]);
                map.put("mp3", array[3]);
                map.put("date", array[4]);
                if(!fileList.contains(array[3])){
                    errorData.add(array[3]);
                    flag = true;
                }else{
                    newExcel.add(map);
                }
            }
        }

        if(flag){
            // 重新生成excel
            String[] headers = new String[]{"文本", "nlu结果", "udid", "mp3名称", "时间"};
            String[] properties = new String[]{"text", "nluRet", "udid", "mp3", "date"};
            String newFile = filePath.replaceAll("mp3", "新excel");
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
