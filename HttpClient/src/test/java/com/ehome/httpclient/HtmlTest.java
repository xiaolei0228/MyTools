package com.ehome.httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * MyTools
 *
 * @author: 郝晓雷
 * @date: 2015-07-30 20:05
 * @desc: 同一session下模拟登录抓取页面源代码
 */
public class HtmlTest {

    String startStr = "约仓单号</font></b></td>" + "</tr>";
    String endStr = "<tr>\n" + "  \t\t\t<td  bgcolor=\"whitesmoke\" valign=\"top\" align=\"center\"><font face=\"宋体\" size=\"2\" color=\"midnightblue\">合计：</font></td>";

    @Test
    public void createHtml()throws Exception{
        String[] params = {"27001264", "93550016"};
        String html = Html.createHtml(params);
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("td[bgcolor=black]").select("table").select("tr");
        if (elements != null && elements.size() > 0) {
            List<Order> orderList = new ArrayList<>();
            for (int i = 2; i < elements.size() - 2; i++) {
                Element element = elements.get(i);
                Elements ets = element.children();
                if (ets != null && ets.size() > 0) {
                    Order order = new Order();
                    order.setDeliveryPlace(ets.get(0).text());
                    order.setOrderDate(ets.get(1).text());
                    order.setOrderNo(ets.get(2).text());
                    order.setDeliveryDate(ets.get(3).text());
                    order.setOrderType(ets.get(4).text());
                    order.setRelatedDate(ets.get(5).text());
                    order.setOrderAmount(Double.valueOf(ets.get(6).text().replaceAll(",", "")));
                    orderList.add(order);
                }
            }

            List<OrderReport> reportList = new ArrayList<>();   // 报表对象列表
            for (Order order : orderList) {
                //System.out.println(order.getDeliveryPlace() + "\t" + order.getOrderDate() + "\t" + order.getOrderNo() + "\t" + order.getDeliveryDate() + "\t" + order.getOrderType() + "\t" + order.getRelatedDate() + "\t" + order.getOrderAmount());
                String forwardUrl = "http://218.28.18.2:9090/report.asp?pono=" + order.getOrderNo() + "&strSCMSupName=%D6%A3%D6%DD%B4%D3%D4%BD%C9%CC%C3%B3%D3%D0%CF%DE%B9%AB%CB%BE";
                String reportHtml = Html.forwardHtml(Html.conn, Html.url, forwardUrl);
                Html.write(reportHtml, "g:/hidden_file/desk/reportHtml.html");
                Document rptDoc = Jsoup.parse(reportHtml);
                Element rptEmt = rptDoc.getElementById("tb1");
                Elements children = rptEmt.select("tr");
                children.remove(0);

                OrderReport report;
                for (Element child : children) {
                    report = new OrderReport();
                    Elements son = child.children();
                    report.setStoreNo(son.get(0).text());
                    report.setOrderNo(son.get(1).text());
                    report.setManufacturerCode(son.get(2).text());
                    report.setManufacturerName(son.get(3).text());
                    report.setTax(Double.valueOf(son.get(4).text()));
                    report.setOrderDate(son.get(5).text());
                    report.setArrivalDate(son.get(6).text());
                    report.setCancelDate(son.get(7).text());
                    report.setOrderType(son.get(8).text());
                    report.setIsUrgent(Integer.valueOf(son.get(9).text()));
                    report.setIsGiftOrder(son.get(10).text());
                    report.setGoodsCode(son.get(12).text());
                    report.setGoodsBarCode(son.get(13).text());
                    report.setGoodsSubCode(son.get(14).text());
                    report.setGoodsName(son.get(15).text());
                    report.setGoodsSpec(son.get(16).text());
                    report.setBoxNumber(Integer.valueOf(son.get(17).text()));
                    report.setPackingNumber(Integer.valueOf(son.get(18).text()));
                    report.setZeroOrder(Integer.valueOf(son.get(19).text()));
                    report.setOrderQuantity(Integer.valueOf(son.get(20).text()));
                    report.setGiftsNumber(Integer.valueOf(son.get(21).text()));
                    report.setTaxRate(Double.valueOf(son.get(22).text()));
                    report.setTaxPrice(Double.valueOf(son.get(23).text()));
                    report.setTaxAmount(Double.valueOf(son.get(24).text()));
                    report.setUnTaxPrice(Double.valueOf(son.get(25).text()));
                    report.setUnTaxAmount(Double.valueOf(son.get(26).text()));
                    report.setRemark(son.get(27).text());
                    reportList.add(report);
                }
            }

            // 要导出的报表对象列表
            for (OrderReport rpt : reportList) {
                System.out.println(
                        rpt.getStoreNo() + "\t" + rpt.getOrderNo() + "\t" + rpt.getManufacturerCode() + "\t" + rpt.getManufacturerName() + "\t" + rpt.getTax() + "\t" +
                        rpt.getOrderDate() + "\t" + rpt.getArrivalDate() + "\t" + rpt.getCancelDate() + "\t" + rpt.getOrderType() + "\t" + rpt.getIsUrgent() + "\t" +
                        rpt.getIsGiftOrder() + "\t" + rpt.getGoodsCode() + "\t" + rpt.getGoodsBarCode() + "\t" + rpt.getGoodsSubCode() + "\t" + rpt.getGoodsName() + "\t" +
                        rpt.getGoodsSpec() + "\t" + rpt.getBoxNumber() + "\t" + rpt.getPackingNumber() + "\t" + rpt.getZeroOrder() + "\t" + rpt.getOrderQuantity() + "\t" +
                        rpt.getGiftsNumber() + "\t" + rpt.getTaxRate() + "\t" + rpt.getTaxPrice() + "\t" + rpt.getTaxAmount() + "\t" + rpt.getUnTaxPrice() + "\t" +
                        rpt.getUnTaxAmount() + "\t" + rpt.getRemark());
            }
            System.out.println("------\n" + reportList.size() + "个");
        }
    }
}
