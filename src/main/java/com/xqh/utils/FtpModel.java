package com.xqh.utils;

import lombok.Data;

/**
 * @ClassName FtpModel
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/10/10 18:35
 * @Version 1.0
 */
@Data
public class FtpModel {

    private String ipAddr;//ip地址

    private Integer port;//端口号

    private String userName;//用户名

    private String pwd;//密码

    private String path;//aaa路径
}
