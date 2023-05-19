<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.domain.Exchange" %>
<%@ page import="com.chengxusheji.domain.Product" %>
<%@ page import="com.chengxusheji.domain.Customer" %>
<%@ page import="com.chengxusheji.domain.Employee" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的productObj信息
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    //获取所有的customerObj信息
    List<Customer> customerList = (List<Customer>)request.getAttribute("customerList");
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    Exchange exchange = (Exchange)request.getAttribute("exchange");

%>
<!DOCTYPE html>
<html>
<head><TITLE>查看超市换货单</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:14px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</head>
<body>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding="10px" width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>超市换货单id:</td>
    <td width=70%><%=exchange.getExchangeId() %></td>
  </tr>

  <tr>
    <td width=30%>超市换货超市商品:</td>
    <td width=70%>
      <%
        for(Product product:productList) {
          String selected = "";
          if(product.getProductNo().equals(exchange.getProductObj().getProductNo())) {
      %>
      		<%=product.getProductName() %>
      <%
      		break;
          }
        }
      %>
    </td>
  </tr>
  <tr>
    <td width=30%>超市换货数量:</td>
    <td width=70%><%=exchange.getCount() %></td>
  </tr>

  <tr>
    <td width=30%>超市换货客户:</td>
    <td width=70%>
      <%
        for(Customer customer:customerList) {
          String selected = "";
          if(customer.getCustomerId() == exchange.getCustomerObj().getCustomerId()) {
      %>
      		<%=customer.getCustomerName() %>
      <%
      		break;
          }
        }
      %>
    </td>
  </tr>
  <tr>
    <td width=30%>超市换货日期:</td>
    <td width=70%><%=exchange.getExchangeDate() %></td>
  </tr>

  <tr>
    <td width=30%>处理结果:</td>
    <td width=70%><%=exchange.getHandleRes() %></td>
  </tr>

  <tr>
    <td width=30%>工作人员:</td>
    <td width=70%>
      <%
        for(Employee employee:employeeList) {
          String selected = "";
          if(employee.getEmployeeNo().equals(exchange.getEmployeeObj().getEmployeeNo())) {
      %>
      		<%=employee.getName() %>
      <%
      		break;
          }
        }
      %>
    </td>
  </tr>
  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</body>
</html>
