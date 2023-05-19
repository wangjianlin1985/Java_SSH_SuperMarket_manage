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
import com.chengxusheji.domain.Supplier;

@Service @Transactional
public class SupplierDAO {

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

    /*添加Supplier信息*/
    public void AddSupplier(Supplier supplier) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(supplier);
    }

    /*查询Supplier信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Supplier> QuerySupplierInfo(String supplierName,String connectPerson,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Supplier supplier where 1=1";
    	if(!supplierName.equals("")) hql = hql + " and supplier.supplierName like '%" + supplierName + "%'";
    	if(!connectPerson.equals("")) hql = hql + " and supplier.connectPerson like '%" + connectPerson + "%'";
    	if(!telephone.equals("")) hql = hql + " and supplier.telephone like '%" + telephone + "%'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List supplierList = q.list();
    	return (ArrayList<Supplier>) supplierList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Supplier> QuerySupplierInfo(String supplierName,String connectPerson,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Supplier supplier where 1=1";
    	if(!supplierName.equals("")) hql = hql + " and supplier.supplierName like '%" + supplierName + "%'";
    	if(!connectPerson.equals("")) hql = hql + " and supplier.connectPerson like '%" + connectPerson + "%'";
    	if(!telephone.equals("")) hql = hql + " and supplier.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List supplierList = q.list();
    	return (ArrayList<Supplier>) supplierList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Supplier> QueryAllSupplierInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Supplier";
        Query q = s.createQuery(hql);
        List supplierList = q.list();
        return (ArrayList<Supplier>) supplierList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String supplierName,String connectPerson,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Supplier supplier where 1=1";
        if(!supplierName.equals("")) hql = hql + " and supplier.supplierName like '%" + supplierName + "%'";
        if(!connectPerson.equals("")) hql = hql + " and supplier.connectPerson like '%" + connectPerson + "%'";
        if(!telephone.equals("")) hql = hql + " and supplier.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List supplierList = q.list();
        recordNumber = supplierList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Supplier GetSupplierBySupplierId(int supplierId) {
        Session s = factory.getCurrentSession();
        Supplier supplier = (Supplier)s.get(Supplier.class, supplierId);
        return supplier;
    }

    /*更新Supplier信息*/
    public void UpdateSupplier(Supplier supplier) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(supplier);
    }

    /*删除Supplier信息*/
    public void DeleteSupplier (int supplierId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object supplier = s.load(Supplier.class, supplierId);
        s.delete(supplier);
    }

}
