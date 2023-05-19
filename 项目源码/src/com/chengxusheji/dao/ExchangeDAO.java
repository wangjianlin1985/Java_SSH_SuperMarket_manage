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
import com.chengxusheji.domain.Exchange;

@Service @Transactional
public class ExchangeDAO {

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

    /*添加Exchange信息*/
    public void AddExchange(Exchange exchange) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(exchange);
    }

    /*查询Exchange信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exchange> QueryExchangeInfo(Product productObj,Customer customerObj,String exchangeDate,Employee employeeObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Exchange exchange where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and exchange.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != customerObj && customerObj.getCustomerId()!=0) hql += " and exchange.customerObj.customerId=" + customerObj.getCustomerId();
    	if(!exchangeDate.equals("")) hql = hql + " and exchange.exchangeDate like '%" + exchangeDate + "%'";
    	if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and exchange.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List exchangeList = q.list();
    	return (ArrayList<Exchange>) exchangeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exchange> QueryExchangeInfo(Product productObj,Customer customerObj,String exchangeDate,Employee employeeObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Exchange exchange where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and exchange.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != customerObj && customerObj.getCustomerId()!=0) hql += " and exchange.customerObj.customerId=" + customerObj.getCustomerId();
    	if(!exchangeDate.equals("")) hql = hql + " and exchange.exchangeDate like '%" + exchangeDate + "%'";
    	if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and exchange.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
    	Query q = s.createQuery(hql);
    	List exchangeList = q.list();
    	return (ArrayList<Exchange>) exchangeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exchange> QueryAllExchangeInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Exchange";
        Query q = s.createQuery(hql);
        List exchangeList = q.list();
        return (ArrayList<Exchange>) exchangeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Product productObj,Customer customerObj,String exchangeDate,Employee employeeObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Exchange exchange where 1=1";
        if(null != productObj && !productObj.getProductNo().equals("")) hql += " and exchange.productObj.productNo='" + productObj.getProductNo() + "'";
        if(null != customerObj && customerObj.getCustomerId()!=0) hql += " and exchange.customerObj.customerId=" + customerObj.getCustomerId();
        if(!exchangeDate.equals("")) hql = hql + " and exchange.exchangeDate like '%" + exchangeDate + "%'";
        if(null != employeeObj && !employeeObj.getEmployeeNo().equals("")) hql += " and exchange.employeeObj.employeeNo='" + employeeObj.getEmployeeNo() + "'";
        Query q = s.createQuery(hql);
        List exchangeList = q.list();
        recordNumber = exchangeList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Exchange GetExchangeByExchangeId(int exchangeId) {
        Session s = factory.getCurrentSession();
        Exchange exchange = (Exchange)s.get(Exchange.class, exchangeId);
        return exchange;
    }

    /*更新Exchange信息*/
    public void UpdateExchange(Exchange exchange) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(exchange);
    }

    /*删除Exchange信息*/
    public void DeleteExchange (int exchangeId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object exchange = s.load(Exchange.class, exchangeId);
        s.delete(exchange);
    }

}
