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
import com.chengxusheji.dao.EmployeeDAO;
import com.chengxusheji.domain.Employee;
import com.chengxusheji.dao.DepartmentDAO;
import com.chengxusheji.domain.Department;
@Controller @Scope("prototype")
public class EmployeeAction extends ActionSupport {

    /*界面层需要查询的属性: 员工编号*/
    private String employeeNo;
    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }
    public String getEmployeeNo() {
        return this.employeeNo;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 所在部门*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
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

    /*要删除的记录主键集合*/
    private String employeeNos;
    public String getEmployeeNos() {
		return employeeNos;
	}
	public void setEmployeeNos(String employeeNos) {
		this.employeeNos = employeeNos;
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
    @Resource EmployeeDAO employeeDAO;

    @Resource DepartmentDAO departmentDAO;
    /*待操作的Employee对象*/
    private Employee employee;
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Employee getEmployee() {
        return this.employee;
    }

    /*ajax添加Employee信息*/
    @SuppressWarnings("deprecation")
    public void ajaxAddEmployee() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        /*验证员工编号是否已经存在*/
        String employeeNo = employee.getEmployeeNo();
        Employee db_employee = employeeDAO.GetEmployeeByEmployeeNo(employeeNo);
        if(null != db_employee) {
        	message = "该员工编号已经存在!";
        	writeJsonResponse(success, message);
        	return ;
        }
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentNo(employee.getDepartmentObj().getDepartmentNo());
            employee.setDepartmentObj(departmentObj);
            employeeDAO.AddEmployee(employee);
            success = true;
            writeJsonResponse(success, message); 
        } catch (Exception e) {
            e.printStackTrace();
            message = "Employee添加失败!";
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

    /*查询Employee信息*/
    public void ajaxQueryEmployee() throws IOException, JSONException {
        if(page == 0) page = 1;
        if(employeeNo == null) employeeNo = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        if(rows != 0) employeeDAO.setRows(rows);
        List<Employee> employeeList = employeeDAO.QueryEmployeeInfo(employeeNo, name, departmentObj, birthday, telephone, page);
        /*计算总的页数和总的记录数*/
        employeeDAO.CalculateTotalPageAndRecordNumber(employeeNo, name, departmentObj, birthday, telephone);
        /*获取到总的页码数目*/
        totalPage = employeeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = employeeDAO.getRecordNumber();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Employee employee:employeeList) {
			JSONObject jsonEmployee = employee.getJsonObject();
			jsonArray.put(jsonEmployee);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
    }

    /*查询Employee信息*/
    public void ajaxQueryAllEmployee() throws IOException, JSONException {
        List<Employee> employeeList = employeeDAO.QueryAllEmployeeInfo();        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONArray jsonArray = new JSONArray();
		for(Employee employee:employeeList) {
			JSONObject jsonEmployee = new JSONObject();
			jsonEmployee.accumulate("employeeNo", employee.getEmployeeNo());
			jsonEmployee.accumulate("name", employee.getName());
			jsonArray.put(jsonEmployee);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
    }

    /*查询要修改的Employee信息*/
    public void ajaxModifyEmployeeQuery() throws IOException, JSONException {
        /*根据主键employeeNo获取Employee对象*/
        Employee employee = employeeDAO.GetEmployeeByEmployeeNo(employeeNo);

        HttpServletResponse response=ServletActionContext.getResponse(); 
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//将要被返回到客户端的对象 
		JSONObject jsonEmployee = employee.getJsonObject(); 
		out.println(jsonEmployee.toString()); 
		out.flush();
		out.close();
    };

    /*更新修改Employee信息*/
    public void ajaxModifyEmployee() throws IOException, JSONException{
    	String message = "";
    	boolean success = false;
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentNo(employee.getDepartmentObj().getDepartmentNo());
            employee.setDepartmentObj(departmentObj);
            employeeDAO.UpdateEmployee(employee);
            success = true;
            writeJsonResponse(success, message);
        } catch (Exception e) {
            message = "Employee修改失败!"; 
            writeJsonResponse(success, message);
       }
   }

    /*删除Employee信息*/
    public void ajaxDeleteEmployee() throws IOException, JSONException {
    	String message = "";
    	boolean success = false;
        try { 
        	String _employeeNos[] = employeeNos.split(",");
        	for(String _employeeNo: _employeeNos) {
        		employeeDAO.DeleteEmployee(_employeeNo);
        	}
        	success = true;
        	message = _employeeNos.length + "条记录删除成功";
        	writeJsonResponse(success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(success, message);
        }
    }

    /*前台查询Employee信息*/
    public String FrontQueryEmployee() {
        if(page == 0) page = 1;
        if(employeeNo == null) employeeNo = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        List<Employee> employeeList = employeeDAO.QueryEmployeeInfo(employeeNo, name, departmentObj, birthday, telephone, page);
        /*计算总的页数和总的记录数*/
        employeeDAO.CalculateTotalPageAndRecordNumber(employeeNo, name, departmentObj, birthday, telephone);
        /*获取到总的页码数目*/
        totalPage = employeeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = employeeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("employeeList",  employeeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("page", page);
        ctx.put("employeeNo", employeeNo);
        ctx.put("name", name);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("birthday", birthday);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*查询要修改的Employee信息*/
    public String FrontShowEmployeeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键employeeNo获取Employee对象*/
        Employee employee = employeeDAO.GetEmployeeByEmployeeNo(employeeNo);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("employee",  employee);
        return "front_show_view";
    }

    /*删除Employee信息*/
    public String DeleteEmployee() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            employeeDAO.DeleteEmployee(employeeNo);
            ctx.put("message", "Employee删除成功!");
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error", "Employee删除失败!");
            return "error";
        }
    }

    /*后台导出到excel*/
    public String queryEmployeeOutputToExcel() { 
        if(employeeNo == null) employeeNo = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        List<Employee> employeeList = employeeDAO.QueryEmployeeInfo(employeeNo,name,departmentObj,birthday,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Employee信息记录"; 
        String[] headers = { "员工编号","姓名","性别","所在部门","出生日期","联系电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<employeeList.size();i++) {
        	Employee employee = employeeList.get(i); 
        	dataset.add(new String[]{employee.getEmployeeNo(),employee.getName(),employee.getSex(),employee.getDepartmentObj().getDepartmentName(),
employee.getBirthday(),employee.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Employee.xls");//filename是下载的xls的名，建议最好用英文 
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
