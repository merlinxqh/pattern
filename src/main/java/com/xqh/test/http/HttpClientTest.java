package com.xqh.test.http;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.common.HttpResult;
import com.arronlong.httpclientutil.common.SSLs.SSLProtocolVersion;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpClientTest
 * @Description @see https://github.com/Arronlong/httpclientutil
 * @Author xuqianghui
 * @Date 2019/5/9 13:54
 * @Version 1.0
 */
public class HttpClientTest {

    public static void main(String[] args) throws HttpProcessException {

        String url = "http://localhost:8088/casr/upload";
//        String url = "http://192.168.3.239:8181/importHotData";

        //插件式配置Header（各种header信息、自定义header）
        Header[] headers = HttpHeader.custom()
                .userAgent("javacl")
                .other("customer", "自定义")
                .build();

        //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
        HCB hcb = HCB.custom()
                .timeout(1000) //超时
                .pool(100, 10) //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                .sslpv(SSLProtocolVersion.TLSv1_2) 	//设置ssl版本号，默认SSLv3，也可以调用sslpv("TLSv1.2")
                .ssl()  	  	//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                .retry(5)		//重试5次
                ;
        HttpClient client = hcb.build();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)	//设置headers，不需要时则无需设置
                .url(url)	          //设置请求的url
                .json("{\"appKey\":\"\",\"userId\":\"\",\"data\":[\n" +
                        "{\"tagId\":\"107\",\"values\":[\"小茂小茂\"]},\n" +
                        "{\"tagId\":\"1000\",\"values\":[\"辅灯\",\"右阅读灯\",\"左阅读灯\",\"灯带\"]},\n" +
                        "{\"tagId\":\"1001\",\"values\":[\"雾化玻璃\"]},\n" +
                        "{\"tagId\":\"1002\",\"values\":[\"客卫\",\"厨卫\",\"主卫\",\"影厅\",\"台厅\",\"主卫\"]},\n" +
                        "{\"tagId\":\"1004\",\"values\":[\"观影模式\"]}\n" +
                        "]}")
//                .map(map)	          //设置请求参数，没有则无需设置
                .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
                .client(client)    //如果只是简单使用，无需设置，会自动获取默认的一个client对象

                //.inenc("utf-8")  //设置请求编码，如果请求返回一直，不需要再单独设置
                //.inenc("utf-8")	//设置返回编码，如果请求返回一直，不需要再单独设置
                //.json("json字符串")                          //json方式请求的话，就不用设置map方法，当然二者可以共用。
                //.context(HttpCookies.custom().getContext()) //设置cookie，用于完成携带cookie的操作
                //.out(new FileOutputStream("保存地址"))       //下载的话，设置这个方法,否则不要设置
                //.files(new String[]{"d:/1.txt","d:/2.txt"}) //上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
                ;
        //使用方式：
//        String result1 = HttpClientUtil.get(config);     //get请求
        String result2 = HttpClientUtil.post(config);    //post请求
//        System.out.println(result1);
        System.out.println("req result====>"+result2);

        //如果指向看是否访问正常
        //String result3 = HttpClientUtil.head(config); // 返回Http协议号+状态码
        //int statusCode = HttpClientUtil.status(config);//返回状态码

        //[新增方法]sendAndGetResp，可以返回原生的HttpResponse对象，
        //同时返回常用的几类对象：result、header、StatusLine、StatusCode
//        HttpResult respResult = HttpClientUtil.sendAndGetResp(config);
//        System.out.println("返回结果：\n"+respResult.getResult());
//        System.out.println("返回resp-header："+respResult.getRespHeaders());//可以遍历
//        System.out.println("返回具体resp-header："+respResult.getHeaders("Date"));
//        System.out.println("返回StatusLine对象："+respResult.getStatusLine());
//        System.out.println("返回StatusCode："+respResult.getStatusCode());
//        System.out.println("返回HttpResponse对象）（可自行处理）："+respResult.getResp());
    }
}
