var newUrl="/api/rightTransfers/page?pageSize=10&page=1";
var searchConditionVal = "all";
window.addEventListener('load', function (e) {
	queryData("/api/rightTransfers/page?page=1&pageSize=10",1);
	//输入框 查询
	$('#searchCondition').change(function(){
		searchConditionVal  = $("#searchCondition").find("option:selected").val();
		var placeholder ="";
		switch (searchConditionVal){
			case "resourceName":
				placeholder = "例如：小学语文"
				break;
			case "no":
				placeholder = "例如：94477851492352"
				break;
		}
		$("#searchInputVal").attr("placeholder",placeholder)

	});
	$('#searchBtn').click(function(){

		var inputVal = $("#searchInputVal").val();

		var startTime = $("#sdate").val();
		if(startTime ==''){
			startTime ="2014-09-08";
		}

		var endTime = $("#edate").val();

		var str = searchConditionVal+ "::"+inputVal;
		var cUrl;
		if(newUrl.indexOf("str")==-1){
			if(inputVal !=''){
				cUrl=newUrl+'&str='+str;
			}else{
				cUrl=newUrl
			}
			newUrl = cUrl;
		}else{
			cUrl = delQueStr(newUrl,"str")+'&str='+str;
			newUrl = cUrl;
		}
		if(newUrl.indexOf("startTime")==-1){
			cUrl=newUrl+'&startTime='+startTime;
			//时间搜索时 需要将page参数 默认为1；
			cUrl=delQueStr(cUrl,"page")+'&page=1';
			newUrl = cUrl;
		}else{
			cUrl = delQueStr(newUrl,"startTime")+'&startTime='+startTime;
			//时间搜索时 需要将page参数 默认为1；
			cUrl=delQueStr(cUrl,"page")+'&page=1';
			newUrl = cUrl;
		}

		if(newUrl.indexOf("endTime")==-1){
			cUrl=newUrl+'&endTime='+endTime;
			newUrl = cUrl;
		}else{
			cUrl = delQueStr(newUrl,"endTime")+'&endTime='+endTime;
			newUrl = cUrl;
		}

		queryData(newUrl,1)

	});
}, false);



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