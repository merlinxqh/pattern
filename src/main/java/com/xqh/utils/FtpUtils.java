package com.xqh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.Objects;

/**
 * @ClassName FtpUtils
 * @Description ftp文件上传
 * @Author xuqianghui
 * @Date 2019/10/10 18:33
 * @Version 1.0
 */
@Slf4j
public class FtpUtils {

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.error("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            log.error("FTP的IP地址可能错误，请正确配置。", e);
        } catch (IOException e) {
            System.out.println("");
            log.error("FTP的端口错误,请正确配置。", e);
        }
        return ftpClient;
    }

    /*
     * 从FTP服务器下载文件
     *
     * @param ftpHost FTP IP地址
     * @param ftpUserName FTP 用户名
     * @param ftpPassword FTP用户名密码
     * @param ftpPort FTP端口
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     * @param localPath 下载到本地的位置 格式：H:/download
     * @param fileName 文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName,
                                       String ftpPassword, int ftpPort, String ftpPath, String localPath,
                                       String fileName) {

        FTPClient ftpClient = null;
        OutputStream os = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

            File localFile = new File(localPath + File.separatorChar + fileName);
            os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(fileName, os);
        } catch (FileNotFoundException e) {
            log.error("没有找到" + ftpPath + "文件", e);
        } catch (SocketException e) {
            log.error("连接FTP失败.", e);
        } catch (IOException e) {
            log.error("文件读取错误。", e);
        } finally {

            try {
                if (Objects.nonNull(os)) {
                    os.close();
                }
                if (Objects.nonNull(ftpClient)) {
                    ftpClient.logout();
                }
            } catch (IOException e) {
                log.error("", e);
            }

        }

    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param ftpHost     FTP服务器hostname
     * @param ftpUserName 账号
     * @param ftpPassword 密码
     * @param ftpPort     端口
     * @param ftpPath     FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName    ftp文件名称
     * @param input       文件流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName,
                                     String ftpPassword, int ftpPort, String ftpPath,
                                     String fileName, InputStream input) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

            ftpClient.storeFile(fileName, input);

            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public static void main(String[] args) throws FileNotFoundException {
        
    }
}
