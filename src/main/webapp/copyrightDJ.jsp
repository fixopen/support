<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <script src="js/jquery-1.7.2.min.js"></script>
    <script src="js/uploadFiles.js"></script>
    <script src="js/common.js"></script>
</head>
<body>

<div class="container">
    <div class="row" style="background-color: #e9e9e9; min-height: 820px;">
        <form action="/api/resources" method="post"  name="form1" id="form1">
        <div class="col-lg-12">
            <div class="inputArea">
                <strong>图书编号：</strong> <input type="text" name="textfield" name="no" id="no" />
            </div>

            <div class="inputArea">
                <strong>书名：</strong> <input type="text" name="textfield" name="name" id="name" />
            </div>
            <div class="inputArea">
                <strong>教材封面：</strong> <input type="file" name="cover-input" id="cover-input" />
                <input type="text" name="textfield" name="cover" id="cover" />
            </div>
            <div class="inputArea">
                <strong>学科：</strong> <input type="text" name="textfield" name="subject" id="subject" />
            </div>
            <div class="inputArea">
                <strong>作者：</strong> <input type="text" name="textfield" name="author" id="author" />
                <div id="author_error" class="error" zd="author" yz="bt">请填写出版社</div>
            </div>
            <div class="inputArea">
                <strong>出版社：</strong> <input type="text" name="textfield" name="publisher" id="publisher" />
                <div id="publisher_error" class="error" zd="publisher" yz="bt">请填写出版社</div>
            </div>
            <div class="inputArea">
                <strong>适用年级：</strong> <input type="text" name="textfield" name="stage" id="stage" />
                <div id="stage_error" class="error" zd="stage" yz="bt">请填写适用年级</div>
            </div>
            <div class="inputArea">
                <strong>教材类型：</strong> <input type="text" name="textfield" name="type" id="type" />
                <div id="type_error" class="error" zd="type" yz="bt">请填写教材类型</div>
            </div>

            <div class="inputArea">
                <strong>教材文件：</strong> <input type="file" name="file-input" id="file-input" />
                <input type="text" name="textfield" name="file_path" id="file_path" />
                <div id="file_path_error" class="error" zd="file_path" yz="bt">请上传教材文件</div>
            </div>

            <div class="inputArea">
                <input type="button" name="tijiao" id="tijiao" value="提交" />
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
            console.log("开始");
            uploadFileByForm(evt.target.files[0], $("#file_path"));
        }else{
            $(this).val("");
        }

    });

    $("#cover-input").on('change', function (evt) {
        if(confirm("确认上传？")){
            console.log("开始");
            uploadFileByForm(evt.target.files[0], $("#cover"));
        }else{
            $(this).val("");
        }

    });


    $("#tijiao").on("click", function(evt){

        var v = fei_check();

        if(v == 0){return false;}

        $.ajax({
            url: "/api/resources",
            type: "post",
            dataType:"json",
            data: $('#form1').serialize(),
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                console.log(data.meta.status);
                if(data.meta.status==200){
                    console.log("成功！");

                }else{

                }
            },
            cache: false,
            timeout: 500000,
            error: function(){

            }
        });
    });

</script>



