package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    /*进货id*/
    private int purchaseId;
    public int getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /*进货超市商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*供应商*/
    private Supplier supplierObj;
    public Supplier getSupplierObj() {
        return supplierObj;
    }
    public void setSupplierObj(Supplier supplierObj) {
        this.supplierObj = supplierObj;
    }

    /*进货单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*进货数量*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    /*进货日期*/
    private String purchaseDate;
    public String getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /*留言备注*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /*进货员*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonPurchase=new JSONObject(); 
		jsonPurchase.accumulate("purchaseId", this.getPurchaseId());
		jsonPurchase.accumulate("productObj", this.getProductObj().getProductName());
		jsonPurchase.accumulate("productObjPri", this.getProductObj().getProductNo());
		jsonPurchase.accumulate("supplierObj", this.getSupplierObj().getSupplierName());
		jsonPurchase.accumulate("supplierObjPri", this.getSupplierObj().getSupplierId());
		jsonPurchase.accumulate("price", this.getPrice());
		jsonPurchase.accumulate("count", this.getCount());
		jsonPurchase.accumulate("purchaseDate", this.getPurchaseDate().length()>19?this.getPurchaseDate().substring(0,19):this.getPurchaseDate());
		jsonPurchase.accumulate("memo", this.getMemo());
		jsonPurchase.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonPurchase.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		return jsonPurchase;
    }}