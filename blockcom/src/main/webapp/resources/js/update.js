$(function(){
	$("#btn_list").click(function(){
		location.href="/blockcom/boardlist?bf_cate_idx="+$("#bf_cate_idx").val()+"&page=1&perPageNumber=10&pageNumber=5";
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
		
		var formData = new FormData($("#form")[0]);
		formData.append("bf_contents", CKEDITOR.instances.bf_contents.getData());
		
		if($("#selectSecure").is(":checked")) {
			formData.append("use_sec", 'Y');
		} else {
			formData.append("use_sec", 'N');
		}
		
		$.ajax({
			type : "POST",
			url  : "/blockcom/boardupdate",
			data : formData,
			processData : false,
            contentType : false,
			success : function(response){
				if(response == "true") {
					alert("게시물이 수정되었습니다.");
					location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&bf_cate_idx="+$("#bf_cate_idx").val()+"&rpage=1";
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
			location.href="/blockcom/boardread?bf_idx="+$("#bf_idx").val()+"&bf_cate_idx="+$("#bf_cate_idx").val()+"&rpage=1";
		}
	})
	
	$(".fileDelBtn").click(function(){
		$(this).hide();
		var bff_idx = $(this).val();
		var bf_idx = $("#bf_idx").val();
		var data = {};
		data["bff_idx"] = bff_idx;
		data["bf_idx"] = bf_idx;
		if(confirm("파일을 삭제하시겠습니까?")) {
			$.ajax({
				type : "POST",
				url  : "/blockcom/fileDelete",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("파일이 삭제되었습니다.");
					}
					else if(response == "false")
						alert("DB Delete Error!");
				},
				error : function(){
					console.log('error');
				}
			});
		}
	});
	
	$("#addRow").click(function(){
		
		if($("#file").val() != "") {
			var table = document.getElementById("update");
			var trIdx = $('#update tr:last').attr('id');
			var row = table.insertRow(trIdx);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			cell1.innerHTML = "파일첨부";
			cell2.innerHTML = "<input type='file' id='file' name='file' multiple><input type='button' id='addRow' name='addRow' style='float:right;' value='추가' >";
			$(this).hide();
		}
	});
});