<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <%--<link href="css/bootstrap.min.css" rel="stylesheet">--%>
    <jsp:include page="/tc_headers.jsp" />
    <style type="text/css">
        .inputArea{
            width:100%;
        }
        .inputArea strong{
            width:80px;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="inputArea">
                <strong>图书编号：</strong> ${copyright.no}
            </div>

            <div class="inputArea">
                <strong>书&nbsp;&nbsp;名：</strong>${copyright.resource.name}
            </div>
            <div class="inputArea">
                <strong>教材封面：</strong> <img src="/api/resources/${copyright.no}/cover" width="90px" height="120px"/>
            </div>
            <div class="inputArea">
                <strong>学&nbsp;&nbsp;科：</strong> ${copyright.resource.subject}
            </div>
            <div class="inputArea">
                <strong>作&nbsp;&nbsp;者：</strong> ${copyright.resource.author}
            </div>
            <div class="inputArea">
                <strong>出 版 社：</strong> ${copyright.resource.publisher}
            </div>
            <div class="inputArea">
                <strong>适用年纪：</strong> ${copyright.resource.stage}
            </div>
            <div class="inputArea">
                <strong>教材类型：</strong> ${copyright.resource.type}
            </div>

        </div>
    </div>
</div>


</body>
</html>


