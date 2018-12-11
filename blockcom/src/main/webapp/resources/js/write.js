$(function(){
	$("#btn_save").click(function(){
		if($("#bf_title").val()==""){
			alert("제목을 입력하세요");
			$("#bf_title").focus();
			return;
		}
		if(	CKEDITOR.instances.bf_contents.getData()==""){
			alert("내용을 입력하세요");
			$("#bf_contents").focus();
			return;
		}
		
		var formData = new FormData($("#form")[0]);
		formData.append("bf_contents", CKEDITOR.instances.bf_contents.getData());
		
		if($("#selectSecure").is(":checked")) {
			formData.append("use_sec", 'Y');
		} else {
			formData.append("use_sec", 'N');
		}
		
		$.ajax({
			type : "POST",
			url  : "/blockcom/boardwrite",
			data : formData,
			processData : false,
            contentType : false,
			success : function(response){
				if(response == "true") {
					alert("게시물이 등록되었습니다.");
					location.href="/blockcom/boardlist?bf_cate_idx="+$("#bf_cate_idx").val()+"&page=1&perPageNumber=10&pageNumber=5";
				}
				else if(response == "false")
					alert("DB Insert Error!");
			},
			error : function(){
				console.log('error');
			}
		});
	});
		
	$("#btn_cancel").click(function(){
		if(confirm("취소하시겠습니까?")) {
			location.href="/blockcom/boardlist?bf_cate_idx="+$("#bf_cate_idx").val()+"&page=1&perPageNumber=10&pageNumber=5";
		};
	});
	
	$("#addRow").click(function(){
		var table = document.getElementById("writeTable");
		var row = table.insertRow(3);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		cell1.innerHTML = "파일첨부";
		cell2.innerHTML = "<input type='file' name='file' multiple>";
	});
});