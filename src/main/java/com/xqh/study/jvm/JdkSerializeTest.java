package com.xqh.study.jvm;

import com.alibaba.fastjson.JSON;
import com.xqh.utils.SerializeUtils;

import java.io.Serializable;

/**
 * Created by leo on 2018/2/26.
 */
public class JdkSerializeTest {

    public static class Shape implements Serializable{
        public String name;
    }

    public static class Circle extends Shape implements Serializable{

        private float radius;

        transient int color;

        public static String type = "Circle";

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.name="parentName";
        circle.setRadius(0.02f);
        circle.setColor(2);

        byte[] bytes = SerializeUtils.serialize(circle);

        System.out.println(JSON.toJSONString(SerializeUtils.unserialize(bytes)));
    }
}
