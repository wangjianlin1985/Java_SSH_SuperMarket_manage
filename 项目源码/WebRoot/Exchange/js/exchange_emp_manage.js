var exchange_manage_tool = null; 
$(function () { 
	initExchangeManageTool(); //建立Exchange管理对象
	exchange_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#exchange_manage").datagrid({
		url : 'Exchange/Exchange_ajaxQueryExchange.action',
		queryParams: {
			"employeeObj.employeeNo": $("#employeeObj_employeeNo_query").val()
		},
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "exchangeId",
		sortOrder : "desc",
		toolbar : "#exchange_manage_tool",
		columns : [[
			{
				field : "exchangeId",
				title : "超市换货单id",
				width : 70,
			},
			{
				field : "productObj",
				title : "超市换货超市商品",
				width : 140,
			},
			{
				field : "count",
				title : "超市换货数量",
				width : 70,
			},
			{
				field : "customerObj",
				title : "超市换货客户",
				width : 140,
			},
			{
				field : "exchangeDate",
				title : "超市换货日期",
				width : 140,
			},
			{
				field : "employeeObj",
				title : "工作人员",
				width : 140,
			},
		]],
	});

	$("#exchangeEditDiv").dialog({
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
				if ($("#exchangeEditForm").form("validate")) {
					//验证表单 
					if(!$("#exchangeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#exchangeEditDiv").dialog("close");
			                        exchange_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#exchangeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#exchangeEditDiv").dialog("close");
				$("#exchangeEditForm").form("reset"); 
			},
		}],
	});
});

function initExchangeManageTool() {
	exchange_manage_tool = {
		init: function() {
			$.ajax({
				url : "Product/Product_ajaxQueryAllProduct.action",
				type : "post",
				success : function (data, response, status) {
					$("#productObj_productNo_query").combobox({ 
					    valueField:"productNo",
					    textField:"productName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{productNo:"",productName:"不限制"});
					$("#productObj_productNo_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "Customer/Customer_ajaxQueryAllCustomer.action",
				type : "post",
				success : function (data, response, status) {
					$("#customerObj_customerId_query").combobox({ 
					    valueField:"customerId",
					    textField:"customerName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{customerId:0,customerName:"不限制"});
					$("#customerObj_customerId_query").combobox("loadData",data); 
				}
			});
			 
		},
		reload : function () {
			$("#exchange_manage").datagrid("reload");
		},
		redo : function () {
			$("#exchange_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#exchange_manage").datagrid("options").queryParams;
			queryParams["productObj.productNo"] = $("#productObj_productNo_query").combobox("getValue");
			queryParams["customerObj.customerId"] = $("#customerObj_customerId_query").combobox("getValue");
			queryParams["exchangeDate"] = $("#exchangeDate").datebox("getValue"); 
			queryParams["employeeObj.employeeNo"] = $("#employeeObj_employeeNo_query").val();
			$("#exchange_manage").datagrid("options").queryParams=queryParams; 
			$("#exchange_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#exchangeQueryForm").form({
			    url:"Exchange/Exchange_queryExchangeOutputToExcel.action",
			});
			//提交表单
			$("#exchangeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#exchange_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var exchangeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							exchangeIds.push(rows[i].exchangeId);
						}
						$.ajax({
							type : "POST",
							url : "Exchange/Exchange_ajaxDeleteExchange.action",
							data : {
								exchangeIds : exchangeIds.join(","),
							},
							beforeSend : function () {
								$("#exchange_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#exchange_manage").datagrid("loaded");
									$("#exchange_manage").datagrid("load");
									$("#exchange_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#exchange_manage").datagrid("loaded");
									$("#exchange_manage").datagrid("load");
									$("#exchange_manage").datagrid("unselectAll");
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
			var rows = $("#exchange_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Exchange/Exchange_ajaxModifyExchangeQuery.action",
					type : "post",
					data : {
						exchangeId : rows[0].exchangeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (exchange, response, status) {
						$.messager.progress("close");
						if (exchange) { 
							$("#exchangeEditDiv").dialog("open");
							$("#exchange_exchangeId_edit").val(exchange.exchangeId);
							$("#exchange_exchangeId_edit").validatebox({
								required : true,
								missingMessage : "请输入超市换货单id",
								editable: false
							});
							$("#exchange_productObj_productNo_edit").combobox({
								url:"Product/Product_ajaxQueryAllProduct.action",
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
								url:"Customer/Customer_ajaxQueryAllCustomer.action",
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
								url:"Employee/Employee_ajaxQueryAllEmployee.action",
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
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
