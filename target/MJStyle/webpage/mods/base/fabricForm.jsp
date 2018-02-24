<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>面料信息管理</title>
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
					jp.post("${ctx}/base/fabric/save",$('#inputForm').serialize(),function(data){
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
			
	        $('#purchaseTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="fabric" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active" ><label class="pull-right"><font color="red">*</font>面料条码：</label></td>
					<td class="width-35" >
						<form:input path="barcode" htmlEscape="false"   maxlength="64"   class="form-control  required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">品名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"     maxlength="64"  class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>货号：</label></td>
					<td class="width-35">
						<form:input path="number" htmlEscape="false"    maxlength="64"   class="form-control required "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>供应商：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/supplier/data1/1" id="supplier" name="supplier.id" value="${fabric.supplier.id}" labelName="supplier.name" labelValue="${fabric.supplier.name}"
										isMultiSelected="false"  title="选择供应商" cssClass="form-control required" fieldLabels="供应商编码|供应商名称|联系人" fieldKeys="number|name|contacts" searchLabels="供应商编码|供应商名称|联系人" searchKeys="number|name|contacts" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">颜色：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/base/colors/data" id="color" name="color.id" value="${fabric.color.id}" labelName="color.name" labelValue="${fabric.color.name}"
										isMultiSelected="false" title="选择颜色" cssClass="form-control" fieldLabels="颜色编码|颜色名称" fieldKeys="number|name" searchLabels="颜色编码|颜色名称" searchKeys="number|name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">克重：</label></td>
					<td class="width-35">
						<form:input path="weight" htmlEscape="false"    maxlength="64"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">幅宽：</label></td>
					<td class="width-35">
						<form:input path="width" htmlEscape="false"    maxlength="64"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">纱织规格：</label></td>
					<td class="width-35">
						<form:input path="spec" htmlEscape="false"    maxlength="64"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>成分：</label></td>
					<td class="width-35">
						<form:input path="ingredients" htmlEscape="false"    maxlength="64"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">价格(含税)：</label></td>
					<td class="width-35">
						<form:input path="price01" htmlEscape="false"  type="number" maxlength="64"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">价格(不含税)：</label></td>
					<td class="width-35">
						<form:input path="price02" htmlEscape="false" type="number"   maxlength="64"   class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">缩水率：</label></td>
					<td class="width-35">
						<form:input path="shrinkage" htmlEscape="false"    maxlength="64"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">公斤量化：</label></td>
					<td class="width-35">
						<form:input path="kg" htmlEscape="false"    maxlength="64"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>仓位：</label></td>
					<td class="width-35">
						<sys:treeselect id="warehouse" name="warehouse.id" value="${fabric.warehouse.id}" labelName="warehouse.name" labelValue="${fabric.warehouse.name}"
							title="仓位" url="/base/warehouse/treeData" extId="${fabric.id}" cssClass="form-control required" allowClear="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">采购时间：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='purchaseTime'>
			                    <input type='text'  name="purchaseTime" class="form-control"  value="<fmt:formatDate value="${fabric.purchaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
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