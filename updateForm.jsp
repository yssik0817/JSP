<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="board.*, java.util.*" %>
    <%request.setCharacterEncoding("utf-8"); %>   
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 확인 </title>
</head>
<body>
   <form action="updateForm.jsp">
   <table>
   <tr>
   <td> 비밀번호 입력 : </td>
   <td><input type="password" name = "cpass"/></td>
   </tr>
   <tr>
      <td colspan=2>
      <input type="submit" value="확인"/>
      </td>
   </tr>
   </table>
   </form>
   <%
   String cpass = request.getParameter("cpass");
   String passwd = request.getParameter("passwd");
   String num = request.getParameter("num");
   
   if(cpass!=null){
      System.out.println(cpass);
      System.out.println(passwd);
      System.out.println(num);
   }else{
      System.out.println(passwd);
      System.out.println(num);
      request.setAttribute("passwd",passwd);
       request.setAttribute("num",num);
   }
   
   %>

</body>
</html>