package com.ihome.poi.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @version V1.0
 * @Project: MyTools
 * @Title: 处理excel文档
 * @Package PACKAGE_NAME
 * @Description:
 * @Author xiaolei-0228@163.com
 * @Date 13-11-17 下午2:37
 * @Copyright: 2013 ehome.com
 */
public class ExcelUtil {
    /**
     * 简单创建工作表到指定目录
     *
     * @param excelBean 文件存放的目录
     */
    public static boolean createSimpleWorkBook(XLSBean excelBean) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(excelBean.getSheetName());
        Row row;
        Cell cell;

        // 标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        titleStyle.setFillPattern(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeight((short) 300);
        font.setFontName("微软雅黑");
        titleStyle.setFont(font);

        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框

        Map<String, Integer> titleAttrMap = excelBean.getTitleAttr();
        List<Map<String, String>> dataList = excelBean.getSimpleDataList();

        for (int r = 0; r < dataList.size(); r++) {  // 行
            if (r == 0) {       // 标题
                row = sheet.createRow(r);
                int cellNum = 0; // 列标志位
                for (Map.Entry entry : titleAttrMap.entrySet()) {
                    cell = row.createCell(cellNum);
                    // 生成标题
                    cell.setCellValue(entry.getKey().toString());
                    cell.setCellStyle(titleStyle);
                    sheet.setColumnWidth(cellNum, Integer.valueOf(entry.getValue().toString()));
                    cellNum++;
                }
            }

            // 填充数据
            row = sheet.createRow(r + 1);
            int cellNum = 0; // 列标志位
            Map<String, String> dataMap = dataList.get(r);
            for (Map.Entry entry : titleAttrMap.entrySet()) {   // 列
                cell = row.createCell(cellNum);
                cell.setCellValue(dataMap.get(entry.getKey().toString()));
                cell.setCellStyle(cellStyle);

                cellNum++;
            }
        }

