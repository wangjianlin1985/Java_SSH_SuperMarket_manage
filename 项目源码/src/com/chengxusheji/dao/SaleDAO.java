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
import com.chengxusheji.domain.Customer;
import com.chengxusheji.domain.Employee;
import com.chengxusheji.domain.Sale;

@Service @Transactional
public class SaleDAO {

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

    /*添加Sale信息*/
    public void AddSale(Sale sale) throws Exception {
    	Session s = factory.getCurrentSession();
    	Product product = sale.getProductObj();
    	product.setStockCount(product.getStockCount() - sale.getCount()); //增加超市商品库存
    	s.merge(product); 
    	s.merge(sale);
    }

    /*查询Sale信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Sale> QuerySaleInfo(Product productObj,Customer customerObj,String saleDate,Employee employeeObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Sale sale where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and sale.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != customerObj && customerObj.getCustomerId()!=0) hql += " and sale.customerObj.customerId=" + customerObj.getCustomerId();
    	if(!saleDate.equals("")) hql = hql + " and sale.saleDate like '%" + saleDate + "%'";
    	if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and sale.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List saleList = q.list();
    	return (ArrayList<Sale>) saleList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Sale> QuerySaleInfo(Product productObj,Customer customerObj,String saleDate,Employee employeeObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Sale sale where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and sale.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != customerObj && customerObj.getCustomerId()!=0) hql += " and sale.customerObj.customerId=" + customerObj.getCustomerId();
    	if(!saleDate.equals("")) hql = hql + " and sale.saleDate like '%" + saleDate + "%'";
    	if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and sale.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
    	Query q = s.createQuery(hql);
    	List saleList = q.list();
    	return (ArrayList<Sale>) saleList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Sale> QueryAllSaleInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Sale";
        Query q = s.createQuery(hql);
        List saleList = q.list();
        return (ArrayList<Sale>) saleList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Product productObj,Customer customerObj,String saleDate,Employee employeeObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Sale sale where 1=1";
        if(null != productObj && !productObj.getProductNo().equals("")) hql += " and sale.productObj.productNo='" + productObj.getProductNo() + "'";
        if(null != customerObj && customerObj.getCustomerId()!=0) hql += " and sale.customerObj.customerId=" + customerObj.getCustomerId();
        if(!saleDate.equals("")) hql = hql + " and sale.saleDate like '%" + saleDate + "%'";
        if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and sale.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
        Query q = s.createQuery(hql);
        List saleList = q.list();
        recordNumber = saleList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Sale GetSaleBySaleId(int saleId) {
        Session s = factory.getCurrentSession();
        Sale sale = (Sale)s.get(Sale.class, saleId);
        return sale;
    }

    /*更新Sale信息*/
    public void UpdateSale(Sale sale) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(sale);
    }

    /*删除Sale信息*/
    public void DeleteSale (int saleId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object sale = s.load(Sale.class, saleId);
        s.delete(sale);
    }

}
