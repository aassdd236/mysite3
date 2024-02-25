<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.javaex.vo.GuestVo"%>

<%
List<GuestVo> guestList = (List<GuestVo>) request.getAttribute("guestList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="/mysite3/assets/css/mysite.css" rel="stylesheet"
	type="text/css">
<link href="/mysite3/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">

</head>

<body>
	<div id="wrap">

		<!-- header.jsp 불러오는 코드 -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		
		<!-- //header -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head" class="clearfix">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
				</div>
				
			<div id="guestbook">	
				<form action="http://localhost:8080/mysite3/gbc" method="get">
					<table border="1" width="540px">
						<tr>
							<td>이름</td>
							<td><input type="text" name="name" value=""></td>
							<td>비밀번호</td>
							<td><input type="password" name="pw" value=""></td>
						</tr>
						<tr>
							<td colspan="4"><textarea cols="72" rows="5" name="content"
									value="">
				</textarea></td>
						</tr>
						<tr>
							<td colspan="4"><button type="submit">등록</button></td>
						</tr>
					</table>
					<input type="hidden" name="action" value="write">
				</form>
				<br>

				<%
				for (int i = 0; i < guestList.size(); i++) {
				%>
				<table border="1" width="540px">
					<tr>
						<td>[<%=guestList.get(i).getNo()%>]
						</td>
						<td><%=guestList.get(i).getName()%></td>
						<td><%=guestList.get(i).getRegDate()%></td>
						<td><a
							href="/mysite3/gbc?action=deleteform&no=<%=guestList.get(i).getNo()%>">삭제</a></td>
					</tr>
					<tr>
						<td colspan="4"><%=guestList.get(i).getContent()%></td>
					</tr>
				</table>
				<br>
				<%
				}
				%>
				</div>
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