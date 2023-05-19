$(function () {
	$("#product_productNo").validatebox({
		required : true, 
		missingMessage : '请输入超市商品编号',
	});

	$("#product_productClassObj_productClassId").combobox({
	    url:'ProductClass/ProductClass_ajaxQueryAllProductClass.action',
	    valueField: "productClassId",
	    textField: "productClassName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#product_productClassObj_productClassId").combobox("getData"); 
            if (data.length > 0) {
                $("#product_productClassObj_productClassId").combobox("select", data[0].productClassId);
            }
        }
	});
	$("#product_productName").validatebox({
		required : true, 
		missingMessage : '请输入超市商品名称',
	});

	$("#product_productDesc").validatebox({
		required : true, 
		missingMessage : '请输入超市商品介绍',
	});

	$("#product_price").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入市场价',
		invalidMessage : '市场价输入不对',
	});

	$("#product_stockCount").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入超市商品库存',
		invalidMessage : '超市商品库存输入不对',
	});

	//单击添加按钮
	$("#productAddButton").click(function () {
		//验证表单 
		if(!$("#productAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		} else {
			$("#productAddForm").form({
			    url:"Product/Product_ajaxAddProduct.action",
			    onSubmit: function(){
					if($("#productAddForm").form("validate"))  { 
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
                        $("#productAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                    }
			    }
			});
			//提交表单
			$("#productAddForm").submit();
		}
	});

	//单击清空按钮
	$("#productClearButton").click(function () { 
		$("#productAddForm").form("clear"); 
	});
});
