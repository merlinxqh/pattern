package com.xqh.test.yzs.rtc;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName RtcReqMain
 * @Description rtc api test
 * @Version 1.0
 */
public class RtcReqMain {

    private static final String rtc_url = "http://192.168.3.240:8810/rtc/api/oneshot";

    public static void main(String[] args) throws Exception{
        reqPersonalDataService("LTE3Nzg0MjcyMTUwMDVhN2JlNWUxN2JlMQ");
    }

//    private static final String casr_url = "http://192.168.188.131:8808/oneshot";
    public static void reqPersonalDataService(String udid) throws Exception{
        String reqJson = "{\n" +
                "\"ak\": \"hsczbh65gx63mt77fp4va3aa3xg2n67kjbl3gpyc\",\n" +
                "\"neww\": [\"阔四你好\", \"科视你好\", \"你好科视\", \"你好阔似\"],\n" +
                "\"v\": \"1.0\",\n" +
                "\"udid\": \""+udid+"\",\n" +
                "\"curw\": [\"阔四你好\", \"科视你好\", \"你好科视\", \"你好阔四\"],\n" +
                "\"reqID\": \"80d0e3bf54583aaf8c836390818eba67\"\n" +
                "}";
        submitOneshotData(udid, reqJson);
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

    public static void submitOneshotData(String udid, String jsonstr) throws IOException {
        byte[] userid = udid.getBytes();

        byte[] jsonstrBytes = jsonstr.getBytes();

        byte[] oneshotBytes = new byte[jsonstrBytes.length + userid.length + 5];

        byte[] retbyte = encodes(udid.getBytes(), jsonstr.getBytes(), oneshotBytes);

        URL url = new URL(rtc_url);// 创建连接

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
