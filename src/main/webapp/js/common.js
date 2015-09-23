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

function leftMenu(){
    var b = "";
    b += "<div class=\"col-lg-2\"";
    b +="         style=\"cursor: pointer; height: 820px; background-color: #373d48; color: #ffffff; line-height: 200%; font-size: x-large; padding-top: 60px; padding-bottom: 60px\">";
    b +="        <ul style=\"list-style: none; font-size: 16px;padding-left:10%;\">";
    b +="            <li id=\"bookList\"";
    b +="                class=\"menu-item\"";
    b +="                style=\"background-position: 0 -124px;\">";
    b +="                <a href=\"/copyrightGLList.htm\">版权管理</a>";
    b +="            </li>";
    b +="";
    b +="            <li id=\"bookList3\"";
    b +="                class=\"menu-item\"";
    b +="                style=\"background-position: 0 -124px;\">";
    b +="                <a href=\"/copyrightSPList.htm\">版权审核</a>";
    b +="            </li>";
    b +="";
    b +="            <li id=\"bookList4\"";
    b +="                class=\"menu-item\"";
    b +="                style=\"background-position: 0 -124px;\">";
    b +="                <a href=\"/copyrightList.htm\">版权库</a>";
    b +="            </li>";
    b +="";
    b +="            <li id=\"bookList2\"";
    b +="                class=\"menu-item\"";
    b +="                style=\"background-position: 0 -124px;\">";
    b +="                <a href=\"/rightTransfer.htm\">资源流转追溯</a>";
    b +="            </li>";
    b +="        </ul>";
    b +="    </div>";
    return b;
}