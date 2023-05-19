$(function () {
	$.ajax({
		url : "Exchange/Exchange_ajaxModifyExchangeQuery.action",
		type : "post",
		data : {
			exchangeId : $("#exchange_exchangeId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (exchange, response, status) {
			$.messager.progress("close");
			if (exchange) { 
				$("#exchange_exchangeId_edit").val(exchange.exchangeId);
				$("#exchange_exchangeId_edit").validatebox({
					required : true,
					missingMessage : "请输入超市换货单id",
					editable: false
				});
				$("#exchange_productObj_productNo_edit").combobox({
					url:"../Product/Product_ajaxQueryAllProduct.action",
					valueField:"productNo",
					textField:"productName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#exchange_productObj_productNo_edit").combobox("select", exchange.productObjPri);
						//var data = $("#exchange_productObj_productNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#exchange_productObj_productNo_edit").combobox("select", data[0].productNo);
						//}
					}
				});
				$("#exchange_count_edit").val(exchange.count);
				$("#exchange_count_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入超市换货数量",
					invalidMessage : "超市换货数量输入不对",
				});
				$("#exchange_customerObj_customerId_edit").combobox({
					url:"../Customer/Customer_ajaxQueryAllCustomer.action",
					valueField:"customerId",
					textField:"customerName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#exchange_customerObj_customerId_edit").combobox("select", exchange.customerObjPri);
						//var data = $("#exchange_customerObj_customerId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#exchange_customerObj_customerId_edit").combobox("select", data[0].customerId);
						//}
					}
				});
				$("#exchange_exchangeDate_edit").datebox({
					value: exchange.exchangeDate,
					required: true,
					showSeconds: true,
				});
				$("#exchange_handleRes_edit").val(exchange.handleRes);
				$("#exchange_handleRes_edit").validatebox({
					required : true,
					missingMessage : "请输入处理结果",
				});
				$("#exchange_employeeObj_employeeNo_edit").combobox({
					url:"../Employee/Employee_ajaxQueryAllEmployee.action",
					valueField:"employeeNo",
					textField:"name",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#exchange_employeeObj_employeeNo_edit").combobox("select", exchange.employeeObjPri);
						//var data = $("#exchange_employeeObj_employeeNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#exchange_employeeObj_employeeNo_edit").combobox("select", data[0].employeeNo);
						//}
					}
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
			}
		}
	});

	$("#exchangeModifyButton").click(function(){ 
		if ($("#exchangeEditForm").form("validate")) {
			$("#exchangeEditForm").form({
			    url:"Exchange/Exchange_ajaxModifyExchange.action",
			    onSubmit: function(){
					if($("#exchangeEditForm").form("validate"))  {
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
                        location.href="Exchange_FrontQueryExchange.action";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#exchangeEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
