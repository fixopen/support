

function blind(element, data) {
    if (element.innerHTML) {
        element.innerHTML = element.innerHTML.replace('%7B', '{').replace('%7D', '}').replace(/\$_\{([\w|\.]+)\}/g, function (all, variable) {
            if (!variable) {
                return ""
            }
            var r = data
            var parts = variable.split('.')
            for (var p = 0; p < parts.length; ++p) {
                r = r[parts[p]]
                if(!r){
                   r = "";
                    return r;
                }
            }
            return r; // data[variable];
        })
    } else {
        var children = element.childNodes
        for (var i = 0, c = children.length; i < c; ++i) {
            var child = children.item(i)
            if (child.innerHTML) {
                child.innerHTML = child.innerHTML.replace('%7B', '{').replace('%7D', '}').replace(/\$\{(\w+)\}/g, function (all, variable) {
                    var result = ''
                    if (variable) {
                        result = data[variable]
                    }
                    return result
                })
            }
        }
    }
    return element
}



//删除参数值
function delQueStr(url, ref) {
    var str = "";
    if (url.indexOf('?') != -1) {
        str = url.substr(url.indexOf('?') + 1);
    }
    else {
        return url;
    }
    var arr = "";
    var returnurl = "";
    var setparam = "";
    if (str.indexOf('&') != -1) {
        arr = str.split('&');
        for (var i = 0;i < arr.length; i++) {
            if (arr[i].split('=')[0] != ref) {
                returnurl = returnurl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
            }
        }
        return url.substr(0, url.indexOf('?')) + "?" + returnurl.substr(0, returnurl.length - 1);
    }
    else {
        arr = str.split('=');
        if (arr[0] == ref) {
            return url.substr(0, url.indexOf('?'));
        }
        else {
            return url;
        }
    }
}

function replaceQueStr(ref,pnoVal){
    var url = newUrl;
    var str = "";
    if (url.indexOf('?') != -1) {
        str = url.substr(url.indexOf('?') + 1);//url中?前的部分
    }
    else {
        return url;
    }
    var arr = "";
    var returnurl = "";
    if (str.indexOf('&') != -1) {
        arr = str.split('&');
        for (var i = 0;i < arr.length; i++) {
            if (arr[i].split('=')[0] != ref) {
                returnurl = returnurl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
            }
        }
        return url.substr(0, url.indexOf('?')) + "?" + "page="+pnoVal +"&" +returnurl.substr(0, returnurl.length - 1);
    }
    else {
        arr = str.split('=');
        if (arr[0] == ref) {
            return url.substr(0, url.indexOf('?'))+ "?" + "page="+pnoVal+"&"+"pageSize=10";
        }
        else {
            return url;
        }
    }
}

function queryData(cUrl,page){
    g.getData(cUrl,[
        {name: 'Content-Type', value: 'application/json'},
        {name: 'Accept', value: 'application/json'}
    ],function(result){
        if(result.meta.code == 200){
            var totalRow = result.data.allNum;//所有数据
            var totalPage =result.data.allPage//页数
            ajaxpager(totalPage,totalRow,page);
            console.log(result.data);
            render(result.data.list)
        }
    }, true);
}


function sp(id){
    if(confirm("确认要通过审核吗？")){
        alert(id);
    }
}


function fei_check(){
    var v = 1;
    $(".error").css("display","none"); //

    $(".error").each(function(){
        if($(this).attr("zd")!=""){
            var c = $(this).attr("zd");
            if($(this).attr("yz")=="bt"){
                // alert($("#"+c).val());
                if($("#"+c).val()==""){
                    //$("#"+c+"_error").css("display","block");
                    alert($(this).html());
                    v = 0;
                    return false;
                }
            }
            if($(this).attr("yz")=="bt_shuzi"){
                if($("#"+c).val()==""){
                    $("#"+c+"_error").css("display","block");
                    v = 0;
                }else{
                    if(isNaN($("#"+c).val())){
                        $("#"+c+"_error").css("display","block");
                        v = 0;
                    }else{
                        //alert(Number($("#"+c).attr("value"))+"||"+Number($("#"+c).attr("min")));
                        if(Number($("#"+c).val())<Number($("#"+c+"_error").attr("min")) || Number($("#"+c).val())>Number($("#"+c+"_error").attr("max"))){
                            $("#"+c+"_error").css("display","block");
                            v = 0;
                        }
                    }
                }
            }
        }
    });

    return v;
}


function formDataToJson(data){

    var json = {};

    var datas = data.split("&");
    if(datas.length < 1){return json;}
    for(var i = 0; i < datas.length; i++){
        var dds = datas[i].split("=");
        var key = dds[0];
        var value = dds[1];
        json[key] = value;
    }

    return json;
}


