<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Read</title>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript"
	src="/blockcom/resources/js/ckeditor/ckeditor.js"></script>
<script src="/blockcom/resources/js/update.js"></script>
</head>
<body>
	<h2>게시글 수정</h2>
	<form id="form" name="form" method="post" action="boardupdate"
		accept-charset="UTF-8" enctype="multipart/form-data">
		<p>
			비밀글<input type="checkbox" id="selectSecure" name="selectSecure" />
		</p>
		<table border="1" id="update" style="width: 600px">
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<tr>
					<td>작성일자</td>
					<c:set var="date" value="${read.mod_date}" />
					<c:if test="${date == null}">
						<td><fmt:formatDate value="${read.reg_date}"
								pattern="yyyy-MM-dd a HH:mm" /></td>
					</c:if>
					<c:if test="${date != null}">
						<td><fmt:formatDate value="${read.mod_date}"
								pattern="yyyy-MM-dd a HH:mm" /></td>
					</c:if>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" id="mem_name" name="mem_name" readonly
						size="20" maxlength="20" c:out value="${read.mem_name}" /></td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" id="bf_title" name="bf_title" size="65"
						maxlength="200" c:out value="${read.bf_title}" /></td>
				</tr>
				<tr>
					<c:set var="auth" value="${read.bf_contents}" />
				<tr>
					<c:if test="${auth == '수정할 수 없습니다.'}">
						<td colspan="2">${read.bf_contents}</td>
					</c:if>
					<c:if test="${auth != '수정할 수 없습니다.'}">
						<td colspan="2"><textarea id="bf_contents" rows="6" cols="70">${read.bf_contents}</textarea></td>
						<script>
							CKEDITOR.replace('bf_contents', {
								enterMode : '2',
							});
						</script>
					</c:if>
				</tr>
				<c:set var="file" value="${file}" />
				<c:if test="${file != null}">
					<c:forEach var="row" items="${file}">
						<tr>
							<td>첨부파일</td>
							<td>${row.file_name}&nbsp<font size="1em">(${row.file_size}KB)</font>
								<button type="button" class="fileDelBtn" value="${row.bff_idx}">삭제</button>
								<input type="hidden" id="bff_idx" name="bff_idx"
								value="${row.bff_idx}" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td>파일첨부</td>
					<td><input type="file" id="file" name="file" multiple>
						<input type="button" id="addRow" name="addRow"
						style="float: right;" value="추가"></td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" id="bf_idx" name="bf_idx" value="${read.bf_idx}" />
		<input type="hidden" id="bf_cate_idx" name="bf_cate_idx"
			value="${read.bf_cate_idx}" /> <input type="hidden" id="mem_idx"
			name="mem_idx" value="${read.mem_idx}" />
	</form>
	<button type="button" id="btn_list">목록</button>
	<c:set var="auth" value="${read.bf_contents}" />
	<c:if test="${auth == '수정할 수 없습니다.'}"></c:if>
	<c:if test="${auth != '수정할 수 없습니다.'}">
		<button type="button" id="btn_save">수정</button>
		<button type="button" id="btn_cancel">취소</button>
	</c:if>
</body>
</html>