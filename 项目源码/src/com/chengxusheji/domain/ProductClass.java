package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductClass {
    /*超市商品类别id*/
    private int productClassId;
    public int getProductClassId() {
        return productClassId;
    }
    public void setProductClassId(int productClassId) {
        this.productClassId = productClassId;
    }

    /*超市商品类别名称*/
    private String productClassName;
    public String getProductClassName() {
        return productClassName;
    }
    public void setProductClassName(String productClassName) {
        this.productClassName = productClassName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonProductClass=new JSONObject(); 
		jsonProductClass.accumulate("productClassId", this.getProductClassId());
		jsonProductClass.accumulate("productClassName", this.getProductClassName());
		return jsonProductClass;
    }}