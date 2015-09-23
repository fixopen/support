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
            alert("请您先登录！");
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
        element.innerHTML = element.innerHTML.replace('%7B', '{').replace('%7D', '}').replace(/\$\{(\w+)\}/g, function (all, variable) {
            if (!variable) {
                return ""
            }
            return data[variable];
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
    var links = "版权管理|copyrightGLList.htm," +
        "版权审核|copyrightSPList.htm," +
        "版权库|copyrightList.htm," +
        "资源流转追溯|rightTransfer.htm," +
        "用户管理|userManager.htm";

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
