<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "logon.*, java.util.* " %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공 여부</title>
</head>
<body>
<!-- id, passwd를 받기 -->
<%
	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	
	// DAO호출 실행하고 결과 가지고 alert띄움
	LogonDAO dao= LogonDAO.getInstance();
	int c = dao.userCheck(id, passwd);
	// 회원이면 회원페이지로
	
	if(c ==1){
		//쿠키를 생성해서 저장하고 //클라이언트에 생성됨
		Cookie ck = new Cookie("memId",id);
		
		//쿠키가 살아있는 시간
		ck.setMaxAge(10*60);
		response.addCookie(ck);	//쿠키저장
		response.sendRedirect("ckLogonConfirm.jsp");
	}else{
		out.println("아이디가 없거나 비밀번호가 틀립니다.");
	}
	// 아니면 오류메세지 출력
%>
</body>
</html>