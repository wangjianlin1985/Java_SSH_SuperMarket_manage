$(function () {
	$.ajax({
		url : "Department/Department_ajaxModifyDepartmentQuery.action",
		type : "post",
		data : {
			departmentNo : $("#department_departmentNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (department, response, status) {
			$.messager.progress("close");
			if (department) { 
				$("#department_departmentNo_edit").val(department.departmentNo);
				$("#department_departmentNo_edit").validatebox({
					required : true,
					missingMessage : "请输入部门编号",
					editable: false
				});
				$("#department_departmentName_edit").val(department.departmentName);
				$("#department_departmentName_edit").validatebox({
					required : true,
					missingMessage : "请输入部门名称",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
			}
		}
	});

	$("#departmentModifyButton").click(function(){ 
		if ($("#departmentEditForm").form("validate")) {
			$("#departmentEditForm").form({
			    url:"Department/Department_ajaxModifyDepartment.action",
			    onSubmit: function(){
					if($("#departmentEditForm").form("validate"))  {
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
                        location.href="Department_FrontQueryDepartment.action";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#departmentEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
