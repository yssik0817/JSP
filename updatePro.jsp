<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="board.BoardDAO" %>
    <%request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 처리</title>
</head>
<body>
	<jsp:useBean id="dto" class="board.BoardDTO"/>
	<jsp:setProperty property="*" name="dto"/>
	<%
			//DAO에 대한 인스턴스 받아오기
			BoardDAO dao = BoardDAO.getInstance();
			//DAO에 해당 데이터 저장하는 로직을 만들고
			//그 로직을 사용한 후 
			int r = dao.boardUpdate(dto);
			//다음 페이지로 이동시킴
			if(r>0) response.sendRedirect("boardList.jsp");
			else out.println("저장 못함");
	%>
</body>
</html>