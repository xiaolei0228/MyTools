package com.ehome.httpclient;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: 处理excel文档
 * @Author xiaolei-0228@163.com
 * @Date 2015-08-09 19:41:58
 */
public class ExcelUtil {

    /**
     * 生成excel文件
     *
     * @param sheetTitle  工作表名称
     * @param titleMap    标题列，一定要是LinkedHashMap  字段名_列宽:字段显示名称，其中字段名中下划线右边是列的宽度
     * @param mergedLines 要合并的等号集合，传null表示不合并
     * @param dataMapList 数据集
     * @param filePath    文件全路径
     * @param summaryLine 汇总行
     */
    public static void create(String sheetTitle, Map<String, String> titleMap, List<Map<String, Object>> dataMapList, List<Integer> mergedLines, String filePath, Integer summaryLine) {
        mkdirs(filePath);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetTitle);

        // 标题样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 15);
        font.setFontName("微软雅黑");
        titleStyle.setFont(font);

        // 创建单元格样式
        CellStyle dataCellStyle = wb.createCellStyle();
        dataCellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        dataCellStyle.setWrapText(true);
        dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框
        Font dataCellFont = wb.createFont();
        dataCellFont.setColor(HSSFColor.BLACK.index);
        dataCellFont.setFontHeightInPoints((short) 11);
        dataCellStyle.setFont(dataCellFont);

        // 存放了列名
        List<String> titleList = new ArrayList<>();
        // 第一行(标题行)
        int column = 0;
        HSSFRow firstRow = sheet.createRow(0);
        firstRow.setHeightInPoints(25);
        for (Map.Entry<String, String> entry : titleMap.entrySet()) {
            HSSFCell cell = firstRow.createCell(column);
            cell.setCellValue(entry.getValue());
            // 设置样式
            String[] key = entry.getKey().split("_");
            sheet.setColumnWidth(column, Integer.valueOf(key[1]));
            cell.setCellStyle(titleStyle);
            sheet.createFreezePane(0, 1);

            titleList.add(key[0]);
            column++;
        }

        // 填充数据
        for (int r = 0; r < dataMapList.size(); r++) {
            HSSFRow row = sheet.createRow(r + 1);               // 创建一行
            Map<String, Object> dataMap = dataMapList.get(r);   // 拿一行数据
            for (int c = 0; c < titleList.size(); c++) {
                HSSFCell cell = row.createCell(c);
                String value = dataMap.get(titleList.get(c)) != null ? dataMap.get(titleList.get(c)).toString() : "";
                if (StringUtils.isNotBlank(value) && !"null".equals(value) && StringUtils.isNumeric(value)) {
                    cell.setCellValue(Double.parseDouble(value));
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                } else {
                    cell.setCellValue(value);
                }
                // 设置样式
                cell.setCellStyle(dataCellStyle);
            }
        }

        // 合并单元格
        if (mergedLines != null && mergedLines.size() > 0)
        for (Integer line : mergedLines) {
            CellRangeAddress mergedRange = new CellRangeAddress(line, line, 0, (titleList.size() - 1));
            sheet.addMergedRegion(mergedRange);
            // 为合并的单元格设置样式
            HSSFRow row = sheet.getRow(line);
            if (row != null) {
                row.setHeightInPoints(22);
                HSSFCell cell = row.getCell(0);
                HSSFCellStyle mergedCellStyle = wb.createCellStyle();
                mergedCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                Font mergedCellFont = wb.createFont();
                mergedCellFont.setFontHeightInPoints((short) 11);
                mergedCellFont.setFontName("微软雅黑");
                mergedCellStyle.setFont(mergedCellFont);
                mergedCellStyle.setFillBackgroundColor(HSSFColor.AQUA.index);
                cell.setCellStyle(mergedCellStyle);
            }
        }

