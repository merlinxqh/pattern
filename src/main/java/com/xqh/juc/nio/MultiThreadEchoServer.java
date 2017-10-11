package com.xqh.juc.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/10/11.
 */
public class MultiThreadEchoServer {
    private static ExecutorService tp= Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable{
        Socket clientClient;

        public HandleMsg(Socket clientClient){
            this.clientClient=clientClient;
        }
        @Override
        public void run() {
            BufferedReader is=null;
            PrintWriter os=null;
            try {
                is = new BufferedReader(new InputStreamReader(clientClient.getInputStream()));
                os = new PrintWriter(clientClient.getOutputStream(),true);
                //从InputStream读取客户端所发送的数据
                String inputLine=null;
                long b=System.currentTimeMillis();
                while ((inputLine = is.readLine()) != null){
                    os.println(inputLine);
                }
                long e=System.currentTimeMillis();
                System.out.println("Spend time: "+(e-b)+" ms");
            }catch (IOException e){

            }finally {
                try {
                    if(null != is){
                      is.close();
                    }
                    if(null != os){
                        os.close();
                    }
                    clientClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        ServerSocket echoServer=null;
        Socket clientSocket=null;
        try {
            echoServer=new ServerSocket(8000);
        } catch (IOException e) {
            System.out.println(e);
        }
        while (true){
            try {
                clientSocket=echoServer.accept();
                System.out.println(clientSocket.getLocalSocketAddress()+" connect!");
                tp.execute(new HandleMsg(clientSocket));
            }catch (IOException e){
                System.out.println(e);
            }
        }
    }
}