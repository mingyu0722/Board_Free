<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Read</title>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script src="/blockcom/resources/js/jquery/autolink.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="/blockcom/resources/js/read.js"></script>
<style>

	#replyTable tr, td {
		border: 1px solid #ddd;
		text-align: left;
	}
	#replyTable {
		border-collapse: collapse;
		width: 600px;
	}
	
	.pagingUl {
		text-align: center;
	}
	.pagingUl li {
		display: inline-block;
		color: black;
		float: middle;
		padding: 8px 16px;
		text-decoration: none;
	}
</style>
</head>
<body>
<h2>게시글 보기</h2>
	<table border="1" id="detail" name="detail" style="width:600px">
		<colgroup>
			<col width='15%' />
			<col width='*%' />
		</colgroup>
		<tbody>
			<tr>
				<td>작성일자</td>
				<c:set var="date" value="${read.mod_date}" />
				<c:if test="${date == null}"><td><fmt:formatDate value="${read.reg_date}" pattern="yyyy-MM-dd a HH:mm" /></td></c:if>
				<c:if test="${date != null}"><td><fmt:formatDate value="${read.mod_date}" pattern="yyyy-MM-dd a HH:mm" /></td></c:if>
				<%-- <td><fmt:formatDate value="${read.reg_date}" pattern="yyyy-MM-dd a HH:mm" /></td> --%>			
			</tr>
			<tr>
				<td>작성자</td> 
				<td>${read.mem_name}</td> 
			</tr>
			<tr>
				<td>제목</td> 
				<td>${read.bf_title}</td> 
			</tr>
			<tr>
				<c:set var="bf_contents" value="${read.bf_contents}"></c:set>
				<c:if test="${bf_contents != '등록되지 않은 글입니다.'}">
					<td colspan="2" style="word-break:break-all; word-wrap:break-word;">
						<c:set var="file" value="${file}" />
						<c:if test="${file != null}">
							<c:forEach var="row" items="${file}">
								<c:if test="${row.file_ext == 'jpg' || row.file_ext == 'png' || row.file_ext == 'gif'}">
									<img src="${row.file_path}" style="width:200px;height:200px;" value="${row.file_name}"/>
								</c:if>
							</c:forEach>	
						</c:if>
						<p id="bf_contents">${read.bf_contents}</p>
					</td>
				</c:if>
				<c:if test="${bf_contents == '등록되지 않은 글입니다.'}">
					<script>
						alert("등록되지 않은 글입니다.");
						history.back();
					</script>
				</c:if>
			</tr>
			<c:set var="file" value="${file}" />
			<c:if test="${file != null}">
				<c:forEach var="row" items="${file}">
					<tr>
						<td>첨부파일</td>
						<td>
							<a href="${row.file_path}" download>${row.file_name}</a>&nbsp<font size="1em">(${row.file_size}KB)</font>
						</td>
					</tr>
				</c:forEach>	
			</c:if>
		</tbody>
	</table>

	<input type="hidden" id="bf_idx" value="${read.bf_idx}" />
	<input type="hidden" id="mem_idx" value="${read.mem_idx}" />
	<input type="hidden" id="bf_cate_idx" value="${read.bf_cate_idx}" />
	<input type="hidden" id="memIdx" value="${memIdx}" />
	
	<button type = "button" id="btn_list">목록</button>
	
	<c:set var="memIdx" value="${memIdx}" />
	<c:set var="mem_idx" value="${read.mem_idx}" />
		<c:choose>
			<c:when test="${memIdx eq mem_idx || memIdx eq 1}">
				<button type = "button" id="btn_delete">삭제</button>	
				<button type = "button" id="btn_update">수정</button>	
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	<c:set var="rec_flag" value="${rec_flag}" />
		<c:if test="${rec_flag}"><input type="checkbox" id="recommend" checked="checked" onchange="delRecommend()">추천</></c:if>
		<c:if test="${rec_flag eq false}"><input type="checkbox" id="recommend" onchange="recommend()">추천</></c:if>
	<br></br>
	<table border="1" style="width:600px" name="preNextArticle">
		<tr>
			<td>다음글</td>
	 			<c:set var="nextArticle" value="${nextArticle}" />
	 				<c:if test="${nextArticle == null}">
	 						<td colspan="2">다음글이 없습니다.</td>
	 				</c:if>
	 				<c:if test="${nextArticle != null}">
	 					<c:set var="use_sec" value="${nextArticle.use_sec}" />
							<c:if test="${use_sec == 'Y' }"><td><a href="#" onclick="nextArticle(); return false;">(비밀글)${nextArticle.bf_title}</td></c:if>
							<c:if test="${use_sec == 'N' }"><td><a href="#" onclick="nextArticle(); return false;">${nextArticle.bf_title}</td></c:if>
	 						<td>작성자: ${nextArticle.mem_name}</td>
	 				</c:if>
	 			<input type="hidden" id="next_mem_idx" value="${nextArticle.mem_idx}" />
	 			<input type="hidden" id="next_use_sec" value="${nextArticle.use_sec}" />
	 			<input type="hidden" id="next_bf_idx" value="${nextArticle.bf_idx}" /> 							
	 		</tr>	
	 		<tr>
	 			<td>이전글</td>
	 			<c:set var="preArticle" value="${preArticle}" />
	 				<c:if test="${preArticle == null}"><td colspan="2">이전글이 없습니다.</td></c:if>
	 				<c:if test="${preArticle != null}">
	 					<c:set var="use_sec" value="${preArticle.use_sec}" /> 				
							<c:if test="${use_sec == 'Y'}"><td><a href="#" onclick="preArticle(); return false;">(비밀글)${preArticle.bf_title}</a></td></c:if>
							<c:if test="${use_sec == 'N'}"><td><a href="#" onclick="preArticle(); return false;">${preArticle.bf_title}</td></c:if>
	 						<td>작성자: ${preArticle.mem_name}</td>
	 				</c:if>
	 			<input type="hidden" id="pre_mem_idx" value="${preArticle.mem_idx}" />
	 			<input type="hidden" id="pre_use_sec" value="${preArticle.use_sec}" />
	 			<input type="hidden" id="pre_bf_idx" value="${preArticle.bf_idx}" /> 
	 		</tr>
	 	</table>	 	
	 <br>
	 <h3>댓글</h3>
	 	<textarea id="bfr_contents" rows="4" cols="80" placeholder="댓글 입력(최대 300자)"></textarea>
	 	<br>
	 	<button type="button" id="reply_btn">확인</button>
	 	<br></br>
	 	<c:forEach var="replyList" items="${replyList}">
	 	<table border="1" id="replyTable">
	 		<tr>
	 			<td rowspan="3">
	 				<textarea readonly class="bfr_contents_${replyList.bfr_idx}" rows="5" cols="60">${replyList.bfr_contents}</textarea>
	 				<button type="button" class="save_btn_${replyList.bfr_idx}" style="display:none;">확인</button>
	 			</td>
	 			<td colspan="2">작성자: ${replyList.mem_name}</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">작성일:
	 				<c:set var="date" value="${replyList.mod_date}" />
					<c:if test= "${date == null}"><fmt:formatDate value="${replyList.reg_date}" pattern="yyyy-MM-dd" /></c:if>
					<c:if test= "${date != null}"><fmt:formatDate value="${replyList.mod_date}" pattern="yyyy-MM-dd" /></c:if>
				</td>
	 		</tr>
	 		<tr>
	 			<c:set var="memIdx" value="${memIdx}" />
				<c:set var="mem_idx" value="${replyList.mem_idx}" />
				<c:choose>
					<c:when test="${memIdx eq mem_idx || memIdx eq 1}">
						<td><button class="modify_btn" type="button" value="${replyList.bfr_idx}">수정</button></td>
	 					<td><button class="delete_btn" type="button" value="${replyList.bfr_idx}">삭제</button></td>
					</c:when>
					<c:otherwise></c:otherwise>				
				</c:choose>
	 		</tr>
	 		<input type="hidden" class="mem_idx_${replyList.bfr_idx}" value="${replyList.mem_idx}" />
	 	</table>
	 	</c:forEach>
	 	<ul class="pagingUl">
		<c:if test="${prev}">
			<li><a 
				href='boardread?bf_idx=${read.bf_idx}&bf_cate_idx=${read.bf_cate_idx}&rpage=${rpage - 1}'>
				&laquo;
				</a>
			</li>
		</c:if>
		<c:forEach begin="${startPage}" end="${endPage}" var="idx">
			<li
				class='<c:out value="${idx == rpage? 'current':''}" />'>
				<a href='boardread?bf_idx=${read.bf_idx}&bf_cate_idx=${read.bf_cate_idx}&rpage=${idx}'>${idx}</a>
			</li>
		</c:forEach>
		<c:if test="${next}">
			<li><a href='boardread?bf_idx=${read.bf_idx}&rpage=${rpage + 1}'>&raquo;</a></li>
		</c:if>
		</ul>
</body>
</html>