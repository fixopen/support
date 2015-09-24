<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
</head>
<body>

<div class="container">
    <div class="row" style="background-color: #e9e9e9; min-height: 820px;">
        <div class="inputArea">
            <strong>图书编号：</strong> ${copyright.no}
        </div>
        <div class="inputArea">
            <strong>书名：</strong>${copyright.name}
        </div>
        <div class="inputArea">
            <strong>作者：</strong> ${copyright.author}
        </div>
        <div class="inputArea">
            <strong>入库时间：</strong> ${copyright.uploadLog.timeStr}
        </div>
        <div class="inputArea">
            <strong>版权人：</strong> ${copyright.user.name}
        </div>
    </div>

</div>

</body>
</html>


