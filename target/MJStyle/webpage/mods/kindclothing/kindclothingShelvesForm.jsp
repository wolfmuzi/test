<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>样衣管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        var validateForm;
        var $table; // 父页面table表格id
        var $topIndex;//弹出窗口的 index
        function doSubmit(table, index) {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if (validateForm.form()) {
                $table = table;
                $topIndex = index;
                jp.loading();
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function () {
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    jp.post("${ctx}/kindclothing/kindclothingShelves/save", $('#inputForm').serialize(), function (data) {
                        if (data.success) {
                            $table.bootstrapTable('refresh');
                            jp.success(data.msg);
                            jp.close($topIndex);//关闭dialog

                        } else {
                            jp.error(data.msg);
                        }
                    })
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $('#shelvesDate').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
        });
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="kindclothingShelves"  class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>上架单号：</label></td>
            <td class="width-35">
                <form:input path="code" htmlEscape="false" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>上架数量：</label></td>
            <td class="width-35">
                <form:input path="num" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>对应货架：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/base/shelves/data" id="shelves" name="shelves.id"
                                value="${kindclothingShelves.shelves.id}" labelName="shelves.name"
                                labelValue="${kindclothingShelves.shelves.name}"
                                isMultiSelected="false" title="选择对应货架" cssClass="form-control required"
                                fieldLabels="货架编码|货架名称" fieldKeys="code|name" searchLabels="货架编码|货架名称"
                                searchKeys="code|name"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>上架时间：</label></td>
            <td class="width-35">
                <p class="input-group">
                <div class='input-group form_datetime' id='shelvesDate'>
                    <input type='text' name="shelvesDate" class="form-control"
                           value="<fmt:formatDate value="${kindclothingShelves.shelvesDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
                </div>
                </p>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>上架人员：</label></td>
            <td class="width-35">
                <sys:userselect id="user" name="user.id" value="${kindclothingShelves.user.id}" labelName="user.name"
                                labelValue="${kindclothingShelves.user.name}"
                                cssClass="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
            <td class="width-35">
                <form:textarea path="remarks" htmlEscape="false" rows="4" class="form-control "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>