$(function () {
	$.ajax({
		url : "Supplier/Supplier_ajaxModifySupplierQuery.action",
		type : "post",
		data : {
			supplierId : $("#supplier_supplierId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (supplier, response, status) {
			$.messager.progress("close");
			if (supplier) { 
				$("#supplier_supplierId_edit").val(supplier.supplierId);
				$("#supplier_supplierId_edit").validatebox({
					required : true,
					missingMessage : "请输入供应商id",
					editable: false
				});
				$("#supplier_supplierName_edit").val(supplier.supplierName);
				$("#supplier_supplierName_edit").validatebox({
					required : true,
					missingMessage : "请输入供应商名称",
				});
				$("#supplier_connectPerson_edit").val(supplier.connectPerson);
				$("#supplier_connectPerson_edit").validatebox({
					required : true,
					missingMessage : "请输入联系人",
				});
				$("#supplier_telephone_edit").val(supplier.telephone);
				$("#supplier_telephone_edit").validatebox({
					required : true,
					missingMessage : "请输入联系电话",
				});
				$("#supplier_fax_edit").val(supplier.fax);
				$("#supplier_fax_edit").validatebox({
					required : true,
					missingMessage : "请输入传真",
				});
				$("#supplier_address_edit").val(supplier.address);
				$("#supplier_address_edit").validatebox({
					required : true,
					missingMessage : "请输入供应商地址",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
			}
		}
	});

	$("#supplierModifyButton").click(function(){ 
		if ($("#supplierEditForm").form("validate")) {
			$("#supplierEditForm").form({
			    url:"Supplier/Supplier_ajaxModifySupplier.action",
			    onSubmit: function(){
					if($("#supplierEditForm").form("validate"))  {
	                	$.messager.progress({
							text : "正在提交数据中...",
						});
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        location.href="Supplier_FrontQuerySupplier.action";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#supplierEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
