package com.ihome.poi.test.util;

import com.ihome.poi.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * @version V1.0
 * @Project: ci-webapi
 * @Title:
 * @Package PACKAGE_NAME
 * @Description:
 * @Author xiaolei-0228@163.com
 * @Date 13-11-17 下午4:11
 * @Copyright: 2013 ihome.com
 */
public class ExcelUtilTest {

    @Test
    public void createGroupedWorkBook() {
        String path = "C:\\Users\\Administrator\\Desktop";
        String fileName = "result.xls";
        Map<String, Integer> titleMap = new LinkedHashMap<String, Integer>();
        titleMap.put("关键词名称", 5000);
        titleMap.put("对手域名", 5000);
        titleMap.put("平均左排名", 5000);
        titleMap.put("平均右排名", 5000);
        titleMap.put("置左率", 4000);
        titleMap.put("关键词展示率", 5000);
        ExcelUtil.XLSBean excelBean = new ExcelUtil.XLSBean();
        excelBean.setTitleAttr(titleMap);
        excelBean.setPath(path);
        excelBean.setFileName(fileName);
        excelBean.setSheetName("对手分析报告");

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("关键词名称", "智能电视");
        dataMap.put("对手域名", "tmail.com");
        dataMap.put("平均左排名", "4.0");
        dataMap.put("平均右排名", "1.2");
        dataMap.put("置左率", "10%");
        dataMap.put("关键词展示率", "30%");
        Map<String, String> dataMap2 = new HashMap<String, String>();
        dataMap2.put("关键词名称", "智能电视");
        dataMap2.put("对手域名", "jd.com");
        dataMap2.put("平均左排名", "1.0");
        dataMap2.put("平均右排名", "2.2");
        dataMap2.put("置左率", "90%");
        dataMap2.put("关键词展示率", "60%");
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        dataList.add(dataMap);
        dataList.add(dataMap2);
        Map<String, List<Map<String, String>>> dm = new LinkedHashMap<String, List<Map<String, String>>>();
        dm.put("智能电视", dataList);

        Map<String, String> dataMap6 = new HashMap<String, String>();
        dataMap6.put("关键词名称", "笔记本");
        dataMap6.put("对手域名", "jd.com");
        dataMap6.put("平均左排名", "1.0");
        dataMap6.put("平均右排名", "2.2");
        dataMap6.put("置左率", "90%");
        dataMap6.put("关键词展示率", "60%");
        List<Map<String, String>> dataList6 = new ArrayList<Map<String, String>>();
        dataList6.add(dataMap6);
        dm.put("笔记本", dataList6);

        Map<String, String> dataMap3 = new HashMap<String, String>();
        dataMap3.put("关键词名称", "手机");
        dataMap3.put("对手域名", "taobao.com");
        dataMap3.put("平均左排名", "1.0");
        dataMap3.put("平均右排名", "2.2");
        dataMap3.put("置左率", "90%");
        dataMap3.put("关键词展示率", "60%");
        List<Map<String, String>> dataList2 = new ArrayList<Map<String, String>>();
        dataList2.add(dataMap3);
        Map<String, String> dataMap4 = new HashMap<String, String>();
        dataMap4.put("关键词名称", "手机");
        dataMap4.put("对手域名", "jd.com");
        dataMap4.put("平均左排名", "1.0");
        dataMap4.put("平均右排名", "2.2");
        dataMap4.put("置左率", "90%");
        dataMap4.put("关键词展示率", "60%");
        dataList2.add(dataMap4);
        Map<String, String> dataMap5 = new HashMap<String, String>();
        dataMap5.put("关键词名称", "手机");
        dataMap5.put("对手域名", "jd.com");
        dataMap5.put("平均左排名", "1.0");
        dataMap5.put("平均右排名", "2.2");
        dataMap5.put("置左率", "90%");
        dataMap5.put("关键词展示率", "60%");
        dataList2.add(dataMap5);
        dm.put("手机", dataList2);
        excelBean.setMergerDataMap(dm);

        // 纵向合并哪一列
        //excelBean.setTitleIndex("关键词名称");

        ExcelUtil.createGroupedWorkBook(excelBean);
    }

    @Test
    public void createWorkbookByTemplate() {
        File template = new File("H:\\haoxiaolei\\Desktop\\tempate.xlsx");
        ExcelUtil.XLSBean xlsBean = new ExcelUtil.XLSBean();
        xlsBean.setTemplate(template);
        xlsBean.setPath("H:\\haoxiaolei\\Desktop");
        xlsBean.setFileName("result.xls");
        ExcelUtil.createWorkbookByTemplate(xlsBean);
    }

    @Test
    public void readExcelTemplate() {
        File template = new File("H:\\haoxiaolei\\Desktop\\test.xlsx");
        Workbook wb = ExcelUtil.readXLSTemplate(template);
    }

    @Test
    public void fillDataIntoWorkbook() {
        File template = new File("H:\\haoxiaolei\\Desktop\\CreativeTemplate.xlsx");
        Workbook wb = ExcelUtil.readXLSTemplate(template);
        List<Map<String, String>> srcData = new ArrayList<Map<String, String>>();
        Map<String, String> data1 = new HashMap<String, String>();
        data1.put("创意标题", "智能电视,首选乐视TV超级电视");
        data1.put("创意描述", "50寸原装进口LG IPS硬屏,LetvUI2.3智能操作系统,4.5mm极窄边,智能语音助手,仅2499元!两倍性能,一半价格.立即购买,开启你的智能大屏新生活.智能电视,只在乐视商城!");
        data1.put("域名", "shop.letv.com");
        data1.put("平均左排名", "4.0");
        data1.put("平均右排名", "1.2");
        Map<String, String> data2 = new HashMap<String, String>();
        data2.put("创意标题", "智能电视,首选乐视TV超级电视2");
        data2.put("创意描述", "50寸原装进口LG IPS硬屏,LetvUI2.3智能操作系统,4.5mm极窄边,智能语音助手,仅2499元!两倍性能,一半价格.立即购买,开启你的智能大屏新生活.智能电视,只在乐视商城!");
        data2.put("域名", "jd.com");
        data2.put("平均左排名", "1.0");
        data2.put("平均右排名", "1.2");
        srcData.add(data1);
        srcData.add(data2);

        ExcelUtil.XLSBean bean = new ExcelUtil.XLSBean();
        bean.setPath("H:\\haoxiaolei\\Desktop");
        bean.setFileName("result.xlsx");
        bean.setSimpleDataList(srcData);
        Map<String, Integer> titleMap = new LinkedHashMap<String, Integer>();
        titleMap.put("创意标题", 0);
        titleMap.put("创意描述", 0);
        titleMap.put("域名", 0);
        titleMap.put("平均左排名", 0);
        titleMap.put("平均右排名", 0);
        bean.setTitleAttr(titleMap);
        ExcelUtil.fillDataIntoWorkbook(wb, bean);
    }

    @Test
    public void testList() {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("c");
        String[] s = {"a", "b", "c"};
        int aNum = 1,bNum = 1,cNum = 1;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < s.length; j++) {
                if (list.contains(s[j])) {

                }
            }
        }
        System.out.println(list);
    }


}
