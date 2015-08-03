package com.ehome.httpclient;
/**
 * MyTools
 *
 * @author: 郝晓雷
 * @date: 2015-07-30 20:42
 * @desc: 
 */
public class Order {

    /*送货地点*/
    private String deliveryPlace;
    /*订单日期*/
    private String orderDate;
    /*订单号码*/
    private String orderNo;
    /*应送货日期*/
    private String deliveryDate;
    /**
     * 订单类型
     * 如：直送订单、退货单...
     */
    private String orderType;
    /*相关日期*/
    private String relatedDate;
    /*订单金额*/
    private Double orderAmount;


    /****************************************/
    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRelatedDate() {
        return relatedDate;
    }

    public void setRelatedDate(String relatedDate) {
        this.relatedDate = relatedDate;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
