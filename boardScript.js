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
      openWin = window.open("idCheck.jsp", "idCheck","width=500","height=200"
            ,"resizable=no","scrollbars=no");
   }
   function setParentText(object){
      opener.document.parentForm.cpass.value=
         document.getElementById("pwCheck").value;
      window.close();
      compare();
   /*var pw = document.parentForm.passwd.value;
   var cpw =document.parentForm.cpass.value;
   alert(pw+":"+cpw);
   if(pw==cpw){
      document.parentForm.submit();
      idCheck.window.close();
   }else{
      alert("비밀번호 오류");
      idCheck.window.close();
   }*/
      }
   function compare(){
      
      var pw = opener.document.parentForm.passwd.value;
      var cpw =opener.document.parentForm.cpass.value;
      //alert(pw+":"+cpw);
      if(pw==cpw){
         var f = opener.document.parentForm;
         //f.action = "updateForm.jsp";
         f.submit();
         idCheck.window.close();
         //opener.document.parentForm.submit();
      }else{
         alert("비밀번호 오류");
         idCheck.window.close();
   }
   
      
}
   
   
   