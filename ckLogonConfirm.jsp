<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
       <%@ page import="logon.*, java.util.* " %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원인증 확인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function(){
	$('#logout').click(function(){
		$('#form1').submit();
	});
})
</script>
</head>
<body>
<!-- 쿠키를 가져와서 해당 쿠키에 memId가 있는지 확인 -->
<!-- 있으면 회원이 맞고 연결(세션도)남아 있으므로 유지-->

	<%
		String id="1";
		//모든 쿠키를 배열로 만든다.
		
		Cookie[] cookies = request.getCookies();
	
		if(cookies!=null){
				//memId가 있는지 확인해서 해당 id가져오기
				for(Cookie ck: cookies){
					if(ck.getName().equals("memId")){
						id=ck.getValue();
					}
				}
				if(id.equals("")){
					//로그인 화면으로 이동
					response.sendRedirect("cookieLogin.jsp");
					
				}
		}	else{
				//다시 로그인 화면으로 전송
					response.sendRedirect("cookieLogin.jsp");
		}
	%>
</body>
<!-- 회원이 맞으므로 세션(연결) 부여 -->
	<form action="cookieLogout.jsp" id="logout">
		<table>
			<tbody>
				<tr>
				<th>로그인 성공</th>
				<td><%=id %>님이 로그인하셨습니다.</td>
				</tr>
				
				<tr><th colspan="2">
					<div id="logout">로그아웃</div>
				</th>
				</tr>
			</tbody>
		
		</table>
	</form>

</html>

