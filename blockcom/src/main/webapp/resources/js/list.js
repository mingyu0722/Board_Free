$(function(){
	$("#btn_write").click(function(){
		location.href="/blockcom/boardwrite?bf_cate_idx="+$("#bf_cate_idx").val();
	});	
		
	$("#btn_free").click(function(){
		location.href="/blockcom/boardlist?bf_cate_idx=1&page=1&perPageNumber=10&blockSize=5";
	});	
		
	$("#btn_notice").click(function(){
		location.href="/blockcom/boardlist?bf_cate_idx=2&page=1&perPageNumber=10&blockSize=5";
	});
		
 	$("#list tr a").click(function(){
		var tr = $(this).parents("tr");
		var td = tr.children();
		var use_sec = td.eq(7).text();
		var mem_idx = td.eq(3).text();
		var bf_idx = td.eq(1).text();
		var bf_cate_idx = $("#bf_cate_idx").val();
		var data = {};
		data["mem_idx"] = mem_idx;
		if(use_sec == 'Y'){
			$.ajax({
				type : "POST",
				url  : "/blockcom/boardlist",
				data : data,
				success : function(response) {
					if(response == "true")
						location.href="/blockcom/boardread?bf_idx="+bf_idx+"&bf_cate_idx="+bf_cate_idx+"&rpage=1";
					else if(response == "false")
						alert("읽을 권한이 없습니다.");
				},
				error : function(){
					console.log('error');
				}	
			});
		} else if(use_sec == 'N') {
			location.href="/blockcom/boardread?bf_idx="+bf_idx+"&bf_cate_idx="+bf_cate_idx+"&rpage=1";
		}
		
		return false;    //이벤트 버블링 방지 : 주소창에 # 제거
	});
 		
 	$("#btn_member").click(function(){
		location.href="/blockcom/member";
	});
 		
 	$("#searchBtn").click(function(){
 		var searchCondition = $("#searchCondition").val();
 		var searchValue = $("#searchValue").val();
 		var bf_cate_idx = $("#bf_cate_idx").val();
 		if(searchValue == "") {
 			alert("검색어를 입력하세요.");
 			$("#searchValue").focus();
 			return;
 		}
 		location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx+"&searchCondition="+searchCondition+"&searchValue="+searchValue+"&page=1&perPageNumber=10&blockSize=5";
 	});
});

function ListCondition() {
	var searchCondition = $("#searchCondition").val();
	var searchValue = $("#searchValue").val();
	var bf_cate_idx = $("#bf_cate_idx").val();
	var perPageNumber = $("#listCondition").val();
	location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx+"&searchCondition="+searchCondition+"&searchValue="+searchValue+"&page=1&perPageNumber="+perPageNumber+"&blockSize=5";
}

function EnterFunc() {
	if(event.keyCode == 13) {
		var searchCondition = $("#searchCondition").val();
 		var searchValue = $("#searchValue").val();
 		var bf_cate_idx = $("#bf_cate_idx").val();
	 			
	 	location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx+"&searchCondition="+searchCondition+"&searchValue="+searchValue+"&page=1&perPageNumber=10&blockSize=5";
	}
};