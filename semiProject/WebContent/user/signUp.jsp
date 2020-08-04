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
         <a href="/semiProject/index.jsp">
            <img src="../로고1.png" style="width:200px; padding:15px;">
         </a>
      </div>
      <div id="divMenu"><jsp:include page="../menu.jsp"/></div>
      <div id="divHeader"><h2>SIGN UP</h2></div>
      <!-- 여기부터 내용출력 시작 -->
            <form name="frm" action="signUp" method="post">
               <table  id="loginTbl">
                  <tr>
					<td class="title" width=300 colspan=2>
						<h2>회원가입</h2>
					</td>
				  </tr>
                  <tr class="row">
                     <td class="title">아이디: </td>
                     <td><input type="text" name="id"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">비밀번호: </td>
                     <td><input type="password" name="pass"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">이름: </td>
                     <td><input type="text" name="name"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">나이: </td>
                     <td><input type="text" name="age"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">이메일: </td>
                     <td><input type="text" name="email"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">핸드폰번호: </td>
                     <td><input type="text" name="phone"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">생일: </td>
                     <td><input type="text" name="birth"></td>
                  </tr>
                  <tr class="row">
                     <td class="title">주소: </td>
                     <td><input type="text" name="address"></td>
                  </tr>
                  <tr class="title">
					<td colspan=2 id="btnLogin" style="text-align: center; padding:5px;">
						<input type="submit" value="저장">
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
		location.href="login.jsp";
	});

   $(frm).submit(function(e){
      e.preventDefault();
      if(!confirm("등록 하시겠습니까?")) return;
      var id=$(frm.id).val();
      var pass=$(frm.pass).val();
      var name=$(frm.name).val();
      var age=$(frm.age).val();
      var email=$(frm.email).val();
      var phone=$(frm.phone).val();
      var birth=$(frm.birth).val();
      var address=$(frm.address).val();
      
      if(id=="" || pass=="" || name=="" || age=="" || email=="" || phone=="" || birth=="" || address==""){
         alert("빈칸을 입력하시길 바랍니다.");
      }else{
         $.ajax({
            type:"post",
            url:"/semiProject/user/signUp",
            data:{"id":id, "pass":pass,"name":name, "age":age, "email":email, "phone":phone, "birth":birth, "address":address},
            dataType:"json",
            success:function(data){
               if(data.count==0){
                  alert("회원가입 되었습니다.");
                  location.href="/semiProject/user/login.jsp";
               }else{
                  alert("이미 등록된 회원입니다.");
                  $(frm.id).focus();
               }
            }
         });
      }
   });
</script>
</html>