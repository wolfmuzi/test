<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>解绑记录管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="kindclothingUnbindList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">样衣解绑</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="kindclothingUnbind" class="form form-horizontal well clearfix" onkeypress="onEnterSearch(event,'kindclothingUnbindTable')">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="解绑单号：">解绑单号：</label>
				<form:input path="code" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="解绑人员：">解绑人员：</label>
					<sys:userselect id="unbindUser" name="unbindUser.id" value="${kindclothingUnbind.unbindUser.id}" labelName="unbindUser.name" labelValue="${kindclothingUnbind.unbindUser.name}"
									cssClass="form-control required"/>
				</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" title="解绑时间：">&nbsp;解绑时间：</label>
					<div class="col-xs-12">
						   <div class="col-xs-12 col-sm-5">
					        	  <div class='input-group date' id='beginUnbindDate' style="left: -10px;" >
					                   <input type='text'  name="beginUnbindDate" class="form-control"  />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					             </div>	
					        </div>
					        <div class="col-xs-12 col-sm-1">
					        		~
					       	</div>
					        <div class="col-xs-12 col-sm-5">
					          	<div class='input-group date' id='endUnbindDate' style="left: -10px;" >
					                   <input type='text'  name="endUnbindDate" class="form-control" />
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
		<!--
			<shiro:hasPermission name="kindclothing:kindclothingUnbind:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="kindclothing:kindclothingUnbind:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="kindclothing:kindclothingUnbind:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="kindclothing:kindclothingUnbind:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/kindclothing/kindclothingUnbind/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
			-->
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="kindclothingUnbindTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <%--<ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="kindclothing:kindclothingUnbind:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="kindclothing:kindclothingUnbind:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  --%>
	</div>
	</div>
	</div>
</body>
</html>