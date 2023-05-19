package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Exchange {
    /*超市换货单id*/
    private int exchangeId;
    public int getExchangeId() {
        return exchangeId;
    }
    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    /*超市换货超市商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*超市换货数量*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    /*超市换货客户*/
    private Customer customerObj;
    public Customer getCustomerObj() {
        return customerObj;
    }
    public void setCustomerObj(Customer customerObj) {
        this.customerObj = customerObj;
    }

    /*超市换货日期*/
    private String exchangeDate;
    public String getExchangeDate() {
        return exchangeDate;
    }
    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    /*处理结果*/
    private String handleRes;
    public String getHandleRes() {
        return handleRes;
    }
    public void setHandleRes(String handleRes) {
        this.handleRes = handleRes;
    }

    /*工作人员*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonExchange=new JSONObject(); 
		jsonExchange.accumulate("exchangeId", this.getExchangeId());
		jsonExchange.accumulate("productObj", this.getProductObj().getProductName());
		jsonExchange.accumulate("productObjPri", this.getProductObj().getProductNo());
		jsonExchange.accumulate("count", this.getCount());
		jsonExchange.accumulate("customerObj", this.getCustomerObj().getCustomerName());
		jsonExchange.accumulate("customerObjPri", this.getCustomerObj().getCustomerId());
		jsonExchange.accumulate("exchangeDate", this.getExchangeDate().length()>19?this.getExchangeDate().substring(0,19):this.getExchangeDate());
		jsonExchange.accumulate("handleRes", this.getHandleRes());
		jsonExchange.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonExchange.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		return jsonExchange;
    }}