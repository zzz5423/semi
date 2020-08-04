<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>레시피</title>
	<link rel="stylesheet" href="../home.css">
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
</head>
<body>
	<div id="divPage">
		<div style="text-align:center;">
			<a href="/semiProject/index.jsp">
				<img src="../로고1.png" style="width:200px; padding:15px;">
			</a>
		</div>
		<div id="divMenu"><jsp:include page="../menu.jsp"/></div>
		<div id="divHeader"><h2>LOGIN</h2></div>
		<!-- 여기부터 내용출력 시작 -->
		<form name="frm" action="login" method="post">
			<table id="loginTbl">
				<tr>
					<td class="title" width=300 colspan=2>
						<h2>로그인</h2>
					</td>
				</tr>
				<tr>
					<td class="title" width=150>아이디</td>
					<td><input type="text" name="id" placeholder="ID"></td>
				</tr>
				<tr>
					<td class="title" width=150>비밀번호</td>
					<td><input type="password" name="pass" placeholder="PASS"></td>
				</tr>
				<tr class="title">
					<td colspan=2 id="btnLogin" style="text-align: center; padding:5px;">
						<input type="submit" value="로그인">
						<input type="button" id="btnSignUp" value="회원가입">
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
	// 로그인
	$(frm).submit(function(e){
		e.preventDefault();
		var id=$(frm.id).val();
		var pass=$(frm.pass).val();
		
		$.ajax({
			type:"post",
			url:"/semiProject/user/login",
			data:{"id":id, "pass":pass},
			dataType:"json",
			success:function(data){
				if(data.check==0){
					alert("아이디가 존재하지 않습니다.");
				}else if(data.check==1){
					alert("아이디와 비밀번호가 틀립니다.");
				}else if(data.check==3){
		               alert("탈퇴한 회원입니다!");
	            }else{
	               alert("로그인 성공!");
	               location.href="/semiProject/index.jsp";
	            }

			}
		});
	});
	
	// 회원가입
	$("#loginTbl").on("click", ".title #btnSignUp", function(){
		location.href="/semiProject/user/signUp.jsp";
	});
</script>
</html>