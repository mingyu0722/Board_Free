<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="/blockcom/resources/js/ckeditor/ckeditor.js"></script>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<title>Write</title>
<script>
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

			var data = {};
			data["bf_title"] = $("#bf_title").val();
			data["bf_contents"] = CKEDITOR.instances.bf_contents.getData();
			data["bf_cate_idx"] = ${bf_cate_idx}
			data["mem_idx"] = ${mem_idx};
			data["use_sec"] = 'N';
			if($("#selectSecure").is(":checked")) {
				data["use_sec"] = 'Y';
			}
			
			$.ajax({
				type : "POST",
				url  : "/blockcom/boardwrite",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("게시물이 등록되었습니다.");
						location.href="/blockcom/boardlist?bf_cate_idx=${bf_cate_idx}";
					}
					else if(response == "false")
						alert("DB Insert Error!");
				},
				error : function(){
					console.log('error');
				}
			});
		});
	});
	
	$(function(){
		$("#btn_cancel").click(function(){
			if(confirm("취소하시겠습니까?")) {
				location.href="/blockcom/boardlist?bf_cate_idx=${bf_cate_idx}";
			};
		});
	});
	
</script>
</head>

<body>
	<h2>게시글 작성</h2>
	<form id="form" name="form" method="post" action="boardwrite" accept-charset="UTF-8">
		<p>비밀글<input type="checkbox" id="selectSecure" name="selectSecure" /></p>		
		<table border="1" style="width: 650px;">
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<!-- <tr>
					<td>작성자</td> 
					<td><input type="text" name="brdwriter" size="20" maxlength="20"></td> 
				</tr> -->
				<tr>
					<td>제목</td>
					<td><input type="text" id="bf_title" name="bf_title" size="70" maxlength="250" placeholder="제목을 입력해주세요"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea id="bf_contents" name="bf_contents" rows="6" cols="70"></textarea></td> 
					<script>
						CKEDITOR.replace('bf_contents', {enterMode:'2'});
					</script>
				</tr>
			</tbody>
		</table>
		<div style="width: 100px; text-align: center;">
			<button type="button" id="btn_save">확인</button>
			<button type="button" id="btn_cancel">취소</button>
		</div>
	</form>
</body>
</html>
