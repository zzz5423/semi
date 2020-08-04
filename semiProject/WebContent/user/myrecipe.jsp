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
      <div style="text-align: center;">
         <a href="/semiProject/index.jsp"> <img src="../로고1.png"
            style="width: 200px; padding: 15px;">
         </a>
      </div>
      <div id="divMenu"><jsp:include page="../menu.jsp" /></div>
      <div id="divHeader">
         <h2>RECIPE UPDATE</h2>
      </div>
      <!-- 여기부터 내용출력 시작 -->
      <form name="frm" action="/semiProject/recipe/update" method="post"
         enctype="multipart/form-data">
         <div id="tbl1">
            <c:if test="${v.r_img==null}">
               <img src="http://placehold.it/700x400" id="image" name=r_img />
            </c:if>
            <c:if test="${v.r_img!=null}">
               <img src="/img/recipe/${v.r_img}" id="image" name=r_img width=700 />
            </c:if>
            <br>
            <input type="file" name="r_img" accept="image/*" style="visibility: hidden;">
            <input type="text" name="r_id" value="${v.r_id}" style="visibility: hidden;">
            <input type="text" name="u_id" value="${id}" style="visibility: hidden;">
            <div>
               <input type="text" name="r_name" size=115 value="${v.r_name}">
            </div>
            <br>
            <div>
               <textarea rows="30" cols="120" name="r_content">${v.r_content}</textarea>
            </div>
         </div>
         <div id="pagination">
         	<input type="submit" value="수정" id="btnUpdate">
         	<input type="button" value="삭제" id="btnDelete">
         	<input type="reset" value="취소" id="btnReset">
         </div>
      </form>
      <!-- 여기부터 내용출력 종료 -->
      <div id="divFooter"><jsp:include page="../footer.jsp" /></div>
   </div>
</body>
<script>
	var r_id="${v.r_id}";

	$(frm).on("click", "#btnReset", function(){
		location.href="../user/mypage.jsp";
	});
	
   //등록할 이미지 불러오기
   $("#image").on("click", function() {
      $(frm.r_img).click();
   });
   $(frm.r_img).on("change", function() {
      var reader = new FileReader();
      reader.onload = function(e) {
         $("#image").attr("src", e.target.result);
      }
      reader.readAsDataURL(this.files[0]);
   });

   //수정버튼
   $(frm).submit(function(e){
       e.preventDefault();
       var r_id=$(frm.r_id).val();
      var r_name=$(frm.r_name).val();
      var r_img=$(frm.r_img).val();
      var u_id=$(frm.u_id).val();
      var r_content=$(frm.r_content).val();

       if(!confirm("레시피를 수정하시겠습니까?"))return;
            frm.submit();   
          alert("수정이 완료되었습니다.");
       
   });

   //삭제버튼
   $("#btnDelete").on("click", function(){
      if(!confirm("레시피를 삭제하시겠습니까?")) return;
      $.ajax({
         type:"post",
         url:"/semiProject/recipe/delete",
         data:{"r_id":r_id},
         dataType:"json",
         success:function(data){
            if(data.count==0){
               alert("삭제완료!");
               location.href="/semiProject/user/mypage.jsp";
            }else{
               alert("회원들이 즐겨찾기한 레시피이므로 삭제할 수 없습니다.");
            }
         }
      });
   });

   //이미지 출력
   Handlebars.registerHelper("printImage", function(image) {
      var src;
      if (!image) {
         src = "http://placehold.it/300x200";
      } else {
         src = "/img/img/" + image;
         alert(c_img);
      }
      return src;
   });
</script>
</html>