        return writeXLSFile(workbook, excelBean);
    }

    /**
     * 创建工作表到指定目录 并动态合并指定列的某些行，即：分组
     *
     * @param excelBean 文件存放的目录
     */
    public static boolean createGroupedWorkBook(XLSBean excelBean) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(excelBean.getSheetName());

        // 标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        titleStyle.setFillPattern(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeight((short) 300);
        font.setFontName("微软雅黑");
        titleStyle.setFont(font);

        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);          // 上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);        // 右边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);       // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);         // 左边框

        Map<String, Integer> titleAttrMap = excelBean.getTitleAttr();
        Map<String, List<Map<String, String>>> dataMap = excelBean.getMergerDataMap();
        Integer titleIndex = excelBean.getTitleIndex();
        String mergeColName = excelBean.getMergeColName();

        // 第一行---标题
        createTitle(sheet, titleStyle, titleAttrMap);

        // 组
        int rowNum = 1;
        int firstRow = 1;
        for (Map.Entry entry : dataMap.entrySet()) {
            List<Map<String, String>> data = (List<Map<String, String>>) entry.getValue();

            // 填充数据
            Row dataRow;
            Cell cell;
            for (Map<String, String> rowDataMap : data) {  // 行
                dataRow = sheet.createRow(rowNum);
                int cellIndex = 0; // 列标志位
                for (Map.Entry entryData : titleAttrMap.entrySet()) {   // 列
                    cell = dataRow.createCell(cellIndex);
                    cell.setCellValue(rowDataMap.get(entryData.getKey().toString()));
                    cell.setCellStyle(cellStyle);

                    cellIndex++;
                }
                rowNum++;
            }

            // 合并单元格
            if (mergeColName != null && !"".equals(mergeColName.trim()) && !"null".equals(mergeColName)) {
                sheet.addMergedRegion(new CellRangeAddress(firstRow, (firstRow + data.size() - 1), titleIndex, titleIndex));
                firstRow = rowNum;
            }
        }

        return writeXLSFile(workbook, excelBean);
    }

    /**
     * 生成标题
     *
     * @param sheet        当前工作表
     * @param titleStyle   标题样式
     * @param titleAttrMap 标题字样
     */
    public static void createTitle(Sheet sheet, CellStyle titleStyle, Map<String, Integer> titleAttrMap) {
        Row row = sheet.createRow(0);
        int cellNum = 0; // 列标志位
        for (Map.Entry entry : titleAttrMap.entrySet()) {
            Cell cell = row.createCell(cellNum);
            // 生成标题
            cell.setCellValue(entry.getKey().toString());
            cell.setCellStyle(titleStyle);
            sheet.setColumnWidth(cellNum, Integer.valueOf(entry.getValue().toString()));
            cellNum++;
        }
    }

    /**
     * 写excel文件
     *
     * @param workbook 工作簿
     * @param bean     excel文件参数对象
     */
    private static boolean writeXLSFile(Workbook workbook, XLSBean bean) {
        FileOutputStream fileOut = null;
        try {
            String path = bean.getPath();
            File f = new File(path);
            if (!f.exists()) f.mkdirs();

            fileOut = new FileOutputStream(bean.getFullName());
            workbook.write(fileOut);

            System.out.println("生成excel文件成功^_^");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * 通过读取excel模板文件生成工作簿
     *
     * @param xlsBean 从对象里取出模板文件
     */
    public static void createWorkbookByTemplate(XLSBean xlsBean) {
        try {
            Workbook wb = WorkbookFactory.create(xlsBean.getTemplate());
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));

            int sheetCnt = wb.getNumberOfSheets();          // 获取有多少sheet
            for (int s = 0; s < sheetCnt; s++) {
                Sheet sheet = wb.getSheetAt(s);
                for (int r = 1; r <= 100; r++) {
                    Row row = sheet.createRow(r);
                    Cell cell = row.createCell(1);
                    cell.setCellValue(0.83698);
                    cell.setCellStyle(cellStyle);
                }
            }

            writeXLSFile(wb, xlsBean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取模板文件
     *
     * @param template 模板文件对象
     *
     * @return Workbook的模板
     */
    public static Workbook readXLSTemplate(File template) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(template);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return wb;
    }

    /**
     * 填充工作表数据， 并生成文件到目标目录
     *
     * @param workbook 工作表
     * @param bean     源数据
     *                 <p/>
     *                 注：这是在已经设置了标题，各种样式之后才能用的方法
     */
    public static boolean fillDataIntoWorkbook(Workbook workbook, XLSBean bean) {
        int sheetNum = workbook.getNumberOfSheets();
        for (int s = 0; s < sheetNum; s++) {        // 工作表
            Sheet sheet = workbook.getSheetAt(s);
            List<Map<String, String>> dataList = bean.getSimpleDataList();
            Map<String, Integer> titleMap = bean.getTitleAttr();

            Map<String, String> data;
            for (int r = 0; r < dataList.size(); r++) {     // 行
                Row row = sheet.createRow(r + 1);
                data = dataList.get(r);
                int cellNum = 0;
                for (String key : titleMap.keySet()) {      // 列
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(data.get(key));
//                    sheet.autoSizeColumn(cellNum);
                    cellNum++;
                }
            }
        }

        FileOutputStream fileOut = null;
        try {
            String path = bean.getPath();
            File f = new File(path);
            if (!f.exists()) f.mkdirs();

            String fullName = bean.getFullName();
            fileOut = new FileOutputStream(fullName);
            workbook.write(fileOut);

            System.out.println("根据模板生成excel文件成功^_^");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    /**
     * @version V1.0
     * @Title: excel各种属性bean
     * @Author xiaolei-0228@163.com
     * @Date 13-11-22 上午9:59
     * @Copyright: 2013 ihome.com
     */
    public static class XLSBean implements Serializable {

        /**
         * excel存放路径
         */
        private String path;
        /**
         * excel名称
         */
        private String fileName;
        /**
         * excel全名称
         */
        private String fullName;
        /**
         * 工作表名称
         */
        private String sheetName;
        /**
         * 数据列表
         */
        private List<Map<String, String>> simpleDataList;
        /**
         * 标题属性. 注：map必须是有序的，如：LinkedHashMap
         */
        private Map<String, Integer> titleAttr;
        /**
         * 列的标志位,从0开始
         */
        private int titleIndex;
        /**
         * 要纵向合并的列名称
         */
        private String mergeColName;
        /**
         * 包含合并项的数据列表
         */
        private Map<String, List<Map<String, String>>> mergerDataMap;
        /**
         * 外部模板
         */
        private File template;

        /**
         * ******************************************************************
         */
        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFullName() {
            return this.path + File.separator + this.fileName;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public List<Map<String, String>> getSimpleDataList() {
            return simpleDataList;
        }

        public void setSimpleDataList(List<Map<String, String>> simpleDataList) {
            this.simpleDataList = simpleDataList;
        }

        public Map<String, Integer> getTitleAttr() {
            return titleAttr;
        }

        public void setTitleAttr(Map<String, Integer> titleAttr) {
            this.titleAttr = titleAttr;
        }

        public int getTitleIndex() {
            return titleIndex;
        }

        public void setTitleIndex(String title) {
            Map<String, Integer> returnMap = new HashMap<String, Integer>();
            int indexNum = 0;
            for (String key : titleAttr.keySet()) { // 对标题添加标志位
                returnMap.put(key, indexNum);
                indexNum++;
            }
            this.titleIndex = returnMap.get(title);
            setMergeColName(title);
        }

        public String getMergeColName() {
            return mergeColName;
        }

        public void setMergeColName(String mergeColName) {
            this.mergeColName = mergeColName;
        }

        public Map<String, List<Map<String, String>>> getMergerDataMap() {
            return mergerDataMap;
        }

        public void setMergerDataMap(Map<String, List<Map<String, String>>> mergerDataMap) {
            this.mergerDataMap = mergerDataMap;
        }

        public File getTemplate() {
            return template;
        }

        public void setTemplate(File template) {
            this.template = template;
        }
    }
}
