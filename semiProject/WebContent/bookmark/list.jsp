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
		<div id="divHeader"><h2>FAVORITES</h2></div>
		<!-- 여기부터 내용출력 시작 -->
		<div id="divCondition">
			<div id="divSearch">
				<select id="selKey">
					<option value="r_id">레시피코드</option>
					<option value="b_name">레시피이름</option>
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
		<div style="width:800px; margin:auto;">
			<input type="button" value="선택삭제" id="btnSel">
		</div>
		<table id="tbl"></table>
		<script id="temp" type="text/x-handlebars-template">
			<tr class="title">
				<td width=20><input type="checkbox" id="chkAll"></td>
				<td width=130>레시피코드</td>
				<td width=300>이미지</td>
				<td>레시피이름</td>
				<td width=100>삭제</td>
			</tr>
			{{#each list}}
				<tr class="row">
					<td><input type="checkbox" class="chk"></td>
					<td class="r_id">{{r_id}}</td>
					<td class="image"><img src="{{printImage b_img}}" width="150" height=140></td>
					<td class="b_name">{{b_name}}</td>
					<td><button>삭제</button></td>
				</tr>
			{{/each}}
		</script>
		<div id="pagination">
			<button id="btnPre">◀</button>
			[<span id="curPage"></span>/<span id="totPage"></span>]
			<button id="btnNext">▶</button>
		</div>
		<br>
		<!-- 여기부터 내용출력 종료 -->
		<div id="divFooter"><jsp:include page="../footer.jsp"/></div>
	</div>
</body>
<script>
	var key, word, desc, perPage, page=1;
	getList();
	$("#pagination").hide();
	
	//선택삭제 버튼을 클릭했을 때
	$("#btnSel").on("click", function(){
		if($("#tbl .row .chk:checked").length>0){
			$("#tbl .row .chk:checked").each(function(){
				var r_id=$(this).parent().parent().find(".r_id").html();
				var u_id="${id}";
				
				$.ajax({
					type:"post",
					url:"/semiProject/bookmark/delete",
					data:{"r_id":r_id, "u_id":u_id},
					success:function(){}
				});
			})
			if(!confirm("선택한 레시피를 삭제하시겠습니까?")) return;
			alert("삭제완료!");
			location.href="/semiProject/bookmark/list.jsp";
		}else{
			alert("체크된 것이 한개도 없습니다.");
		}
	});
		
	
	//삭제버튼을 클릭한 경우
	$("#tbl").on("click", ".row button", function(){
		var r_id=$(this).parent().parent().find(".r_id").html();
		var u_id="${id}";
		if(!confirm("해당 레시피를 삭제하시겠습니까?"))return;
		
		$.ajax({
			type:"post",
			url:"/semiProject/bookmark/delete",
			data:{"r_id":r_id, "u_id":u_id},
			success:function(){
				alert("삭제완료!");
				location.href="/semiProject/bookmark/list.jsp";
			}
		});
	});
	
	//각 행의 체크버튼을 클릭한 경우
	$("#tbl").on("click", ".row .chk", function(){
		var isChkAll=true;
		$("#tbl .row .chk").each(function(){ 
			if(!$(this).is(":checked")) isChkAll=false; 
		});
		if(isChkAll){
			$("#tbl #chkAll").prop("checked", true);
		}else{
			$("#tbl #chkAll").prop("checked", false);
		}
	});

	//전체체크버튼을 클릭한 경우
	$("#tbl").on("click", "#chkAll", function(){
		if($(this).is(":checked")){
			$("#tbl .row .chk").each(function(){ 
				$(this).prop("checked",true); 
			});
		}else{
			$("#tbl .row .chk").each(function(){
				$(this).prop("checked",false);
			});
		}
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
	
	//정보읽기
	$("#tbl").on("click", ".row .image", function(){
		var r_id=$(this).parent().find(".r_id").html();
		var u_id="${id}";
		location.href="/semiProject/bookmark/read?r_id="+r_id+"&u_id="+u_id;
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
			url : "/semiProject/bookmark/list",
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