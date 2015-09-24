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
        <form action="" method="post" enctype="multipart/form-data" name="form1" id="form1">
            <input type="file" name="file-input" id="file-input" />
            <input type="button" name="button" id="button" value="上传" />
            <input type="text" name="textfield" name="file-name" id="file-name" />
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



