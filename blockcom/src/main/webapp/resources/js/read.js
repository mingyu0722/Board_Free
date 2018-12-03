$(function(){
	
	
	$("#btn_list").click(function(){
		location.href="/blockcom/boardlist?bf_cate_idx=1&page=1&perPageNumber=10&pageNumber=5";
	})
	$("#btn_update").click(function(){
		location.href="/blockcom/boardupdate?bf_idx="+$("#bf_idx").val();
	});		
	
	$("#btn_delete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			var bf_cate_idx = $("#bf_cate_idx").val();
			var data = {};
			data["bf_idx"] = $("#bf_idx").val();
			data["mem_idx"] = $("#mem_idx").val();
			$.ajax({
				type : "POST",
				url : "/blockcom/boardread",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("게시물이 삭제되었습니다.");
						location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx+"&page=1&perPageNumber=10&pageNumber=5";
					}
				},
				error : function(response){
					if(response == "false") 
						console.log('error');
				}
			});
		}
	});	
});

function recommend() {
	if($("#recommend").prop("checked", true)) {
		var data = {};
		data["bf_idx"] = $("#bf_idx").val();
		data["mem_idx"] = $("#memIdx").val();
		
		$.ajax({
			type : "POST",
			url : "/blockcom/recommend",
			data : data,
			success : function(response){
				alert("추천되었습니다.");
				location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&rpage=1";
				return;
			},
			error : function(response){ 
				console.log('error');
				return;
			}
		});
	}
}

function delRecommend() {
	if($("#recommend").prop('checked', false)) {
		var data = {};
		data["bf_idx"] = $("#bf_idx").val();
		data["mem_idx"] = $("#memIdx").val();
		
		$.ajax({
			type : "POST",
			url : "/blockcom/delRecommend",
			data : data,
			success : function(response){
				alert("추천이 취소되었습니다.");
				location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&rpage=1";
				return;
			},
			error : function(response){ 
				console.log('error');
				return;
			}
		});
	}
}

//댓글관련 function
$(function(){	
	$("#reply_btn").click(function(){
		if($("#bfr_contents").val()==""){
			alert("내용을 입력하세요");
			$("#bfr_contents").focus();
			return;
		}
		
		var data = {};
		data["bf_idx"] = $("#bf_idx").val();
		data["bfr_contents"] = $("#bfr_contents").val();
		data["mem_idx"] = $("#memIdx").val();
		$.ajax({
			type : "POST",
			url : "/blockcom/replyInsert",
			data : data,
			success : function(response){
				if(response == "true") {
					alert("댓글이 등록되었습니다.");
					location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&rpage=1";
				}
			},
			error : function(){
				console.log('error');
			}
		});
	});
	
	$(".modify_btn").click(function(){
		var bfr_idx = $(this).val();
		var mem_idx =  $("#memIdx").val();
		var reply_memIdx = $('.mem_idx_'+bfr_idx).val();
		
		$('.bfr_contents_'+bfr_idx).removeAttr("readonly");
		$('.bfr_contents_'+bfr_idx).focus();
		$('.save_btn_'+bfr_idx).removeAttr("style");
		
		$(".save_btn_"+bfr_idx).click(function(){
			if(confirm("수정하시겠습니까?")) {
				var bfr_contents = $('.bfr_contents_'+bfr_idx).val();
				var data = {};
				data["bfr_idx"] = bfr_idx;
				data["bfr_contents"] = bfr_contents;
				
				$.ajax({
					type : "POST",
					url : "/blockcom/replyUpdate",
					data : data,
					success : function(response){
						if(response == "true") {
							alert("댓글이 수정되었습니다.");
							location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&rpage=1";
						}
					},
					error : function(){
						console.log('error');
					}
				});
			}
		});
	});
	
	$(".delete_btn").click(function(){
		var bfr_idx = $(this).val();
		var reply_memIdx = $('.mem_idx_'+bfr_idx).val();
		
		if(confirm("댓글을 삭제 하시겠습니까?")) {
			var data = {};
			data["bfr_idx"] = bfr_idx;
			data["mem_idx"] = reply_memIdx;
			$.ajax({
				type : "POST",
				url : "/blockcom/replyDelete",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("댓글이 삭제되었습니다.");
						location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&rpage=1";
					}
					else if(response == "Auth") {
						alert("삭제 권한이 없습니다.");
						return;
					}
				},
				error : function(){
					console.log('error');
				}
			});
		}
	});
});

//이전글, 다음글
function preArticle() {
	var use_sec = $("#pre_use_sec").val();
	var memIdx = $("#memIdx").val();		//세션접속된 member
	var bf_idx = $("#pre_bf_idx").val();
	if(use_sec == 'Y') {
		if(memIdx == $("#pre_mem_idx").val())
			location.href="/blockcom/boardread?bf_idx="+bf_idx+"&rpage=1";	
		else
			alert("(비밀글)읽을 권한이 없습니다.");			
	} else if(use_sec == 'N') {
		location.href="/blockcom/boardread?bf_idx="+bf_idx+"&rpage=1";
	}
};

function nextArticle() {
	var use_sec = $("#next_use_sec").val();
	var memIdx =  $("#memIdx").val();		//세션접속된 member
	var bf_idx = $("#next_bf_idx").val();
	if(use_sec == 'Y') {
		if(memIdx == $("#next_mem_idx").val()) {
			location.href="/blockcom/boardread?bf_idx="+bf_idx+"&rpage=1";
		}
		else {
			alert("(비밀글)읽을 권한이 없습니다.");
		}
	} else if(use_sec == 'N') {
		location.href="/blockcom/boardread?bf_idx="+bf_idx+"&rpage=1";
	}
};