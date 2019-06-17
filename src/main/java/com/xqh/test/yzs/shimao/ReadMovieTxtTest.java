package com.xqh.test.yzs.shimao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xqh.utils.ReadTxtFileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ReadMovieTxtTest
 * @Description 读取影视 名称  用于导入 对应 影视领域 热词
 * @Author xuqianghui
 * @Date 2019/6/13 14:43
 * @Version 1.0
 */
public class ReadMovieTxtTest {

    public static void main(String[] args) {
        File file = new File("E:\\document\\yzs\\program\\世茂\\酒店项目\\影视热词.txt");
        List<String> list = ReadTxtFileUtils.readTxt(file);
        List<String> movieList = Lists.newArrayList();
        for(String str:list){
            JSONObject json = JSON.parseObject(str);
            String movie = json.getString("zh-CN");
//            if(movie.contains("·")){
//                movie = movie.replaceAll("·", "");
//            }

            movie = replaceStr(movie, Arrays.asList("·", "：", "-", "\\(", "\\)", "！"));

//            movie = substringChar(movie, "：");
//            movie = substringChar(movie, "-");
//            movie = substringChar(movie, "(上)");
//            movie = substringChar(movie, "II");
//            movie = substringChar(movie, "之");
//            movie = matchEndWith(movie, Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));

            if(!movieList.contains(movie)){
                movieList.add(movie);
                System.out.println(movie);
            }
        }
    }

    public static String replaceStr(String movie, List<String> list){
        for(String s:list){
            if(movie.contains(s)){
                movie = movie.replaceAll(s, "");
            }
        }
        return movie;
    }

    public static String matchEndWith(String movie, List<String> endList){
        for(String end:endList){
            if(movie.endsWith(end)){
               movie = movie.substring(0, movie.lastIndexOf(end));
               break;
            }
        }
        return movie;
    }

    /**
     *  截取字符之前的内容
     *  @param movie
     * @param symbol
     * @return
     */
    public static String substringChar(String movie, String symbol){
        if(movie.contains(symbol)){
            return movie.substring(0, movie.indexOf(symbol));
        }
        return movie;
    }

}
