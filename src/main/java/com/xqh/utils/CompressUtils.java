package com.xqh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

/**
 * Created by leo on 2017/10/25.
 * 压缩文件 & 解压缩文件
 */
@Slf4j
public class CompressUtils {

    private static final int byte_size=5*100*1024;

    /**
     * 压缩文件
     * @param targetPath  目标目录
     * @param sourcePath  被压缩文件
     */
    public static void zipFile(String targetPath, String sourcePath) {
        ZipOutputStream _zipOut=null;
        try {
            if (targetPath.endsWith(".zip") || targetPath.endsWith(".ZIP")) {
                _zipOut = new ZipOutputStream(new FileOutputStream(new File(targetPath)));
                _zipOut.setEncoding("GBK");
                File file =new File(sourcePath);
                handlerFile(targetPath, _zipOut, file, "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != _zipOut){
                try {
                    _zipOut.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * @param zip     压缩的目的地址
     * @param zipOut
     * @param srcFile 被压缩的文件信息
     * @param path    在zip中的相对路径
     * @throws IOException
     */
    private static void handlerFile(String zip, ZipOutputStream zipOut, File srcFile, String path) throws IOException {
//        System.out.println(" begin to compression file[" + srcFile.getName() + "]");
        if (!"".equals(path) && !path.endsWith(File.separator)) {
            path += File.separator;
        }
        byte[] _byte = new byte[byte_size];
        if (!srcFile.getPath().equals(zip)) {
            if (srcFile.isDirectory()) {
                File[] _files = srcFile.listFiles();
                if (_files.length == 0) {
                    zipOut.putNextEntry(new ZipEntry(path + srcFile.getName() + File.separator));
                    zipOut.closeEntry();
                } else {
                    for (File _f : _files) {
                        handlerFile(zip, zipOut, _f, path + srcFile.getName());
                    }
                }
            } else {
                InputStream _in = new FileInputStream(srcFile);
                zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
                int len = 0;
                while ((len = _in.read(_byte)) > 0) {
                    zipOut.write(_byte, 0, len);
                }
                _in.close();
                zipOut.closeEntry();
            }
        }
    }

    /**
     * 解压缩文件 至当前文件夹
     * @param zipPath
     */
    public static void upZipFile(String zipPath){
        upZipFile(new File(zipPath), getFileDirPath(zipPath));
    }



    /**
     * 解压缩文件
     * @param zipPath  压缩文件路径
     * @param descDir  目标路径
     * @return
     */
    public static void upZipFile(String zipPath, String descDir){
        upZipFile(new File(zipPath), descDir);
    }

    /**
     * 对.zip文件进行解压缩
     *
     * @param zipFile 解压缩文件
     * @param descDir 压缩的目标地址
     * @return
     */
    public static void upZipFile(File zipFile, String descDir) {
        byte[] _byte = new byte[byte_size];
        ZipFile _zipFile=null;
        try {
            _zipFile = new ZipFile(zipFile, "GBK");
            for (Enumeration entries = _zipFile.getEntries(); entries.hasMoreElements(); ) {
                InputStream _in=null;
                OutputStream _out=null;
                try {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    File _file = new File(descDir + File.separator + entry.getName());
                    if (entry.isDirectory()) {
                        _file.mkdirs();
                    } else {
                        File _parent = _file.getParentFile();
                        if (!_parent.exists()) {
                            _parent.mkdirs();
                        }
                        _in = _zipFile.getInputStream(entry);
                        _out = new FileOutputStream(_file);
                        int len = 0;
                        while ((len = _in.read(_byte)) > 0) {
                            _out.write(_byte, 0, len);
                        }
                        _out.flush();
                    }
                }finally {
                    try {
                        if(null != _in){
                            _in.close();
                        }
                        if(null != _out){
                            _out.close();
                        }
                    } catch (IOException e) {
                        log.error("", e);
                    }
                }

            }
        } catch (IOException e) {
        }finally {
            if(null != _zipFile){
                try {
                    _zipFile.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 对临时生成的文件夹和文件夹下的文件进行删除
     */
    public static void deleteFile(String delPath) {
        try {
            File file = new File(delPath);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delPath + File.separator + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deleteFile(delPath + File.separator + filelist[i]);
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }


    /**
     * 获取文件目录路径
     * @param path 文件路径
     * @return
     */
    public static String getFileDirPath(String path){
       if(path.contains(File.separator)){
           return path.substring(0,path.lastIndexOf(File.separator));
       }
       return path;
    }

}
