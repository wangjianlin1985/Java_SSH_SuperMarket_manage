<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_employee_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/exchange.css" />
<div id="exchangeAddDiv">
	<form id="exchangeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">超市换货超市商品:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_productObj_productNo" name="exchange.productObj.productNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">超市换货数量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_count" name="exchange.count" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">超市换货客户:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_customerObj_customerId" name="exchange.customerObj.customerId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">超市换货日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_exchangeDate" name="exchange.exchangeDate" />

			</span>

		</div>
		<div>
			<span class="label">处理结果:</span>
			<span class="inputControl">
				<textarea id="exchange_handleRes" name="exchange.handleRes" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div style="display:none;">
			<span class="label">工作人员:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="exchange_employeeObj_employeeNo" name="exchange.employeeObj.employeeNo" value="<%=session.getAttribute("employee") %>" style="width: auto"/>
			</span>
		</div>
		<div class="operation">
			<a id="exchangeAddButton" class="easyui-linkbutton">添加</a>
			<a id="exchangeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Exchange/js/exchange_empAdd.js"></script> 
