<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_employee_logstate.jsp"/>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee.css" />
 
<div id="employee_editDiv">
	<form id="employeeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">员工编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_employeeNo_edit" name="employee.employeeNo" value="<%=session.getAttribute("employee") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_password_edit" name="employee.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_name_edit" name="employee.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_sex_edit" name="employee.sex" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">所在部门:</span>
			<span class="inputControl">
				<input class="textbox"  id="employee_departmentObj_departmentNo_edit" name="employee.departmentObj.departmentNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_birthday_edit" name="employee.birthday" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_telephone_edit" name="employee.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">员工简介:</span>
			<span class="inputControl">
				<textarea id="employee_employeeDesc_edit" name="employee.employeeDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="employeeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div> 
<script src="${pageContext.request.contextPath}/Employee/js/employee_modify.js"></script> 
 
