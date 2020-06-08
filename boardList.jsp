<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="board.*, java.util.*" %>
    <%request.setCharacterEncoding("utf-8"); %>   
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="./board2/css/board.css" rel="stylesheet">
<script type="text/javascript">
function callContent(num){
   	document.content.num.value=num;
   	document.content.submit();
	}
</script>
</head>
<body>
   <!-- 글목록 출력 -->
   
   
   <h3 style="text-align: center;">게시글목록</h3>
   
   <c:choose>
   <c:when test="${list.size()==0}">
   <table border="1">
      <tbody class = "class02">
         <tr>
         <th><h4>게시글이 없습니다.</h4></th>
         </tr>
      </tbody>
   </table>
   </c:when>
   <c:when test="${list.size()!=0}">
   <table border="1">
   <thead class = "class01">
   		<tr><td colspan="5">
   		<h4 style="text-align: right;">전체 글 개수:&nbsp;<c:out value="${pdto.allCount}"/>&nbsp;건</h4>
   		</td></tr>
   		
      <tr><td>글번호</td>
         <th>글제목</th>
         <td>글작성자</td>
         <td>작성일자</td>
         <td>조회수</td>
         </tr>
      <tbody class = "class02">
      
         <!-- 해당 리스트 출력 jstl사용 -->
      <c:forEach var="dto" items="${list}">
         <tr>
            <td><c:out value="${dto.rr}"/>
            <input type = "hidden" value = '<c:out value="${dto.num}"/>'>
            </td>
            <c:if test="${dto.re_level == 1 }">
            <td align="left">
            <a href='#' onclick="javascript:callContent('<c:out value="${dto.num}"/>');">
            <c:out value="${dto.subject}"/>
            </a></td>
            </c:if>
            <c:if test="${dto.re_level >1 }">
            <td align="left">
            <img src="./board2/images/level.gif" width = "${dto.re_level*5 }">
            <img src="./board2/images/re.gif">
            <a href='#' onclick="javascript:callContent('<c:out value="${dto.num}"/>');">
            <c:out value="${dto.subject}"/>
            </a></td>
            </c:if>
            <td >
            <a href='mailto:<c:out value="${ dto.email}"/>'>
            <c:out value="${dto.writer}"/>
            </a></td>
            <td><c:out value="${dto.reg_date}"/></td>
            <td><c:out value="${dto.readcnt}"/></td>
         </tr>
      </c:forEach>
      <tr>
         <td colspan="5" align="right">
         <a href = "writeForm.do">
         <input type = "button" value="글쓰기"/></a>
         </td>
      </tr>
      </tbody>
   </table>
   		<h3 style="text-align: center">
   			<c:if test="${pdto.startPage>pdto.pageBlock}">
   				<a href="/JSP/list.do?currentPage=${pdto.startPage-pageBlock}&currPageBlock=${pdto.currPageBlock-1}"
   				 style="text-decoration: none;">[이전]</a>
  			</c:if>
   			<c:forEach var="i" begin="${pdto.startPage}" end="${pdto.endPage}">
   				<a href="/JSP/list.do?currentPage=${i}&currPaageBlock=${pdto.currPageBlock}"
   					style="text-decoration: none;">
   				<c:out value="[${i}]"/>&nbsp;</a>
   			</c:forEach>
   			<c:if test="${pdto.endPage<pdto.allPage}">
   				<a href="/JSP/list.do?currentPage=${pdto.endPage+1}&currPageBlock=${pdto.currPageBlock+1}"
   				 style="text-decoration: none;">[다음]</a>
   			</c:if>
   		</h3>
   </c:when>
   </c:choose>
   
<!--    <form action="board">
   		<input type="hidden" name="currentPage"/>
   		<input type="hidden" name="currentBlock"/>
   </form> -->
   
   <form action="content.do" name = "content" method="post" >
   <input type = "hidden" name = "currentPage" value = '<c:out value="${pdto.currentPage}"/>'>
   <input type = "hidden" name = "currPageBlock" value = '<c:out value="${pdto.currPageBlock}"/>'>
   <input type = "hidden" name = "num" value = '<c:out value="${dto.num}"/>'>
   </form>
<%--       
   
   <c:forEach var="dto" items="${list}">
      <c:out value="${dto.writer}"/>
   </c:forEach>    --%>
   
   
   <!-- 해당 리스트를 출력 jstl사용 -->
   
</body>
</html>