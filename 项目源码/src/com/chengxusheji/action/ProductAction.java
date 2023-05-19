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
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.ProductClassDAO;
import com.chengxusheji.domain.ProductClass;
@Controller @Scope("prototype")
public class ProductAction extends ActionSupport {

/*Product类字段productPhoto参数接收*/
	 private File productPhotoFile;
	 private String productPhotoFileFileName;
	 private String productPhotoFileContentType;
	 public File getProductPhotoFile() {
		return productPhotoFile;
	}
	public void setProductPhotoFile(File productPhotoFile) {
		this.productPhotoFile = productPhotoFile;
	}
	public String getProductPhotoFileFileName() {
		return productPhotoFileFileName;
	}
	public void setProductPhotoFileFileName(String productPhotoFileFileName) {
		this.productPhotoFileFileName = productPhotoFileFileName;
	}
	public String getProductPhotoFileContentType() {
		return productPhotoFileContentType;
	}
	public void setProductPhotoFileContentType(String productPhotoFileContentType) {
		this.productPhotoFileContentType = productPhotoFileContentType;
	}
    /*界面层需要查询的属性: 超市商品编号*/
    private String productNo;
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return this.productNo;
    }

    /*界面层需要查询的属性: 超市商品类别*/
    private ProductClass productClassObj;
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }
    public ProductClass getProductClassObj() {
        return this.productClassObj;
    }

    /*界面层需要查询的属性: 超市商品名称*/
    private String productName;
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return this.productName;
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

    /*要删除的记录主键集合*/
    private String productNos;
    public String getProductNos() {
		return productNos;
	}
	public void setProductNos(String productNos) {
		this.productNos = productNos;
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
    @Resource ProductDAO productDAO;

    @Resource ProductClassDAO productClassDAO;
    /*待操作的Product对象*/
    private Product product;
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return this.product;
    }

    /*ajax添加Product信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddProduct() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        /*验证超市商品编号是否已经存在*/
        String productNo = product.getProductNo();
        Product db_product = productDAO.GetProductByProductNo(productNo);
        if(null != db_product) {
        	message = "该超市商品编号已经存在!";
        	writeJsonResponse(success, message);
        	return ;
        }
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByProductClassId(product.getProductClassObj().getProductClassId());
            product.setProductClassObj(productClassObj);
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String productPhotoFileName = ""; 
       	 	if(productPhotoFile != null) {
       	 		InputStream is = new FileInputStream(productPhotoFile);
       			String fileContentType = this.getProductPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				message = "上传图片格式不正确!";
       				writeJsonResponse(success, message);
       				return ;
       			}
       			File file = new File(path, productPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(productPhotoFile != null)
            	product.setProductPhoto("upload/" + productPhotoFileName);
            else
            	product.setProductPhoto("upload/NoImage.jpg");
            productDAO.AddProduct(product);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Product添加失败!";
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

    /*查询Product信息*/
    public void ajaxQueryProduct() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(rows != 0) productDAO.setRows(rows);
        List<Product> productList = productDAO.QueryProductInfo(productNo, productClassObj, productName, page);
        /*计算总的页数和总的记录数*/
        productDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName);
        /*获取到总的页码数目*/
        totalPage = productDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Product product:productList) {
			JSONObject jsonProduct = product.getJsonObject();
			jsonArray.put(jsonProduct);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Product信息*/
    public void ajaxQueryAllProduct() throws IOException, JSONException {
        List<Product> productList = productDAO.QueryAllProductInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Product product:productList) {
			JSONObject jsonProduct = new JSONObject();
			jsonProduct.accumulate("productNo", product.getProductNo());
			jsonProduct.accumulate("productName", product.getProductName());
			jsonArray.put(jsonProduct);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Product信息*/
    public void ajaxModifyProductQuery() throws IOException, JSONException {
        /*根据主键productNo获取Product对象*/
        Product product = productDAO.GetProductByProductNo(productNo);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonProduct = product.getJsonObject(); 
		out.println(jsonProduct.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Product信息*/
    public void ajaxModifyProduct() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByProductClassId(product.getProductClassObj().getProductClassId());
            product.setProductClassObj(productClassObj);
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String productPhotoFileName = ""; 
       	 	if(productPhotoFile != null) {
       	 		InputStream is = new FileInputStream(productPhotoFile);
       			String fileContentType = this.getProductPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				message = "上传图片格式不正确!";
       				writeJsonResponse(success, message);
       				return ;
       			}
       			File file = new File(path, productPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       			product.setProductPhoto("upload/" + productPhotoFileName);
       	 	}
            productDAO.UpdateProduct(product);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Product修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Product信息*/
    public void ajaxDeleteProduct() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _productNos[] = productNos.split(",");
        	for(String _productNo: _productNos) {
        		productDAO.DeleteProduct(_productNo);
        	}
        	success = true;
        	message = _productNos.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Product信息*/
    public String FrontQueryProduct() {
        if(page == 0) page = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        List<Product> productList = productDAO.QueryProductInfo(productNo, productClassObj, productName, page);
        /*计算总的页数和总的记录数*/
        productDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName);
        /*获取到总的页码数目*/
        totalPage = productDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productList",  productList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        return "front_query_view";
    }

    /*查询要修改的Product信息*/
    public String FrontShowProductQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键productNo获取Product对象*/
        Product product = productDAO.GetProductByProductNo(productNo);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("product",  product);
        return "front_show_view";
    }

    /*删除Product信息*/
    public String DeleteProduct() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productDAO.DeleteProduct(productNo);
            ctx.put("message", "Product删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Product删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryProductOutputToExcel() { 
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        List<Product> productList = productDAO.QueryProductInfo(productNo,productClassObj,productName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Product信息记录"; 
        String[] headers = { "超市商品编号","超市商品类别","超市商品名称","超市商品图片","市场价","超市商品库存"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productList.size();i++) {
        	Product product = productList.get(i); 
        	dataset.add(new String[]{product.getProductNo(),product.getProductClassObj().getProductClassName(),
product.getProductName(),product.getProductPhoto(),product.getPrice() + "",product.getStockCount() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Product.xls");//filename是下载的xls的名，建议最好用英文 
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
