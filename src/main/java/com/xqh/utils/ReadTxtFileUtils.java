package com.xqh.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @ClassName ReadTxtFileUtils
 * @Description 读取txt文件
 * @Author xuqianghui
 * @Date 2019/5/10 18:51
 * @Version 1.0
 */
@Slf4j
public class ReadTxtFileUtils {

    /**
     * 读取txt文件
     * @param file
     * @return
     */
    public static List<String> readTxt(MultipartFile file) {
        try {
            return readByBytes(file.getBytes());
        } catch (IOException e) {
            log.error("", e);
        }
        return Lists.newArrayList();
    }

    /**
     * 读取txt文件
     * @param file
     * @return
     */
    public static List<String> readTxt(File file) {
        try {
            return readByBytes(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            log.error("", e);
        }
        return Lists.newArrayList();
    }

    public static List<String> readByBytes(byte[] bytes){
        List<String> resList = Lists.newArrayList();
        InputStreamReader inputReader = null;
        BufferedReader bf = null;
        try {
            inputReader = new InputStreamReader(new ByteArrayInputStream(bytes));
            bf = new BufferedReader(inputReader);
            String lineTxt = null;
            while ((lineTxt = bf.readLine()) != null) {
                if(StringUtils.hasText(lineTxt)){
                    resList.add(lineTxt.trim());
                }
            }
        }catch (IOException e) {
            log.error("", e);
        }finally {
            try {
                if(null != inputReader){
                    inputReader.close();
                }
                if(null != bf){
                    bf.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }

        return resList;
    }



    public static void main(String[] args) {

    }
}