        // 汇总行
        if (summaryLine != null) {
            CellRangeAddress mergedRange = new CellRangeAddress(summaryLine, summaryLine, 0, (titleList.size() - 1));
            sheet.addMergedRegion(mergedRange);
            // 为合并的单元格设置样式
            HSSFRow row = sheet.getRow(summaryLine);
            if (row != null) {
                row.setHeightInPoints(100);
                HSSFCell cell = row.getCell(0);
                HSSFCellStyle mergedCellStyle = wb.createCellStyle();
                mergedCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                mergedCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                mergedCellStyle.setWrapText(true);
                Font mergedCellFont = wb.createFont();
                mergedCellFont.setFontHeightInPoints((short) 11);
                mergedCellFont.setFontName("微软雅黑");
                mergedCellStyle.setFont(mergedCellFont);
                mergedCellStyle.setFillBackgroundColor(HSSFColor.AQUA.index);
                cell.setCellStyle(mergedCellStyle);
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成excel文件,表头带斜线
     *
     * @param sheetTitle  工作表名称
     * @param titleMap    标题列，一定要是LinkedHashMap  字段名_列宽:字段显示名称，其中字段名中下划线右边是列的宽度。有斜线的cell要中午要用空格分开，比如：样品    市场
     * @param dataMapList 数据集
     * @param filePath    文件全路径
     */
    public static void createWithSlash(String sheetTitle, Map<String, String> titleMap, List<Map<String, Object>> dataMapList, String filePath) {
        mkdirs(filePath);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetTitle);

        // 标题样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillPattern(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 15);
        font.setFontName("微软雅黑");
        titleStyle.setFont(font);

        // 斜线单元格的样式
        CellStyle slashCellStyle = wb.createCellStyle();
        slashCellStyle.setWrapText(true);
        slashCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        slashCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font slashFont = wb.createFont();
        slashFont.setColor(HSSFColor.BLACK.index);
        slashFont.setFontHeightInPoints((short) 11);
        slashFont.setFontName("微软雅黑");
        slashCellStyle.setFont(slashFont);

        // 创建单元格样式
        CellStyle dataCellStyle = wb.createCellStyle();
        dataCellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        dataCellStyle.setWrapText(true);
        dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框
        Font dataCellFont = wb.createFont();
        dataCellFont.setColor(HSSFColor.BLACK.index);
        dataCellFont.setFontHeightInPoints((short) 11);
        dataCellStyle.setFont(dataCellFont);

        // 存放了列名
        List<String> titleList = new ArrayList<>();
        // 第一行(标题行)
        int column = 0;
        HSSFRow firstRow = sheet.createRow(0);
        firstRow.setHeightInPoints(30);
        for (Map.Entry<String, String> entry : titleMap.entrySet()) {
            HSSFCell cell = firstRow.createCell(column);
            cell.setCellValue(entry.getValue());
            // 设置样式
            String[] key = entry.getKey().split("_");
            sheet.setColumnWidth(column, Integer.valueOf(key[1]));
            cell.setCellStyle(titleStyle);
            sheet.createFreezePane(0, 1);

            if (column == 1) {
                //画线(由左上到右下的斜线)
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short) 1, 0, (short) 1, 0);
                HSSFSimpleShape shape1 = patriarch.createSimpleShape(a);
                shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
                shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
                cell.setCellStyle(slashCellStyle);
            }

            titleList.add(key[0]);
            column++;
        }

