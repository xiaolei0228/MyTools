package com.ehome.httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyTools
 *
 * @author: 郝晓雷
 * @date: 2015-07-30 20:05
 * @desc: 同一session下模拟登录抓取页面源代码
 */
public class HtmlTest {

    private String baseUrl = "http://218.28.18.2:9090/scm_sup2_poh_print.asp";

    private String mode = "search";
    private String tranyears = "2015";
    private String tranmonths = "01";
    private String trandays = "01";
    private String tranyeare = "2015";
    private String tranmonthe = "07";
    private String trandaye = "31";
    private String storecode = "all";
    private String whichpage = "1";


    @Test
    public void createHtml() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("userName", "27001264");
        params.put("password", "93550016");
        params.put("mode", "search");
        params.put("tranyears", "2015");
        params.put("tranmonths", "08");
        params.put("trandays", "01");
        params.put("tranyeare", "2015");
        params.put("tranmonthe", "08");
        params.put("trandaye", "31");
        params.put("storecode", "all");

        int page = getTotalPage(params);
        String[] loginPass = {params.get("userName"), params.get("password")};
        for (int i = 1; i <= page; i++) {
            params.put("whichpage", i + "");
            String forwardUrl = makeUrl(params);
            Html.setForwardURL(forwardUrl);
            String html = Html.createHtml(loginPass);
            Document doc = Jsoup.parse(html);
            order(doc);
            //System.out.println("_________________________________________________________________________________________________________________________________________________________________");
        }
    }

    private List<Order> order(Document document) throws Exception {
        List<Order> orderList = new ArrayList<>();
        // 订单元素集合
        Elements orderElements = document.select("td[bgcolor=black]").select("table").select("tr");
        if (orderElements != null && orderElements.size() > 0) {
            for (int i = 2; i < orderElements.size() - 2; i++) {
                Element element = orderElements.get(i);
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
            getOrderItems(orderList);
        }
        return orderList;
    }

    private List<OrderItem> getOrderItems(List<Order> orderList) throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();   // 报表对象列表
        for (Order order : orderList) {
            //System.out.println(order.getDeliveryPlace() + "\t" + order.getOrderDate() + "\t" + order.getOrderNo() + "\t" + order.getDeliveryDate() + "\t" + order.getOrderType() + "\t" + order.getRelatedDate() + "\t" + order.getOrderAmount());
            String forwardUrl = "http://218.28.18.2:9090/report.asp?pono=" + order.getOrderNo() + "&strSCMSupName=%D6%A3%D6%DD%B4%D3%D4%BD%C9%CC%C3%B3%D3%D0%CF%DE%B9%AB%CB%BE";
            String reportHtml = Html.forwardHtml(Html.conn, Html.url, forwardUrl);
            Html.write(reportHtml, "g:/hidden_file/desk/reportHtml.html");
            Document rptDoc = Jsoup.parse(reportHtml);
            Element rptEmt = rptDoc.getElementById("tb1");
            Elements children = rptEmt.select("tr");
            children.remove(0);

            OrderItem report;
            for (Element child : children) {
                report = new OrderItem();
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
                orderItems.add(report);
            }
        }

        // 要导出的报表对象列表
        for (OrderItem rpt : orderItems) {
            System.out.println(
                    rpt.getStoreNo() + "\t" + rpt.getOrderNo() + "\t" + rpt.getManufacturerCode() + "\t" + rpt.getManufacturerName() + "\t" + rpt.getTax() + "\t" +
                            rpt.getOrderDate() + "\t" + rpt.getArrivalDate() + "\t" + rpt.getCancelDate() + "\t" + rpt.getOrderType() + "\t" + rpt.getIsUrgent() + "\t" +
                            rpt.getIsGiftOrder() + "\t" + rpt.getGoodsCode() + "\t" + rpt.getGoodsBarCode() + "\t" + rpt.getGoodsSubCode() + "\t" + rpt.getGoodsName() + "\t" +
                            rpt.getGoodsSpec() + "\t" + rpt.getBoxNumber() + "\t" + rpt.getPackingNumber() + "\t" + rpt.getZeroOrder() + "\t" + rpt.getOrderQuantity() + "\t" +
                            rpt.getGiftsNumber() + "\t" + rpt.getTaxRate() + "\t" + rpt.getTaxPrice() + "\t" + rpt.getTaxAmount() + "\t" + rpt.getUnTaxPrice() + "\t" +
                            rpt.getUnTaxAmount() + "\t" + rpt.getRemark());
        }
        System.out.println("------\n" + orderItems.size() + "个");

        return orderItems;
    }

    private int getTotalPage(Map<String, String> params) throws Exception {
        String forwardUrl = makeUrl(params);
        Html.setForwardURL(forwardUrl);

        String[] loginPass = {params.get("userName"), params.get("password")};
        String html = Html.createHtml(loginPass);
        Document doc = Jsoup.parse(html);

        // 总页数
        int page = 0;
        Elements pageElements = doc.select("select[name=whichpage]");
        for (Element elm : pageElements) {
            Element pageElem = elm.child(elm.children().size() - 1);
            page = Integer.valueOf(pageElem.text());
        }
        return page == 0 ? 1 : page;
    }

    private String makeUrl(Map<String, String> params) {
        String forwardUrl = baseUrl + "?mode=" + params.get("mode") +
                "&tranyears=" + params.get("tranyears") + "&tranmonths=" + params.get("tranmonths") + "&trandays=" + params.get("trandays") +
                "&tranyeare=" + params.get("tranyeare") + "&tranmonthe=" + params.get("tranmonthe") + "&trandaye=" + params.get("trandaye") +
                "&storecode=" + params.get("storecode") + "&whichpage=" + params.get("whichpage");
        return forwardUrl;
    }
}
