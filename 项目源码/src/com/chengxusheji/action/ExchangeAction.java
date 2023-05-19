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
import com.chengxusheji.dao.ExchangeDAO;
import com.chengxusheji.domain.Exchange;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.CustomerDAO;
import com.chengxusheji.domain.Customer;
import com.chengxusheji.dao.EmployeeDAO;
import com.chengxusheji.domain.Employee;
@Controller @Scope("prototype")
public class ExchangeAction extends ActionSupport {

    /*界面层需要查询的属性: 超市换货超市商品*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
    }

    /*界面层需要查询的属性: 超市换货客户*/
    private Customer customerObj;
    public void setCustomerObj(Customer customerObj) {
        this.customerObj = customerObj;
    }
    public Customer getCustomerObj() {
        return this.customerObj;
    }

    /*界面层需要查询的属性: 超市换货日期*/
    private String exchangeDate;
    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
    public String getExchangeDate() {
        return this.exchangeDate;
    }

    /*界面层需要查询的属性: 工作人员*/
    private Employee employeeObj;
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }
    public Employee getEmployeeObj() {
        return this.employeeObj;
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

    private int exchangeId;
    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }
    public int getExchangeId() {
        return exchangeId;
    }

    /*要删除的记录主键集合*/
    private String exchangeIds;
    public String getExchangeIds() {
		return exchangeIds;
	}
	public void setExchangeIds(String exchangeIds) {
		this.exchangeIds = exchangeIds;
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
    @Resource ExchangeDAO exchangeDAO;

    @Resource ProductDAO productDAO;
    @Resource CustomerDAO customerDAO;
    @Resource EmployeeDAO employeeDAO;
    /*待操作的Exchange对象*/
    private Exchange exchange;
    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }
    public Exchange getExchange() {
        return this.exchange;
    }

    /*ajax添加Exchange信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddExchange() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            Product productObj = productDAO.GetProductByProductNo(exchange.getProductObj().getProductNo());
            exchange.setProductObj(productObj);
            Customer customerObj = customerDAO.GetCustomerByCustomerId(exchange.getCustomerObj().getCustomerId());
            exchange.setCustomerObj(customerObj);
            Employee employeeObj = employeeDAO.GetEmployeeByEmployeeNo(exchange.getEmployeeObj().getEmployeeNo());
            exchange.setEmployeeObj(employeeObj);
            exchangeDAO.AddExchange(exchange);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Exchange添加失败!";
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

    /*查询Exchange信息*/
    public void ajaxQueryExchange() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(exchangeDate == null) exchangeDate = "";
        if(rows != 0) exchangeDAO.setRows(rows);
        List<Exchange> exchangeList = exchangeDAO.QueryExchangeInfo(productObj, customerObj, exchangeDate, employeeObj, page);
        /*计算总的页数和总的记录数*/
        exchangeDAO.CalculateTotalPageAndRecordNumber(productObj, customerObj, exchangeDate, employeeObj);
        /*获取到总的页码数目*/
        totalPage = exchangeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = exchangeDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Exchange exchange:exchangeList) {
			JSONObject jsonExchange = exchange.getJsonObject();
			jsonArray.put(jsonExchange);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Exchange信息*/
    public void ajaxQueryAllExchange() throws IOException, JSONException {
        List<Exchange> exchangeList = exchangeDAO.QueryAllExchangeInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Exchange exchange:exchangeList) {
			JSONObject jsonExchange = new JSONObject();
			jsonExchange.accumulate("exchangeId", exchange.getExchangeId());
			jsonExchange.accumulate("exchangeId", exchange.getExchangeId());
			jsonArray.put(jsonExchange);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Exchange信息*/
    public void ajaxModifyExchangeQuery() throws IOException, JSONException {
        /*根据主键exchangeId获取Exchange对象*/
        Exchange exchange = exchangeDAO.GetExchangeByExchangeId(exchangeId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonExchange = exchange.getJsonObject(); 
		out.println(jsonExchange.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Exchange信息*/
    public void ajaxModifyExchange() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            Product productObj = productDAO.GetProductByProductNo(exchange.getProductObj().getProductNo());
            exchange.setProductObj(productObj);
            Customer customerObj = customerDAO.GetCustomerByCustomerId(exchange.getCustomerObj().getCustomerId());
            exchange.setCustomerObj(customerObj);
            Employee employeeObj = employeeDAO.GetEmployeeByEmployeeNo(exchange.getEmployeeObj().getEmployeeNo());
            exchange.setEmployeeObj(employeeObj);
            exchangeDAO.UpdateExchange(exchange);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Exchange修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Exchange信息*/
    public void ajaxDeleteExchange() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _exchangeIds[] = exchangeIds.split(",");
        	for(String _exchangeId: _exchangeIds) {
        		exchangeDAO.DeleteExchange(Integer.parseInt(_exchangeId));
        	}
        	success = true;
        	message = _exchangeIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Exchange信息*/
    public String FrontQueryExchange() {
        if(page == 0) page = 1;
        if(exchangeDate == null) exchangeDate = "";
        List<Exchange> exchangeList = exchangeDAO.QueryExchangeInfo(productObj, customerObj, exchangeDate, employeeObj, page);
        /*计算总的页数和总的记录数*/
        exchangeDAO.CalculateTotalPageAndRecordNumber(productObj, customerObj, exchangeDate, employeeObj);
        /*获取到总的页码数目*/
        totalPage = exchangeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = exchangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("exchangeList",  exchangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("customerObj", customerObj);
        List<Customer> customerList = customerDAO.QueryAllCustomerInfo();
        ctx.put("customerList", customerList);
        ctx.put("exchangeDate", exchangeDate);
        ctx.put("employeeObj", employeeObj);
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();
        ctx.put("employeeList", employeeList);
        return "front_query_view";
    }

    /*查询要修改的Exchange信息*/
    public String FrontShowExchangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键exchangeId获取Exchange对象*/
        Exchange exchange = exchangeDAO.GetExchangeByExchangeId(exchangeId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<Customer> customerList = customerDAO.QueryAllCustomerInfo();
        ctx.put("customerList", customerList);
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();
        ctx.put("employeeList", employeeList);
        ctx.put("exchange",  exchange);
        return "front_show_view";
    }

    /*删除Exchange信息*/
    public String DeleteExchange() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            exchangeDAO.DeleteExchange(exchangeId);
            ctx.put("message", "Exchange删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Exchange删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryExchangeOutputToExcel() { 
        if(exchangeDate == null) exchangeDate = "";
        List<Exchange> exchangeList = exchangeDAO.QueryExchangeInfo(productObj,customerObj,exchangeDate,employeeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Exchange信息记录"; 
        String[] headers = { "超市换货单id","超市换货超市商品","超市换货数量","超市换货客户","超市换货日期","工作人员"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<exchangeList.size();i++) {
        	Exchange exchange = exchangeList.get(i); 
        	dataset.add(new String[]{exchange.getExchangeId() + "",exchange.getProductObj().getProductName(),
exchange.getCount() + "",exchange.getCustomerObj().getCustomerName(),
exchange.getExchangeDate(),exchange.getEmployeeObj().getName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"Exchange.xls");//filename是下载的xls的名，建议最好用英文 
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
