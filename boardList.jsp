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
<!-- <link href="/css/board.css" rel="stylesheet"> -->
<link rel="stylesheet" href="./css/board.css" >
</head>
<body>
	<!-- 글목록 출력 -->
	
	<%
		//게시글 리스트 갖고와서 저장 DAO
		BoardDAO dao = BoardDAO.getInstance();
	
		int sRow=1;
		int pageSize=20;
		List<BoardDTO> list = 
				dao.getArticles(sRow, pageSize); 	
		request.setAttribute("list",list);
	%>
	<h3>게시글 목록</h3>
  <!-- 해당 리스트를 출력하는 jstl 시용 -->
   <c:choose>
      <c:when test="${list.size()==0}">
         <table border="1" class="class01">
            <tbody>
               <tr>
                  <th><h4>게시글이 없습니다</h4></th>
               </tr>
            </tbody>
         </table>
      </c:when>
      <c:when test="${list.size()!=0}">
         <table border="1">
            <thead class="class02">
               <tr>
                  <td>글번호</td>
                  <td>글제목</td>
                  <td>작성자</td>
                  <td>작성일자</td>
                  <td>조회수</td>
               </tr>
            </thead>

            <tbody class="class03">
               <c:forEach var="dto" items="${list}">
                  <tr>
                     <td><c:out value="${dto.num }" /></td>
                     <td><a href='content.jsp?num=<c:out value="${dto.num }"/>'>
                           <c:out value="${dto.subject }" />
                     </a></td>
                     <td><a href='mailto: <c:out value="${dto.email}"/>'> 
                     <c:out  value="${dto.writer }" /></a></td>
                     <td><c:out value="${dto.reg_date }" /></td>
                     <td><c:out value="${dto.readcnt}" /></td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
      </c:when>
   </c:choose>

</body>
</html>