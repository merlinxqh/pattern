package com.xqh.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


/**

 */
@Slf4j
public class ExcelUtil {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");// 格式化 number为整

  private static final DecimalFormat DECIMAL_FORMAT_PERCENT = new DecimalFormat(
      "##.00%");//格式化分比格式，后面不足2位的用0补齐

  private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy/MM/dd");

  private static final DecimalFormat DECIMAL_FORMAT_NUMBER = new DecimalFormat(
      "0.00E000"); //格式化科学计数器

  private static final Pattern POINTS_PATTERN = Pattern.compile("0.0+_*[^/s]+"); //小数匹配

  /**
   * 对外提供读取excel 的方法
   */
  public static List<List<JSONObject>> readExcel(MultipartFile file) {
    try {

      String extension = file.getOriginalFilename()
          .substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
      if (Objects.equals("xls", extension) || Objects.equals("xlsx", extension)) {
        return readExcel(file.getInputStream());
      } else {
        throw new RuntimeException("不支持的文件类型");
      }
    } catch (Exception e) {
      throw new RuntimeException("", e);
    }
  }


  /**
   * 读取 office excel
   */
  public static List<List<JSONObject>> readExcel(InputStream inputStream) {
    List<List<JSONObject>> list1 = new LinkedList<>();
    Workbook workbook = null;
    try {
      workbook = WorkbookFactory.create(inputStream);
      int sheetsNumber = workbook.getNumberOfSheets();
      for (int n = 0; n < sheetsNumber; n++) {
        Sheet sheet = workbook.getSheetAt(n);
        Object value = null;
        Row row = null;
        Row row1 = sheet.getRow(sheet.getFirstRowNum());
        Cell cell = null;
        List<JSONObject> tems = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows();
            i++) { // 从第二行开始读取
          row = sheet.getRow(i);
          if (StringUtils.isEmpty(row)) {
            continue;
          }
          JSONObject json = new JSONObject();
          for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
            cell = row.getCell(j);
            if (StringUtils.isEmpty(cell)) {
              continue;
            }
            try {

              value = getCellValue(cell);
              if (value == null) {
                continue;
              }
              json.put(row1.getCell(j).toString(), value);
            } catch (Exception e) {
              json.clear();
            }
          }
          if (!json.isEmpty()) {
            tems.add(json);
          }
        }
        list1.add(tems);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
    return list1;
  }

  /**
   * 获取excel 单元格数据
   */
  private static Object getCellValue(Cell cell) {
    Object value = null;
    switch (cell.getCellTypeEnum()) {
      case _NONE:
        break;
      case STRING:
        value = cell.getStringCellValue();
        break;
      case NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) { //日期
          value = FAST_DATE_FORMAT
              .format(DateUtil.getJavaDate(cell.getNumericCellValue()));//统一转成 yyyy/MM/dd
        } else if ("@".equals(cell.getCellStyle().getDataFormatString())
            || "General".equals(cell.getCellStyle().getDataFormatString())
            || "0_ ".equals(cell.getCellStyle().getDataFormatString())) {
          //文本  or 常规 or 整型数值
          value = DECIMAL_FORMAT.format(cell.getNumericCellValue());
        } else if (POINTS_PATTERN.matcher(cell.getCellStyle().getDataFormatString())
            .matches()) { //正则匹配小数类型
          value = cell.getNumericCellValue();  //直接显示
        } else if ("0.00E+00".equals(cell.getCellStyle().getDataFormatString())) {//科学计数
          value = cell.getNumericCellValue();    //待完善
          value = DECIMAL_FORMAT_NUMBER.format(value);
        } else if ("0.00%".equals(cell.getCellStyle().getDataFormatString())) {//百分比
          value = cell.getNumericCellValue(); //待完善
          value = DECIMAL_FORMAT_PERCENT.format(value);
        } else if ("# ?/?".equals(cell.getCellStyle().getDataFormatString())) {//分数
          value = cell.getNumericCellValue(); ////待完善
        } else { //货币
          value = cell.getNumericCellValue();
          value = DecimalFormat.getCurrencyInstance().format(value);
        }
        break;
      case BOOLEAN:
        value = cell.getBooleanCellValue();
        break;
      case BLANK:
        //value = ",";
        break;
      default:
        value = cell.toString();
    }
    return value;
  }


  public static void main(String[] args) {
    try {

      InputStream fin = new FileInputStream(new File(("D:\\学生导入模板.xlsx")));

      List<List<JSONObject>> res = readExcel(fin);
      res.forEach(f -> {
        for (JSONObject jsonObject : f) {
          log.info("{}", jsonObject);
        }
        log.info("_____");
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}

