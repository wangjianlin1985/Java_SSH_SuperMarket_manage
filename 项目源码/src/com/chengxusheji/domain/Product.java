package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    /*超市商品编号*/
    private String productNo;
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /*超市商品类别*/
    private ProductClass productClassObj;
    public ProductClass getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*超市商品名称*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*超市商品图片*/
    private String productPhoto;
    public String getProductPhoto() {
        return productPhoto;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    /*超市商品介绍*/
    private String productDesc;
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /*市场价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*超市商品库存*/
    private int stockCount;
    public int getStockCount() {
        return stockCount;
    }
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonProduct=new JSONObject(); 
		jsonProduct.accumulate("productNo", this.getProductNo());
		jsonProduct.accumulate("productClassObj", this.getProductClassObj().getProductClassName());
		jsonProduct.accumulate("productClassObjPri", this.getProductClassObj().getProductClassId());
		jsonProduct.accumulate("productName", this.getProductName());
		jsonProduct.accumulate("productPhoto", this.getProductPhoto());
		jsonProduct.accumulate("productDesc", this.getProductDesc());
		jsonProduct.accumulate("price", this.getPrice());
		jsonProduct.accumulate("stockCount", this.getStockCount());
		return jsonProduct;
    }}