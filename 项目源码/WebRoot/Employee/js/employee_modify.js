$(function () {
	$.ajax({
		url : "Employee/Employee_ajaxModifyEmployeeQuery.action",
		type : "post",
		data : {
			employeeNo : $("#employee_employeeNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (employee, response, status) {
			$.messager.progress("close");
			if (employee) { 
				$("#employee_employeeNo_edit").val(employee.employeeNo);
				$("#employee_employeeNo_edit").validatebox({
					required : true,
					missingMessage : "请输入员工编号",
					editable: false
				});
				$("#employee_password_edit").val(employee.password);
				$("#employee_password_edit").validatebox({
					required : true,
					missingMessage : "请输入登录密码",
				});
				$("#employee_name_edit").val(employee.name);
				$("#employee_name_edit").validatebox({
					required : true,
					missingMessage : "请输入姓名",
				});
				$("#employee_sex_edit").val(employee.sex);
				$("#employee_sex_edit").validatebox({
					required : true,
					missingMessage : "请输入性别",
				});
				$("#employee_departmentObj_departmentNo_edit").combobox({
					url:"Department/Department_ajaxQueryAllDepartment.action",
					valueField:"departmentNo",
					textField:"departmentName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#employee_departmentObj_departmentNo_edit").combobox("select", employee.departmentObjPri);
						//var data = $("#employee_departmentObj_departmentNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#employee_departmentObj_departmentNo_edit").combobox("select", data[0].departmentNo);
						//}
					}
				});
				$("#employee_birthday_edit").datebox({
					value: employee.birthday,
					required: true,
					showSeconds: true,
				});
				$("#employee_telephone_edit").val(employee.telephone);
				$("#employee_telephone_edit").validatebox({
					required : true,
					missingMessage : "请输入联系电话",
				});
				$("#employee_employeeDesc_edit").val(employee.employeeDesc);
				$("#employee_employeeDesc_edit").validatebox({
					required : true,
					missingMessage : "请输入员工简介",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
			}
		}
	});

	$("#employeeModifyButton").click(function(){ 
		if ($("#employeeEditForm").form("validate")) {
			$("#employeeEditForm").form({
			    url:"Employee/Employee_ajaxModifyEmployee.action",
			    onSubmit: function(){
					if($("#employeeEditForm").form("validate"))  {
	                	$.messager.progress({
							text : "正在提交数据中...",
						});
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        //location.href="Employee_FrontQueryEmployee.action";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#employeeEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
