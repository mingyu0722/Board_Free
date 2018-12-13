<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript"
	src="/blockcom/resources/js/ckeditor/ckeditor.js"></script>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script src="/blockcom/resources/js/write.js"></script>
<title>Write</title>
</head>
<body>
	<h2>게시글 작성</h2>
	<form id="form" name="form" method="post" action="boardwrite"
		accept-charset="UTF-8" enctype="multipart/form-data">
		<p>
			비밀글<input type="checkbox" id="selectSecure" name="selectSecure" />
		</p>
		<table border="1" id="writeTable" style="width: 650px;">
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<tr>
					<td>제목</td>
					<td><input type="text" id="bf_title" name="bf_title" size="70"
						maxlength="250" placeholder="제목을 입력해주세요"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea id="bf_contents" rows="6" cols="70"></textarea></td>
					<script>
						CKEDITOR.replace('bf_contents', {
							enterMode : '2',
						});
					</script>
				</tr>
				<tr>
					<td>파일첨부</td>
					<td><input type="file" name="file" multiple> <input
						type="button" id="addRow" name="addRow" style="float: right;"
						value="추가"></td>
				</tr>
			</tbody>
		</table>
		<div style="width: 100px; text-align: center;">
			<button type="button" id="btn_save">확인</button>
			<button type="button" id="btn_cancel">취소</button>
		</div>
		<input type="hidden" id="bf_cate_idx" name="bf_cate_idx"
			value="${bf_cate_idx}" /> <input type="hidden" id="mem_idx"
			name="mem_idx" value="${mem_idx}" />
	</form>
</body>
</html>
