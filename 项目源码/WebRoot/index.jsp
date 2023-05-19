<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>基于H5的衣服仓储系统的设计与实现-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>ProductClass/ProductClass_FrontQueryProductClass.action" target="OfficeMain">超市商品类别</a></li> 
			<li><a href="<%=basePath %>Product/Product_FrontQueryProduct.action" target="OfficeMain">超市商品</a></li> 
			<li><a href="<%=basePath %>Department/Department_FrontQueryDepartment.action" target="OfficeMain">部门</a></li> 
			<li><a href="<%=basePath %>Employee/Employee_FrontQueryEmployee.action" target="OfficeMain">员工</a></li> 
			<li><a href="<%=basePath %>Purchase/Purchase_FrontQueryPurchase.action" target="OfficeMain">进货</a></li> 
			<li><a href="<%=basePath %>Sale/Sale_FrontQuerySale.action" target="OfficeMain">订单</a></li> 
			<li><a href="<%=basePath %>Supplier/Supplier_FrontQuerySupplier.action" target="OfficeMain">供应商</a></li> 
			<li><a href="<%=basePath %>Customer/Customer_FrontQueryCustomer.action" target="OfficeMain">客户</a></li> 
			<li><a href="<%=basePath %>Exchange/Exchange_FrontQueryExchange.action" target="OfficeMain">超市换货单</a></li> 
			<li><a href="<%=basePath %>Notice/Notice_FrontQueryNotice.action" target="OfficeMain">公告通知</a></li> 
		</ul>
		<br />
	</div> 

	<div id="loginBar">
	  <%
	  	String user_name = (String)session.getAttribute("user_name");
	    if(user_name==null){
	  %>
	  <form action="<%=basePath %>login/login_CheckFrontLogin.action" style="height:25px;margin:0px 0px 2px 0px;" method="post">
		用户名：<input type=text name="userName" size="12"/>&nbsp;&nbsp;
		密码：<input type=password name="password" size="12"/>&nbsp;&nbsp;
		<input type=submit value="登录" />
	  </form>
	  <%} else { %>
	    <ul>
	    	<li><a href="<%=basePath %>UserInfo/UserInfo_SelfModifyUserInfoQuery.action?user_name=<%=user_name %>" target="OfficeMain">【修改个人信息】</a></li>
	    	<li><a href="<%=basePath %>login/login_LoginOut.action">【退出登陆】</a></li>
	    </ul>
	 <% } %>
	</div> 

	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>&nbsp;&nbsp;<a href="<%=basePath%>login.jsp"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
