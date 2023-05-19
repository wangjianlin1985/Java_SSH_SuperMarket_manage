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
import com.chengxusheji.domain.Customer;

@Service @Transactional
public class CustomerDAO {

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

    /*添加Customer信息*/
    public void AddCustomer(Customer customer) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(customer);
    }

    /*查询Customer信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Customer> QueryCustomerInfo(String customerName,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Customer customer where 1=1";
    	if(!customerName.equals("")) hql = hql + " and customer.customerName like '%" + customerName + "%'";
    	if(!telephone.equals("")) hql = hql + " and customer.telephone like '%" + telephone + "%'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List customerList = q.list();
    	return (ArrayList<Customer>) customerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Customer> QueryCustomerInfo(String customerName,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Customer customer where 1=1";
    	if(!customerName.equals("")) hql = hql + " and customer.customerName like '%" + customerName + "%'";
    	if(!telephone.equals("")) hql = hql + " and customer.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List customerList = q.list();
    	return (ArrayList<Customer>) customerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Customer> QueryAllCustomerInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Customer";
        Query q = s.createQuery(hql);
        List customerList = q.list();
        return (ArrayList<Customer>) customerList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String customerName,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Customer customer where 1=1";
        if(!customerName.equals("")) hql = hql + " and customer.customerName like '%" + customerName + "%'";
        if(!telephone.equals("")) hql = hql + " and customer.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List customerList = q.list();
        recordNumber = customerList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Customer GetCustomerByCustomerId(int customerId) {
        Session s = factory.getCurrentSession();
        Customer customer = (Customer)s.get(Customer.class, customerId);
        return customer;
    }

    /*更新Customer信息*/
    public void UpdateCustomer(Customer customer) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(customer);
    }

    /*删除Customer信息*/
    public void DeleteCustomer (int customerId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object customer = s.load(Customer.class, customerId);
        s.delete(customer);
    }

}
