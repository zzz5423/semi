<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
   #login{text-align:right; margin-right:25px;}
   #logout{text-align:right; margin-right:25px;}
</style>

<div class="item"><a href="/semiProject/index.jsp">HOME</a></div>
<div class="item"><a href="/semiProject/intro.jsp">회사소개</a></div>
<div class="item"><a href="/semiProject/korea/list.jsp">한식</a></div>
<div class="item"><a href="/semiProject/china/list.jsp">중식</a></div>
<div class="item"><a href="/semiProject/usa/list.jsp">양식</a></div>
<div class="item"><a href="/semiProject/japan/list.jsp">일식</a></div>
<div class="item"><a href="/semiProject/etc/list.jsp">기타</a></div>

<c:if test="${id==null}">
   <div id="login">
      <a href="/semiProject/user/login.jsp">로그인</a>
   </div>
</c:if>

<c:if test="${id!=null}">
   <div id="logout">
        <a href="/semiProject/bookmark/list.jsp">즐겨찾기</a>
      <a href="/semiProject/recipe/insert.jsp">&nbsp;레시피 등록</a>
      <a href="/semiProject/user/mypage.jsp">&nbsp;마이페이지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
      <span style="font-size:13px;"><b>안녕하세요! '${name}'님&nbsp;</b></span>
      <a href="/semiProject/user/logout">로그아웃</a>
   </div>
</c:if>