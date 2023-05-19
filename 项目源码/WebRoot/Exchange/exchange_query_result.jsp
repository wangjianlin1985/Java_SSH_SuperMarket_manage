﻿<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/exchange.css" /> 

<div id="exchange_manage"></div>
<div id="exchange_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="exchange_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="exchange_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="exchange_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="exchange_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exchange_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="exchangeQueryForm" method="post">
			超市换货超市商品：<input class="textbox" type="text" id="productObj_productNo_query" name="productObj.productNo" style="width: auto"/>
			超市换货客户：<input class="textbox" type="text" id="customerObj_customerId_query" name="customerObj.customerId" style="width: auto"/>
			超市换货日期：<input type="text" id="exchangeDate" name="exchangeDate" class="easyui-datebox" editable="false" style="width:100px">
			工作人员：<input class="textbox" type="text" id="employeeObj_employeeNo_query" name="employeeObj.employeeNo" style="width: auto"/>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="exchange_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="exchangeEditDiv">
	<form id="exchangeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">超市换货单id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_exchangeId_edit" name="exchange.exchangeId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">超市换货超市商品:</span>
			<span class="inputControl">
				<input class="textbox"  id="exchange_productObj_productNo_edit" name="exchange.productObj.productNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">超市换货数量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_count_edit" name="exchange.count" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">超市换货客户:</span>
			<span class="inputControl">
				<input class="textbox"  id="exchange_customerObj_customerId_edit" name="exchange.customerObj.customerId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">超市换货日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_exchangeDate_edit" name="exchange.exchangeDate" />

			</span>

		</div>
		<div>
			<span class="label">处理结果:</span>
			<span class="inputControl">
				<textarea id="exchange_handleRes_edit" name="exchange.handleRes" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">工作人员:</span>
			<span class="inputControl">
				<input class="textbox"  id="exchange_employeeObj_employeeNo_edit" name="exchange.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
	</form>
</div>
<script type="text/javascript" src="Exchange/js/exchange_manage.js"></script> 
