package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Sale {
    /*销售id*/
    private int saleId;
    public int getSaleId() {
        return saleId;
    }
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    /*销售超市商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*销售客户*/
    private Customer customerObj;
    public Customer getCustomerObj() {
        return customerObj;
    }
    public void setCustomerObj(Customer customerObj) {
        this.customerObj = customerObj;
    }

    /*销售单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*销售数量*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    /*销售日期*/
    private String saleDate;
    public String getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    /*留言备注*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /*销售员*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSale=new JSONObject(); 
		jsonSale.accumulate("saleId", this.getSaleId());
		jsonSale.accumulate("productObj", this.getProductObj().getProductName());
		jsonSale.accumulate("productObjPri", this.getProductObj().getProductNo());
		jsonSale.accumulate("customerObj", this.getCustomerObj().getCustomerName());
		jsonSale.accumulate("customerObjPri", this.getCustomerObj().getCustomerId());
		jsonSale.accumulate("price", this.getPrice());
		jsonSale.accumulate("count", this.getCount());
		jsonSale.accumulate("saleDate", this.getSaleDate().length()>19?this.getSaleDate().substring(0,19):this.getSaleDate());
		jsonSale.accumulate("memo", this.getMemo());
		jsonSale.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonSale.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		return jsonSale;
    }}