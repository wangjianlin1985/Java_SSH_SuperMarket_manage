<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_user_logstate.jsp"/>
<!DOCTYPE html>
<html>
<head>
<title>修改页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/supplier.css" />
</head>
<body style="margin:0px; font-size:14px; background-image:url(../images/bg.jpg); background-position:bottom; background-repeat:repeat;">
<div id="supplier_editDiv">
	<form id="supplierEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">供应商id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="supplier_supplierId_edit" name="supplier.supplierId" value="<%=request.getParameter("supplierId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">供应商名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="supplier_supplierName_edit" name="supplier.supplierName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系人:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="supplier_connectPerson_edit" name="supplier.connectPerson" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="supplier_telephone_edit" name="supplier.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">传真:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="supplier_fax_edit" name="supplier.fax" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">供应商地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="supplier_address_edit" name="supplier.address" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="supplierModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" ></script>
<script src="${pageContext.request.contextPath}/Supplier/js/supplier_modify.js"></script> 
</body>
</html>
