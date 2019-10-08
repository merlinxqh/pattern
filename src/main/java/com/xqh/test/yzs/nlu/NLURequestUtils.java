package com.xqh.test.yzs.nlu;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Maps;
import com.xqh.test.yzs.yinpin.TalkParameterNames;
import com.xqh.utils.EncryptUtils;
import com.xqh.utils.HttpClientUtils;
import com.xqh.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

/**
 * @ClassName NLURequestUtils
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/8/6 20:00
 * @Version 1.0
 */
@Slf4j
public class NLURequestUtils {

    private final static String nluReqParams = "appendLength=1&appver=1.0.1&filterName=nlu3&screen=&platform=&audioUrl=false&viewid=&scenario=hotelDefault&udid=LTE3Nzg0MjcyMTUwMDVhN2JlNWUxN2JlMQ&dpi=&filterUrl=http%3A%2F%2F47.107.47.68%3A19998%2Fprocess%2F%2Ftr%2FdataProcess&ver=3.2&method=iss.getTalk&gps=22.55428%2C113.948524&history=&oneshotKeyProperty=wakeup&additionalService=athenaAppService&voiceid=8a44654f6482a9e348e7d198fe5f7383&appsig=031F6557A74DDB60008A13BE78C442A323428394&fullDuplex=false&appkey=nmugoqugf3ikbhkhbaixhefxdinqcmgyhobsvjiv&time=2019-07-2211%3A04%3A28&req_nlu_length=1&returnType=json";

    private final static String appKey = "nmugoqugf3ikbhkhbaixhefxdinqcmgyhobsvjiv";

    private final static String appSecret = "ea5d2f4793aa48dfdb67d29aae506843";

//    private final static String nluUrl = "http://scv2.sh.hivoice.cn:80/service/iss";
    private final static String nluUrl = "http://route.igaicloud.cn:8088/service/iss";

    /**
     * 执行 nlu 请求线程
     */
    public static class ReqNluThread implements Callable<String> {

        private String reqText;// 请求内容


        public ReqNluThread(String reqText){
            this.reqText = reqText;
        }

        @Override
        public String call() throws Exception {
            String pubUrl = nluUrl.concat("&appkey=").concat(appKey).concat("&text=").concat(reqText);
            if(nluUrl.indexOf("?") == -1){
                pubUrl = nluUrl.concat("?appkey=").concat(appKey).concat("&text=").concat(reqText);
            }
            Map<String, String> parMap = Maps.newHashMap();
            // 先赋值  系统配置 参数
            parMap.putAll(getParamMap(nluReqParams));
            parMap.putAll(getParamMap(pubUrl));// 前端 输入的 参数
            parMap.put("wakeupword", "小茂小茂");
            parMap.put("city", "深圳市");
            parMap.put("udid", IdWorker.get32UUID());
            String sign = getNluSign(parMap, appSecret);
            parMap.put("appsig", sign);
            String reqUrl = splicingUrl(parMap, pubUrl);
            log.info("req nlu url ==>{}", reqUrl);
            String resp = HttpClientUtils.get(reqUrl);
            return resp;
        }
    }

    public static void main(String[] args) {
        String text = "关灯";
        try {
            new ReqNluThread(text).call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 拼接访问Url
     * @param paramMap
     * @param url
     * @return
     */
    public static String splicingUrl(Map<String, String> paramMap, String url){
        StringBuilder builder = new StringBuilder(url.substring(0, url.indexOf("?")+1));
        for(Entry<String, String> entry:paramMap.entrySet()){
            builder.append(entry.getKey()).append("=").append(EncryptUtils.urlEncode(entry.getValue())).append("&");
        }
        String urlRet = builder.toString();
        return urlRet.substring(0, urlRet.length()-1);
    }

    /**
     * 返回 参数 map
     * @param url
     * @return
     */
    public static Map<String, String> getParamMap(String url){
        String params = url.substring(url.indexOf("?")+1);
        Map<String, String> paramMap = Maps.newHashMap();
        for(String p:params.split("&")){
            String[] array = p.split("=");
            String key = array[0];
            String value = "";
            if(array.length == 2){
                value = array[1];
            }
            paramMap.put(key, delSpace(EncryptUtils.urlDecode(value)));
        }
        return paramMap;
    }

    /**
     * 删除字符串中的空格
     * @param str
     * @return
     */
    public static String delSpace(String str){
        if(null != str){
            return str.replaceAll("(?<=[^a-zA-Z<>])\\s+|\\s+(?=[^a-zA-Z<>])", "").trim();
        }
        return null;
    }

    public static String getNluSign(Map<String, String> parMap, String secret){
        HashMap<String, String> needSignParams = new HashMap<String, String>();
        putMapParam(needSignParams, parMap, TalkParameterNames.APP_KEY);
        putMapParam(needSignParams, parMap, TalkParameterNames.METHOD);
        putMapParam(needSignParams, parMap, TalkParameterNames.VER);
        putMapParam(needSignParams, parMap, TalkParameterNames.UDID);
        putMapParam(needSignParams, parMap, TalkParameterNames.GPS);
        putMapParam(needSignParams, parMap, TalkParameterNames.APPVER);
        putMapParam(needSignParams, parMap, TalkParameterNames.TEXT);
        putMapParam(needSignParams, parMap, TalkParameterNames.HISTORY);
        putMapParam(needSignParams, parMap, TalkParameterNames.CITY);
        putMapParam(needSignParams, parMap, TalkParameterNames.TIME);
        putMapParam(needSignParams, parMap, TalkParameterNames.VOICE_ID);
        putMapParam(needSignParams, parMap, TalkParameterNames.SCENARIO);
        putMapParam(needSignParams, parMap, TalkParameterNames.SCREEN);
        putMapParam(needSignParams, parMap, TalkParameterNames.DPI);
        putMapParam(needSignParams, parMap, TalkParameterNames.PLATFORM);
        putMapParam(needSignParams, parMap, TalkParameterNames.VIEWID);

        String sign = SDKUtils.sign(needSignParams, secret);
        return sign;
    }

    public static void putMapParam(Map<String, String> signMap, Map<String, String> parMap, String key){
        signMap.put(key, parMap.get(key));
    }

}
