<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>레시피</title>
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
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
            	<c:if test="${vo.r_img==null}"><img src="http://placehold.it/700x400" id="image"/></c:if>
				<c:if test="${vo.r_img!=null}"><img src="/img/recipe/${vo.r_img}" id="image" width=700 /></c:if>
            <br>
            <div>
               <h2>${vo.r_name}</h2>
            </div>
             <br>
            <div id="box">
				<pre>${vo.r_content}</pre>
			</div>
            <br>
         </div>
      </form>
      <br>
      <!-- 여기부터 내용출력 종료 -->
      <div id="divFooter"><jsp:include page="../footer.jsp"/></div>
   </div>
</body>
<script>
	
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