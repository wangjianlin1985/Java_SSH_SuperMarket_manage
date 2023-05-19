<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css" /> 

<div id="product_manage"></div>
<div id="product_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="product_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="product_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="product_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="product_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="product_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="productQueryForm" method="post">
			超市商品编号：<input type="text" class="textbox" id="productNo" name="productNo" style="width:110px" />
			超市商品类别：<input class="textbox" type="text" id="productClassObj_productClassId_query" name="productClassObj.productClassId" style="width: auto"/>
			超市商品名称：<input type="text" class="textbox" id="productName" name="productName" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="product_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="productEditDiv">
	<form id="productEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">超市商品编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productNo_edit" name="product.productNo" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">超市商品类别:</span>
			<span class="inputControl">
				<input class="textbox"  id="product_productClassObj_productClassId_edit" name="product.productClassObj.productClassId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">超市商品名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productName_edit" name="product.productName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">超市商品图片:</span>
			<span class="inputControl">
				<img id="product_productPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="product_productPhoto" name="product.productPhoto"/>
				<input id="productPhotoFile" name="productPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">超市商品介绍:</span>
			<span class="inputControl">
				<textarea id="product_productDesc_edit" name="product.productDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">市场价:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_price_edit" name="product.price" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">超市商品库存:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_stockCount_edit" name="product.stockCount" style="width:80px" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Product/js/product_manage.js"></script> 
