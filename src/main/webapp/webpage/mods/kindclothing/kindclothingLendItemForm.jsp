<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>样衣借用详情管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/kindclothing/kindclothingLendItem/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="kindclothingLendItem"  class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>借用人：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/baseCommonRole/data" id="baseCommonRole" name="baseCommonRole.id" value="${kindclothingLendItem.baseCommonRole.id}" labelName="baseCommonRole.name" labelValue="${kindclothingLendItem.baseCommonRole.name}"
							isMultiSelected="false" title="选择借用人" cssClass="form-control required" fieldLabels="名字|类型" fieldKeys="name|type" searchLabels="名字|类型" searchKeys="name|type" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>借用单号：</label></td>
					<td class="width-35">
						<form:input path="lendNo" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣品名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣标签：</label></td>
					<td class="width-35">
						<form:input path="barCode" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>风格：</label></td>
					<td class="width-35">
						<form:input path="style" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>版单号：</label></td>
					<td class="width-35">
						<form:input path="editionnumber" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>供应商：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/supplier/data" id="supplier" name="supplier.id" value="${kindclothingLendItem.supplier.id}" labelName="supplier.name" labelValue="${kindclothingLendItem.supplier.name}"
							isMultiSelected="false" title="选择供应商" cssClass="form-control required" fieldLabels="供应商名称|供应商编码|联系人|联系电话" fieldKeys="name|number|contacts|phone" searchLabels="供应商名称|供应商编码|联系人|联系电话" searchKeys="name|number|contacts|phone" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>