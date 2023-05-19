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
import com.chengxusheji.dao.PurchaseDAO;
import com.chengxusheji.domain.Purchase;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.SupplierDAO;
import com.chengxusheji.domain.Supplier;
import com.chengxusheji.dao.EmployeeDAO;
import com.chengxusheji.domain.Employee;
@Controller @Scope("prototype")
public class PurchaseAction extends ActionSupport {

    /*界面层需要查询的属性: 进货超市商品*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
    }

    /*界面层需要查询的属性: 供应商*/
    private Supplier supplierObj;
    public void setSupplierObj(Supplier supplierObj) {
        this.supplierObj = supplierObj;
    }
    public Supplier getSupplierObj() {
        return this.supplierObj;
    }

    /*界面层需要查询的属性: 进货日期*/
    private String purchaseDate;
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public String getPurchaseDate() {
        return this.purchaseDate;
    }

    /*界面层需要查询的属性: 进货员*/
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

    private int purchaseId;
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
    public int getPurchaseId() {
        return purchaseId;
    }

    /*要删除的记录主键集合*/
    private String purchaseIds;
    public String getPurchaseIds() {
		return purchaseIds;
	}
	public void setPurchaseIds(String purchaseIds) {
		this.purchaseIds = purchaseIds;
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
    @Resource PurchaseDAO purchaseDAO;

    @Resource ProductDAO productDAO;
    @Resource SupplierDAO supplierDAO;
    @Resource EmployeeDAO employeeDAO;
    /*待操作的Purchase对象*/
    private Purchase purchase;
    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
    public Purchase getPurchase() {
        return this.purchase;
    }

    /*ajax添加Purchase信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddPurchase() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            Product productObj = productDAO.GetProductByProductNo(purchase.getProductObj().getProductNo());
            purchase.setProductObj(productObj);
            Supplier supplierObj = supplierDAO.GetSupplierBySupplierId(purchase.getSupplierObj().getSupplierId());
            purchase.setSupplierObj(supplierObj);
            Employee employeeObj = employeeDAO.GetEmployeeByEmployeeNo(purchase.getEmployeeObj().getEmployeeNo());
            purchase.setEmployeeObj(employeeObj);
            purchaseDAO.AddPurchase(purchase);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Purchase添加失败!";
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

    /*查询Purchase信息*/
    public void ajaxQueryPurchase() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(purchaseDate == null) purchaseDate = "";
        if(rows != 0) purchaseDAO.setRows(rows);
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(productObj, supplierObj, purchaseDate, employeeObj, page);
        /*计算总的页数和总的记录数*/
        purchaseDAO.CalculateTotalPageAndRecordNumber(productObj, supplierObj, purchaseDate, employeeObj);
        /*获取到总的页码数目*/
        totalPage = purchaseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = purchaseDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Purchase purchase:purchaseList) {
			JSONObject jsonPurchase = purchase.getJsonObject();
			jsonArray.put(jsonPurchase);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Purchase信息*/
    public void ajaxQueryAllPurchase() throws IOException, JSONException {
        List<Purchase> purchaseList = purchaseDAO.QueryAllPurchaseInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Purchase purchase:purchaseList) {
			JSONObject jsonPurchase = new JSONObject();
			jsonPurchase.accumulate("purchaseId", purchase.getPurchaseId());
			jsonPurchase.accumulate("purchaseId", purchase.getPurchaseId());
			jsonArray.put(jsonPurchase);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Purchase信息*/
    public void ajaxModifyPurchaseQuery() throws IOException, JSONException {
        /*根据主键purchaseId获取Purchase对象*/
        Purchase purchase = purchaseDAO.GetPurchaseByPurchaseId(purchaseId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonPurchase = purchase.getJsonObject(); 
		out.println(jsonPurchase.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Purchase信息*/
    public void ajaxModifyPurchase() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            Product productObj = productDAO.GetProductByProductNo(purchase.getProductObj().getProductNo());
            purchase.setProductObj(productObj);
            Supplier supplierObj = supplierDAO.GetSupplierBySupplierId(purchase.getSupplierObj().getSupplierId());
            purchase.setSupplierObj(supplierObj);
            Employee employeeObj = employeeDAO.GetEmployeeByEmployeeNo(purchase.getEmployeeObj().getEmployeeNo());
            purchase.setEmployeeObj(employeeObj);
            purchaseDAO.UpdatePurchase(purchase);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Purchase修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Purchase信息*/
    public void ajaxDeletePurchase() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _purchaseIds[] = purchaseIds.split(",");
        	for(String _purchaseId: _purchaseIds) {
        		purchaseDAO.DeletePurchase(Integer.parseInt(_purchaseId));
        	}
        	success = true;
        	message = _purchaseIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Purchase信息*/
    public String FrontQueryPurchase() {
        if(page == 0) page = 1;
        if(purchaseDate == null) purchaseDate = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(productObj, supplierObj, purchaseDate, employeeObj, page);
        /*计算总的页数和总的记录数*/
        purchaseDAO.CalculateTotalPageAndRecordNumber(productObj, supplierObj, purchaseDate, employeeObj);
        /*获取到总的页码数目*/
        totalPage = purchaseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = purchaseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("purchaseList",  purchaseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("supplierObj", supplierObj);
        List<Supplier> supplierList = supplierDAO.QueryAllSupplierInfo();
        ctx.put("supplierList", supplierList);
        ctx.put("purchaseDate", purchaseDate);
        ctx.put("employeeObj", employeeObj);
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();
        ctx.put("employeeList", employeeList);
        return "front_query_view";
    }

    /*查询要修改的Purchase信息*/
    public String FrontShowPurchaseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键purchaseId获取Purchase对象*/
        Purchase purchase = purchaseDAO.GetPurchaseByPurchaseId(purchaseId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<Supplier> supplierList = supplierDAO.QueryAllSupplierInfo();
        ctx.put("supplierList", supplierList);
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();
        ctx.put("employeeList", employeeList);
        ctx.put("purchase",  purchase);
        return "front_show_view";
    }

    /*删除Purchase信息*/
    public String DeletePurchase() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            purchaseDAO.DeletePurchase(purchaseId);
            ctx.put("message", "Purchase删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Purchase删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryPurchaseOutputToExcel() { 
        if(purchaseDate == null) purchaseDate = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(productObj,supplierObj,purchaseDate,employeeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Purchase信息记录"; 
        String[] headers = { "进货id","进货超市商品","供应商","进货单价","进货数量","进货日期","留言备注","进货员"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<purchaseList.size();i++) {
        	Purchase purchase = purchaseList.get(i); 
        	dataset.add(new String[]{purchase.getPurchaseId() + "",purchase.getProductObj().getProductName(),
purchase.getSupplierObj().getSupplierName(),
purchase.getPrice() + "",purchase.getCount() + "",purchase.getPurchaseDate(),purchase.getMemo(),purchase.getEmployeeObj().getName()
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
			response.setHeader("Content-disposition","attachment; filename="+"Purchase.xls");//filename是下载的xls的名，建议最好用英文 
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
