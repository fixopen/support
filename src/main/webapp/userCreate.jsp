<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <script src="js/jquery-1.7.2.min.js"></script>
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
    <jsp:include page="tc_headers.jsp" />
</head>
<body>

<div class="container">
    <div class="row">
        <form action="/api/user/create" method="post"  name="form1" id="form1">
        <div class="col-lg-12">
            <div class="inputArea">
                <strong>设置登录名：</strong> <input type="text"  name="name" id="name" />
                <div id="name_error" class="error" zd="name" yz="bt">请填写登录名</div>
            </div>

            <div class="inputArea">
                <strong>设置&nbsp;&nbsp;密码：</strong> <input type="password"  name="password" id="password" />
                <div id="password_error" class="error" zd="password" yz="bt">请填写密码</div>
            </div>
            <div class="inputArea">
                <strong>用户类型：</strong>
                <select id="type" name="type.code" >
                    <option value="0">普通用户</option>
                    <option value="2">版权登记(出版社)</option>
                    <option value="9">版权审核人员</option>
                    <option value="-1">管理员</option>
                </select>

            </div>
            <div class="inputArea">
                <strong>所属单位：</strong> <input type="text"  name="companyName" id="companyName" />
                <div id="companyName_error" class="error" zd="companyName" yz="bt">请填写适用年级</div>
            </div>

            <div class="popup-bottom">
                <input type="button" name="tijiao" id="tijiao" value="&nbsp;创&nbsp;&nbsp;建&nbsp;" />
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
        if(confirm("确认提交登记？")) {
            var v = fei_check();

            if (v == 0) {
                return false;
            }
            console.log(JSON.stringify(formDataToJson($('#form1').serialize())));
            $.ajax({
                url: "/api/user/create",
                type: "post",
                dataType: "json",
                data: JSON.stringify(formDataToJson($('#form1').serialize())),
                contentType: "application/json; charset=UTF-8",
                success: function (data) {
                    console.log(data.meta.status);
                    if (data.meta.status == 200) {
                        console.log("成功！");
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



