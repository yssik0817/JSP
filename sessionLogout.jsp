<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원인증 확인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style type="text/css">
   #logout{
      cursor: pointer;
   }
</style>
</head>
<body>
<!-- 쿠키를 가져와서 해당 쿠키에 memId가 있는지 확인 -->
<!-- 있으면 회원이 맞고 연결(세션도)남아 있으므로 유지 -->
<%
 session.invalidate();
%>
<script type="text/javascript">
	alert("로그아웃 이다!");
	location.href="sessionLogonConfirm.jsp"
</script>
</body>
 
</html>