<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <script src="/js/jquery-1.7.2.min.js"></script>
    <style type="text/css">
        .inputArea {
            width: 100%;
            overflow: hidden;
            line-height: 24px;
            margin: 12px 0;
        }
        .inputArea strong {
            position: relative;
            float: left;
            width: 100px;
            font-weight: normal;
            padding-left: 80px;
        }
        .popup-bottom {
            height: 35px;
            text-align: right;
            background: #f6f6f6;
            border-top: #fff 1px solid;
            padding: 5px 5px 0 0;
        }
    </style>
    <jsp:include page="/tc_headers.jsp" />
</head>
<body>

<div class="container">
    <div class="row">
        <form action="/api/user/editDo" method="post"  name="form1" id="form1">
        <div class="col-lg-12">
            <input  type="hidden" value="${user.id}"  name="id" id="uid"/>
            <div class="inputArea">
                <strong>登录名：</strong>
                <input class="ipt" type="text" value="${user.account.loginName}" id="loginName" name="loginName" disabled="disabled"/>
            </div>
            <div class="inputArea">
                <strong>真实姓名：</strong> <input type="text"  name="name" id="name" value="${user.name}"/>
                <div id="name_error" class="error" zd="name" yz="bt">请填写登录名</div>
            </div>

            <div class="inputArea">
                <strong>设置密码：</strong> <input type="password" value="${user.account.password}" name="password" id="password" />
                <div id="password_error" class="error" zd="password" yz="bt">请填写密码</div>
            </div>
            <div class="inputArea">
                <strong>所属单位：</strong> <input type="text"  name="companyName" id="companyName" value="${user.companyName}"/>
                <div id="companyName_error" class="error" zd="companyName" yz="bt">请填写所属单位</div>
            </div>

            <div class="popup-bottom">
                <input type="button" name="tijiao" id="tijiao" value="&nbsp;修&nbsp;&nbsp;改&nbsp;" />
                <input  type="button" value="取 消" onclick="closeOpen();"/>
            </div>

        </div>
        </form>
    </div>

</div>

</body>
</html>
<script language="JavaScript">
    $("#tijiao").on("click", function(evt){
        if(confirm("确认提交修改？")) {
            var v = fei_check();

            if (v == 0) {
                return false;
            }
           var uid=  $("#uid").val();
            $.ajax({
                url: "/api/user/editDo?uid="+uid,
                type: "post",
                dataType: "json",
                data: JSON.stringify(formDataToJson($('#form1').serialize())),
                contentType: "application/json; charset=UTF-8",
                success: function (data) {
                    if (data.code == 200) {
                        alert('修改成功！');
                        winClose(1);
                    } else {

                    }
                },
                cache: false,
                timeout: 500000,
                error: function () {

                }
            });
        }
    });


</script>



