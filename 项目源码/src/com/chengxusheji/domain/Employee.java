package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Employee {
    /*员工编号*/
    private String employeeNo;
    public String getEmployeeNo() {
        return employeeNo;
    }
    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*所在部门*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*出生日期*/
    private String birthday;
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*员工简介*/
    private String employeeDesc;
    public String getEmployeeDesc() {
        return employeeDesc;
    }
    public void setEmployeeDesc(String employeeDesc) {
        this.employeeDesc = employeeDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonEmployee=new JSONObject(); 
		jsonEmployee.accumulate("employeeNo", this.getEmployeeNo());
		jsonEmployee.accumulate("password", this.getPassword());
		jsonEmployee.accumulate("name", this.getName());
		jsonEmployee.accumulate("sex", this.getSex());
		jsonEmployee.accumulate("departmentObj", this.getDepartmentObj().getDepartmentName());
		jsonEmployee.accumulate("departmentObjPri", this.getDepartmentObj().getDepartmentNo());
		jsonEmployee.accumulate("birthday", this.getBirthday().length()>19?this.getBirthday().substring(0,19):this.getBirthday());
		jsonEmployee.accumulate("telephone", this.getTelephone());
		jsonEmployee.accumulate("employeeDesc", this.getEmployeeDesc());
		return jsonEmployee;
    }}