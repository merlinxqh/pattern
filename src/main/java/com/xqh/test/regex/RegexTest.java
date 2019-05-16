package com.xqh.test.regex;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexTest
 * @Description 正则测试
 * @Author xuqianghui
 * @Date 2019/5/15 18:11
 * @Version 1.0
 */
public class RegexTest {

    public static void main(String[] args) {
        filterLnMatch();
    }

    /**
     * 过滤 换行符 \n 正则匹配
     */
    public static void filterLnMatch(){
        String line_2 = "<asr_contact>\n张三\n李四\n网络名称7\n</asr_contact>\n<channel>\n复仇者联盟\n战狼\n流浪地球\n</channel>\n";
        Pattern p = Pattern.compile("<(.*?)>(.*?)</(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line_2);
        List<HashMap<String,String>> mList = new ArrayList<>();
        while (m.find()) {
            for(int i=0;i<=m.groupCount();i++){
                if(i>0){

                    System.out.println(m.group(i));
                }
            }
        }
    }
}
