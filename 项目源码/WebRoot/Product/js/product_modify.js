$(function () {
	$.ajax({
		url : "Product/Product_ajaxModifyProductQuery.action",
		type : "post",
		data : {
			productNo : $("#product_productNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (product, response, status) {
			$.messager.progress("close");
			if (product) { 
				$("#product_productNo_edit").val(product.productNo);
				$("#product_productNo_edit").validatebox({
					required : true,
					missingMessage : "请输入超市商品编号",
					editable: false
				});
				$("#product_productClassObj_productClassId_edit").combobox({
					url:"../ProductClass/ProductClass_ajaxQueryAllProductClass.action",
					valueField:"productClassId",
					textField:"productClassName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#product_productClassObj_productClassId_edit").combobox("select", product.productClassObjPri);
						//var data = $("#product_productClassObj_productClassId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#product_productClassObj_productClassId_edit").combobox("select", data[0].productClassId);
						//}
					}
				});
				$("#product_productName_edit").val(product.productName);
				$("#product_productName_edit").validatebox({
					required : true,
					missingMessage : "请输入超市商品名称",
				});
				$("#product_productPhoto").val(product.productPhoto);
				$("#product_productPhotoImg").attr("src", "../" +　product.productPhoto);
				$("#product_productDesc_edit").val(product.productDesc);
				$("#product_productDesc_edit").validatebox({
					required : true,
					missingMessage : "请输入超市商品介绍",
				});
				$("#product_price_edit").val(product.price);
				$("#product_price_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入市场价",
					invalidMessage : "市场价输入不对",
				});
				$("#product_stockCount_edit").val(product.stockCount);
				$("#product_stockCount_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入超市商品库存",
					invalidMessage : "超市商品库存输入不对",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
			}
		}
	});

	$("#productModifyButton").click(function(){ 
		if ($("#productEditForm").form("validate")) {
			$("#productEditForm").form({
			    url:"Product/Product_ajaxModifyProduct.action",
			    onSubmit: function(){
					if($("#productEditForm").form("validate"))  {
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
                        location.href="Product_FrontQueryProduct.action";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#productEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
