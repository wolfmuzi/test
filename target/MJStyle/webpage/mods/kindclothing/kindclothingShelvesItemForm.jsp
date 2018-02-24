<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title> 样衣库存清单管理</title>
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
					jp.post("${ctx}/kindclothing/kindclothingShelvesItem/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="kindclothingShelvesItem"  class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣条码：</label></td>
					<td class="width-35">
						<form:input path="barCode" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣标签号：</label></td>
					<td class="width-35">
						<form:input path="epc" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣品名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>供应商：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/supplier/data" id="supplier" name="supplier.id" value="${kindclothingShelvesItem.supplier.id}" labelName="supplier.name" labelValue="${kindclothingShelvesItem.supplier.name}"
							isMultiSelected="false" title="选择供应商" cssClass="form-control required" fieldLabels="供应商名称|供应商编码|联系人|联系电话" fieldKeys="name|number|contacts|phone" searchLabels="供应商名称|供应商编码|联系人|联系电话" searchKeys="name|number|contacts|phone" ></sys:gridselect>
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
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣上架单：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/kindclothing/kindclothingShelves/data" id="kindclothingShelves" name="kindclothingShelves.id" value="${kindclothingShelvesItem.kindclothingShelves.id}" labelName="kindclothingShelves.code" labelValue="${kindclothingShelvesItem.kindclothingShelves.code}"
							isMultiSelected="false" title="选择样衣上架单id" cssClass="form-control required" fieldLabels="上架单号" fieldKeys="code" searchLabels="上架单号" searchKeys="code" ></sys:gridselect>
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