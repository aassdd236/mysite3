<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%
	UserVo authUser=(UserVo)session.getAttribute("authUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite3/assets/css/mysite.css" rel="stylesheet"
	type="text/css">
<link href="/mysite3/assets/css/user.css" rel="stylesheet"
	type="text/css">

</head>

<body>
	<div id="wrap">

		<!-- header.jsp 불러오는 코드 -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


		<div id="container" class="clearfix">
			<div id="aside">
				<h2>회원</h2>
				<ul>
					<li>회원정보</li>
					<li>로그인</li>
					<li>회원가입</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head">
					<h3>회원정보</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">회원정보</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->

				<div id="user">
					<div id="modifyForm">
						<form action="http://localhost:8080/mysite3/user" method="get">

							<input type="hidden" name="no" value="<%=authUser.getNo() %>">
							<input type="hidden" name="id" value="<%=authUser.getId() %>">
							<!-- 아이디 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> <span
									class="text-large bold"><%=authUser.getId() %></span>
							</div>

							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">패스워드</label> <input
									type="password" id="input-pass" name="pw" value=""
									placeholder="비밀번호를 입력하세요">
							</div>

							<!-- 이메일 -->
							<div class="form-group">
								<label class="form-text" for="input-name">이름</label> <input
									type="text" id="input-name" name="name"
									value="<%=authUser.getName() %>">
							</div>

							<!-- //나이 -->
							<div class="form-group">
								<span class="form-text">성별</span> <label for="rdo-male">남</label>
								<input type="radio" id="rdo-male" name="gender" value="male"> <label
									for="rdo-female">여</label> <input type="radio" id="rdo-female"
									name="gender" value="female">

							</div>
							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">회원정보수정</button>
							</div>

							<input type="hidden" name="action" value="update">
						</form>


					</div>
					<!-- //modifyForm -->
				</div>
				<!-- //user -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->

		<!-- footer.jsp 불러오는 코드 -->
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>

	</div>
	<!-- //wrap -->

</body>

</html>