<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="headers.jsp" />
</head>
<body>
<div class="container">
    <jsp:include page="topMenu.jsp" />
    <div class="row" style="background-color: #e9e9e9; min-height: 820px;">
<div id="leftMenu"></div>
        <div class="col-lg-10" style="padding: 0; height: 100%; line-height: 200%">
            <div id="contentTitle" style="background-color: #e9e9e9; color: #0091ff; padding-top: 54px; padding-left: 20px; height: 102px;">
                <input id="search" style="width: 200px"/><button id="button"> 查询</button >
            </div>
            <div id="mainContainer" style="padding-top: 30px; padding-bottom: 60px; padding-left: 20px; padding-right: 20px;">
                <div class="table" id="table">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color:white;">
                        <!--表头-->
                        <thead id="header">
                            <tr class="permission-tr">

                                <th style="min-width: 10%;text-align: center;">图书编号</th>
                                <th style="min-width: 10%;text-align: center;">书名</th>
                                <th style="min-width: 10%;text-align: center;">作者</th>
                                <th style="min-width: 10%;text-align: center;">版本</th>
                                <th style="min-width: 10%;text-align: center;">入库时间</th>
                                <th style="min-width: 10%;text-align: center;">版权人</th>
                                <th style="min-width: 10%;text-align: center;">状态</th>
                                <th style="min-width: 10%;text-align: center;">操作</th>
                            </tr>
                        </thead>
                        <tbody id="permission-tbody" style="font-size: 12px;"></tbody>

                    </table>
                    <div id="page"></div>
                    <div class="list-page" style="width:600px;float:right;">
                        <div id="kkpager">
                            <input type="hidden" id="totalRow" value="">
                            <input type="hidden" id="totalPage" value="">
                            <input type="hidden" id="action" value="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--<div class="row" style="height: 88px;">-->
        <!--<div class="col-lg-12" style="background-color: #373d48; color: #ffffff; text-align: center">-->
            <!--footer info, legal info, address info-->
        <!--</div>-->
    <!--</div>-->
</div>



<template id="permission-tr">
    <tr class="permission-tr">
        <td style="text-align: center">$_{resource.no}</td>
        <td style="text-align: center"><a href="javascript:void(0);" onclick="openUrl('/api/copyrights/infoJSP?copyrightId=$_{id}', '查看', 600, 550);">$_{resource.name}</a></td>
        <td style="text-align: center">$_{resource.author}</td>
        <td style="text-align: center">
            $_{resource.version}</td>
        <td style="text-align: center">
            $_{resource.uploadLog.timeStr}</td>
        <td style="text-align: center">
            $_{resource.user.name}</td>
        <td style="text-align: center">$_{statusStr}</td>
        <td style="text-align: center" class="" ><div id="disable$_{id}"></div></td>
    </tr>
</template>
<link rel="stylesheet" type="text/css" href="/css/kkpager_blue.css"/>
<script src="js/jquery-1.4.2.min.js"></script>
<script src="js/util.js"></script>
<script src="/js/kkpager.js"></script>
<script src="js/common.js"></script>


</body>
</html>
<script>
    var newUrl="/api/copyrights/page?page=1&pageSize=10";
    window.addEventListener('load', function (e) {
        queryData("/api/copyrights/page?page=1&pageSize=10",1);

    }, false);

    function render(datas){
        var container = document.getElementById('permission-tbody');
        container.innerHTML = '';
        var count=datas.length;
        for (var i = 0; i < count; ++i) {
            var body = document.getElementById('permission-tr').content.cloneNode(true).children[0]
            blind(body,datas[i])
            container.appendChild(body)
            if(datas[i].statusStr == '待审核'){
                $("#disable"+ datas[i].id).html("<a href=\"/api/copyrights/delete/"+datas[i].id+"\" onclick=\"sp();\">撤销<\/a>");
            }
        }
    }

</script>
<script src="/js/ajaxpager.js"></script>