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
import com.chengxusheji.domain.Notice;

@Service @Transactional
public class NoticeDAO {

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

    /*添加Notice信息*/
    public void AddNotice(Notice notice) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(notice);
    }

    /*查询Notice信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Notice> QueryNoticeInfo(String title,String noticeDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Notice notice where 1=1";
    	if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
    	if(!noticeDate.equals("")) hql = hql + " and notice.noticeDate like '%" + noticeDate + "%'";
    	 Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.rows;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.rows);
    	List noticeList = q.list();
    	return (ArrayList<Notice>) noticeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Notice> QueryNoticeInfo(String title,String noticeDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Notice notice where 1=1";
    	if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
    	if(!noticeDate.equals("")) hql = hql + " and notice.noticeDate like '%" + noticeDate + "%'";
    	Query q = s.createQuery(hql);
    	List noticeList = q.list();
    	return (ArrayList<Notice>) noticeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Notice> QueryAllNoticeInfo() {
        Session s = factory.getCurrentSession();
        String hql = "From Notice";
        Query q = s.createQuery(hql);
        List noticeList = q.list();
        return (ArrayList<Notice>) noticeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,String noticeDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Notice notice where 1=1";
        if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
        if(!noticeDate.equals("")) hql = hql + " and notice.noticeDate like '%" + noticeDate + "%'";
        Query q = s.createQuery(hql);
        List noticeList = q.list();
        recordNumber = noticeList.size();
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Notice GetNoticeByNoticeId(int noticeId) {
        Session s = factory.getCurrentSession();
        Notice notice = (Notice)s.get(Notice.class, noticeId);
        return notice;
    }

    /*更新Notice信息*/
    public void UpdateNotice(Notice notice) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(notice);
    }

    /*删除Notice信息*/
    public void DeleteNotice (int noticeId) throws Exception {
        Session s = factory.getCurrentSession(); 
        Object notice = s.load(Notice.class, noticeId);
        s.delete(notice);
    }

}
