<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customer.css" />
<div id="customerAddDiv">
	<form id="customerAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">客户名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_customerName" name="customer.customerName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_telephone" name="customer.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">收货地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_address" name="customer.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">备注:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_memo" name="customer.memo" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="customerAddButton" class="easyui-linkbutton">添加</a>
			<a id="customerClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Customer/js/customer_add.js"></script> 
