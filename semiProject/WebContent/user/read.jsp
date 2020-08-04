<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피</title>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script
   src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<link rel="stylesheet" href="../home.css">
</head>
<body>
      <div id="divPage">
      <div style="text-align:center;">
         <a href="/semiProject/index.jsp">
            <img src="../로고1.png" style="width:200px; padding:15px;">
         </a>
      </div>
      <div id="divMenu"><jsp:include page="../menu.jsp"/></div>
      <div id="divHeader"><h2>RECIPE INFORMATION</h2></div>
      <!-- 여기부터 내용출력 시작 -->
      <form name="frm" action="update" method="post" enctype="multipart/form-data">
         <div id="tbl1">
            <div>
                <c:if test="${vo.r_img==null}"><img src="http://placehold.it/150x120" id="image" width=150 /></c:if>
				<c:if test="${vo.r_img!=null}"><img src="/img/recipe/${vo.r_img}" id="image" width=150 /></c:if>
				<input type="file" name="image" accept="image/*" style="visibility: hidden;"> <br>
               <br>
               <input type="file" name="image" accept="image/*" style="visibility: hidden;">
            </div>
            <br>
            <div>
               <h2>${vo.r_name}</h2>
            </div>
             <hr>
            <div>
               <h2>${vo.r_content}</h2>
            </div>
            <hr>
         </div>
      </form>
      		<input type="submit" value="수정" id="btnUpdate">
			<input type="button" value="삭제" id="btnDelete">
      <!-- 여기부터 내용출력 종료 -->
      <div id="divFooter"><jsp:include page="../footer.jsp"/></div>
   </div>

</body>
<script>
	$("#btnDelete").on("click", function(){
		if(!confirm("삭제하시겠습니까?"))return;
		var r_id="${vo.r_id}";
		
		$.ajax({
			type:"get",
			url:"/semiProject/user/delete",
			data:{"r_id":r_id},
			dataType:"json",
			success:function(data){
				if(data.count==0){
					alert("삭제되었습니다.");
					location.href="/semiProject/user/mypage.jsp";
				}else{
					alert("즐겨찾기목록에 있어서 삭제불가능");
				}
			}
		});
	});
	
   //이미지 출력
   Handlebars.registerHelper("printImage", function(image){
      var src;
      if(!image){
         src="http://placehold.it/300x200";
      }else{
         src="/img/img/" + image;
         alert(c_img);
      }return src;
   });
</script>
</html>