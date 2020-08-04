<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
   <div id="divHeader"><h2>MY PAGE</h2></div>
   <!-- 여기부터 내용출력 시작 -->
   <div id="divHeader" style="margin-top:60px;"><h4>[ MY RECIPE ]</h4></div>
   <div id="divCondition">
      <div id="divSearch">
         <select id="selKey">
            <option value="r_name">레시피이름</option>
         </select>
         <input type="text" id="txtWord"> 
         <select id="selPerPage">
            <option value="8" selected>8개씩 출력</option>
            <option value="16">16개씩 출력</option>
            <option value="24">24개씩 출력</option>
         </select> 
         <input type="button" id="btnSearch" value="검색"> 
         <span style="font-size: 12px; color:white;">검색수: <b id="count"></b>건</span>
      </div>
      <div id="divSort">
         <select id="selDesc">
            <option value="">오름차순</option>
            <option value="desc">내림차순</option>
         </select>
      </div>
   </div>
   <div id="tbl"></div>
   <script id="temp" type="text/x-handlebars-template">
         {{#each list}}
            <div class="box">
               <div class="u_id">'<b>{{u_id}}</b>'님의 레시피</div>
               <div class="image"><img src="{{printImage r_img}}" width="150" height=140></div>
               <div class="r_id" style="visibility: hidden;">{{r_id}}</div>
               <div class="r_name">{{r_name}}</div>
            </div>
         {{/each}}
      </script>
   <div id="pagination">
      <button id="btnPre">◀</button>
      [<span id="curPage"></span>/<span id="totPage"></span>]
      <button id="btnNext">▶</button>
   </div>
   <div id="pagination">
     <input type="button" value="회원탈퇴" id="btnDelete">
     <input type="button" value="회원수정" id="btnUserUpdate">
   </div>
   <!-- 여기부터 내용출력 종료 -->
   <div id="divFooter"><jsp:include page="../footer.jsp"/></div>
</div>
</body>
<script>
var key, word, desc, perPage, page=1;
var u_id="${id}";
getList();
$("#pagination").hide();

//회원정보수정
$("#btnUserUpdate").on("click",function(){
   var u_id="${id}";
   location.href="/semiProject/user/read?u_id="+u_id;
})

$("#btnDelete").on("click", function(){
    if(!confirm("정말 탈퇴 하시겠습니까?")) return;
       $.ajax({
          type:"get",
          url:"/semiProject/user/userdelete",
          data:{"u_id":u_id},
          success:function(){
            alert("탈퇴가 완료되었습니다!");
             location.href="/semiProject/user/logout";
          }
       });
    });
    
//이미지 출력
Handlebars.registerHelper("printImage", function(r_img){
   var src;
   if(!r_img){
      src="http://placehold.it/300x200";
   }else{
      src="/img/recipe/" + r_img;
   }return src;
});

//내 레시피 정보읽기
$("#tbl").on("click", ".box .image", function(){
   var r_id=$(this).parent().find(".r_id").html();
   var u_id="${id}";
   location.href="/semiProject/bookmark/reciperead?r_id="+r_id+"&u_id="+u_id;
});

$("#selPerPage, #selOrder, #selDesc").change(function(){
   page=1;
   getList();
});

$("#btnSearch").on("click", function() {
   page = 1;
   getList();
});

$("#txtWord").keydown(function(key) {
   if (key.keyCode == 13) {
      page = 1;
      getList();
   }
});

$("#btnNext").click(function() {
   page++;
   getList();
});

$("#btnPre").click(function() {
   page--;
   getList();
});

//목록 출력을 위한 함수
function getList() {
   key = $("#selKey").val();
   word = $("#txtWord").val();
   desc = $("#selDesc").val();
   perPage = $("#selPerPage").val();
   var u_id="${id}";
   
   $.ajax({
      type : "get",
      url : "/semiProject/recipe/myrecipelist",
      data : {"key" : key, "word" : word, "page" : page, "perPage" : perPage, "desc" : desc, "u_id":u_id},
      dataType : "json",
      success : function(data) {
         var template = Handlebars.compile($("#temp").html());
         $("#tbl").html(template(data));
         // 현재 페이지가 1페이지인 경우
         if (data.page == 1)
            $("#btnPre").attr("disabled", true);
         else
            $("#btnPre").attr("disabled", false);
         // 현재 페이지가 마지막 페이지인 경우
         if (data.page == data.totPage)
            $("#btnNext").attr("disabled", true);
         else
            $("#btnNext").attr("disabled", false);
         $("#curPage").html(data.page);
         $("#totPage").html(data.totPage);
         $("#count").html(data.count);
         if(data.count>0){
             $("#pagination").show();
          }
      }
   });
}
</script>
</html>