$(function () {
	$("#exchange_productObj_productNo").combobox({
	    url:'Product/Product_ajaxQueryAllProduct.action',
	    valueField: "productNo",
	    textField: "productName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#exchange_productObj_productNo").combobox("getData"); 
            if (data.length > 0) {
                $("#exchange_productObj_productNo").combobox("select", data[0].productNo);
            }
        }
	});
	$("#exchange_count").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入超市换货数量',
		invalidMessage : '超市换货数量输入不对',
	});

	$("#exchange_customerObj_customerId").combobox({
	    url:'Customer/Customer_ajaxQueryAllCustomer.action',
	    valueField: "customerId",
	    textField: "customerName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#exchange_customerObj_customerId").combobox("getData"); 
            if (data.length > 0) {
                $("#exchange_customerObj_customerId").combobox("select", data[0].customerId);
            }
        }
	});
	$("#exchange_exchangeDate").datebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#exchange_handleRes").validatebox({
		required : true, 
		missingMessage : '请输入处理结果',
	});

	 
	//单击添加按钮
	$("#exchangeAddButton").click(function () {
		//验证表单 
		if(!$("#exchangeAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		} else {
			$("#exchangeAddForm").form({
			    url:"Exchange/Exchange_ajaxAddExchange.action",
			    onSubmit: function(){
					if($("#exchangeAddForm").form("validate"))  { 
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
                        $("#exchangeAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                    }
			    }
			});
			//提交表单
			$("#exchangeAddForm").submit();
		}
	});

	//单击清空按钮
	$("#exchangeClearButton").click(function () { 
		$("#exchangeAddForm").form("clear"); 
	});
});
