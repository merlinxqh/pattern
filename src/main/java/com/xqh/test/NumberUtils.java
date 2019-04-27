package com.xqh.test;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: leo
 * @Date: 2018/7/16 14:29
 * @Description: 计算工具类
 */
public class NumberUtils {

    /**
     * 减法 默认两位小数点
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal... b){
        BigDecimal res=a;
        for(BigDecimal t:b){
            if(null != res){
                res=subtract(res,t,2);
            }
        }
        return res;
    }

    public static BigDecimal subtract(BigDecimal a,BigDecimal b,int scale){
        if(null != a && null != b){
            return a.subtract(b).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 加法默认两位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(BigDecimal a,BigDecimal... b){
        BigDecimal res=a;
        for(BigDecimal t:b){
            if(null != res){
                res=add(res,t,2);
            }
        }
        return res;
    }

    public static BigDecimal add(BigDecimal a,BigDecimal b, int scale){
        if(null != a && null != b){
            return a.add(b).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 乘法 默认保留两位
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b){
        return multiply(a,b,2);
    }

    /**
     * 乘法
     * @param a
     * @param b
     * @param scale 小数点位数
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b, int scale){
        if(null != a && null != b){
            return a.multiply(b).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 除法 默认保留两位小数点
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b){
        return divide(a,b,2);
    }


    /**
     * 除法
     * @param a
     * @param b
     * @param scale 小数点位数
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale){
        if(null != a && null != b){
            return a.divide(b,scale, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 设置小数点位数
     * @param a
     * @return
     */
    public static BigDecimal setScale(BigDecimal a){
        return setScale(a,2);
    }

    /**
     * 设置小数点位数
     * @param a
     * @param scale
     * @return
     */
    public static BigDecimal setScale(BigDecimal a, int scale){
        if(null != a){
            return a.setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    private static boolean isMatch(String regex, String orginal){
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isPositiveDecimal(String orginal){
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isNegativeDecimal(String orginal){
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 判断是否是 小数
     * @param orginal
     * @return
     */
    public static boolean isDecimal(String orginal){
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    public static boolean isRealNumber(String orginal){
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    public static void main(String[] args) {
        System.out.println(isPositiveInteger("11.0"));
    }
}
