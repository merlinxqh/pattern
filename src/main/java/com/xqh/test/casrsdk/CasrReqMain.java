package com.xqh.test.casrsdk;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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
        StringBuffer sb = new StringBuffer();
        sb.append("jxzqe2bsxddiijynaozhpn4xgtbpltd33flddvyd").append("\n<asr_contact>\n张三\n李四\n网络名称7\n</asr_contact>\n<channel>\n复仇者联盟\n战狼\n流浪地球\n</channel>\n");
        URL url = new URL("http://192.168.188.131:8808/casr/upload");// 创建连接
//        URL url = new URL("http://localhost:9060/test2");// 创建连接
        //URL url = new URL("http://10.20.222.140:8080/picturebook/recognize" + value);// 创建连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(30 * 1000);
        connection.connect();

        BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());

        byte[] targetData = new byte[5 + "TEST".getBytes().length + sb.toString().getBytes().length];

        out.write(encode("TEST", sb.toString(), targetData));
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line, responsestr = null;

        while ((line = in.readLine()) != null)
        {
            System.out.println(line);
            responsestr = responsestr + line;
        }
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

    private static byte[] encode(String key, String data, byte[] targetData) {
        byte[] keybyte = key.getBytes();
        byte[] databyte = data.getBytes();
        byte[] retbyte = new byte[keybyte.length + 10 + databyte.length];

        // 1、将key长度添加到目标数据
        int keylen = keybyte.length;
        targetData[0] = retbyte[0] = (byte) keylen;

        // 2、将key添加到目标数据
        System.arraycopy(keybyte,0,targetData,1,keybyte.length);

        // 3、将需要加密的数据添加到目标数据
        byte param1 = 0;
        byte param2 = keybyte[0];
        for (int i = 0; i < databyte.length; i++) {
            int index = i%keylen;
            param2 = keybyte[index];
            targetData[i+keylen+1] = (byte) (databyte[i] ^ param1 ^ param2);
            param1 = targetData[i+keylen+1];
        }

        int newseqlen = keylen + 1 + databyte.length + 4;

        retbyte[0] = 0;
        retbyte[1] = 0;
        retbyte[2] = 0;
        retbyte[3] = (byte)newseqlen;

        for (int i = 0; i < keylen + 1 + databyte.length; i++) {
            retbyte[i+4] = targetData[i];
        }

        return retbyte;
    }
}
