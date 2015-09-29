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
            <div id="contentTitle" class="contentTitle">
                <div style="float:right; margin-right:20px;"><a href="javascript:void(0);" onclick="openUrl('/userCreate.jsp', '创建用户', 600, 350);"><u>创建用户</u></a></div>
                <div class="title">用户管理</div>
                <div class="row">
                    <div class="col-lg-4">
                        <span>用户类型筛选：</span>
                        <select id="type" style="height: 34px;width: 150px;">
                            <option value="">全部</option>
                            <option value="0">普通用户</option>
                            <option value="9">版权审核人员</option>
                            <option value="2">版权登记(出版社)</option>
                            <option value="-1">管理员</option>
                        </select>
                    </div>
                    <div class="col-lg-3">
                        <span>搜索：</span>
                        <select id="searchCondition" style="height: 34px;width: 100px;">
                            <option value="all">全部</option>
                            <option value="loginName">登录名</option>
                            <option value="id">用户ID</option>
                            <option value="name">真实姓名</option>
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

                                <th style="min-width: 15%;text-align: center;">用户ID</th>
                                <th style="min-width: 15%;text-align: center;">登录名</th>
                                <th style="min-width: 15%;text-align: center;">真实姓名</th>
                                <th style="min-width: 15%;text-align: center;">用户类型</th>
                                <th style="min-width: 20%;text-align: center;">所属单位</th>
                                <th style="min-width: 15%;text-align: center;">操作</th>
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
        <td style="text-align: center">$_{id}</td>
        <td style="text-align: center">$_{account.loginName}</td>
        <td style="text-align: center">$_{name}</td>
        <td style="text-align: center">
            $_{account.typeStr}</td>
        <td style="text-align: center">
            $_{companyName}</td>
        <td style="text-align: center" class="" >
            <a href=javascript:void(0); onclick=deleteUser($_{id})>注销</a>
            ／<a href="javascript:void(0);" onclick="openUrl('/api/user/editHtml?uid=$_{id}', '创建用户', 600, 350);">修改</a></td>

    </tr>
</template>
<link rel="stylesheet" type="text/css" href="css/kkpager_blue.css"/>
<script src="js/jquery-1.4.2.min.js"></script>
<script src="js/util.js"></script>
<script src="js/kkpager.js"></script>
<script src="js/common.js"></script>


</body>
</html>
<script>
    var newUrl="/api/user/page?pageSize=20&page=1";
    var searchConditionVal = "all";
    var currentPage =1;
    window.addEventListener('load', function (e) {
        queryData("/api/user/page?pageSize=20&page=1",1);

        //type发生变化时 发请求
        $('#type').change(function(){
            var type  = $("#type").find("option:selected").val();

            var cUrl="/api/user/page?pageSize=20&page=1" +'&type='+type;

            newUrl = cUrl;
            queryData(cUrl,1)

        });
        //输入框 查询

        $('#searchCondition').change(function(){
            searchConditionVal  = $("#searchCondition").find("option:selected").val();
            var placeholder ="";
            switch (searchConditionVal){
                case "loginName":
                    placeholder = "例如：张某某"
                    break;
                case "id":
                    placeholder = "例如：27317"
                    break;
                case "name":
                    placeholder = "例如：yonghu_123"
                    break;
                default :
                        break
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

            var str = searchConditionVal+ "::"+inputVal;
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
            $("#handle"+ datas[i].id).html("<a href=javascript:void(0); onclick=deleteUser("+datas[i].id+")>注销</a>／<a href=javascript:void(0); onclick=eidtUser("+datas[i].id+")>修改</a>");
        }
    }

    function deleteUser(id){
        if(confirm("确认要删除吗？")){
            g.getData("/api/user/delete?uid="+id,[
                {name: 'Content-Type', value: 'application/json'},
                {name: 'Accept', value: 'application/json'}
            ],function(result){
                if(result.meta.code == 200){
                    alert("删除成功！");
                    queryData(newUrl,currentPage);
                }
            }, true);
        }
    }
    function editUser(id){
        if(confirm("确认要删除吗？")){
            g.getData("/api/copyrights/delete/"+id,[
                {name: 'Content-Type', value: 'application/json'},
                {name: 'Accept', value: 'application/json'}
            ],function(result){
                if(result.meta.code == 200){
                    alert("删除成功！");
                    queryData(newUrl,currentPage);
                }
            }, true);
        }
    }

</script>
<script src="/js/ajaxpager.js"></script>