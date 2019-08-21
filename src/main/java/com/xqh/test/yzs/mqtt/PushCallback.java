package com.xqh.test.yzs.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @ClassName PushCallback
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/8/20 19:24
 * @Version 1.0
 */
@Slf4j
public class PushCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("重连mqtt服务...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("接收到订阅消息, topic==>{}, msg==>{}", topic, new String(mqttMessage.getPayload(), "UTF-8"));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("消息发布成功.........");
    }
}
