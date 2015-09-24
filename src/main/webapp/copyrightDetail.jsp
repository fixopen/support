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
            <strong>图书编号：</strong> $_{resource.no}
        </div>
        <div class="inputArea">
            <strong>书名：</strong>$_{resource.name}
        </div>
        <div class="inputArea">
            <strong>作者：</strong> $_{resource.author}
        </div>
        <div class="inputArea">
            <strong>入库时间：</strong> $_{resource.uploadLog.timeStr}
        </div>
        <div class="inputArea">
            <strong>版权人：</strong> $_{resource.user.name}
        </div>
    </div>

</div>

</body>
</html>
<script>

//    window.addEventListener('load', function (e) {
//        queryData(newUrl,1);
//
//
//        document.getElementById('button').addEventListener('click', function (e) {
//            var search = document.getElementById('search').value;
//            var type = document.getElementById('type').value;
//
//            var cUrl="/api/copyrights/SPpage?page=1&pageSize=10" + '&str='+search+'&type='+type+'';
//
//            console.log(cUrl)
//            newUrl = cUrl;
//            queryData(cUrl)
//
//        }, false)
//
//    }, false);



</script>

