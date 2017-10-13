package com.xqh.juc.NIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by leo on 2017/10/11.
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        Socket client=null;
        PrintWriter writer=null;
        BufferedReader reader=null;

        try {
            client=new Socket();
            client.connect(new InetSocketAddress("localhost",8000));
            writer=new PrintWriter(client.getOutputStream(),true);
            writer.println("Hello! "+System.currentTimeMillis());
            writer.flush();
            reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server:"+ reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != client){
                client.close();
            }
            if(null != writer){
                writer.close();
            }
            if(null != reader){
                reader.close();
            }
        }
    }
}
