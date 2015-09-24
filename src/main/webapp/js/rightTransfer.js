var newUrl="/api/rightTransfers/page?page=1&pageSize=10";
window.addEventListener('load', function (e) {
	queryData("/api/rightTransfers/page?page=1&pageSize=10",1);
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