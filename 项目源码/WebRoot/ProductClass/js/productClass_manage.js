﻿var productClass_manage_tool = null; 
$(function () { 
	initProductClassManageTool(); //建立ProductClass管理对象
	productClass_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#productClass_manage").datagrid({
		url : 'ProductClass/ProductClass_ajaxQueryProductClass.action',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "productClassId",
		sortOrder : "desc",
		toolbar : "#productClass_manage_tool",
		columns : [[
			{
				field : "productClassId",
				title : "超市商品类别id",
				width : 70,
			},
			{
				field : "productClassName",
				title : "超市商品类别名称",
				width : 140,
			},
		]],
	});

	$("#productClassEditDiv").dialog({
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
				if ($("#productClassEditForm").form("validate")) {
					//验证表单 
					if(!$("#productClassEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#productClassEditForm").form({
						    url:"ProductClass/ProductClass_ajaxModifyProductClass.action",
						    onSubmit: function(){
								if($("#productClassEditForm").form("validate"))  {
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
			                        $("#productClassEditDiv").dialog("close");
			                        productClass_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#productClassEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#productClassEditDiv").dialog("close");
				$("#productClassEditForm").form("reset"); 
			},
		}],
	});
});

function initProductClassManageTool() {
	productClass_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#productClass_manage").datagrid("reload");
		},
		redo : function () {
			$("#productClass_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#productClass_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#productClassQueryForm").form({
			    url:"ProductClass/ProductClass_queryProductClassOutputToExcel.action",
			});
			//提交表单
			$("#productClassQueryForm").submit();
		},
		remove : function () {
			var rows = $("#productClass_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var productClassIds = [];
						for (var i = 0; i < rows.length; i ++) {
							productClassIds.push(rows[i].productClassId);
						}
						$.ajax({
							type : "POST",
							url : "ProductClass/ProductClass_ajaxDeleteProductClass.action",
							data : {
								productClassIds : productClassIds.join(","),
							},
							beforeSend : function () {
								$("#productClass_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#productClass_manage").datagrid("loaded");
									$("#productClass_manage").datagrid("load");
									$("#productClass_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#productClass_manage").datagrid("loaded");
									$("#productClass_manage").datagrid("load");
									$("#productClass_manage").datagrid("unselectAll");
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
			var rows = $("#productClass_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "ProductClass/ProductClass_ajaxModifyProductClassQuery.action",
					type : "post",
					data : {
						productClassId : rows[0].productClassId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (productClass, response, status) {
						$.messager.progress("close");
						if (productClass) { 
							$("#productClassEditDiv").dialog("open");
							$("#productClass_productClassId_edit").val(productClass.productClassId);
							$("#productClass_productClassId_edit").validatebox({
								required : true,
								missingMessage : "请输入超市商品类别id",
								editable: false
							});
							$("#productClass_productClassName_edit").val(productClass.productClassName);
							$("#productClass_productClassName_edit").validatebox({
								required : true,
								missingMessage : "请输入超市商品类别名称",
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
