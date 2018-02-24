<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title> 库存清单管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="kindclothingStockList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title"> 样衣库存</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="kindclothingStock" onkeypress="onEnterSearch(event,'kindclothingStockTable')" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="样衣条码：">样衣条码：</label>
				<form:input path="barCode" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="版单号：">版单号：</label>
				<form:input path="panCode" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="供应商：">供应商：</label>
				<sys:gridselect url="${ctx}/base/supplier/data1/2" id="supplier" name="supplier.id" value="${kindclothingStock.supplier.id}" labelName="supplier.name" labelValue="${kindclothingStock.supplier.name}"
					isMultiSelected="false" title="选择供应商" cssClass="form-control required" fieldLabels="供应商名称|供应商编码|联系人|联系电话" fieldKeys="name|number|contacts|phone" searchLabels="供应商名称|供应商编码|联系人|联系电话" searchKeys="name|number|contacts|phone" ></sys:gridselect>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="对应货架：">对应货架：</label>
				<sys:gridselect url="${ctx}/base/shelves/data" id="shelves" name="shelves.id" value="${kindclothingShelves.shelves.id}" labelName="shelves.name" labelValue="${kindclothingShelves.shelves.name}"
								isMultiSelected="false" title="选择对应货架" cssClass="form-control required" fieldLabels="货架编码|货架名称" fieldKeys="code|name" searchLabels="货架编码|货架名称" searchKeys="code|name" ></sys:gridselect>
			</div>
				<%--<div class="col-xs-12 col-sm-6 col-md-4">
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
                </div>--%>
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
			<!--
			<shiro:hasPermission name="kindclothing:kindclothingStock:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="kindclothing:kindclothingStock:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="kindclothing:kindclothingStock:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="kindclothing:kindclothingStock:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/kindclothing/kindclothingStock/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			-->
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="kindclothingStockTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
   <%-- <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="kindclothing:kindclothingStock:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="kindclothing:kindclothingStock:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>--%>
	</div>
	</div>
	</div>
</body>
</html>