        // 填充数据
        for (int r = 0; r < dataMapList.size(); r++) {
            HSSFRow row = sheet.createRow(r + 1);               // 创建一行
            Map<String, Object> dataMap = dataMapList.get(r);   // 拿一行数据
            for (int c = 0; c < titleList.size(); c++) {
                HSSFCell cell = row.createCell(c);
                String value = dataMap.get(titleList.get(c)) != null ? dataMap.get(titleList.get(c)).toString() : "";
                if (c == 0) {
                    value = r + 1 + "";
                }
                cell.setCellValue(value);
                // 设置样式
                cell.setCellStyle(dataCellStyle);
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成excel文件
     *
     * @param sheetTitle  工作表名称
     * @param titleMap    标题列，一定要是LinkedHashMap  字段名_列宽:字段显示名称，其中字段名中下划线右边是列的宽度
     * @param mergedLines 要合并的等号集合，传null表示不合并
     * @param titleLines  多个标题的行号集合，和mergedLines一起使用
     * @param dataMapList 数据集
     * @param filePath    文件全路径
     * @param summaryLine 汇总行
     */
    public static void createWithEachTitle(String sheetTitle, Map<String, String> titleMap, List<Map<String, Object>> dataMapList, List<Integer> mergedLines, List<Integer> titleLines, String filePath, Integer summaryLine) {
        mkdirs(filePath);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetTitle);

        // 标题样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setFontName("微软雅黑");
        titleStyle.setFont(font);

        // 创建单元格样式
        CellStyle dataCellStyle = wb.createCellStyle();
        dataCellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        dataCellStyle.setWrapText(true);
        dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框
        Font dataCellFont = wb.createFont();
        dataCellFont.setColor(HSSFColor.BLACK.index);
        dataCellFont.setFontHeightInPoints((short) 11);
        dataCellStyle.setFont(dataCellFont);

        // 数字格式的单元格样式
        HSSFCellStyle numCellStyle = wb.createCellStyle();
        numCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        numCellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        numCellStyle.setWrapText(true);
        numCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        numCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        numCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        numCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        numCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        numCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框
        Font numDataCellFont = wb.createFont();
        numDataCellFont.setColor(HSSFColor.BLACK.index);
        numDataCellFont.setFontHeightInPoints((short) 11);
        numCellStyle.setFont(numDataCellFont);

        // 存放了列名
        List<String> titleList = new ArrayList<>();
        // 第一行(标题行)
        int column = 0;
        HSSFRow firstRow = sheet.createRow(0);
        firstRow.setHeightInPoints(25);
        for (Map.Entry<String, String> entry : titleMap.entrySet()) {
            HSSFCell cell = firstRow.createCell(column);
            cell.setCellValue(entry.getValue());
            // 设置样式
            String[] key = entry.getKey().split("_");
            sheet.setColumnWidth(column, Integer.valueOf(key[1]));
            cell.setCellStyle(titleStyle);
            //sheet.createFreezePane(0, 1);
            firstRow.setZeroHeight(true);
            titleList.add(key[0]);
            column++;
        }

        // 填充数据
        for (int r = 0; r < dataMapList.size(); r++) {
            HSSFRow row = sheet.createRow(r + 1);               // 创建一行
            Map<String, Object> dataMap = dataMapList.get(r);   // 拿一行数据
            for (int c = 0; c < titleList.size(); c++) {
                HSSFCell cell = row.createCell(c);
                String value = dataMap.get(titleList.get(c)) != null ? dataMap.get(titleList.get(c)).toString() : "";
                if (value.getBytes().length == value.length() && StringUtils.isNotBlank(value) && !"null".equals(value) && !Double.isNaN(Double.valueOf(value))) {
                    double dv = new BigDecimal(Double.valueOf(value)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                    if (dv == 0) {
                        cell.setCellValue("无");
                        cell.setCellStyle(dataCellStyle);
                    } else {
                        cell.setCellValue(dv);
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellStyle(numCellStyle);
                    }
                } else {
                    cell.setCellValue(("0".equals(value) || "0.00".equals(value) || StringUtils.isBlank(value)) ? "无" : value);
                    cell.setCellStyle(dataCellStyle);
                }
            }
        }

        // 合并单元格
        if (mergedLines != null && mergedLines.size() > 0) {
            for (Integer line : mergedLines) {
                CellRangeAddress mergedRange = new CellRangeAddress(line, line, 0, (titleList.size() - 1));
                sheet.addMergedRegion(mergedRange);
                // 为合并的单元格设置样式
                HSSFRow row = sheet.getRow(line);
                if (row != null) {
                    row.setHeightInPoints(22);
                    HSSFCell cell = row.getCell(0);
                    HSSFCellStyle mergedCellStyle = wb.createCellStyle();
                    mergedCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                    Font mergedCellFont = wb.createFont();
                    mergedCellFont.setFontHeightInPoints((short) 11);
                    mergedCellFont.setFontName("微软雅黑");
                    mergedCellStyle.setFont(mergedCellFont);
                    mergedCellStyle.setFillBackgroundColor(HSSFColor.AQUA.index);
                    cell.setCellStyle(mergedCellStyle);
                }
            }

            for (Integer line : titleLines) {
                // 插入标题行
                int titleRowColumn = 0;
                HSSFRow titleRow = sheet.getRow(line);
                for (Map.Entry<String, String> entry : titleMap.entrySet()) {
                    HSSFCell cell = titleRow.getCell(titleRowColumn);
                    cell.setCellValue(entry.getValue());
                    // 设置样式
                    String[] key = entry.getKey().split("_");
                    sheet.setColumnWidth(titleRowColumn, Integer.valueOf(key[1]));
                    cell.setCellStyle(titleStyle);
                    titleRowColumn++;
                }
            }
        }


        // 汇总行
        if (summaryLine != null) {
            CellRangeAddress mergedRange = new CellRangeAddress(summaryLine, summaryLine, 0, (titleList.size() - 1));
            sheet.addMergedRegion(mergedRange);
            // 为合并的单元格设置样式
            HSSFRow row = sheet.getRow(summaryLine);
            if (row != null) {
                row.setHeightInPoints(100);
                HSSFCell cell = row.getCell(0);
                HSSFCellStyle mergedCellStyle = wb.createCellStyle();
                mergedCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                mergedCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                mergedCellStyle.setWrapText(true);
                Font mergedCellFont = wb.createFont();
                mergedCellFont.setFontHeightInPoints((short) 11);
                mergedCellFont.setFontName("微软雅黑");
                mergedCellStyle.setFont(mergedCellFont);
                mergedCellStyle.setFillBackgroundColor(HSSFColor.AQUA.index);
                cell.setCellStyle(mergedCellStyle);
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建目录
     *
     * @param filePath 文件路径
     */
    private static void mkdirs(String filePath) {
        String path = filePath.substring(0, filePath.lastIndexOf(File.separator));
        File f = new File(path);
        if (!f.exists()) f.mkdirs();
    }

}
