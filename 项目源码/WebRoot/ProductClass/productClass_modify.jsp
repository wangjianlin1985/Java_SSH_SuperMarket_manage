<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_user_logstate.jsp"/>
<!DOCTYPE html>
<html>
<head>
<title>修改页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productClass.css" />
</head>
<body style="margin:0px; font-size:14px; background-image:url(../images/bg.jpg); background-position:bottom; background-repeat:repeat;">
<div id="productClass_editDiv">
	<form id="productClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">超市商品类别id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productClass_productClassId_edit" name="productClass.productClassId" value="<%=request.getParameter("productClassId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">超市商品类别名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productClass_productClassName_edit" name="productClass.productClassName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="productClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" ></script>
<script src="${pageContext.request.contextPath}/ProductClass/js/productClass_modify.js"></script> 
</body>
</html>
