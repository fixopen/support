

function uploadFile(files) {
    var length = 8 * 1024;
    var offset = 0;
    var id = "";
    var f = files[0];
    var fileReader = new FileReader();

    fileReader.onloadend = function () {
      

            var xhr = new XMLHttpRequest();
             xhr.responseType="Blob";
            xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        var href = JSON.parse(xhr.responseText);
                        console.log(href.id);
                    }
             }

            var uploadUrl = "/api/resources/file";
            xhr.open("POST", uploadUrl, false);
            xhr.setRequestHeader("Content-Type", f.type);
            xhr.send(fileReader.result);
           
            console.log("send");
    };


 
    console.log("load");
    fileReader.readAsBinaryString(f);

    
}

function uploadFileByForm(file, obj){

            var FileController = "/api/resources/file";                    // 接收上传文件的后台地址

            // FormData 对象

            var form = new FormData();

            form.append("file", file);                           // 文件对象


            // XMLHttpRequest 对象

            var xhr = new XMLHttpRequest();

            xhr.open("post", FileController, true);

            xhr.onload = function () {

                console.log(xhr.readyState);
                if (xhr.readyState === 4) {
                    console.log(xhr.responseText);
                    var href = JSON.parse(xhr.responseText);
                    $(obj).val(href.tmpPath);
                }

            };

            xhr.send(form);
}



