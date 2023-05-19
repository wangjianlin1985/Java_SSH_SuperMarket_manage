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
import com.chengxusheji.dao.ProductClassDAO;
import com.chengxusheji.domain.ProductClass;
@Controller @Scope("prototype")
public class ProductClassAction extends ActionSupport {

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

    private int productClassId;
    public void setProductClassId(int productClassId) {
        this.productClassId = productClassId;
    }
    public int getProductClassId() {
        return productClassId;
    }

    /*要删除的记录主键集合*/
    private String productClassIds;
    public String getProductClassIds() {
		return productClassIds;
	}
	public void setProductClassIds(String productClassIds) {
		this.productClassIds = productClassIds;
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
    @Resource ProductClassDAO productClassDAO;

    /*待操作的ProductClass对象*/
    private ProductClass productClass;
    public void setProductClass(ProductClass productClass) {
        this.productClass = productClass;
    }
    public ProductClass getProductClass() {
        return this.productClass;
    }

    /*ajax添加ProductClass信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddProductClass() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try {
            productClassDAO.AddProductClass(productClass);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "ProductClass添加失败!";
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

    /*查询ProductClass信息*/
    public void ajaxQueryProductClass() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(rows != 0) productClassDAO.setRows(rows);
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(page);
        /*计算总的页数和总的记录数*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = productClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productClassDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(ProductClass productClass:productClassList) {
			JSONObject jsonProductClass = productClass.getJsonObject();
			jsonArray.put(jsonProductClass);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询ProductClass信息*/
    public void ajaxQueryAllProductClass() throws IOException, JSONException {
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(ProductClass productClass:productClassList) {
			JSONObject jsonProductClass = new JSONObject();
			jsonProductClass.accumulate("productClassId", productClass.getProductClassId());
			jsonProductClass.accumulate("productClassName", productClass.getProductClassName());
			jsonArray.put(jsonProductClass);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的ProductClass信息*/
    public void ajaxModifyProductClassQuery() throws IOException, JSONException {
        /*根据主键productClassId获取ProductClass对象*/
        ProductClass productClass = productClassDAO.GetProductClassByProductClassId(productClassId);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonProductClass = productClass.getJsonObject(); 
		out.println(jsonProductClass.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改ProductClass信息*/
    public void ajaxModifyProductClass() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            productClassDAO.UpdateProductClass(productClass);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "ProductClass修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除ProductClass信息*/
    public void ajaxDeleteProductClass() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _productClassIds[] = productClassIds.split(",");
        	for(String _productClassId: _productClassIds) {
        		productClassDAO.DeleteProductClass(Integer.parseInt(_productClassId));
        	}
        	success = true;
        	message = _productClassIds.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询ProductClass信息*/
    public String FrontQueryProductClass() {
        if(page == 0) page = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(page);
        /*计算总的页数和总的记录数*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = productClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        return "front_query_view";
    }

    /*查询要修改的ProductClass信息*/
    public String FrontShowProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键productClassId获取ProductClass对象*/
        ProductClass productClass = productClassDAO.GetProductClassByProductClassId(productClassId);

        ctx.put("productClass",  productClass);
        return "front_show_view";
    }

    /*删除ProductClass信息*/
    public String DeleteProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productClassDAO.DeleteProductClass(productClassId);
            ctx.put("message", "ProductClass删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "ProductClass删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryProductClassOutputToExcel() { 
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ProductClass信息记录"; 
        String[] headers = { "超市商品类别id","超市商品类别名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productClassList.size();i++) {
        	ProductClass productClass = productClassList.get(i); 
        	dataset.add(new String[]{productClass.getProductClassId() + "",productClass.getProductClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ProductClass.xls");//filename是下载的xls的名，建议最好用英文 
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
