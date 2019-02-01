package com.xqh.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xupanpan on 2017/12/26.
 */
public class DateUtil {
  public static final String DATE_DAY_FORMAT = "yyyyMMdd";
  public static final String DATE_MONTH_FORMAT = "yyyyMM";
  public static final String DATE_YEAR= "yyyy";
  public static final String DATE_DAY_DASH_FORMAT = "yyyy-MM-dd";
  public static final String DATE_DAY_ZH_FORMAT = "yyyy年MM月dd日";
  public static final String DATE_PATTERN_SIMPLE="yyyy-MM-dd";

  public static final String DATE_PATTERN_COMPLEX="yyyy-MM-dd HH:mm:ss";

  public static String transferDateStr(String dateDay, String fromFormat, String toFormat) {
    return DateTime.parse(dateDay, DateTimeFormat.forPattern(fromFormat)).toString(DateTimeFormat.forPattern(toFormat));
  }

  public static String getTodayDateStr(String pattern) {
    return DateTime.now().toString(DateTimeFormat.forPattern(pattern));
  }

  public static String getYesterdayDateStr(String pattern) {
    return DateTime.now().minusDays(1).toString(pattern);
  }

  public static String getYesterdayYearMonthDay() {
    return getYesterdayDateStr(DATE_DAY_FORMAT);
  }

  public static String getCurrentYearMonth() {
    return DateTime.now().toString(DATE_MONTH_FORMAT);
  }

  public static boolean isYearDayAfterToday(String yearDay) {
    return yearDay.compareTo(getYesterdayYearMonthDay()) > 0;
  }

  public static boolean isYearMonthGreaterOrEqualCurrentMonth(String yearMonth) {
    String currentMonth = getTodayDateStr("yyyyMM");
    return yearMonth.compareTo(currentMonth) >= 0;
  }

  /**
   * 默认 yyyy-MM-dd
   * @param date
   * @return
   */
  public static Date parseDate(String date){
    return parseDate(date,DATE_PATTERN_SIMPLE);
  }

  /**
   * @param date
   * @param pattern
   * @return
   */
  public static Date parseDate(String date,String pattern){
    if(DATE_PATTERN_SIMPLE.equals(pattern)){
      date=autoMatchPattern(DATE_PATTERN_SIMPLE,date);
      LocalDate localDate=LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
      ZoneId zone = ZoneId.systemDefault();
      return Date.from(localDate.atStartOfDay().atZone(zone).toInstant());
    }
    if(DATE_PATTERN_COMPLEX.equals(pattern)) {
      date=autoMatchPattern(DATE_PATTERN_COMPLEX,date);
      LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
      ZoneId zone = ZoneId.systemDefault();
      return Date.from(localDateTime.atZone(zone).toInstant());
    }

    return null;
  }

  /**
   * 匹配格式
   * @param pattern
   * @param date
   * @return
   */
  public static String autoMatchPattern(String pattern,String date){
    if(date.length() == pattern.length()){
      return date;
    }

    String[] arry=date.split(" ");
    StringBuffer sb=new StringBuffer();
    for(int i=0;i<arry[0].split("-").length;i++){
      String sub=arry[0].split("-")[i];
      if(sub.length() == 1){
        sub="0"+sub;
      }
      if(i == 0){
        sb.append(sub);
      }else{
        sb.append("-"+sub);
      }
    }
    if(arry.length == 2){
      sb.append(" ");
      for(int i=0;i<arry[1].split(":").length;i++){
        String sub=arry[1].split(":")[i];
        if(sub.length() == 1){
          sub="0"+sub;
        }
        if(i == 0){
          sb.append(sub);
        }else{
          sb.append(":"+sub);
        }
      }
    }
    return sb.toString();
  }

  /**
   * 时间格式化
   * @param date
   * @return
   */
  public static String formatDate(Date date){
    return formatDate(date,DATE_PATTERN_SIMPLE);
  }

