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
                <div class="row">
                    <div class="col-lg-3">
                        <span>搜索：</span>
                        <select id="searchCondition" style="height: 34px;width: 100px;">
                            <option value="all">全部</option>
                            <option value="name">书名</option>
                            <option value="author">作者</option>
                            <option value="no">编号</option>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <div class="input-group">
                            <input id="searchInputVal" type="text" class="form-control" placeholder="">
                          <span class="input-group-btn">
                            <button id="searchBtn" class="btn btn-default" type="button">查询</button>
                          </span>
                        </div>
                    </div>
                </div>
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
    var searchConditionVal = "all";
    window.addEventListener('load', function (e) {
        queryData("/api/copyrights/page?page=1&pageSize=10",1);

        //输入框 查询

        $('#searchCondition').change(function(){
            searchConditionVal  = $("#searchCondition").find("option:selected").val();
            var placeholder ="";
            switch (searchConditionVal){
                case "name":
                    placeholder = "例如：小学语文"
                    break;
                case "no":
                    placeholder = "例如：CB-2006044-15F-01-01"
                    break;
                case "author":
                    placeholder = "例如：张三"
                    break;
            }
            $("#searchInputVal").attr("placeholder",placeholder)

        });
        $('#searchBtn').click(function(){

            var inputVal = $("#searchInputVal").val();
            if(searchConditionVal != "all"){
                if(inputVal ==''){
                    alert("请输入文字！")
                    return;
                }
            }

            var str = searchConditionVal+ "="+inputVal;
            var cUrl;
            if(newUrl.indexOf("str")==-1){
                cUrl=newUrl+'&str='+str;
            }else{
                cUrl = delQueStr(newUrl,"str")+'&str='+str;
            }

            newUrl = cUrl;
            queryData(cUrl,1)

        });
    }, false);

    function render(datas){
        var container = document.getElementById('permission-tbody');
        container.innerHTML = '';
        var count=datas.length;
        for (var i = 0; i < count; ++i) {
            var body = document.getElementById('permission-tr').content.cloneNode(true).children[0]
            blind(body,datas[i])
            container.appendChild(body)
        }
    }

</script>
<script src="/js/ajaxpager.js"></script>