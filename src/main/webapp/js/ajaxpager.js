
//init
function  ajaxpager(tpage,trecords,page){
    var totalPage = tpage;
    var totalRecords = trecords;
    var action = "";
    //var pageNo = getParameter('pno')==null? 1:getParameter('pno');
    var pageNo = page;
    var mode = 'click';

    kkpager.generPageHtml({
        pno : pageNo,
        //总页码
        total : totalPage,
        //总数据条数
        totalRecords : totalRecords,
        //链接前部
        hrefFormer : action,
        //链接尾部
        hrefLatter : '',
        getLink : function(n){
            var hrefUrl = this.hrefFormer + this.hrefLatter;
            if(hrefUrl.indexOf("?") == -1){
                return hrefUrl + "?pno="+n;
            }else{
                return hrefUrl + "&pno="+n;
            }

        },

        mode : mode,//默认值是link，可选link或者click
        click : function(n){
            var cUrl= replaceQueStr("page",n);
            newUrl = cUrl;
            alert(newUrl)
            currentPage = n;
            queryData(cUrl,n);
            this.selectPage(n);
        }
    },true);
}

