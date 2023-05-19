<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_user_logstate.jsp"/>
<!DOCTYPE html>
<html>
<head>
<title>修改页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customer.css" />
</head>
<body style="margin:0px; font-size:14px; background-image:url(../images/bg.jpg); background-position:bottom; background-repeat:repeat;">
<div id="customer_editDiv">
	<form id="customerEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">客户id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="customer_customerId_edit" name="customer.customerId" value="<%=request.getParameter("customerId") %>" style="width:200px" />
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
		<div class="operation">
			<a id="customerModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" ></script>
<script src="${pageContext.request.contextPath}/Customer/js/customer_modify.js"></script> 
</body>
</html>
