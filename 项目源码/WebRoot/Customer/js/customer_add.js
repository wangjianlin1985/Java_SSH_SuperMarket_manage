$(function () {
	$("#customer_customerName").validatebox({
		required : true, 
		missingMessage : '请输入客户名称',
	});

	$("#customer_telephone").validatebox({
		required : true, 
		missingMessage : '请输入联系电话',
	});

	$("#customer_address").validatebox({
		required : true, 
		missingMessage : '请输入收货地址',
	});

	//单击添加按钮
	$("#customerAddButton").click(function () {
		//验证表单 
		if(!$("#customerAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		} else {
			$("#customerAddForm").form({
			    url:"Customer/Customer_ajaxAddCustomer.action",
			    onSubmit: function(){
					if($("#customerAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $("#customerAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                    }
			    }
			});
			//提交表单
			$("#customerAddForm").submit();
		}
	});

	//单击清空按钮
	$("#customerClearButton").click(function () { 
		$("#customerAddForm").form("clear"); 
	});
});
