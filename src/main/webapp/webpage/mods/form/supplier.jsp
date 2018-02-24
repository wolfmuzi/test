<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>面料供应商考核</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="supplier.js" %>
    <%@include file="echarts.min.js" %>

</head>
<body>

<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">面料供应商考核</h3>
        </div>
        <div >
            <div>
                <div class="accordion-inner">
                    <form:form id="searchForm" modelAttribute="fabric" class="form form-horizontal well clearfix">

                        <div class="col-xs-12 col-sm-6 col-md-4">
                            <label class="label-item single-overflow pull-left" title="供应商：">供应商：</label>
                            <sys:gridselect url="${ctx}/base/supplier/data" id="id" name="id" value="${id}" labelName="supplier.name" labelValue="${supplier.name}"
                                            isMultiSelected="false" title="选择供应商" cssClass="form-control required" fieldLabels="供应商编码|供应商名称|联系人" fieldKeys="number|name|contacts" searchLabels="供应商编码|供应商名称|联系人" searchKeys="number|name|contacts" ></sys:gridselect>
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


        <div id="main" style="display:none;width: 1700px;height:600px;"></div>



        <div class="panel-body" style="display:none;">
            <sys:message content="${message}"/>

            <!-- 表格 -->
            <table id="fabricOrderItemTable"   data-toolbar="#toolbar"></table>

            <!-- context menu -->
            <%--<ul id="context-menu" class="dropdown-menu">--%>
            <%--<shiro:hasPermission name="fabric:fabricOrder:edit">--%>
            <%--<li data-item="edit"><a>编辑</a></li>--%>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="fabric:fabricOrder:del">--%>
            <%--<li data-item="delete"><a>删除</a></li>--%>
            <%--</shiro:hasPermission>--%>
            <%--<li data-item="action1"><a>取消</a></li>--%>
            <%--</ul>  --%>
        </div>
    </div>

</div>

</body>
</html>