<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <script src="js/jquery-1.7.2.min.js"></script>
    <script src="js/uploadFiles.js"></script>
    <jsp:include page="tc_headers.jsp" />
</head>
<body>

<div class="container">
    <div class="row">
        <form action="/api/resources" method="post"  name="form1" id="form1">
        <div class="col-lg-12">
            <div class="inputArea">
                <strong>图书编号：</strong> <input type="text"  name="no" id="no" />
                <div id="no_error" class="error" zd="no" yz="bt">请填写图书编号</div>
            </div>

            <div class="inputArea">
                <strong>书&nbsp;&nbsp;名：</strong> <input type="text"  name="name" id="name" />
                <div id="name_error" class="error" zd="name" yz="bt">请填写书名</div>
            </div>
            <div class="inputArea">
                <strong>准 用 号：</strong> <input type="text"  name="quasiUseNo" id="quasiUseNo" />
                <div id="quasiUseNo_error" class="error" zd="quasiUseNo" yz="bt">请填写准用号</div>
            </div>
            <div class="inputArea">
                <strong>教材封面：</strong> <input type="file" name="cover-input" id="cover-input" />
                <input type="hidden"  name="cover" id="cover" readonly />
                <div id="cover_error" class="error" zd="cover" yz="bt">请上传教材封面</div>
            </div>
            <div class="inputArea">
                <strong>适用年级：</strong> <input type="text"  name="grade" id="grade" />
                <div id="grade_error" class="error" zd="grade" yz="bt">请填写适用年级</div>
            </div>
            <div class="inputArea">
                <strong>学&nbsp;&nbsp;科：</strong> <input type="text"  name="subject" id="subject" />
                <div id="subject_error" class="error" zd="subject" yz="bt">请填写学科</div>
            </div>
            <div class="inputArea">
                <strong>作&nbsp;&nbsp;者：</strong> <input type="text"  name="author" id="author" />
                <div id="author_error" class="error" zd="author" yz="bt">请填写作者</div>
            </div>
            <div class="inputArea">
                <strong>出 版 社：</strong> <input type="text"  name="publisher" id="publisher" />
                <div id="publisher_error" class="error" zd="publisher" yz="bt">请填写出版社</div>
            </div>
            <div class="inputArea">
                <strong>学&nbsp;&nbsp;段：</strong> <input type="text"  name="stage" id="stage" />
                <div id="stage_error" class="error" zd="stage" yz="bt">请填写学段</div>
            </div>
            <div class="inputArea">
                <strong>教材类型：</strong> <input type="text"  name="type" id="type" />
                <div id="type_error" class="error" zd="type" yz="bt">请填写教材类型</div>
            </div>

            <div class="inputArea">
                <strong>教材文件：</strong> <input type="file" name="file-input" id="file-input" />
                <input type="hidden"  name="filePath" id="filePath" readonly/>
                <div id="filePath_error" class="error" zd="filePath" yz="bt">请上传教材文件</div>
            </div>

            <div class="inputArea">
                <input type="button" name="tijiao" id="tijiao" value="&nbsp;登&nbsp;&nbsp;记&nbsp;" />
                <%--
                &nbsp;
                <input type="button" name="close" id="close" value="关&nbsp;&nbsp;闭"  onclick="closeOpen();"/>
                --%>
            </div>

        </div>



        </form>
    </div>

</div>

</body>
</html>
<script language="JavaScript">
    $("#file-input").on('change', function (evt) {
        if(confirm("确认上传？")){
            uploadFileByForm(evt.target.files[0], $("#filePath"));
        }else{
            $(this).val("");
        }

    });

    $("#cover-input").on('change', function (evt) {
        if(confirm("确认上传？")){
            uploadFileByForm(evt.target.files[0], $("#cover"));
        }else{
            $(this).val("");
        }

    });


    $("#tijiao").on("click", function(evt){
        if(confirm("确认提交登记？")) {
            var v = fei_check();

            if (v == 0) {
                return false;
            }
            console.log(JSON.stringify(formDataToJson($('#form1').serialize())));
            $.ajax({
                url: "/api/resources",
                type: "post",
                dataType: "json",
                data: JSON.stringify(formDataToJson($('#form1').serialize())),
                contentType: "application/json; charset=UTF-8",
                success: function (data) {
                    console.log(data.meta.status);
                    if (data.meta.status == 200) {
                        console.log("成功！");

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



