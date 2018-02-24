<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>仓库信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $warehouseTreeTable; // 父页面table表格id
		var $topIndex;//openDialog的 dialog index
		function doSubmit(treeTable, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $warehouseTreeTable = treeTable;
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
						jp.post("${ctx}/base/warehouse/save",$('#inputForm').serialize(),function(data){
		                    if(data.success){
		                    	var current_id = data.body.warehouse.id;
		                    	var target = $warehouseTreeTable.get(current_id);
		                    	var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
		                    	var current_parent_id = data.body.warehouse.parentId;
		                    	var current_parent_ids = data.body.warehouse.parentIds;
		                    	
		                    	if(old_parent_id == current_parent_id){
		                    		if(current_parent_id == '0'){
		                    			$warehouseTreeTable.refreshPoint(-1);
		                    		}else{
		                    			$warehouseTreeTable.refreshPoint(current_parent_id);
		                    		}
		                    	}else{
		                    		$warehouseTreeTable.del(current_id);//刷新删除旧节点
		                    		$warehouseTreeTable.initParents(current_parent_ids, "0");
		                    	}
		                    	
		                    	jp.success(data.msg);
		                    }else {
	            	  			jp.error(data.msg);
		                    }
		                    
		                        jp.close($topIndex);//关闭dialog
		            });;
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
		<form:form id="inputForm" modelAttribute="warehouse" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">仓库编码：</label></td>
					<td class="width-35">
						<form:input path="number" htmlEscape="false"    maxlength="64"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>仓库名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"     maxlength="64"  class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">父级编号：</label></td>
					<td class="width-35">
						<sys:treeselect id="parent" name="parent.id" value="${warehouse.parent.id}" labelName="parent.name" labelValue="${warehouse.parent.name}"
						title="父级编号" url="/base/warehouse/treeData" extId="${warehouse.id}" cssClass="form-control " allowClear="true"/>
					</td>
				<tr hidden>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		</form:form>
</body>
</html>