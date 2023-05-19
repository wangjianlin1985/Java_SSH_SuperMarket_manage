<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customer.css" /> 

<div id="customer_manage"></div>
<div id="customer_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="customer_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="customer_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="customer_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="customer_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="customer_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="customerQueryForm" method="post">
			客户名称：<input type="text" class="textbox" id="customerName" name="customerName" style="width:110px" />
			联系电话：<input type="text" class="textbox" id="telephone" name="telephone" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="customer_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="customerEditDiv">
	<form id="customerEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">客户id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_customerId_edit" name="customer.customerId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">客户名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_customerName_edit" name="customer.customerName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_telephone_edit" name="customer.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">收货地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_address_edit" name="customer.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">备注:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_memo_edit" name="customer.memo" style="width:200px" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Customer/js/customer_manage.js"></script> 
