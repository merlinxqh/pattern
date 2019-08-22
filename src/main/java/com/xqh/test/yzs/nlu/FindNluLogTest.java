package com.xqh.test.yzs.nlu;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xqh.utils.ReadTxtFileUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

    public static void main(String[] args) {

        List<String> list = ReadTxtFileUtils.readTxt(new File("E:\\secureCRT_file\\download\\nlulog.log"));
        List<MatchResult> retList = Lists.newArrayList();
        for(String str:list){
            if(str.indexOf(matchList.get(0)) != -1){
                String time = str.substring(0, 19);
                String nluUrl = str.substring(str.indexOf(matchList.get(0))+matchList.get(0).length());
                retList.add(MatchResult.builder()
                .time(time)
                .nluUrl(nluUrl).build());
            }
            if(str.indexOf(matchList.get(1)) != -1){
                String nluRet = str.substring(str.indexOf(matchList.get(1))+matchList.get(1).length());
                retList.get(retList.size()-1).setNluRet(nluRet);
            }
        }

        for(MatchResult r:retList){
            System.out.println(JSON.toJSONString(r));
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MatchResult{

        private String time;

        private String nluUrl;

        private String nluRet;
    }

}
