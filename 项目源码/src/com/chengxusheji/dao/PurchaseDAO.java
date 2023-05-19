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
import com.chengxusheji.domain.Product;
import com.chengxusheji.domain.Supplier;
import com.chengxusheji.domain.Employee;
import com.chengxusheji.domain.Purchase;

@Service @Transactional
public class PurchaseDAO {

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

    /*添加Purchase信息*/
    public void AddPurchase(Purchase purchase) throws Exception {
    	Session s = factory.getCurrentSession();
    	Product product = purchase.getProductObj();
    	product.setStockCount(product.getStockCount() + purchase.getCount()); //增加超市商品库存
    	s.merge(product); 
    	s.merge(purchase);
    }

    /*查询Purchase信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Purchase> QueryPurchaseInfo(Product productObj,Supplier supplierObj,String purchaseDate,Employee employeeObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Purchase purchase where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and purchase.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != supplierObj && supplierObj.getSupplierId()!=0) hql += " and purchase.supplierObj.supplierId=" + supplierObj.getSupplierId();
    	if(!purchaseDate.equals("")) hql = hql + " and purchase.purchaseDate like '%" + purchaseDate + "%'";
    	if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and purchase.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List purchaseList = q.list();
    	return (ArrayList<Purchase>) purchaseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Purchase> QueryPurchaseInfo(Product productObj,Supplier supplierObj,String purchaseDate,Employee employeeObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Purchase purchase where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and purchase.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != supplierObj && supplierObj.getSupplierId()!=0) hql += " and purchase.supplierObj.supplierId=" + supplierObj.getSupplierId();
    	if(!purchaseDate.equals("")) hql = hql + " and purchase.purchaseDate like '%" + purchaseDate + "%'";
    	if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and purchase.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
    	Query q = s.createQuery(hql);
    	List purchaseList = q.list();
    	return (ArrayList<Purchase>) purchaseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Purchase> QueryAllPurchaseInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Purchase";
        Query q = s.createQuery(hql);
        List purchaseList = q.list();
        return (ArrayList<Purchase>) purchaseList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Product productObj,Supplier supplierObj,String purchaseDate,Employee employeeObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Purchase purchase where 1=1";
        if(null != productObj && !productObj.getProductNo().equals("")) hql += " and purchase.productObj.productNo='" + productObj.getProductNo() + "'";
        if(null != supplierObj && supplierObj.getSupplierId()!=0) hql += " and purchase.supplierObj.supplierId=" + supplierObj.getSupplierId();
        if(!purchaseDate.equals("")) hql = hql + " and purchase.purchaseDate like '%" + purchaseDate + "%'";
        if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and purchase.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
        Query q = s.createQuery(hql);
        List purchaseList = q.list();
        recordNumber = purchaseList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Purchase GetPurchaseByPurchaseId(int purchaseId) {
        Session s = factory.getCurrentSession();
        Purchase purchase = (Purchase)s.get(Purchase.class, purchaseId);
        return purchase;
    }

    /*更新Purchase信息*/
    public void UpdatePurchase(Purchase purchase) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(purchase);
    }

    /*删除Purchase信息*/
    public void DeletePurchase (int purchaseId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object purchase = s.load(Purchase.class, purchaseId);
        s.delete(purchase);
    }

}
