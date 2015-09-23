var newUrl="/api/rightTransfers/page?page=1&pageSize=10";
window.addEventListener('load', function (e) {
	queryData("/api/rightTransfers/page?page=1&pageSize=10",1);
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

	var b = leftMenu();
	document.getElementById('leftMenu').innerHTML=b;

}, false);

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
			render(result.data.list)
		}
	}, true);
}

document.getElementById('button').addEventListener('click', function (e) {
	var search = document.getElementById('search').value;
	if(search == ""){
		alert('请输入内容')
	}else{
		var cUrl='/api/rightTransfers/page?resourceId='+search+''
		newUrl = cUrl;
		queryData(cUrl)
	}
}, false)

function render(datas){
	var container = document.getElementById('permission-tbody');
	container.innerHTML = '';
	var count=datas.length;
	for (var i = 0; i < count; ++i) {
		var body = document.getElementById('permission-tr').content.cloneNode(true).children[0]
		blind(body,datas[i])
		container.appendChild(body)
	}
}