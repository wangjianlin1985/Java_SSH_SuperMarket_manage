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

@Service @Transactional
public class ProductClassDAO {

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

    /*添加ProductClass信息*/
    public void AddProductClass(ProductClass productClass) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(productClass);
    }

    /*查询ProductClass信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductClass> QueryProductClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductClass productClass where 1=1";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List productClassList = q.list();
    	return (ArrayList<ProductClass>) productClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductClass> QueryProductClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductClass productClass where 1=1";
    	Query q = s.createQuery(hql);
    	List productClassList = q.list();
    	return (ArrayList<ProductClass>) productClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductClass> QueryAllProductClassInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From ProductClass";
        Query q = s.createQuery(hql);
        List productClassList = q.list();
        return (ArrayList<ProductClass>) productClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From ProductClass productClass where 1=1";
        Query q = s.createQuery(hql);
        List productClassList = q.list();
        recordNumber = productClassList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ProductClass GetProductClassByProductClassId(int productClassId) {
        Session s = factory.getCurrentSession();
        ProductClass productClass = (ProductClass)s.get(ProductClass.class, productClassId);
        return productClass;
    }

    /*更新ProductClass信息*/
    public void UpdateProductClass(ProductClass productClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(productClass);
    }

    /*删除ProductClass信息*/
    public void DeleteProductClass (int productClassId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object productClass = s.load(ProductClass.class, productClassId);
        s.delete(productClass);
    }

}