  /**
   * 格式化时间
   * @param date
   * @param pattern
   * @return
   */
  public static String formatDate(Date date, String pattern){
    Instant instant = date.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    LocalDateTime local = LocalDateTime.ofInstant(instant, zone);
    return local.format(DateTimeFormatter.ofPattern(pattern));
  }


  /**
   * 时间添加
   * @param date
   * @param amountToAdd
   * @param unit
   * @return
   */
  public static Date plusDate(Date date,long amountToAdd, ChronoUnit unit){
    Instant instant = date.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    LocalDateTime local = LocalDateTime.ofInstant(instant, zone);
    local=local.plus(amountToAdd,unit);
    return Date.from(local.atZone(zone).toInstant());
  }

  /**
   * 前一天 后一天 返回 字符串
   * @param date
   * @param add  前后几天 正负数
   * @return
   */
  public static String plusDate(String date, long add){
    Date d = parseDate(date);
    Date n = plusDate(d, add, ChronoUnit.DAYS);
    return formatDate(n);
  }

  /**
   * 前一天 后一天 返回 字符串
   * @param date
   * @param add  前后几天 正负数
   * @return
   */
  public static String plusDate(String date, long add, ChronoUnit unit){
    Date d = parseDate(date);
    Date n = plusDate(d, add, unit);
    return formatDate(n);
  }

  /**
   * 获取 指定一天 所在周 所有 日期
   * @param dateStr
   * @return
   * @throws ParseException
   */
  public static List<String> getBelongWeekDays(String dateStr){
    List<String> resList = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    cal.setTime(parseDate(dateStr));

    int d = 0;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      d = -6;
    } else {
      d = 2 - cal.get(Calendar.DAY_OF_WEEK);
    }
    // 所在周开始日期
    cal.add(Calendar.DAY_OF_WEEK, d);
    String monday = formatDate(cal.getTime());//星期一
    resList.add(formatDate(cal.getTime()));
    resList.add(plusDate(monday, 1));
    resList.add(plusDate(monday, 2));
    resList.add(plusDate(monday, 3));
    resList.add(plusDate(monday, 4));
    resList.add(plusDate(monday, 5));
    resList.add(plusDate(monday, 6));

    return resList;

  }



  /**
   * 获取两个日期之间的天数
   *
   * @param before
   * @param after
   * @return
   */
  public static int getDistanceOfTwoDate(Date before, Date after) {
    long beforeTime = before.getTime();
    long afterTime = after.getTime();
    return (new Double((afterTime - beforeTime) / (1000 * 60 * 60 * 24))).intValue();
  }

  /**
   * 获得该月第一天
   * @param month
   * @return
   */
  public static String getFirstDayOfMonth(String year,String month){
    Calendar cal = Calendar.getInstance();
    //设置年份
    cal.set(Calendar.YEAR,Integer.parseInt(year));
    //设置月份
    cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
    //获取某月最小天数
    int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    //设置日历中月份的最小天数
    cal.set(Calendar.DAY_OF_MONTH, firstDay);
    //格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_SIMPLE);
    String firstDayOfMonth = sdf.format(cal.getTime());
    return firstDayOfMonth;
  }

  /**
   * 获得该月最后一天
   * @param month
   * @return
   */
  public static String getLastDayOfMonth(String year,String month) {
    Calendar cal = Calendar.getInstance();
    //设置年份
    cal.set(Calendar.YEAR,Integer.parseInt(year));
    //设置月份
    cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
    //获取某月最大天数
    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    //设置日历中月份的最大天数
    cal.set(Calendar.DAY_OF_MONTH, lastDay);
    //格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_SIMPLE);
    String lastDayOfMonth = sdf.format(cal.getTime());
    return lastDayOfMonth;

  }


  // 获得某天最大时间 2017-10-15 23:59:59
  public static Date getEndOfDay(String date) {
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(DateUtil.parseDate(date).getTime()), ZoneId.systemDefault());;
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
  }

  // 获得某天最小时间 2017-10-15 00:00:00
  public static Date getStartOfDay(String date) {
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(DateUtil.parseDate(date).getTime()), ZoneId.systemDefault());
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
  }

}
