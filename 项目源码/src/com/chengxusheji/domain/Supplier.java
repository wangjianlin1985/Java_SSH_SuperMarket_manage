package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Supplier {
    /*供应商id*/
    private int supplierId;
    public int getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    /*供应商名称*/
    private String supplierName;
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /*联系人*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*传真*/
    private String fax;
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }

    /*供应商地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSupplier=new JSONObject(); 
		jsonSupplier.accumulate("supplierId", this.getSupplierId());
		jsonSupplier.accumulate("supplierName", this.getSupplierName());
		jsonSupplier.accumulate("connectPerson", this.getConnectPerson());
		jsonSupplier.accumulate("telephone", this.getTelephone());
		jsonSupplier.accumulate("fax", this.getFax());
		jsonSupplier.accumulate("address", this.getAddress());
		return jsonSupplier;
    }}