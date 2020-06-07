<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="logon.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공여부</title>
</head>
<body>
<!-- id, passwd를 받기 -->
<jsp:useBean id="dto" class="logon.LogonDTO"/>
<jsp:setProperty property="*" name="dto"/>
<%
// DAO호출 실행하고 결과 가지고 alert 띄움
   LogonDAO dao = LogonDAO.getInstance();
   int c = dao.userCheck(dto.getId(), dto.getPasswd());
// 회원이면 회원페이지로
if(c==1){
   //세션처리
   session.setAttribute("memId", dto.getId());
   response.sendRedirect("sessionLogonConfirm.jsp");
}else{
   out.println("아이디가 없거나 비밀번호가 틀립니다.");
}
//아니면 오류 메시지 출력
%>
</body>
</html>