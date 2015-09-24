<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>支撑管理平台</title>
    <script src="js/jquery-1.7.2.min.js"></script>
    <script src="js/uploadFiles.js"></script>
</head>
<body>

<div class="container">
    <div class="row" style="background-color: #e9e9e9; min-height: 820px;">
        <form action="" method="post"  name="form1" id="form1">
        <div class="col-lg-12">
            <div class="inputArea">
                <strong>图书编号：</strong> ${copyright.no}
            </div>

            <div class="inputArea">
                <strong>书名：</strong>${copyright.resource.name}
            </div>
            <div class="inputArea">
                <strong>教材封面：</strong> <img src="/api/resources/${copyright.no}/cover" width="90px" height="120px"/>
            </div>
            <div class="inputArea">
                <strong>学科：</strong> ${copyright.resource.subject}
            </div>
            <div class="inputArea">
                <strong>作者：</strong> ${copyright.resource.author}
            </div>
            <div class="inputArea">
                <strong>出版社：</strong> ${copyright.resource.publisher}
            </div>
            <div class="inputArea">
                <strong>适用年纪：</strong> ${copyright.resource.stage}
            </div>
            <div class="inputArea">
                <strong>教材类型：</strong> ${copyright.resource.type}
            </div>

            <div class="inputArea">
                <strong>教材文件：</strong> <input type="file" name="file-input" id="file-input" />
                <input type="text" name="textfield" name="file-name" id="file-name" />
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
            uploadFileByForm(evt.target.files[0], $("#file-name"));
        }else{
            $(this).val("");
        }

    });

</script>



