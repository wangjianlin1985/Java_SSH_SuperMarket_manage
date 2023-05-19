package com.chengxusheji.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.dao.NoticeDAO;
import com.chengxusheji.domain.Notice;
@Controller @Scope("prototype")
public class NoticeAction extends ActionSupport {

    /*界面层需要查询的属性: 标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 公告日期*/
    private String noticeDate;
    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }
    public String getNoticeDate() {
        return this.noticeDate;
    }

    /*当前第几页*/
    private int page;
    public void setPage(int page) {
        this.page = page;
    }
    public int getPage() {
        return page;
    }

    /*每页显示多少条数据*/
    private int rows;
    public void setRows(int rows) {
    	this.rows = rows;
    }
    public int getRows() {
    	return this.rows;
    }
    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int noticeId;
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }
    public int getNoticeId() {
        return noticeId;
    }

    /*要删除的记录主键集合*/
    private String noticeIds;
    public String getNoticeIds() {
		return noticeIds;
	}
	public void setNoticeIds(String noticeIds) {
		this.noticeIds = noticeIds;
	}
    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource NoticeDAO noticeDAO;

    /*待操作的Notice对象*/
    private Notice notice;
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
    public Notice getNotice() {
        return this.notice;
    }

    /*ajax添加Notice信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddNotice() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            noticeDAO.AddNotice(notice);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Notice添加失败!";
            writeJsonResponse(success, message);
        }
    }

    /*向客户端输出操作成功或失败信息*/
	private void writeJsonResponse(boolean success,String message)
			throws IOException, JSONException {
		HttpServletResponse response=ServletActionContext.getResponse(); 
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject json=new JSONObject();
		json.accumulate("success", success);
		json.accumulate("message", message);
		out.println(json.toString());
		out.flush(); 
		out.close();
	}

    /*查询Notice信息*/
    public void ajaxQueryNotice() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(title == null) title = "";
        if(noticeDate == null) noticeDate = "";
        if(rows != 0) noticeDAO.setRows(rows);
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, noticeDate, page);
        /*计算总的页数和总的记录数*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, noticeDate);
        /*获取到总的页码数目*/
        totalPage = noticeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = noticeDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Notice notice:noticeList) {
			JSONObject jsonNotice = notice.getJsonObject();
			jsonArray.put(jsonNotice);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Notice信息*/
    public void ajaxQueryAllNotice() throws IOException, JSONException {
        List<Notice> noticeList = noticeDAO.QueryAllNoticeInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Notice notice:noticeList) {
			JSONObject jsonNotice = new JSONObject();
			jsonNotice.accumulate("noticeId", notice.getNoticeId());
			jsonNotice.accumulate("title", notice.getTitle());
			jsonArray.put(jsonNotice);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Notice信息*/
    public void ajaxModifyNoticeQuery() throws IOException, JSONException {
        /*根据主键noticeId获取Notice对象*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonNotice = notice.getJsonObject(); 
		out.println(jsonNotice.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Notice信息*/
    public void ajaxModifyNotice() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            noticeDAO.UpdateNotice(notice);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Notice修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Notice信息*/
    public void ajaxDeleteNotice() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _noticeIds[] = noticeIds.split(",");
        	for(String _noticeId: _noticeIds) {
        		noticeDAO.DeleteNotice(Integer.parseInt(_noticeId));
        	}
        	success = true;
        	message = _noticeIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Notice信息*/
    public String FrontQueryNotice() {
        if(page == 0) page = 1;
        if(title == null) title = "";
        if(noticeDate == null) noticeDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, noticeDate, page);
        /*计算总的页数和总的记录数*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, noticeDate);
        /*获取到总的页码数目*/
        totalPage = noticeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = noticeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noticeList",  noticeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("title", title);
        ctx.put("noticeDate", noticeDate);
        return "front_query_view";
    }

    /*查询要修改的Notice信息*/
    public String FrontShowNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键noticeId获取Notice对象*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "front_show_view";
    }

    /*删除Notice信息*/
    public String DeleteNotice() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            noticeDAO.DeleteNotice(noticeId);
            ctx.put("message", "Notice删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Notice删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryNoticeOutputToExcel() { 
        if(title == null) title = "";
        if(noticeDate == null) noticeDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title,noticeDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Notice信息记录"; 
        String[] headers = { "公告id","标题","公告日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<noticeList.size();i++) {
        	Notice notice = noticeList.get(i); 
        	dataset.add(new String[]{notice.getNoticeId() + "",notice.getTitle(),notice.getNoticeDate()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Notice.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
}
