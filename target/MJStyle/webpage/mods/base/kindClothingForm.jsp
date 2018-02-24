<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>样衣基础信息管理</title>
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
					jp.post("${ctx}/base/kindClothing/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="kindClothing" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">样衣图片：</label></td>
					<td class="width-35">
						<form:hidden id="pictures" path="pictures" htmlEscape="false" maxlength="8000" class="form-control"/>
						<sys:ckfinder input="pictures" type="images" uploadPath="/base/kindClothing" selectMultiple="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>样衣条码：</label></td>
					<td class="width-35">
						<form:input path="number" htmlEscape="false"  maxlength="64"  class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">品名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    maxlength="64"   class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">风格：</label></td>
					<td class="width-35">
						<form:input path="style" htmlEscape="false"    maxlength="64"   class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>版单号：</label></td>
					<td class="width-35">
						<form:input path="editionNumber" htmlEscape="false"     maxlength="64"  class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">供应商：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/supplier/data1/2" id="supplier" name="supplier.id" value="${kindClothing.supplier.id}" labelName="supplier.name" labelValue="${kindClothing.supplier.name}"
							isMultiSelected="false" title="选择供应商" cssClass="form-control" fieldLabels="供应商名称|供应商编码|联系人|联系电话" fieldKeys="name|number|contacts|phone" searchLabels="供应商名称|供应商编码|联系人|联系电话" searchKeys="name|number|contacts|phone" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>颜色：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/colors/data" id="colour" name="colour.id" value="${kindClothing.colour.id}" labelName="colour.name" labelValue="${kindClothing.colour.name}"
										isMultiSelected="false" title="选择颜色" cssClass="form-control required" fieldLabels="颜色名称|颜色编码" fieldKeys="name|number" searchLabels="颜色名称|颜色编码" searchKeys="name|number" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">大类：</label></td>
					<td class="width-35" >
						<sys:gridselect url="${ctx}/base/maxClass/data" id="maxClass" name="maxClass.id" value="${kindClothing.maxClass.id}" labelName="maxClass.name" labelValue="${kindClothing.maxClass.name}"
										isMultiSelected="false" title="选择大类" cssClass="form-control" fieldLabels="大类名称|大类代码" fieldKeys="name|code" searchLabels="大类名称|大类代码" searchKeys="name|code" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">尺码：</label></td>
					<td class="width-35" >
						<sys:gridselect url="${ctx}/base/sizes/data" id="sizes" name="sizes.id" value="${kindClothing.sizes.id}" labelName="sizes.name" labelValue="${kindClothing.sizes.name}"
										isMultiSelected="false" title="选择尺码" cssClass="form-control" fieldLabels="尺码名称|尺码代码" fieldKeys="name|code" searchLabels="尺码名称|尺码代码" searchKeys="name|code" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">季节：</label></td>
					<td class="width-35">
						<form:input path="season" htmlEscape="false"    maxlength="64"   class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">年份：</label></td>
					<td class="width-35">
						<form:input path="year" htmlEscape="false"    maxlength="64"   class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">仓位：</label></td>
					<td class="width-35">
						<sys:treeselect id="warehouse" name="warehouse.id" value="${kindClothing.warehouse.id}" labelName="warehouse.name" labelValue="${kindClothing.warehouse.name}"
							title="仓位" url="/base/warehouse/treeData" extId="${kindClothing.id}" cssClass="form-control" allowClear="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
					<td class="width-35" ></td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>