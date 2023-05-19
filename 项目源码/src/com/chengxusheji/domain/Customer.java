package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {
    /*客户id*/
    private int customerId;
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /*客户名称*/
    private String customerName;
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*收货地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*备注*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCustomer=new JSONObject(); 
		jsonCustomer.accumulate("customerId", this.getCustomerId());
		jsonCustomer.accumulate("customerName", this.getCustomerName());
		jsonCustomer.accumulate("telephone", this.getTelephone());
		jsonCustomer.accumulate("address", this.getAddress());
		jsonCustomer.accumulate("memo", this.getMemo());
		return jsonCustomer;
    }}