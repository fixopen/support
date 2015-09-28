<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style type="text/css">
        .inputArea {
            width: 100%;
            overflow: hidden;
            line-height: 24px;
        }
        .ipt {
            line-height: 22px;
            font-size: 12px;
            border: #dfdfdf 1px solid;
            border-top-color: #cfcfcf;
            background-color: #fff;
            -moz-border-radius: 3px;
            -webkit-border-radius: 3px;
            border-radius: 3px;
            padding: 0 8px;
            box-shadow: 1px 1px 1px #efefef inset;
        }
    </style>
    <jsp:include page="headers.jsp" />
</head>
<body>
<div class="container">
    <jsp:include page="topMenu.jsp" />
    <div class="row" style="background-color: #e9e9e9; min-height: 820px;">
<div id="leftMenu"></div>
        <div class="col-lg-10" style="padding: 0; height: 100%; line-height: 200%">
            <div id="contentTitle"  class="contentTitle">
                <div class="title">资源流转追溯</div>
                <div class="row">
                    <div class="col-lg-5">
                        <div class="inputArea">
                            <span>生成时间：</span>
                            <input id="sdate" class="ipt Wdate " style="height: 34px;width: 130px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'edate\')}'})" />
                            到
                            <input id="edate" class="ipt Wdate" style="height: 34px;width: 130px;"onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'sdate\')}',startDate:'#F{$dp.$D(\'sdate\',{d:+1})}'})" />
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <span>搜索：</span>
                        <select id="searchCondition" style="height: 34px;width: 100px;">
                            <option value="all">全部</option>
                            <option value="name">书名</option>
                            <option value="author">作者</option>
                            <option value="no">编号</option>
                        </select>
                    </div>
                    <div class="col-lg-4" style="margin-left: -20px;">
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
                                <th style="min-width: 10%;text-align: center;">权利转移编号</th>
                                <th style="min-width: 10%;text-align: center;">图书编号</th>
                                <th style="min-width: 10%;text-align: center;">资源名称</th>
                                <th style="min-width: 10%;text-align: center;">权利发出者</th>
                                <th style="min-width: 10%;text-align: center;">权利接受者</th>
                                <th style="min-width: 10%;text-align: center;">权利类型</th>
                                <th style="min-width: 10%;text-align: center;">生成时间</th>
                            </tr>
                        </thead>
                        <tbody id="permission-tbody"></tbody>

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
        <td style="text-align: center">$_{no}</td>
        <td style="text-align: center">$_{bookNo}</td>
        <td style="text-align: center">$_{resourceName}</td>
        <td style="text-align: center">
            $_{fromName}</td>
        <td style="text-align: center">
            $_{toName}</td>
        <td style="text-align: center">
            下载、读</td>
        <td style="text-align: center">$_{timeStr}</td>
    </tr>
</template>
<link rel="stylesheet" type="text/css" href="/css/kkpager_blue.css"/>
<script src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script src="js/util.js"></script>
<script src="/js/kkpager.js"></script>
<script src="js/common.js"></script>
<script src="/js/rightTransfer.js"></script>
<script src="/js/ajaxpager.js"></script>
</body>
</html>