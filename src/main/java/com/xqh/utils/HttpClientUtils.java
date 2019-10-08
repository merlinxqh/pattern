package com.xqh.utils;


import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author leo
 *
 */
public abstract class HttpClientUtils {

    private static final int DOWNLOAD_CACHE=10*10*1024;

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final HttpClient defaultClient = HttpClientBuilder.create()
            //设置默认时间
            .setUserAgent("YZS.COM")
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(10000)         //连接超时时间(ms)
                    .setSocketTimeout(10000)          //读超时时间（等待数据超时时间）(ms)
                    .setConnectionRequestTimeout(5000)    //从池中获取连接超时时间(ms)
                    .build())
            .setConnectionManager(new PoolingHttpClientConnectionManager() {{
                setMaxTotal(200);//最大连接数
                setDefaultMaxPerRoute(10);//默认的每个路由的最大连接数
                setDefaultSocketConfig(SocketConfig.custom()
                        .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                        .setSoTimeout(10000)       //接收数据的等待超时时间，单位ms
                        .setSoKeepAlive(true)    //开启监视TCP连接是否有效
                        .build()
                );
            }})

            .build();


    /**
     * 执行get请求
     */
    public static String get(String url) {
        return get(url, null, null);
    }

    /**
     * 执行get请求
     */
    public static String get(String url, Map<String, String> query) {
        return get(url, null, query);
    }


    /**
     * 返回Http 状态码
     * @param url
     * @return
     */
    public static int getHttpStatusCode(String url){
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setCharset(StandardCharsets.UTF_8);

            HttpGet request = new HttpGet(uriBuilder.build());

            //执行请求
            return defaultClient.execute(request, response -> {
                //HTTP响应信息默认处理方法
                    return response.getStatusLine().getStatusCode();
            });
        } catch (Exception e) {
            logger.error("",e);
        }
        return 0;
    }
    /**
     * 执行get请求
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> query) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setCharset(StandardCharsets.UTF_8);
            if (!CollectionUtils.isEmpty(query)) {
                query.entrySet().forEach(entry -> {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                });
            }

            HttpGet request = new HttpGet(uriBuilder.build());

            //设置请求头
            if (!CollectionUtils.isEmpty(headers)) {
                headers.entrySet().forEach(entry -> {
                    request.setHeader(entry.getKey(), entry.getValue());
                });
            }
            //执行请求
            return defaultClient.execute(request, response -> {
                //HTTP响应信息默认处理方法
                try {
                    String responseBody = StreamUtils
                        .copyToString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        logger.info("请求url：{} 返回状态码: {}",request.getURI(),response.getStatusLine());
                        throw new HttpException(request, response, responseBody);
                    }
                    return responseBody;
                } catch (IOException ex) {
                    logger.info("http请求结果异常：{} {}",response.getStatusLine(),ex.getMessage());
                    throw new HttpException(request, response, null);
                }
            });
        } catch (Exception e) {
            logger.error("",e);
        }
        return null;
    }

    /**
     * 执行post请求，将使用x-www-form-urlencode格式提交form表单对象
     */
    public static String post(String url, Map<String, String> form) {
        return post(url, null, form);
    }

    /**
     * 执行post请求
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> form) {
        HttpEntity entity = null;
        if (!CollectionUtils.isEmpty(form)) {
            entity = new UrlEncodedFormEntity(
                    form.entrySet().stream()
                            .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList()),
                    StandardCharsets.UTF_8
            );
        }
        return post(url, headers, entity);
    }


    public static String postFile(String url, Map<String,File> fileMap, Map<String, String> headers,Map<String,String> form){
        try {
            HttpPost request = new HttpPost(url);

            //设置请求头
            if (!CollectionUtils.isEmpty(headers)) {
                headers.entrySet().forEach(entry -> {
                    request.setHeader(entry.getKey(), entry.getValue());
                });
            }

            MultipartEntityBuilder builder= MultipartEntityBuilder.create();
            //添加form表单
            if(null != form && form.keySet().size()>0){
                for(String key:form.keySet()){
                    builder.addTextBody(key, form.get(key), ContentType.DEFAULT_TEXT);
                }
            }
            //添加文件
            if(null != fileMap && fileMap.size()>0){
                fileMap.keySet().forEach(key->{
                    builder.addBinaryBody(key, fileMap.get(key), ContentType.APPLICATION_OCTET_STREAM, fileMap.get(key).getName());
                });
            }

            //设置请求体
            request.setEntity(builder.build());

            return defaultClient.execute(request, response -> {
                //HTTP响应信息默认处理方法
                try {
                    String responseBody = StreamUtils
                        .copyToString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        logger.info("http请求结果异常：{} {}",response.getStatusLine(),responseBody);
                        throw new HttpException(request, response, responseBody);
                    }
                    return responseBody;
                } catch (IOException ex) {
                    logger.info("http请求结果异常：{} {}",response.getStatusLine(),ex.getMessage());
                    throw new HttpException(request, response, null);
                }
            });
        } catch (IOException e) {
            logger.error("",e);
        }
        return null;
    }

    /**
     * 校验 文件Url 是否有效
     * @param url
     * @return
     */
    public static boolean checkFileUrl(String url){
        InputStream is=null;
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = defaultClient.execute(httpget);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer=new byte[DOWNLOAD_CACHE];
            return is.read(buffer) != -1;
        } catch (Exception e) {
            logger.error("",e);
        }finally {
            try {
                if(null != is){
                    is.close();
                }
            } catch (IOException e) {

            }
        }
        return false;
    }

    /**
     * 文件下载
     * @param url
     */
    public static String downloadFile(String url, String saveDir, String fileName){
        if(StringUtils.isEmpty(fileName)){
            fileName = getNameFromUrl(url);
        }
        InputStream is=null;
        FileOutputStream fileout=null;
        String resPath=null;
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = defaultClient.execute(httpget);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            String savePath = isExistDir(saveDir);
            File file = new File(savePath, fileName);file.getParentFile().mkdirs();

            fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer=new byte[DOWNLOAD_CACHE];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer,0,ch);
            }
            fileout.flush();
            resPath=savePath.concat(File.separator).concat(fileName);
        } catch (Exception e) {
            logger.error("",e);
            resPath=null;
        }finally {
            try {
                if(null != is){
                    is.close();
                }
                if(null != fileout){
                    fileout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resPath;
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * 通过request payload 传输参数
     * @param url
     * @param entityStr
     * @return
     */
    public static String postStringEntity(String url,String entityStr){
        /**
         * {
         "query": {
         "match": {
         "message" : {
         "query" : "shopservices.domybox.local"
         }
         }
         }
         }
         */
        return postStringEntity(url,null,entityStr);
    }


    public static String postStringEntity(String url,Map<String, String> headers,String entityStr){
        HttpEntity entity = null;
        if (StringUtils.hasText(entityStr)) {
            try {
                entity = new StringEntity(entityStr);
            } catch (UnsupportedEncodingException e) {
                logger.error("",e);
            }
        }
        return post(url,headers,entity);

    }

    /**
     * 执行post请求
     */
    public static String post(String url, Map<String, String> headers, HttpEntity entity) {
        try {
            HttpPost request = new HttpPost(url);
            //设置请求头
            if (!CollectionUtils.isEmpty(headers)) {
                headers.entrySet().forEach(entry -> {
                    request.setHeader(entry.getKey(), entry.getValue());
                });
            }
            //设置请求体
            if (entity != null) {
                request.setEntity(entity);
            }

            return defaultClient.execute(request, response -> {
                //HTTP响应信息默认处理方法
                try {
                    String responseBody = StreamUtils
                        .copyToString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        throw new HttpException(request, response, responseBody);
                    }
                    return responseBody;
                } catch (IOException ex) {
                    logger.info("http请求结果异常：request url:{},{} {}",url,response.getStatusLine(),ex.getMessage());
                    throw new HttpException(request, response, null);
                }
            });
        } catch (IOException e) {
            logger.error("",e);
            throw new HttpException(null,null,null);
        }
    }

    /**
     * 执行get请求获取json
     */
    public static <T> T getForJson(String url, Map<String, String> query, Class<T> responseClass) {
        Map<String, String> headers = new HashMap<String, String>() {{
            put(HttpHeaders.ACCEPT, MimeTypeUtils.APPLICATION_JSON_VALUE);
        }};
        String response = get(url, headers, query);
        Assert.hasText(response, "HTTP响应体未包含任何信息");
        return JSON.parseObject(response, responseClass);
    }

    /**
     * 执行post请求获取json，将使用x-www-form-urlencode格式提交form表单对象
     */
    public static <T> T postForJson(String url, Map<String, String> form, Class<T> responseClass) {
        Map<String, String> headers = new HashMap<String, String>() {{
            put(HttpHeaders.ACCEPT, MimeTypeUtils.APPLICATION_JSON_VALUE);
        }};
        String response = post(url, headers, form);
        Assert.hasText(response, "HTTP响应体未包含任何信息");
        return JSON.parseObject(response, responseClass);
    }

    /**
     * 执行post请求获取json，将使用json格式提交指定对象
     */
    public static <T> T postJsonForJson(String url, Object object, Class<T> responseClass) {
        HttpEntity entity = object == null ? null
                : new StringEntity(JSON.toJSONString(object), ContentType.APPLICATION_JSON);
        String response = post(url, null, entity);
        Assert.hasText(response, "HTTP响应体未包含任何信息");
        return JSON.parseObject(response, responseClass);
    }


    public static class HttpException extends RuntimeException {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private HttpRequest request;

        private HttpResponse response;

        private String responseBody;

        public HttpException(HttpRequest request, HttpResponse response, String responseBody) {
            this.request = request;
            this.response = response;
            this.responseBody = responseBody;
        }

        public HttpRequest getRequest() {
            return request;
        }

        public void setRequest(HttpRequest request) {
            this.request = request;
        }

        public HttpResponse getResponse() {
            return response;
        }

        public void setResponse(HttpResponse response) {
            this.response = response;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }
    }


    public static void main(String[] args) {
//        Map<String,String> headers = new HashMap<>();
//        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        headers.put("Accept-Encoding","gzip, deflate");
//        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
//        headers.put("Connection","keep-alive");
//        headers.put("Cookie","DICT_UGC=be3af0da19b5c5e6aa4e17bd8d90b28a|; OUTFOX_SEARCH_USER_ID=1010675437@27.38.248.77; OUTFOX_SEARCH_USER_ID_NCOO=1049921357.8418585; _ntes_nnid=fef7db9b765553b5af510c2aecc25aad,1523443894642; search-popup-show=-1; UM_distinctid=162b794941670-07ebf7c6943f-3a614f0b-100200-162b79494176b3; P_INFO=qhx_kael@163.com|1523849604|0|search|00&99|gud&1523502868&search#gud&440300#10#0#0|&0|search&youdaodict_client|qhx_kael@163.com; tabRecord.examples=%23bilingual; tabRecord.webTrans=%23tEETrans; DICT_UGC=be3af0da19b5c5e6aa4e17bd8d90b28a|; JSESSIONID=abcqSWBMhKlIY53mvlrlw; webDict_HdAD=%7B%22req%22%3A%22http%3A//dict.youdao.com%22%2C%22width%22%3A960%2C%22height%22%3A240%2C%22showtime%22%3A5000%2C%22fadetime%22%3A500%2C%22notShowInterval%22%3A3%2C%22notShowInDays%22%3Afalse%2C%22lastShowDate%22%3A%22Mon%20Nov%2008%202010%22%7D; ___rl__test__cookies=1523938153130");
//        headers.put("Upgrade-Insecure-Requests","1");
//        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
//        String response = get("http://dict.youdao.com/search?q=fashion&keyfrom=dict2.index",headers,null);
//        System.out.println(response);
//        String url = "http://localhost:8080/login";
//        Map<String,String> headers = new HashMap<>();
////        headers.put("Authorization","bearer SPKvErMHWzDaOolFLhCyfPRtZvEy13nf");
//        headers.put("Content-Type", "application/json");
//        Map<String,String> form = new HashMap<>();
//        form.put("username", "18665878018");
//        form.put("password","12345678");
////        File file = new File("F:\\desktop\\picture\\timg.jpg");
////        String resp = postFile(url, file, "imgPath", headers, form);
//        String resp = post(url, headers, form);
//        System.out.println(resp);
//        uploadQuest();
//        videoTest();
//        importWord();
//        downloadFile("http://static.vip-young.com/exercise/vocabulary/us/f/feature1.mp3", "D:\\", "abcddd.mp3");
        System.out.println(getHttpStatusCode("http://static.vip-young.com/exercise/vocabulary/us/f/feature1.mp3"));

//        System.out.println("=====>"+checkFileUrl("http://static.vip-young.com/exercise/vocabulary/us/f/feature1.mp3"));
    }


    public static void videoTest(){
       String url = "http://yong.yongjiu6.com/20180710/XrxhyP3Y/1000kb/hls/7o9cw9wV6466024.jpeg";
       downloadFile(url, "D:\\tmp", "test.mp4");
    }

    public static void strTest(){
    }

    public static void uploadStu(){
        Map<String,File> fileMap = new HashMap<>();
        fileMap.put("file",new File("F:\\desktop\\excel\\学生.xlsx"));
        Map<String, String> params = new HashMap<>();
        params.put("classId","74");

        Map<String,String> headers =  new HashMap<>();
        headers.put("Authorization","bearer CLUcDzLCoiI3PJyHdAb06GvkL0LUxHHJ");

        String resp = postFile("http://localhost:8080/student/import/excel",fileMap, headers, params);
        System.out.println(resp);
    }

    public static void uploadQuest(){
        Map<String,File> fileMap = new HashMap<>();
        fileMap.put("fileExcel",new File("E:\\work\\vip-young\\人教-七下词汇定位U11-v1.1.xls"));
//        fileMap.put("fileZip",new File("F:\\测试内容\\Unit1课文音频.zip"));
        Map<String, String> params = new HashMap<>();
        params.put("ralationId","26602");

        Map<String,String> headers =  new HashMap<>();
        headers.put("Authorization","bearer olI0ma9RCyNYUujZsblABK1J7fUKELjJ");

        String resp = postFile("http://localhost:8080/excel/upload",fileMap, headers, params);
        System.out.println(resp);
    }

    public static void importWord(){
        Map<String,File> fileMap = new HashMap<>();
        fileMap.put("excel",new File("E:\\work\\vip-young\\语料库\\美国当代英语语料库20000词频表.xlsx"));
//        fileMap.put("fileZip",new File("F:\\测试内容\\Unit1课文音频.zip"));
        Map<String, String> params = new HashMap<>();
//        params.put("ralationId","26602");

        Map<String,String> headers =  new HashMap<>();
        headers.put("Authorization","bearer olI0ma9RCyNYUujZsblABK1J7fUKELjJ");

        String resp = postFile("http://localhost:8007/ques/importWordRate",fileMap, headers, params);
        System.out.println(resp);
    }
}

