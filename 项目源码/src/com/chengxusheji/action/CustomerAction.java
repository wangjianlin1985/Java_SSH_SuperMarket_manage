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
import com.chengxusheji.dao.CustomerDAO;
import com.chengxusheji.domain.Customer;
@Controller @Scope("prototype")
public class CustomerAction extends ActionSupport {

    /*界面层需要查询的属性: 客户名称*/
    private String customerName;
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerName() {
        return this.customerName;
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

    private int customerId;
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getCustomerId() {
        return customerId;
    }

    /*要删除的记录主键集合*/
    private String customerIds;
    public String getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
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
    @Resource CustomerDAO customerDAO;

    /*待操作的Customer对象*/
    private Customer customer;
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Customer getCustomer() {
        return this.customer;
    }

    /*ajax添加Customer信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddCustomer() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            customerDAO.AddCustomer(customer);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Customer添加失败!";
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

    /*查询Customer信息*/
    public void ajaxQueryCustomer() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(customerName == null) customerName = "";
        if(telephone == null) telephone = "";
        if(rows != 0) customerDAO.setRows(rows);
        List<Customer> customerList = customerDAO.QueryCustomerInfo(customerName, telephone, page);
        /*计算总的页数和总的记录数*/
        customerDAO.CalculateTotalPageAndRecordNumber(customerName, telephone);
        /*获取到总的页码数目*/
        totalPage = customerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = customerDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Customer customer:customerList) {
			JSONObject jsonCustomer = customer.getJsonObject();
			jsonArray.put(jsonCustomer);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Customer信息*/
    public void ajaxQueryAllCustomer() throws IOException, JSONException {
        List<Customer> customerList = customerDAO.QueryAllCustomerInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Customer customer:customerList) {
			JSONObject jsonCustomer = new JSONObject();
			jsonCustomer.accumulate("customerId", customer.getCustomerId());
			jsonCustomer.accumulate("customerName", customer.getCustomerName());
			jsonArray.put(jsonCustomer);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Customer信息*/
    public void ajaxModifyCustomerQuery() throws IOException, JSONException {
        /*根据主键customerId获取Customer对象*/
        Customer customer = customerDAO.GetCustomerByCustomerId(customerId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonCustomer = customer.getJsonObject(); 
		out.println(jsonCustomer.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Customer信息*/
    public void ajaxModifyCustomer() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            customerDAO.UpdateCustomer(customer);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Customer修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Customer信息*/
    public void ajaxDeleteCustomer() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _customerIds[] = customerIds.split(",");
        	for(String _customerId: _customerIds) {
        		customerDAO.DeleteCustomer(Integer.parseInt(_customerId));
        	}
        	success = true;
        	message = _customerIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Customer信息*/
    public String FrontQueryCustomer() {
        if(page == 0) page = 1;
        if(customerName == null) customerName = "";
        if(telephone == null) telephone = "";
        List<Customer> customerList = customerDAO.QueryCustomerInfo(customerName, telephone, page);
        /*计算总的页数和总的记录数*/
        customerDAO.CalculateTotalPageAndRecordNumber(customerName, telephone);
        /*获取到总的页码数目*/
        totalPage = customerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = customerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("customerList",  customerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("customerName", customerName);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*查询要修改的Customer信息*/
    public String FrontShowCustomerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键customerId获取Customer对象*/
        Customer customer = customerDAO.GetCustomerByCustomerId(customerId);

        ctx.put("customer",  customer);
        return "front_show_view";
    }

    /*删除Customer信息*/
    public String DeleteCustomer() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            customerDAO.DeleteCustomer(customerId);
            ctx.put("message", "Customer删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Customer删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryCustomerOutputToExcel() { 
        if(customerName == null) customerName = "";
        if(telephone == null) telephone = "";
        List<Customer> customerList = customerDAO.QueryCustomerInfo(customerName,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Customer信息记录"; 
        String[] headers = { "客户id","客户名称","联系电话","收货地址"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<customerList.size();i++) {
        	Customer customer = customerList.get(i); 
        	dataset.add(new String[]{customer.getCustomerId() + "",customer.getCustomerName(),customer.getTelephone(),customer.getAddress()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Customer.xls");//filename是下载的xls的名，建议最好用英文 
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
