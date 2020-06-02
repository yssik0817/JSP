<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="board.BoardDAO" %>
    <%request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		int num = Integer.parseInt(request.getParameter("num"));
		//DAO 가져오기
		BoardDAO dao = BoardDAO.getInstance();
		//DAO 메소드 실행
		int r = dao.deleteArticle(num);
		//성공하면 boardList.jsp
		if(r>0) response.sendRedirect("boardList.jsp");//성공
		else out.print("삭제 실패");
		
	%>
</body>
</html>