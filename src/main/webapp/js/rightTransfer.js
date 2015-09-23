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


}, false);



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