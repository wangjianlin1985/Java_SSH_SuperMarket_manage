var product_manage_tool = null; 
$(function () { 
	initProductManageTool(); //建立Product管理对象
	product_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#product_manage").datagrid({
		url : 'Product/Product_ajaxQueryProduct.action',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "productNo",
		sortOrder : "desc",
		toolbar : "#product_manage_tool",
		columns : [[
			{
				field : "productNo",
				title : "超市商品编号",
				width : 140,
			},
			{
				field : "productClassObj",
				title : "超市商品类别",
				width : 140,
			},
			{
				field : "productName",
				title : "超市商品名称",
				width : 140,
			},
			{
				field : "productPhoto",
				title : "超市商品图片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "price",
				title : "市场价",
				width : 70,
			},
			{
				field : "stockCount",
				title : "超市商品库存",
				width : 70,
			},
		]],
	});

	$("#productEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#productEditForm").form("validate")) {
					//验证表单 
					if(!$("#productEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#productEditDiv").dialog("close");
			                        product_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#productEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#productEditDiv").dialog("close");
				$("#productEditForm").form("reset"); 
			},
		}],
	});
});

function initProductManageTool() {
	product_manage_tool = {
		init: function() {
			$.ajax({
				url : "ProductClass/ProductClass_ajaxQueryAllProductClass.action",
				type : "post",
				success : function (data, response, status) {
					$("#productClassObj_productClassId_query").combobox({ 
					    valueField:"productClassId",
					    textField:"productClassName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{productClassId:0,productClassName:"不限制"});
					$("#productClassObj_productClassId_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#product_manage").datagrid("reload");
		},
		redo : function () {
			$("#product_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#product_manage").datagrid("options").queryParams;
			queryParams["productNo"] = $("#productNo").val();
			queryParams["productClassObj.productClassId"] = $("#productClassObj_productClassId_query").combobox("getValue");
			queryParams["productName"] = $("#productName").val();
			$("#product_manage").datagrid("options").queryParams=queryParams; 
			$("#product_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#productQueryForm").form({
			    url:"Product/Product_queryProductOutputToExcel.action",
			});
			//提交表单
			$("#productQueryForm").submit();
		},
		remove : function () {
			var rows = $("#product_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var productNos = [];
						for (var i = 0; i < rows.length; i ++) {
							productNos.push(rows[i].productNo);
						}
						$.ajax({
							type : "POST",
							url : "Product/Product_ajaxDeleteProduct.action",
							data : {
								productNos : productNos.join(","),
							},
							beforeSend : function () {
								$("#product_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#product_manage").datagrid("loaded");
									$("#product_manage").datagrid("load");
									$("#product_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#product_manage").datagrid("loaded");
									$("#product_manage").datagrid("load");
									$("#product_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#product_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Product/Product_ajaxModifyProductQuery.action",
					type : "post",
					data : {
						productNo : rows[0].productNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (product, response, status) {
						$.messager.progress("close");
						if (product) { 
							$("#productEditDiv").dialog("open");
							$("#product_productNo_edit").val(product.productNo);
							$("#product_productNo_edit").validatebox({
								required : true,
								missingMessage : "请输入超市商品编号",
								editable: false
							});
							$("#product_productClassObj_productClassId_edit").combobox({
								url:"ProductClass/ProductClass_ajaxQueryAllProductClass.action",
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
							$("#product_productPhotoImg").attr("src", product.productPhoto);
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
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
