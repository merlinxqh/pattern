package com.xqh.test.casrsdk;

import com.xqh.utils.ThreadPoolUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName CasrReqMain
 * @Description CASR 请求sdk
 * @Author xuqianghui
 * @Date 2019/5/9 11:08
 * @Version 1.0
 */
public class CasrReqMain {

    public static void main(String[] args) throws Exception{
//        for(int i = 0; i< 50; i++){
//            final  int idx = i;
//            ThreadPoolUtils.execute(()->{
//                try {
//                    reqPersonalDataService("user_id_"+idx);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
        reqPersonalDataService("LTE3Nzg0MjcyMTUwMDVhN2JlNWUxN2JlMQ");
    }

    private static final String casr_url = "http://192.168.3.239:8808/casr/upload";
//    private static final String casr_url = "http://10.20.222.128:8282/casr/upload"; // 公有云test 地址
    public static void reqPersonalDataService(String udid) throws Exception{
        String appkey = "nmugoqugf3ikbhkhbaixhefxdinqcmgyhobsvjiv";
//        String jsonstr= appkey + ";" + udid + "\n" +
//                "<asr_contact>\n" +
//                "张三\n" +
//                "李四\n" +
//                "郭建峰\n" +
//                "小李飞刀\n" +
//                "</asr_contact>\n" +
//                "<hotel_light>\n" +
//                "门廊灯门廊灯\n" +
//                "廊灯门廊灯\n" +
//                "</hotel_light>\n" +
//                "<wechat_contact>\n" +
//                "希拉里阁下\n" +
//                "特浪普\n" +
//                "</wechat_contact>\n";

        String jsonstr= appkey + ";" + udid + "\n" +
                "<asr_contact>\n" +
                "张三\n" +
                "王小\n" +
                "郭靖\n" +
                "李立\n" +
                "黄蓉\n" +
                "杨大奇\n" +
                "许仙志\n" +
                "张大笨\n" +
                "张三丰\n" +
                "吴淞\n" +
                "</asr_contact>\n";

//        String jsonstr =appkey + ";" + udid + "\n" +"<asr_contact>\n张三\n李四\n网络名称1\n网络名称2" +
//                "\n网络名称3\n网络名称4\n网络名称5\n" +
//                "网络名称6\n网络名称7\n网络名称3\n</asr_contact>\n<channel>\n复仇者联盟\n战狼\n流浪地球\n</channel>\n";
        submitCasrData(udid, jsonstr);
    }

    private static String replaceRegex ="<(asr_contact)>(.*?)</(asr_contact)>";


    public static void parseTxt(){
        StringBuffer sb = new StringBuffer();
        sb.append("<asr_contact>\n张三\n李四\n网络名称7\n</asr_contact>\n<channel>\n复仇者联盟\n战狼\n流浪地球\n</channel>\n");
        String txt = sb.toString();
        Pattern replacePattern = Pattern.compile(replaceRegex);
        Pattern p = Pattern.compile("<(.*?)>(.*?)</(.*?)>");
        Matcher replaceMatcher = p.matcher(txt);
        while (replaceMatcher.find()){
            String matched = replaceMatcher.group();
            System.out.println(matched);
        }


    }

    private static byte[] encodes(byte[] chKey, byte[] chTarget, byte[] chEncode){

        int keyLen = chKey.length;

        chEncode[0] = (byte) keyLen;

        for (int i = 0; i < chKey.length; i++) {
            chEncode[i+1] = chKey[i];
        }

        byte para1= 0;
        byte para2= 0;

        for(int i=0; i<chTarget.length; i++)
        {
            int indexOfKey = i%keyLen;
            para2 = chKey[indexOfKey];
            chEncode[i+keyLen+1] =(byte) (chTarget[i] ^ para1 ^ para2);
            para1 = chEncode[i+keyLen+1];
        }

        int totalLen = keyLen + 1 + chTarget.length + 4;

        System.out.println("totalLen is " + totalLen);

        byte[] retbyte = new byte[chEncode.length + 5 + keyLen];

        retbyte[0] = (byte)(0xff & totalLen >> 24);
        retbyte[1] = (byte)(0xff & totalLen >> 16);
        retbyte[2] = (byte)(0xff & totalLen >> 8);
        retbyte[3] = (byte)(0xff & totalLen);

        for (int i = 0; i < chEncode.length; i++) {
            retbyte[4+i] = chEncode[i];
        }

        return retbyte;
    }

    public static void submitCasrData(String udid, String jsonstr) throws IOException {
        byte[] userid = udid.getBytes();

        byte[] jsonstrBytes = jsonstr.getBytes();

        byte[] oneshotBytes = new byte[jsonstrBytes.length + userid.length + 5];

        byte[] retbyte = encodes(udid.getBytes(), jsonstr.getBytes(), oneshotBytes);

        URL url = new URL(casr_url);// 创建连接

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(30 * 1000);
        connection.connect();

        BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
        out.write(retbyte);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = null;

        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}
