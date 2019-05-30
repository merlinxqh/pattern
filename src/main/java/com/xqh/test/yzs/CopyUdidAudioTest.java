package com.xqh.test.yzs;

import com.google.common.collect.Lists;
import com.xqh.test.MainTest.ExcelModel;
import com.xqh.utils.ExcelReader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CopyUdidAudioTest
 * @Description 复制 对应Udid文件
 * @Author xuqianghui
 * @Date 2019/5/30 14:42
 * @Version 1.0
 */
public class CopyUdidAudioTest {

    public static void main(String[] args) throws IOException {
        copyFile("E:\\secureCRT_file\\download\\2019-05-29", "E:\\secureCRT_file\\download\\2019-05-29\\audio.xlsx",
                "LTE3Nzg0MjcyMTUwMEVDRTE1NEYwMjZDNg", "D:\\out");
    }

    /**
     * 将 指定Udid 音频文件 copy到 指定目录
     * @param targetPath 目标 文件目录
     * @param excelFile 解析excel
     * @param udid 需要copy的udid
     * @param outPath 输出目录
     * @throws IOException
     */
    public static void copyFile(String targetPath, String excelFile,  String udid, String outPath) throws IOException {
        String pcm = outPath+ File.separator+udid+File.separator+"pcm";
        String mp3 = outPath+File.separator+udid+File.separator+"mp3";
        File nf = new File(pcm);
        if(!nf.exists()){
            nf.mkdirs();
        }

        File nf2 = new File(mp3);
        if(!nf2.exists()){
            nf2.mkdirs();
        }
        List<String[]> list = ExcelReader.getExcelData(new File(excelFile), 1);
        List<ExcelModel> excelData = Lists.newArrayList();
        for(String[] array:list){
            ExcelModel m = new ExcelModel();

            m.setUdid(array[1]);
            m.setSessionId(array[2].replaceAll(".mp3", ""));
            excelData.add(m);
        }

        excelData = excelData.stream().filter(e-> e.getUdid().equals(udid)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(excelData)){
            System.out.println("没有这个udid==>"+udid);
            return ;
        }
        File target = new File(targetPath);
        for(ExcelModel e:excelData){
            for(File f:target.listFiles()){
                if(f.isDirectory()){
                    if(f.getName().equals("pcm")){
                        for(File c:f.listFiles()){
                            if(c.isFile() && c.getName().contains(e.getSessionId())){
                                FileCopyUtils.copy(c, new File(pcm+File.separator+c.getName()));
                            }
                        }
                    }
                    if(f.getName().equals("mp3")){
                        for(File c:f.listFiles()){
                            if(c.isFile() && c.getName().contains(e.getSessionId())){
                                FileCopyUtils.copy(c, new File(mp3+File.separator+c.getName()));
                            }
                        }
                    }
                }
            }
        }



    }
}
