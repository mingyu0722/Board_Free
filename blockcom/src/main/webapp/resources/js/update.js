$(function(){
	$("#btn_list").click(function(){
		location.href="/blockcom/boardlist?bf_cate_idx="+$("#bf_cate_idx").val();
	});
	
	$("#btn_save").click(function(){
		if($("#bf_title").val()==""){
			alert("제목을 입력하세요");
			$("#bf_title").focus();
			return;
		}
		if(CKEDITOR.instances.bf_contents.getData()==""){
			alert("내용을 입력하세요");
			$("#bf_contents").focus();
			return;
		}
			
		var data = {};
		data["bf_idx"] = $("#bf_idx").val();
		data["bf_title"] = $("#bf_title").val();
		data["bf_contents"] = CKEDITOR.instances.bf_contents.getData();
		data["use_sec"] = 'N';
		if($("#selectSecure").is(":checked")) {
			data["use_sec"] = 'Y';
		}
		
		$.ajax({
			type : "POST",
			url  : "/blockcom/boardupdate",
			data : data,
			success : function(response){
				if(response == "true") {
					alert("게시물이 수정되었습니다.");
					location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val();
				}
				else if(response == "false")
					alert("DB Update Error!");
			},
			error : function(){
				console.log('error');
			}
		});
	});
	
	$("#btn_cancel").click(function(){
		if(confirm("취소하시겠습니까?")) {
			location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val();
		}
	})
});