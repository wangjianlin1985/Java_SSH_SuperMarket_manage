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
import com.chengxusheji.dao.SupplierDAO;
import com.chengxusheji.domain.Supplier;
@Controller @Scope("prototype")
public class SupplierAction extends ActionSupport {

    /*界面层需要查询的属性: 供应商名称*/
    private String supplierName;
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getSupplierName() {
        return this.supplierName;
    }

    /*界面层需要查询的属性: 联系人*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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

    private int supplierId;
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    public int getSupplierId() {
        return supplierId;
    }

    /*要删除的记录主键集合*/
    private String supplierIds;
    public String getSupplierIds() {
		return supplierIds;
	}
	public void setSupplierIds(String supplierIds) {
		this.supplierIds = supplierIds;
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
    @Resource SupplierDAO supplierDAO;

    /*待操作的Supplier对象*/
    private Supplier supplier;
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    public Supplier getSupplier() {
        return this.supplier;
    }

    /*ajax添加Supplier信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddSupplier() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            supplierDAO.AddSupplier(supplier);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Supplier添加失败!";
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

    /*查询Supplier信息*/
    public void ajaxQuerySupplier() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(supplierName == null) supplierName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        if(rows != 0) supplierDAO.setRows(rows);
        List<Supplier> supplierList = supplierDAO.QuerySupplierInfo(supplierName, connectPerson, telephone, page);
        /*计算总的页数和总的记录数*/
        supplierDAO.CalculateTotalPageAndRecordNumber(supplierName, connectPerson, telephone);
        /*获取到总的页码数目*/
        totalPage = supplierDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = supplierDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Supplier supplier:supplierList) {
			JSONObject jsonSupplier = supplier.getJsonObject();
			jsonArray.put(jsonSupplier);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Supplier信息*/
    public void ajaxQueryAllSupplier() throws IOException, JSONException {
        List<Supplier> supplierList = supplierDAO.QueryAllSupplierInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Supplier supplier:supplierList) {
			JSONObject jsonSupplier = new JSONObject();
			jsonSupplier.accumulate("supplierId", supplier.getSupplierId());
			jsonSupplier.accumulate("supplierName", supplier.getSupplierName());
			jsonArray.put(jsonSupplier);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Supplier信息*/
    public void ajaxModifySupplierQuery() throws IOException, JSONException {
        /*根据主键supplierId获取Supplier对象*/
        Supplier supplier = supplierDAO.GetSupplierBySupplierId(supplierId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonSupplier = supplier.getJsonObject(); 
		out.println(jsonSupplier.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Supplier信息*/
    public void ajaxModifySupplier() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            supplierDAO.UpdateSupplier(supplier);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Supplier修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Supplier信息*/
    public void ajaxDeleteSupplier() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _supplierIds[] = supplierIds.split(",");
        	for(String _supplierId: _supplierIds) {
        		supplierDAO.DeleteSupplier(Integer.parseInt(_supplierId));
        	}
        	success = true;
        	message = _supplierIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Supplier信息*/
    public String FrontQuerySupplier() {
        if(page == 0) page = 1;
        if(supplierName == null) supplierName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Supplier> supplierList = supplierDAO.QuerySupplierInfo(supplierName, connectPerson, telephone, page);
        /*计算总的页数和总的记录数*/
        supplierDAO.CalculateTotalPageAndRecordNumber(supplierName, connectPerson, telephone);
        /*获取到总的页码数目*/
        totalPage = supplierDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = supplierDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("supplierList",  supplierList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("supplierName", supplierName);
        ctx.put("connectPerson", connectPerson);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*查询要修改的Supplier信息*/
    public String FrontShowSupplierQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键supplierId获取Supplier对象*/
        Supplier supplier = supplierDAO.GetSupplierBySupplierId(supplierId);

        ctx.put("supplier",  supplier);
        return "front_show_view";
    }

    /*删除Supplier信息*/
    public String DeleteSupplier() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            supplierDAO.DeleteSupplier(supplierId);
            ctx.put("message", "Supplier删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Supplier删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String querySupplierOutputToExcel() { 
        if(supplierName == null) supplierName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Supplier> supplierList = supplierDAO.QuerySupplierInfo(supplierName,connectPerson,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Supplier信息记录"; 
        String[] headers = { "供应商id","供应商名称","联系人","联系电话","传真","供应商地址"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<supplierList.size();i++) {
        	Supplier supplier = supplierList.get(i); 
        	dataset.add(new String[]{supplier.getSupplierId() + "",supplier.getSupplierName(),supplier.getConnectPerson(),supplier.getTelephone(),supplier.getFax(),supplier.getAddress()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Supplier.xls");//filename是下载的xls的名，建议最好用英文 
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
