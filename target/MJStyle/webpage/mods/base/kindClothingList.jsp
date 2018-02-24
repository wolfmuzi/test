<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>样衣基础信息管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="kindClothingList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">样衣基础信息管理</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="kindClothing" class="form form-horizontal well clearfix" onkeypress="onEnterSearch(event,'kindClothingTable')">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="样衣编码：">样衣条码：</label>
				<form:input path="number" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="品名：">品名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="风格：">风格：</label>
				<form:input path="style" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="版单号：">版单号：</label>
				<form:input path="editionNumber" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="供应商：">供应商：</label>
				<sys:gridselect url="${ctx}/base/supplier/data1/2" id="supplier" name="supplier.id" value="${kindClothing.supplier.id}" labelName="supplier.name" labelValue="${kindClothing.supplier.name}"
								isMultiSelected="false" title="选择供应商" cssClass="form-control required" fieldLabels="供应商名称|供应商编码|联系人|联系电话" fieldKeys="name|number|contacts|phone" searchLabels="供应商名称|供应商编码|联系人|联系电话" searchKeys="name|number|contacts|phone" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="仓位：">仓位：</label>
				<sys:treeselect id="warehouse" name="warehouse.id" value="${kindClothing.warehouse.id}" labelName="warehouse.name" labelValue="${kindClothing.warehouse.name}"
					title="仓位" url="/base/warehouse/treeData" extId="${kindClothing.id}" cssClass="form-control " allowClear="true"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="创建时间：">&nbsp;创建时间：</label>
					<div class="col-xs-12">
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='beginCreateDate' style="left: -10px;" >
								<input type='text'  name="beginCreateDate" class="form-control"  />
								<span class="input-group-addon">
									   <span class="glyphicon glyphicon-calendar"></span>
								   </span>
							</div>
						</div>
						<div class="col-xs-12 col-sm-1">
							~
						</div>
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='endCreateDate' style="left: -10px;" >
								<input type='text'  name="endCreateDate" class="form-control" />
								<span class="input-group-addon">
									   <span class="glyphicon glyphicon-calendar"></span>
								   </span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="上架时间：">&nbsp;上架时间：</label>
					<div class="col-xs-12">
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='beginShelvesDate' style="left: -10px;" >
								<input type='text'  name="beginShelvesDate" class="form-control"  />
								<span class="input-group-addon">
									   <span class="glyphicon glyphicon-calendar"></span>
								   </span>
							</div>
						</div>
						<div class="col-xs-12 col-sm-1">
							~
						</div>
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='endShelvesDate' style="left: -10px;" >
								<input type='text'  name="endShelvesDate" class="form-control" />
								<span class="input-group-addon">
									   <span class="glyphicon glyphicon-calendar"></span>
								   </span>
							</div>
						</div>
					</div>
				</div>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="base:kindClothing:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="base:kindClothing:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="base:kindClothing:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="base:kindClothing:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/base/kindClothing/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="base:kindClothing:export">
				<table:exportExcel url="${ctx}/base/kindClothing/export"></table:exportExcel><!-- 导出按钮 -->
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				<a id="validData" class="btn btn-primary" onclick="validDatas()">校验</a>
		    </div>
	<!-- 表格 -->
	<table id="kindClothingTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <%--<ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="base:kindClothing:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="base:kindClothing:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>--%>
	</div>
	</div>
	</div>
</body>
</html>