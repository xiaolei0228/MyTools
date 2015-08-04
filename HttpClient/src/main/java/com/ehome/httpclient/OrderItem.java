package com.ehome.httpclient;
/**
 * MyTools
 *
 * @author: haoxiaolei
 * @date: 2015-08-03 17:41
 * @desc: 订单明细，即：订单报表
 */
public class OrderItem {
    /*门店编号*/
    private String storeNo;
    /*订单号码*/
    private String orderNo;
    /*厂商编码*/
    private String manufacturerCode;
    /*厂商名称*/
    private String manufacturerName;
    /*订单日期*/
    private String orderDate;
    /*到货日期*/
    private String arrivalDate;
    /*取消日期*/
    private String cancelDate;
    /*单据类型*/
    private String orderType;
    /**
     * 是否紧急
     * 1：紧急。 2：不紧急
     */
    private Integer isUrgent;
    /**
     * 是否赠品订单
     * 包括：赠品订单， 正常订单
     */
    private String isGiftOrder;
    /*商品编码*/
    private String goodsCode;
    /*商品条码*/
    private String goodsBarCode;
    /*商品子码*/
    private String goodsSubCode;
    /*商品名称*/
    private String goodsName;
    /*商品规格*/
    private String goodsSpec;
    /*订货箱数*/
    private Integer boxNumber;
    /*包装入数*/
    private Integer packingNumber;
    /*订货零数*/
    private Integer zeroOrder;
    /*订货数量*/
    private Integer orderQuantity;
    /*赠品数量*/
    private Integer giftsNumber;
    /*课*/
    private Double tax;
    /*税率*/
    private Double taxRate;
    /*含税单价*/
    private Double taxPrice;
    /*含税金额*/
    private Double taxAmount;
    /*未税单价*/
    private Double unTaxPrice;
    /*未税金额*/
    private Double unTaxAmount;
    /*备注信息*/
    private String remark;

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getIsGiftOrder() {
        return isGiftOrder;
    }

    public void setIsGiftOrder(String isGiftOrder) {
        this.isGiftOrder = isGiftOrder;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsSubCode() {
        return goodsSubCode;
    }

    public void setGoodsSubCode(String goodsSubCode) {
        this.goodsSubCode = goodsSubCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public Integer getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(Integer packingNumber) {
        this.packingNumber = packingNumber;
    }

    public Integer getZeroOrder() {
        return zeroOrder;
    }

    public void setZeroOrder(Integer zeroOrder) {
        this.zeroOrder = zeroOrder;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getGiftsNumber() {
        return giftsNumber;
    }

    public void setGiftsNumber(Integer giftsNumber) {
        this.giftsNumber = giftsNumber;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getUnTaxPrice() {
        return unTaxPrice;
    }

    public void setUnTaxPrice(Double unTaxPrice) {
        this.unTaxPrice = unTaxPrice;
    }

    public Double getUnTaxAmount() {
        return unTaxAmount;
    }

    public void setUnTaxAmount(Double unTaxAmount) {
        this.unTaxAmount = unTaxAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }
}
