package com.xqh.test.yzs;

import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;
import com.xqh.test.yzs.nlu.NLURequestUtils.ReqNluThread;
import com.xqh.utils.ReadTxtFileUtils;
import com.xqh.utils.ThreadPoolUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * @ClassName CheckXiaomaoControlTest
 * @Description 验证小茂音箱 客控协议
 * @Author xuqianghui
 * @Date 2019/8/16 10:13
 * @Version 1.0
 */
@Slf4j
public class CheckXiaomaoControlTest {

    private static Set<String> serviceSet = new HashSet<String>(){
        {
            add("cn.yunzhisheng.setting.tv");
            add("cn.yunzhisheng.setting.air");
            add("cn.yunzhisheng.setting.thermostat");
        }
    };

    public static void main(String[] args) {
        removeRepeat();
    }

    public static void removeRepeat(){
        File txtFile = new File("E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\nlu测试集-new.txt");
        List<String> list = ReadTxtFileUtils.readTxt(txtFile);
        List<String> retList = Lists.newArrayList();
        list.stream().forEach(l-> {
            if(!retList.contains(l)){
                retList.add(l);
            }
        });
        ReadTxtFileUtils.writeToTxt(retList, "E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\nlu测试集-去重.txt");

    }

    // 直接校验 nlu结果
    public static void directCheckText(){
        File txtFile = new File("E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\all_nlu_ret.txt");
        List<String> nluRetList = ReadTxtFileUtils.readTxt(txtFile);
        List<String> sendList = Lists.newArrayList();
        List<String> noSendList = Lists.newArrayList();

        for(String nluRet:nluRetList){
            if(isRequestConnectPlatform(nluRet)){
                sendList.add(nluRet);
            }else {
                noSendList.add(nluRet);
            }
        }

        // 写入到文件
        ReadTxtFileUtils.writeToTxt(sendList, "E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\send_list.txt");
        ReadTxtFileUtils.writeToTxt(noSendList, "E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\no_send_list.txt");

    }

    /**
     * 请求 nlu 服务 验证 小茂音箱客控指令
     */
    public static void reqNluAndCheck(){
        File txtFile = new File("E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\nlu测试集-new.txt");
        List<String> txtList = ReadTxtFileUtils.readTxt(txtFile);
        List<ReqModel> nluRetList = Lists.newArrayList();

        for(String txt:txtList){
            FutureTask<String> future = new FutureTask<>(new ReqNluThread(txt));
            ThreadPoolUtils.submit(future);
            nluRetList.add(ReqModel.builder().text(txt)
                    .future(future).build());
        }
        List<ReqModel> noSendList = Lists.newArrayList();
        for(ReqModel m:nluRetList){
            try {
                m.setNluRet(m.getFuture().get());
            } catch (InterruptedException e) {
                log.error("", e);
            } catch (ExecutionException e) {
                log.error("", e);
            }

            if(StringUtils.hasText(m.getNluRet())){
                if(!isRequestConnectPlatform(m.getNluRet())){
                    noSendList.add(m);
                }
            }
        }
        List<String> noSendPrintList = Lists.newArrayList();
        noSendPrintList.add("=============================不发送连接平台指令=================================");
        for(ReqModel r:noSendList){
            noSendPrintList.add(r.getNluRet());
        }
        List<String> printList = nluRetList.stream().map(ReqModel::getNluRet).collect(Collectors.toList());
        ReadTxtFileUtils.writeToTxt(printList, "E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\all_nlu_ret.txt");
        ReadTxtFileUtils.writeToTxt(noSendPrintList, "E:\\document\\yzs\\program\\世茂\\酒店项目\\pass\\世茂数据1\\nlu对比测试集\\no_send_list.txt");

    }

    /**
     * 判断是否 发送客控请求到连接平台
     * @param
     * @return
     */
    private static boolean isRequestConnectPlatform(String nluResultString) {
		String deviceType = (String) JSONPath.read(nluResultString, "$.semantic.intent.operations[0].deviceType");
		String operands = (String) JSONPath.read(nluResultString, "$.semantic.intent.operations[0].operands");
		String service = (String) JSONPath.read(nluResultString, "$.service");
		return StringUtils.hasText(deviceType)
                || "ATTR_MODE".equals(operands)
                || "OBJ_LIGHT".equals(operands)
                || serviceSet.contains(service);
//        return org.apache.commons.lang.StringUtils.isNotEmpty(request.getDeviceType()) ||
//                org.apache.commons.lang.StringUtils.isNotEmpty(request.getOperator())   ||
//                org.apache.commons.lang.StringUtils.isNotEmpty(request.getOperands());

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqModel{

        private String text;

        private String nluRet;

        private FutureTask<String> future;
    }
}
