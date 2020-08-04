<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>레시피</title>
   <link rel="stylesheet" href="../home.css">
   <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
   <style>
         .row{text-align:left;}
   </style>
</head>
<body>
   <div id="divPage">
      <div style="text-align:center;">
         <a href="/semi/index.jsp">
            <img src="../로고1.png" style="width:200px; padding:15px;">
         </a>
      </div>
      <div id="divMenu"><jsp:include page="../menu.jsp"/></div>
      <div id="divHeader"><h2>INFORMATION UPDATE</h2></div>
      <!-- 여기부터 내용출력 시작 -->
            <form name="frm" action="update" method="post">
               <table  id="loginTbl">
                  <tr>
               <td class="title" width=300 colspan=2>
                  <h2>회원정보 수정</h2>
               </td>
              </tr>
                  <tr class="row">
                     <td class="title">아이디: </td>
                     <td><input type="hidden" name="id" value="${vo.u_id}">${vo.u_id}</td>
                  </tr>
                  <tr class="row">
                     <td class="title">비밀번호: </td>
                     <td><input type="password" name="pass" value="${vo.u_pass}"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">이름: </td>
                     <td><input type="text" name="name" value="${vo.u_name}"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">나이: </td>
                     <td><input type="text" name="age" value="${vo.u_age}"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">이메일: </td>
                     <td><input type="text" name="email" value="${vo.u_email}"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">핸드폰번호: </td>
                     <td><input type="text" name="phone" value="${vo.u_phone}"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">생일: </td>
                     <td><input type="hidden" name="birth" value="${vo.u_birth}">${vo.u_birth}</td>
                  </tr>
                  <tr class="row">
                     <td class="title">주소: </td>
                     <td><input type="text" name="address" value="${vo.u_address}"></td>
                  </tr>
                  <tr class="title">
               <td colspan=2 id="btnLogin" style="text-align: center; padding:5px;">
                  <input type="submit" value="수정">
                  <input type="reset" value="취소" id="btnReset">
               </td>
            </tr>
               </table>
            </form>
            <br><br><br>
      <!-- 여기부터 내용출력 종료 -->
      <div id="divFooter"><jsp:include page="../footer.jsp"/></div>
   </div>
</body>
<script>
	$(frm).on("click", "#btnReset", function(){
		location.href="mypage.jsp";
	});

   $(frm).submit(function(e){
      e.preventDefault();
      var id=$(frm.id).val();
      var pass=$(frm.pass).val();
      var name=$(frm.name).val();
      var age=$(frm.age).val();
      var email=$(frm.email).val();
      var phone=$(frm.phone).val();
      var birth=$(frm.birth).val();
      var address=$(frm.address).val();
      
      if(!confirm("회원정보를 수정하시겠습니까?"))return;
      if(pass==""){
    	  alert("비밀번호를 입력해주세요!");
      }else{
    	  frm.submit();   
          alert("수정이 완료되었습니다.");
      }
   });
</script>
</html>