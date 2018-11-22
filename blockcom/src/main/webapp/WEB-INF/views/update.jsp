<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Read</title>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="/blockcom/resources/js/ckeditor/ckeditor.js"></script>
<script>
	$(function(){
		$("#btn_list").click(function(){
			location.href="/blockcom/boardlist?bf_cate_idx=${read.bf_cate_idx}";
		});	
	});
</script>
<script>
	$(function(){
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
			data["bf_idx"] = ${read.bf_idx};
			data["bf_title"] = $("#bf_title").val();
			data["bf_contents"] = CKEDITOR.instances.bf_contents.getData();
			data["use_sec"] = 'N';
			if($("#selectSecure").is(":checked")) {
				data["use_sec"] = 'Y';
			}
			var bf_cate_idx = ${read.bf_cate_idx};
			$.ajax({
				type : "POST",
				url  : "/blockcom/boardupdate",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("게시물이 수정되었습니다.");
						location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx;
					}
					else if(response == "false")
						alert("DB Update Error!");
				},
				error : function(){
					console.log('error');
				}
			});
		})
	})
	$(function(){
		$("#btn_cancel").click(function(){
			if(confirm("취소하시겠습니까?")) {
				location.href="/blockcom/boardread?bf_idx=${read.bf_idx}";
			}
		})
	})
</script>
</head>
<body>
<h2>게시글 수정</h2>
<form id="form" name="form" method="post" action="boardupdate" accept-charset="UTF-8">
	<p>비밀글<input type="checkbox" id="selectSecure" name="selectSecure" /></p>
	<table border="1" style="width:600px">
		<colgroup>
			<col width='15%' />
			<col width='*%' />
		</colgroup>
		<tbody>
			<tr>
				<td>작성일자</td>
				<c:set var="date" value="${read.mod_date}" />
				<c:if test= "${date == null}"><td><fmt:formatDate value="${read.reg_date}" pattern="yyyy-MM-dd a HH:mm" /></td></c:if>
				<c:if test= "${date != null}"><td><fmt:formatDate value="${read.mod_date}" pattern="yyyy-MM-dd a HH:mm" /></td></c:if>		
			</tr>
			<tr>
				<td>작성자</td> 
				<td><input type="text" id="mem_name" name="mem_name" readonly size="20" maxlength="20" c:out value="${read.mem_name}" /></td> 
			</tr>
			<tr>
				<td>제목</td> 
				<td><input type="text" id="bf_title" name="bf_title" size="70" maxlength="250" c:out value="${read.bf_title}" /></td> 
			</tr>
			<tr>
				<td>내용</td> 
				<td><textarea id="bf_contents" name="bf_contents" rows="6" cols="60"></textarea></td>
				<script>
					CKEDITOR.replace('bf_contents');
				</script>
			</tr>
		</tbody>
	</table>
	<input type="hidden" id="bf_idx" name="bf_idx" value="${read.bf_idx}" />	
</form>
	 <button type = "button" id="btn_list">목록</button>
	 <button type = "button" id="btn_save">수정</button>
	 <button type = "button" id="btn_cancel">취소</button>	 
</body>
</html>