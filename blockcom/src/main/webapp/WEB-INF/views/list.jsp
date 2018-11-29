<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%> <!-- c:forEach 사용하기위한 태그 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Board List</title>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script src="/blockcom/resources/js/list.js"></script>
<style>
	.pagingUl li {
		display: inline-block;
	}
	.pagingUl li {
		color: black;
		float: left;
		padding: 8px 16px;
		text-decoration: none;
	}
</style>
</head> 
<body>
	<h2>게시글 목록</h2>
	<button type="button" id="btn_free" name="btn_free">자유게시판</button>
	<button type="button" id="btn_notice" name="btn_notice">공지사항</button>
	<button type="button" id="btn_member" name="btn_member">회원선택</button>
	<span class="select" name="searchCondition" style="width:110px; height:40px;">
		<input name="search_t" type="hidden">
		<select id="searchCondition" name="searchCondition">
			<option value="1">제목</option>
			<option value="2">날짜</option>
			<option value="3">작성자</option>
		</select>
	</span>
	<span class="input placeholder ml20" style="width:237px; height:40px;">
		<input type="text" name="searchValue" id="searchValue" class="i_text" onKeyPress="EnterFunc();"/>
	</span>
	<button type="button" id="searchBtn" class="btn_m btn_type3 bt_srch">검색</button>
	<br></br>
	전체 글 : ${totalCount}
	<table  border="1" id="list" name="list" style="width:800px">
		<colgroup>
			<col width='8%' />
			<col width='*%' />
			<col width='15%' />
			<col width='15%' />
			<col width='15%' />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th> 
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
		<%-- <c:forEach var="row" items="${recList}">
			<tr>
				<td><span style="color:blue">추천</span></td>
				<td style="display:none">${row.bf_idx}</td>
 				<c:set var="use_sec" value="${row.use_sec}" /> 				
				<c:if test="${use_sec == 'Y' }"><td>(비밀글)${row.bf_title} [${row.replyCnt}]</td></c:if>
				<c:if test="${use_sec == 'N' }"><td>${row.bf_title} [${row.replyCnt}]</td></c:if>
 				<td style="display:none">${row.mem_idx}</td>
				<td>${row.mem_name}</td>
				<c:set var="date" value="${row.mod_date}" />
				<c:if test= "${date == null}"><td><fmt:formatDate value="${row.reg_date}" pattern="yyyy-MM-dd" /></td></c:if>
				<c:if test= "${date != null}"><td><fmt:formatDate value="${row.mod_date}" pattern="yyyy-MM-dd" /></td></c:if>
				<td>${row.view_cnt}</td>
				<td style="display:none">${row.use_sec}</td>
				<td style="display:none">${row.mem_idx}</td>
			</tr>
		</c:forEach> --%>
		<c:forEach var="row" items="${list}">
			<tr>
				<td>${row.RNUM} </td>
				<td style="display:none">${row.bf_idx}</td>
 				<c:set var="use_sec" value="${row.use_sec}" /> 				
				<c:if test="${use_sec == 'Y' }">
					<td>
						<a href="#">
						(비밀글)${row.bf_title} [${row.replyCnt}]
						</a>						
					</td>
				</c:if>
				<c:if test="${use_sec == 'N' }">
					<td>
						<a href="#">
						${row.bf_title} [${row.replyCnt}]
						</a>
					</td>
				</c:if>
 				<td style="display:none">${row.mem_idx}</td>
				<td>${row.mem_name}</td>
				<c:set var="date" value="${row.mod_date}" />
				<c:if test= "${date == null}"><td><fmt:formatDate value="${row.reg_date}" pattern="yyyy-MM-dd" /></td></c:if>
				<c:if test= "${date != null}"><td><fmt:formatDate value="${row.mod_date}" pattern="yyyy-MM-dd" /></td></c:if>
				<td>${row.view_cnt}</td>
				<td style="display:none">${row.use_sec}</td>
				<td style="display:none">${row.mem_idx}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<c:if test= "${fn:length(list) == 0}"><br>등록된 게시글이 없습니다.<br><br></c:if>
	<button id="btn_write">글쓰기</button>
	<input type="hidden" id="bf_cate_idx" value="${bf_cate_idx}" />
	${memIdx}<br>

	<ul class="pagingUl">
		<c:if test="${prev}">
			<li><a 
				href='boardlist?bf_cate_idx=${bf_cate_idx}&page=${page - 1}&perPageNumber=${perPageNumber}&blockSize=${blockSize}'>
				&laquo;
				</a>
			</li>
		</c:if>
		<c:forEach begin="${startPage}" end="${endPage}" var="idx">
			<li
				class='<c:out value="${idx == page? 'current':''}" />'>
				<a href='boardlist?bf_cate_idx=${bf_cate_idx}&page=${idx}&perPageNumber=${perPageNumber}&blockSize=${blockSize}'>${idx}</a>
			</li>
		</c:forEach>
		<c:if test="${next}">
			<li><a href='boardlist?bf_cate_idx=${bf_cate_idx}&page=${page + 1}&perPageNumber=${perPageNumber}&blockSize=${blockSize}'>&raquo;</a></li>
		</c:if>
	</ul>
</body>
</html>
