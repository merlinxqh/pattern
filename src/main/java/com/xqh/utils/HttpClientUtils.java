package com.xqh.utils;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Maps;
import org.apache.http.Header;
import org.apache.http.protocol.HttpContext;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName HttpClientUtils
 * @Description http client utils
 * @Author xuqianghui
 * @Date 2019/5/11 23:20
 * @Version 1.0
 */
public class HttpClientUtils {

    public static void site2(){
        String betUrl = "https://c8084.com/member/bet";
        String betParams = "{\"lottery\":\"BJKL8\",\"drawNumber\":\"952174\",\"bets\":[{\"game\":\"ZDS\",\"contents\":\"D\",\"amount\":1,\"odds\":1.995}],\"ignore\":false}";
    }

    public static void main(String[] args) throws HttpProcessException {
//        String url = "https://www.mtc46.com/static/data/65CurIssue.json?_t=1557587420572";
//        String resp = HttpClientUtil.get(HttpConfig.custom().url(url));
//
//        /// 投注

        Header[] headers = HttpHeader.custom()
                .cookie("checkCode=f12118d3-5088-4976-9c8b-a6c9c50e835c; _skin_=red; x-session-token=LuCSlUMDd58u8z7M7FV5hDwkBbSurr9aVEQJ2p%2BfG4Od%2BejesRATNg%3D%3D")
                .build();
        String betUrl = "https://www.mtc46.com/bet/bet.do?_t=1557588539345";
        Map<String, Object>  repMap = Maps.newHashMap();
        /**
         * gameId: 65
         * totalNums: 1
         * totalMoney: 1
         * betSrc: 0
         * turnNum: 951473
         * betBean[0].playId: 652104
         * betBean[0].money: 1
         */
        repMap.put("gameId", 65);
        repMap.put("totalNums", 1);
        repMap.put("totalMoney", 1);
        repMap.put("betSrc", 0);
        repMap.put("turnNum", 951638);
        repMap.put("betBean[0].playId", 652104);
        repMap.put("betBean[0].money", 1);
        //{"success":true,"msg":"","info":"","code":0}
        String resp = HttpClientUtil.post(HttpConfig.custom().url(betUrl)
                         .headers(headers)
                         .map(repMap));

        System.out.println(resp);

        //获取要投注的 issue
//        String nextIssueUrl = "https://www.mtc46.com/lottery/getNextIssue.do?_t=1557666126518&gameId=65";
//         loginTest();
//        getIssue();

    }

    public static void getIssue() throws HttpProcessException {
        String url = "https://www.mtc46.com/lottery/getNextIssue.do?gameId=65&_t="+ (new Date().getTime());
        Header[] headers = HttpHeader.custom()
                .cookie("checkCode=f12118d3-5088-4976-9c8b-a6c9c50e835c; _skin_=red; x-session-token=LuCSlUMDd58u8z7M7FV5hDwkBbSurr9aVEQJ2p%2BfG4Od%2BejesRATNg%3D%3D")
                .build();
        HttpConfig config = HttpConfig.custom().url(url).headers(headers);
        String resp = HttpClientUtil.get(config);
        System.out.println("==============>"+resp);
    }

    public static void loginTest() throws HttpProcessException {
        String url = "https://www.mtc46.com/api/login.do";
        Header[] headers = HttpHeader.custom()
//                .cookie("checkCode=f12118d3-5088-4976-9c8b-a6c9c50e835c; _skin_=red; x-session-token=h9qD1wNUGY5DfpgT60l6UI1K%2Bby1aEGeJQ27Kg7La7rFZzaRdeDkmA%3D%3D")
                .cookie("checkCode=f12118d3-5088-4976-9c8b-a6c9c50e835c; _skin_=red")
                .build();
        Map<String, Object> parMap = Maps.newHashMap();
        parMap.put("account", "a2313165");
        parMap.put("password", "294dc462594e027634f398985706895d");
        parMap.put("pwdtext", "6225029");
        parMap.put("loginSrc", "0");
        HttpConfig config = HttpConfig.custom().map(parMap).url(url);
        String resp = HttpClientUtil.post(config);
        System.out.println("=================>" +resp);
//        application/x-www-form-urlencoded; charset=UTF-8
// x-session-token=h9qD1wNUGY5DfpgT60l6UI1K%2Bby1aEGeJQ27Kg7La7rFZzaRdeDkmA%3D%3D; domain=www.mtc46.com; path=/
    }
}
