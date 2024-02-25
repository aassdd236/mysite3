<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	UserVo authUser=(UserVo)session.getAttribute("authUser");
%>

<div id="header" class="clearfix">
	<h1>
		<a href="">MySite</a>
	</h1>
	<ul>
		<c:if test="${empty sessionScope.authUser}">
			<!-- 로그인 전 -->
			<li><a href="/mysite3/user?action=loginform">로그인</a></li>
			<li><a href="/mysite3/user?action=joinform">회원가입</a></li>
		</c:if>

		<c:if test="${ !(empty sessionScope.authUser) }">
			<!-- 로그인 후 -->
			<li>${authUser.name}님 안녕하세요^^;</li>
			<li><a href="/mysite3/user?action=logout">로그아웃</a></li>
			<li><a href="/mysite3/user?action=updateform">회원정보수정</a></li>

		</c:if>
	</ul>

</div>
<div id="nav">
	<ul class="clearfix">
		<li><a href="">입사지원서</a></li>
		<li><a href="/mysite3/bc?action=list">게시판</a></li>
		<li><a href="">갤러리</a></li>
		<li><a href="">방명록</a></li>
	</ul>
</div>