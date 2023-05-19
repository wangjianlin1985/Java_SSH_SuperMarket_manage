$(function () {
	$.ajax({
		url : "Customer/Customer_ajaxModifyCustomerQuery.action",
		type : "post",
		data : {
			customerId : $("#customer_customerId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (customer, response, status) {
			$.messager.progress("close");
			if (customer) { 
				$("#customer_customerId_edit").val(customer.customerId);
				$("#customer_customerId_edit").validatebox({
					required : true,
					missingMessage : "请输入客户id",
					editable: false
				});
				$("#customer_customerName_edit").val(customer.customerName);
				$("#customer_customerName_edit").validatebox({
					required : true,
					missingMessage : "请输入客户名称",
				});
				$("#customer_telephone_edit").val(customer.telephone);
				$("#customer_telephone_edit").validatebox({
					required : true,
					missingMessage : "请输入联系电话",
				});
				$("#customer_address_edit").val(customer.address);
				$("#customer_address_edit").validatebox({
					required : true,
					missingMessage : "请输入收货地址",
				});
				$("#customer_memo_edit").val(customer.memo);
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
			}
		}
	});

	$("#customerModifyButton").click(function(){ 
		if ($("#customerEditForm").form("validate")) {
			$("#customerEditForm").form({
			    url:"Customer/Customer_ajaxModifyCustomer.action",
			    onSubmit: function(){
					if($("#customerEditForm").form("validate"))  {
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
                        location.href="Customer_FrontQueryCustomer.action";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#customerEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
