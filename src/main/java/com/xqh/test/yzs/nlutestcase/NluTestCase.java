package com.xqh.test.yzs.nlutestcase;

import com.google.common.collect.Lists;
import com.xqh.utils.ReadTxtFileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @ClassName NluTestCase
 * @Description nlu 测试集
 * @Author xuqianghui
 * @Date 2019/7/25 16:40
 * @Version 1.0
 */
@Slf4j
public class NluTestCase {

    public static void main(String[] args) {
        List<String> uniqueList = Lists.newArrayList();
//
        List<String> list1 = ReadTxtFileUtils.readTxt(new File("C:\\Users\\YZS\\Desktop\\nlu测试集-new.txt"));
        List<String> list2 = ReadTxtFileUtils.readTxt(new File("C:\\Users\\YZS\\Desktop\\nlu测试指令.txt"));

        removeRepeat(uniqueList, list1);
        removeRepeat(uniqueList, list2);
        String outTxt = "C:\\Users\\YZS\\Desktop\\out.txt";
        ReadTxtFileUtils.writeToTxt(uniqueList, outTxt);

    }

    public static void removeRepeat(List<String> list, List<String> newList){
        newList.stream().forEach(f-> {
            if(!list.contains(f)){
                list.add(f);
            }
        });
    }


}
