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
import com.chengxusheji.dao.SaleDAO;
import com.chengxusheji.domain.Sale;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.CustomerDAO;
import com.chengxusheji.domain.Customer;
import com.chengxusheji.dao.EmployeeDAO;
import com.chengxusheji.domain.Employee;
@Controller @Scope("prototype")
public class SaleAction extends ActionSupport {

    /*界面层需要查询的属性: 销售超市商品*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
    }

    /*界面层需要查询的属性: 销售客户*/
    private Customer customerObj;
    public void setCustomerObj(Customer customerObj) {
        this.customerObj = customerObj;
    }
    public Customer getCustomerObj() {
        return this.customerObj;
    }

    /*界面层需要查询的属性: 销售日期*/
    private String saleDate;
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
    public String getSaleDate() {
        return this.saleDate;
    }

    /*界面层需要查询的属性: 销售员*/
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

    private int saleId;
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
    public int getSaleId() {
        return saleId;
    }

    /*要删除的记录主键集合*/
    private String saleIds;
    public String getSaleIds() {
		return saleIds;
	}
	public void setSaleIds(String saleIds) {
		this.saleIds = saleIds;
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
    @Resource SaleDAO saleDAO;

    @Resource ProductDAO productDAO;
    @Resource CustomerDAO customerDAO;
    @Resource EmployeeDAO employeeDAO;
    /*待操作的Sale对象*/
    private Sale sale;
    public void setSale(Sale sale) {
        this.sale = sale;
    }
    public Sale getSale() {
        return this.sale;
    }

    /*ajax添加Sale信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddSale() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            Product productObj = productDAO.GetProductByProductNo(sale.getProductObj().getProductNo());
            sale.setProductObj(productObj);
            Customer customerObj = customerDAO.GetCustomerByCustomerId(sale.getCustomerObj().getCustomerId());
            sale.setCustomerObj(customerObj);
            Employee employeeObj = employeeDAO.GetEmployeeByEmployeeNo(sale.getEmployeeObj().getEmployeeNo());
            sale.setEmployeeObj(employeeObj);
            
            if(sale.getCount() > productObj.getStockCount()) {
            	message = "超市商品库存不足!";
                writeJsonResponse(success, message);
                return ;
            }
            saleDAO.AddSale(sale);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Sale添加失败!";
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

    /*查询Sale信息*/
    public void ajaxQuerySale() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(saleDate == null) saleDate = "";
        if(rows != 0) saleDAO.setRows(rows);
        List<Sale> saleList = saleDAO.QuerySaleInfo(productObj, customerObj, saleDate, employeeObj, page);
        /*计算总的页数和总的记录数*/
        saleDAO.CalculateTotalPageAndRecordNumber(productObj, customerObj, saleDate, employeeObj);
        /*获取到总的页码数目*/
        totalPage = saleDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = saleDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Sale sale:saleList) {
			JSONObject jsonSale = sale.getJsonObject();
			jsonArray.put(jsonSale);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Sale信息*/
    public void ajaxQueryAllSale() throws IOException, JSONException {
        List<Sale> saleList = saleDAO.QueryAllSaleInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Sale sale:saleList) {
			JSONObject jsonSale = new JSONObject();
			jsonSale.accumulate("saleId", sale.getSaleId());
			jsonSale.accumulate("saleId", sale.getSaleId());
			jsonArray.put(jsonSale);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Sale信息*/
    public void ajaxModifySaleQuery() throws IOException, JSONException {
        /*根据主键saleId获取Sale对象*/
        Sale sale = saleDAO.GetSaleBySaleId(saleId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonSale = sale.getJsonObject(); 
		out.println(jsonSale.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Sale信息*/
    public void ajaxModifySale() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            Product productObj = productDAO.GetProductByProductNo(sale.getProductObj().getProductNo());
            sale.setProductObj(productObj);
            Customer customerObj = customerDAO.GetCustomerByCustomerId(sale.getCustomerObj().getCustomerId());
            sale.setCustomerObj(customerObj);
            Employee employeeObj = employeeDAO.GetEmployeeByEmployeeNo(sale.getEmployeeObj().getEmployeeNo());
            sale.setEmployeeObj(employeeObj);
            saleDAO.UpdateSale(sale);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Sale修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Sale信息*/
    public void ajaxDeleteSale() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _saleIds[] = saleIds.split(",");
        	for(String _saleId: _saleIds) {
        		saleDAO.DeleteSale(Integer.parseInt(_saleId));
        	}
        	success = true;
        	message = _saleIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Sale信息*/
    public String FrontQuerySale() {
        if(page == 0) page = 1;
        if(saleDate == null) saleDate = "";
        List<Sale> saleList = saleDAO.QuerySaleInfo(productObj, customerObj, saleDate, employeeObj, page);
        /*计算总的页数和总的记录数*/
        saleDAO.CalculateTotalPageAndRecordNumber(productObj, customerObj, saleDate, employeeObj);
        /*获取到总的页码数目*/
        totalPage = saleDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = saleDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("saleList",  saleList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("customerObj", customerObj);
        List<Customer> customerList = customerDAO.QueryAllCustomerInfo();
        ctx.put("customerList", customerList);
        ctx.put("saleDate", saleDate);
        ctx.put("employeeObj", employeeObj);
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();
        ctx.put("employeeList", employeeList);
        return "front_query_view";
    }

    /*查询要修改的Sale信息*/
    public String FrontShowSaleQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键saleId获取Sale对象*/
        Sale sale = saleDAO.GetSaleBySaleId(saleId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<Customer> customerList = customerDAO.QueryAllCustomerInfo();
        ctx.put("customerList", customerList);
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();
        ctx.put("employeeList", employeeList);
        ctx.put("sale",  sale);
        return "front_show_view";
    }

    /*删除Sale信息*/
    public String DeleteSale() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            saleDAO.DeleteSale(saleId);
            ctx.put("message", "Sale删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Sale删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String querySaleOutputToExcel() { 
        if(saleDate == null) saleDate = "";
        List<Sale> saleList = saleDAO.QuerySaleInfo(productObj,customerObj,saleDate,employeeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Sale信息记录"; 
        String[] headers = { "销售id","销售超市商品","销售客户","销售单价","销售数量","销售日期","留言备注","销售员"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<saleList.size();i++) {
        	Sale sale = saleList.get(i); 
        	dataset.add(new String[]{sale.getSaleId() + "",sale.getProductObj().getProductName(),
sale.getCustomerObj().getCustomerName(),
sale.getPrice() + "",sale.getCount() + "",sale.getSaleDate(),sale.getMemo(),sale.getEmployeeObj().getName()
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
			response.setHeader("Content-disposition","attachment; filename="+"Sale.xls");//filename是下载的xls的名，建议最好用英文 
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
