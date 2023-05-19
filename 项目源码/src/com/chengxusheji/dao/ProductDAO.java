package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.ProductClass;
import com.chengxusheji.domain.Product;

@Service @Transactional
public class ProductDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private int rows = 5;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加Product信息*/
    public void AddProduct(Product product) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(product);
    }

    /*查询Product信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Product> QueryProductInfo(String productNo,ProductClass productClassObj,String productName,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Product product where 1=1";
    	if(!productNo.equals("")) hql = hql + " and product.productNo like '%" + productNo + "%'";
    	if(null != productClassObj && productClassObj.getProductClassId()!=0) hql += " and product.productClassObj.productClassId=" + productClassObj.getProductClassId();
    	if(!productName.equals("")) hql = hql + " and product.productName like '%" + productName + "%'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List productList = q.list();
    	return (ArrayList<Product>) productList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Product> QueryProductInfo(String productNo,ProductClass productClassObj,String productName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Product product where 1=1";
    	if(!productNo.equals("")) hql = hql + " and product.productNo like '%" + productNo + "%'";
    	if(null != productClassObj && productClassObj.getProductClassId()!=0) hql += " and product.productClassObj.productClassId=" + productClassObj.getProductClassId();
    	if(!productName.equals("")) hql = hql + " and product.productName like '%" + productName + "%'";
    	Query q = s.createQuery(hql);
    	List productList = q.list();
    	return (ArrayList<Product>) productList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Product> QueryAllProductInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Product";
        Query q = s.createQuery(hql);
        List productList = q.list();
        return (ArrayList<Product>) productList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String productNo,ProductClass productClassObj,String productName) {
        Session s = factory.getCurrentSession();
        String hql = "From Product product where 1=1";
        if(!productNo.equals("")) hql = hql + " and product.productNo like '%" + productNo + "%'";
        if(null != productClassObj && productClassObj.getProductClassId()!=0) hql += " and product.productClassObj.productClassId=" + productClassObj.getProductClassId();
        if(!productName.equals("")) hql = hql + " and product.productName like '%" + productName + "%'";
        Query q = s.createQuery(hql);
        List productList = q.list();
        recordNumber = productList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Product GetProductByProductNo(String productNo) {
        Session s = factory.getCurrentSession();
        Product product = (Product)s.get(Product.class, productNo);
        return product;
    }

    /*更新Product信息*/
    public void UpdateProduct(Product product) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(product);
    }

    /*删除Product信息*/
    public void DeleteProduct (String productNo) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object product = s.load(Product.class, productNo);
        s.delete(product);
    }

}
