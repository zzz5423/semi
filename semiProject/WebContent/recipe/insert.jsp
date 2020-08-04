<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>레시피</title>
   <link rel="stylesheet" href="../home.css">
   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
</head>
<body>
   <div id="divPage">
      <div style="text-align:center;">
         <a href="/semiProject/index.jsp">
            <img src="../로고1.png" style="width:200px; padding:15px;">
         </a>
      </div>
      <div id="divMenu"><jsp:include page="../menu.jsp"/></div>
      <div id="divHeader"><h2>RECIPE REGISTRATION</h2></div>
      <!-- 여기부터 내용출력 시작 -->
      <form name="frm" action="insert" method="post" enctype="multipart/form-data">
         <div id="tbl1">
            <div style="margin-left:550px;">
               <input type="text" name="r_id" size=10 placeholder="레시피코드">
               <select name="category_id">
                  <option value="k">한식</option>
                  <option value="c">중식</option>
                  <option value="u">양식</option>
                  <option value="j">일식</option>
                  <option value="o">기타</option>
               </select>
            </div>
            <br>
            <div style="margin-left:230px;">
                 <img src="http://placehold.it/700x400" id="image" width=700>
               <input type="file" name="r_img" accept="image/*" style="visibility: hidden;">
            </div>
            <br>
            <div>
               <input type="text" name="r_name" size=100 placeholder="레시피 이름">
               <input type="text" name="u_id" size=10 value="${id}" readonly>
            </div>
            <br>
            <div>
               <textarea rows="30" cols="120" name="r_content" placeholder="레시피를 작성해주세요!"></textarea>
            </div>
         </div>
         <div id="pagination">
            <input type="submit" value="저장">
            <input type="reset" value="취소" id="btnReset">
         </div>
      </form>
      <!-- 여기부터 내용출력 종료 -->
      <div id="divFooter"><jsp:include page="../footer.jsp"/></div>
   </div>
</body>
<script>
	$(frm).on("click", "#btnReset", function(){
		location.href="/semiProject/user/mypage.jsp";
	});

	 $(frm).submit(function(e){
	      e.preventDefault();
	      var r_id=$(frm.r_id).val();
	      var r_name=$(frm.r_name).val();
	      var u_id=$(frm.u_id).val();
	      var r_content=$(frm.r_content).val();

	      $.ajax({
	         type:"get",
	         url:"/semiProject/recipe/check",
	         data:{"r_id":r_id},
	         dataType:"json",
	         success:function(data){
	            if(data.count==0){
	               if(!confirm("레시피를 등록하시겠습니까?"))return;
	               if(r_id=="" || r_name=="" || u_id=="" || r_content==""){
	                    alert("내용을 모두 입력해주세요!");
	                 }else{
	                    frm.submit();   
	                    alert("등록완료!");
	                 }
	            }else{
	               alert("중복된 레시피 코드입니다.");
	            }
	         }
	      });
	   });

   // 등록할 이미지 불러오기
   $("#image").on("click", function(){
      $(frm.r_img).click();
   });
   $(frm.r_img).on("change", function(){
      var reader=new FileReader();
      reader.onload=function(e){
         $("#image").attr("src", e.target.result);
      }
      reader.readAsDataURL(this.files[0]);
   });
</script>
</html>