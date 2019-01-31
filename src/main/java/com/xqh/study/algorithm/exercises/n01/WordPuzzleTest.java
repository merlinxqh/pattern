package com.xqh.study.algorithm.exercises.n01;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 字谜游戏问题
 */
public class WordPuzzleTest {

    public static final String[] WORDS=new String[]{"love","apple","dream","happy"};

    public static final char[][] chars={{'d','e','r','t'},
                                        {'a','l','z','v'},
                                        {'p','b','s','o'},
                                        {'a','d','m','p'}};

    public static void main(String[] args) {
        for(String word:WORDS){
            checkExist(word);
        }
    }

    public static void checkExist(String word){
       List<Position> plist=new ArrayList<>();
        for(String w:word.split("")){
            breakPosition:for(int i=0;i<chars.length;i++){
                char[] c1=chars[i];
                for(int j=0;j<c1.length;j++){
                    if(w.equals(String.valueOf(c1[j]))){
                        plist.add(new Position(i,j));
                        break breakPosition;
                    }
                }
            }
        }
        if(plist.size() == word.length()){
            //说明全部匹配上
            System.out.println("单词("+word+")坐标为:"+ JSON.toJSONString(plist));
        }
    }

    public static class Position{
        private int i1;

        private int i2;

        public Position(int i1,int i2){
            this.i1=i1;
            this.i2=i2;
        }

        public int getI1() {
            return i1;
        }

        public void setI1(int i1) {
            this.i1 = i1;
        }

        public int getI2() {
            return i2;
        }

        public void setI2(int i2) {
            this.i2 = i2;
        }
    }


}
