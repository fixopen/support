var name = "";
var userId = "";
var type = 0;

window.addEventListener('load', function (e) {
    g.getData('/api/user/session',[
        {name: 'Content-Type', value: 'application/json'},
        {name: 'Accept', value: 'application/json'}
    ],function(result){
        if(result.meta.code == 200){
            var d = result.data;
            name = d.user.name;
            userId = d.user.id;
            type = d.account.type;
            document.getElementById("userName").innerHTML=name;
        }else{
            alert("登录超时！");
            window.location.href="/login.html";
        }
    }, false);




    document.getElementById('logout').addEventListener('click', function (e) {
        g.deleteData('/api/sessions/me',[
            {name: 'Content-Type', value: 'application/json'},
            {name: 'Accept', value: 'application/json'}
        ],function(result){
            if(result.meta.code == 200){
                alert("退出成功！");
                window.location.href="/login.html";
            }
        }, false);
    });

    console.log(type);
    var b = leftMenu(type);
    document.getElementById('leftMenu').innerHTML=b;

}, false);


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

function leftMenu(type){
    var links = "版权管理|copyrightGLList.jsp," +
        "版权审核|copyrightSPList.jsp," +
        "版权库|copyrightList.jsp," +
        "资源流转追溯|rightTransfer.jsp," +
        "用户管理|userManager.jsp";

    var link_1=new Array(0,1,2,3,4);

    var link9=new Array(0,1,2);

    var link2=new Array(0,2);
    var link1 = link_1;
    if(type == 9){
        link1 = link9;
    }
    if(type == -1){
        link1 = link_1;
    }
    if(type == 2){
        link1 = link2;
    }


    var linkss = links.split(",");

    var b = "";
    b += "<div class=\"col-lg-2\"";
    b +="         style=\"cursor: pointer; height: 820px; background-color: #373d48; color: #ffffff; line-height: 200%; font-size: x-large; padding-top: 60px; padding-bottom: 60px\">";
    b +="        <ul style=\"list-style: none; font-size: 16px;padding-left:10%;\">";

    for(var i = 0; i <link1.length; i++){
        var lll = link1[i];
        var llls = linkss[lll].split("|");
        b +="            <li id=\"bookList\"";
        b +="                class=\"menu-item\"";
        b +="                style=\"background-position: 0 -124px;\">";
        b +="                <a href=\"/"+llls[1]+"\">"+llls[0]+"</a>";
        b +="            </li>";
    }


    b +="        </ul>";
    b +="    </div>";
    return b;
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


