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
<script>
	$(function(){
		$("#btn_write").click(function(){
			location.href="/blockcom/boardwrite?bf_cate_idx=${bf_cate_idx}";
		});	
		
		$("#btn_free").click(function(){
			location.href="/blockcom/boardlist?bf_cate_idx=1";
		});	
		
		$("#btn_notice").click(function(){
			location.href="/blockcom/boardlist?bf_cate_idx=2";
		});
		
 		$("#list tr").click(function(){
			var tr = $(this);
			var td = tr.children();
			var use_sec = td.eq(7).text();
			var mem_idx = td.eq(3).text();
			var bf_idx = td.eq(1).text();
			var data = {};
			data["mem_idx"] = mem_idx;
			if(use_sec == 'Y'){
				$.ajax({
					type : "POST",
					url  : "/blockcom/boardlist",
					data : data,
					success : function(response) {
						if(response == "true")
							location.href="/blockcom/boardread?bf_idx="+bf_idx;
						else if(response == "false")
							alert("읽을 권한이 없습니다.");
					},
					error : function(){
						console.log('error');
					}	
				});
			} else if(use_sec == 'N') {
				location.href="/blockcom/boardread?bf_idx="+bf_idx;
			}
			
			return false;    //이벤트 버블링 방지 : 주소창에 # 제거
		});
 		
 		$("#btn_member").click(function(){
			location.href="/blockcom/member";
		});
 		
 		$("#searchBtn").click(function(){
 			var searchCondition = $("#searchCondition").val();
 			var searchValue = $("#searchValue").val();
 			var bf_cate_idx = ${bf_cate_idx};
 			
 			location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx+"&searchCondition="+searchCondition+"&searchValue="+searchValue;
 		});
	});
	function EnterFunc() {
		if(event.keyCode == 13) {
			var searchCondition = $("#searchCondition").val();
 			var searchValue = $("#searchValue").val();
 			var bf_cate_idx = ${bf_cate_idx};
	 			
	 		location.href="/blockcom/boardlist?bf_cate_idx="+bf_cate_idx+"&searchCondition="+searchCondition+"&searchValue="+searchValue;
		}
	};
</script>
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
		<c:forEach var="row" items="${list}">
			<tr>
				
				<td>${row.RNUM} </td>
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
		</c:forEach>	
		</tbody>
	</table>
	<c:if test= "${fn:length(list) == 0}"><br>등록된 게시글이 없습니다.<br><br></c:if>
	<button id="btn_write">글쓰기</button>
	${memIdx}
</body>
</html>
