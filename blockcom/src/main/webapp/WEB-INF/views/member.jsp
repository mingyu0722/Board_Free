<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%> <!-- c:forEach 사용하기위한 태그 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Member</title>
<script src="/blockcom/resources/js/jquery/jquery-1.12.4.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="/blockcom/resources/js/member.js"></script>
</head>
<body>
	<h2>멤버 목록</h2>
	<table id="member" border="1" style="width:200px">
		<colgroup>
			<col width='5%' />
			<col width='8%' />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th> 
				<th>이름</th>
			</tr>
		</thead>
		<tbody>
 			<c:forEach var="member" items="${member}">
				<tr>
					<td>${member.mem_idx}</td>
					<td><a>${member.mem_name}</a></td>
				</tr>			
			</c:forEach>
		</tbody>	
	</table>
	<c:if test= "${fn:length(member) == 0}">등록된 회원이 없습니다.</c:if>	
</body>
</html>