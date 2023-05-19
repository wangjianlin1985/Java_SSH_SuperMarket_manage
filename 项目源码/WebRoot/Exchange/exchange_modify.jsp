<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_user_logstate.jsp"/>
<!DOCTYPE html>
<html>
<head>
<title>修改页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/exchange.css" />
</head>
<body style="margin:0px; font-size:14px; background-image:url(../images/bg.jpg); background-position:bottom; background-repeat:repeat;">
<div id="exchange_editDiv">
	<form id="exchangeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">超市换货单id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_exchangeId_edit" name="exchange.exchangeId" value="<%=request.getParameter("exchangeId") %>" style="width:200px" />
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
		<div class="operation">
			<a id="exchangeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" ></script>
<script src="${pageContext.request.contextPath}/Exchange/js/exchange_modify.js"></script> 
</body>
</html>
