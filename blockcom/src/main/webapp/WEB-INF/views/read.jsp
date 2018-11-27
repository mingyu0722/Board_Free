<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Read</title>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(function(){
	
	
	$("#btn_list").click(function(){
		location.href="/blockcom/boardlist?bf_cate_idx=${read.bf_cate_idx}";
	})
	$("#btn_update").click(function(){
		location.href="/blockcom/boardupdate?bf_idx=${read.bf_idx}";
	});		
	
	$("#btn_delete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			var data = {};
			data["bf_idx"] = ${read.bf_idx};
			data["mem_idx"] = ${read.mem_idx};
			$.ajax({
				type : "POST",
				url : "/blockcom/boardread",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("게시물이 삭제되었습니다.");
						location.href="/blockcom/boardlist?bf_cate_idx=${read.bf_cate_idx}";
					}
				},
				error : function(response){
					if(response == "false") 
						console.log('error');
				}
			});
		}
	});
	
	
});

//댓글관련 function
$(function(){	
	$("#reply_btn").click(function(){
		if($("#bfr_contents").val()==""){
			alert("내용을 입력하세요");
			$("#bfr_contents").focus();
			return;
		}
		
		var data = {};
		data["bf_idx"] = ${read.bf_idx};
		data["bfr_contents"] = $("#bfr_contents").val();
		data["mem_idx"] = ${mem_idx};
		$.ajax({
			type : "POST",
			url : "/blockcom/replyInsert",
			data : data,
			success : function(response){
				if(response == "true") {
					alert("댓글이 등록되었습니다.");
					location.href="/blockcom/boardread?bf_idx=${read.bf_idx}";
				}
			},
			error : function(){
				console.log('error');
			}
		});
	});
	
	$(".modify_btn").click(function(){
		var bfr_idx = $(this).val();
		var mem_idx = ${mem_idx};
		var reply_memIdx = $('.mem_idx_'+bfr_idx).val();
		if(mem_idx != reply_memIdx  && mem_idx != 1) {
			alert("수정 권한이 없습니다.");
			return;
		}
		
		$('.bfr_contents_'+bfr_idx).removeAttr("readonly");
		$('.bfr_contents_'+bfr_idx).focus();
		$('.save_btn_'+bfr_idx).removeAttr("style");
		
		$(".save_btn_"+bfr_idx).click(function(){
			if(confirm("수정하시겠습니까?")) {
				var bfr_contents = $('.bfr_contents_'+bfr_idx).val();
				var data = {};
				data["bfr_idx"] = bfr_idx;
				data["bfr_contents"] = bfr_contents;
				
				$.ajax({
					type : "POST",
					url : "/blockcom/replyUpdate",
					data : data,
					success : function(response){
						if(response == "true") {
							alert("댓글이 수정되었습니다.");
							location.href="/blockcom/boardread?bf_idx=${read.bf_idx}";
						}
					},
					error : function(){
						console.log('error');
					}
				});
			}
		});
	});
	
	$(".delete_btn").click(function(){
		var bfr_idx = $(this).val();
		var reply_memIdx = $('.mem_idx_'+bfr_idx).val();
		
		if(confirm("댓글을 삭제 하시겠습니까?")) {
			var data = {};
			data["bfr_idx"] = bfr_idx;
			data["mem_idx"] = reply_memIdx;
			$.ajax({
				type : "POST",
				url : "/blockcom/replyDelete",
				data : data,
				success : function(response){
					if(response == "true") {
						alert("댓글이 삭제되었습니다.");
						location.href="/blockcom/boardread?bf_idx=${read.bf_idx}";
					}
					else if(response == "Auth") {
						alert("삭제 권한이 없습니다.");
						return;
					}
				},
				error : function(){
					console.log('error');
				}
			});
		}
	});
});

function preArticle() {
	var use_sec = '${preArticle.use_sec}';
	var memIdx = '${memIdx}';
	if(use_sec == 'Y') {
		if(memIdx == '${preArticle.mem_idx}')
			location.href="/blockcom/boardread?bf_idx=${preArticle.bf_idx}";	
		else
			alert("(비밀글)읽을 권한이 없습니다.");			
	} else if(use_sec == 'N') {
		location.href="/blockcom/boardread?bf_idx=${preArticle.bf_idx}";
	}
};

function nextArticle() {
	var use_sec = '${nextArticle.use_sec}';
	var memIdx = '${memIdx}';
	if(use_sec == 'Y') {
		if(memIdx == '${nextArticle.mem_idx}') {
			location.href="/blockcom/boardread?bf_idx=${nextArticle.bf_idx}";
		}
		else {
			alert("(비밀글)읽을 권한이 없습니다.");
		}
	} else if(use_sec == 'N') {
		location.href="/blockcom/boardread?bf_idx=${nextArticle.bf_idx}";
	}
};
</script>
</head>
<body>
<h2>게시글 보기</h2>
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
				<td colspan="2" style="word-break:break-all; word-wrap:break-word;">${read.bf_contents}</td>
			</tr>
		</tbody>
	</table>
	
	<input type="hidden" name="bf_idx" value="${read.bf_idx}">
	<input type="hidden" name="mem_idx" value="${read.mem_idx}">
	<input type="hidden" name="bf_cate_idx" value="${read.bf_cate_idx}">
	
	<button type = "button" id="btn_list">목록</button>
	<c:set var="memIdx" value="${memIdx}" />
	<c:set var="mem_idx" value="${read.mem_idx}" />
		<c:choose>
			<c:when test="${memIdx eq mem_idx}">
				<button type = "button" id="btn_delete">삭제</button>	
				<button type = "button" id="btn_update">수정</button>	
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
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
	 			<input type="hidden" id="mem_idx" value="${nextArticle.mem_idx}" /> 							
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
	 			<input type="hidden" id="mem_idx" value="${preArticle.mem_idx}" />
	 		</tr>
	 	</table>
	 	
	 <br>
	 <h3>댓글</h3>
	 	<textarea id="bfr_contents" rows="4" cols="80" placeholder="댓글 입력(최대 300자)"></textarea>
	 	<br>
	 	<button type="button" id="reply_btn">확인</button>
	 	<br></br>
	 	<c:forEach var="replyList" items="${replyList}">
	 	<table border="1" style="width:600px" name="replyTable">
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
					<c:when test="${memIdx eq mem_idx}">
						<td><button class="modify_btn" type="button" value="${replyList.bfr_idx}">수정</button></td>
	 					<td><button class="delete_btn" type="button" value="${replyList.bfr_idx}">삭제</button></td>
					</c:when>
					<c:otherwise></c:otherwise>				
				</c:choose>
	 		</tr>
	 		<input type="hidden" class="mem_idx_${replyList.bfr_idx}" value="${replyList.mem_idx}" />
	 	</table>
	 	</c:forEach>
</body>
</html>