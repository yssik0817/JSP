<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.*, java.util.*" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용 </title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="../board/boardScript.js"></script>
<!-- <script type="text/javascript">
function sendData(){
   if(document.writeForm.subject.value==""){
      alert("제목은 필수입니다.");
      document.writeForm.subject.focus();
      return false;
   }
   
   if(document.writeForm.writer.value==""){
      alert("작성자는 필수입니다.");
      document.writeForm.writer.focus();
      return false;
   }
   
   if(document.writeForm.content.value==""){
      alert("내용은 필수입니다.");
      document.writeForm.content.focus();
      return false;
   }
   
   if(document.writeForm.passwd.value==""){
      alert("비밀번호는 필수입니다.");
      document.writeForm.passwd.focus();
      return false;
   }
}
   function pwCheck(){

      window.name = "parentForm";
      window.open("idCheck.jsp", "idCheck","width=500","height=200"
            ,"resizable=no","scrollbars=no");
   }
   function setParentText(){
      opener.document.parentForm.cpass.value=
         document.getElementById("pwCheck").value;
   
}

</script> -->
<script type = "text/javascript">
/* function pwCheck(){
      document.confirm.submit();
   } */
   </script>
<!-- <link href="./css/board.css" rel="stylesheet"> -->
</head>
<body>
   <%
   //이전페이지에서 보내준 데이터 받기
   int num = Integer.parseInt(request.getParameter("num"));
   //실제데이터 가져오기
   //DAO사용.. 인스턴스얻어오기
    BoardDAO dao = BoardDAO.getInstance();
   //해당 인스턴스에서 해당되는 메소드 실행
   BoardDTO dto = dao.getArticle(num);
   //setAttribute를 해줘야 됨
     request.setAttribute("dto",dto);
   
   %>
   <table border=1>
         <thead>
            <tr>
               <th colspan=2>
                  <h3>글쓰기</h3>
               </th>
            </tr>
         </thead>
         <tbody>
            <tr>
               <th>제목 : </th>
               <td><c:out value="${dto.subject}"/></td>
            </tr>
            <tr>
               <th>내용 : </th>
               <td><textarea cols="80" rows="20" readonly="readonly">
               <c:out value="${dto.content}"/></textarea></td>
            </tr>
            <tr>
               <th>첨부파일 :</th>
               <td><c:out value="${dto.attachNm}"/></td>
            </tr>
            <tr>
               <th>작 성 자 :</th>
               <td><a href = 'mailto:<c:out value="${dto.email}"/>'>
                       <c:out value="${dto.writer}"/> </td>
            </tr>
           <%--  <tr>
               <th>이 메 일 :</th>
               <td><c:out value="${dto.email}"/></td>
            </tr>
            --%>
            <tr>
               <td colspan="2" align = "center">
               <input type="button" value="글 수정" onclick="pwCheck('u');"/>
               
               <input type="button" value="글 삭제"onclick="pwCheck('d');"/>
               <input type="button" value="글 목록으로" 
               onclick="javascript:location.href='boardList.jsp'" />
               </td>
            </tr>
         </tbody>
      </table>
      <form action="" name = "parentForm" method="post">
         <input type="text" name = "passwd" value = "${dto.passwd }">
         <input type="hidden" name = "num" value = "${dto.num }">
         <input type="hidden" name = "cpass" value = "">
      </form>

</body>
</html>