<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogVo.title }</h1>
				<ul>
				<c:choose>
					<c:when test="${empty authUser }">
						<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>		
					</c:when>	
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
						<li><a href="${pageContext.request.contextPath }/${blogVo.blogID }/admin/basic">블로그 관리</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
				<c:forEach var = 'post' items="${postList }" begin = '0' end = '0'>
					<h4>${post.title }</h4>
					<p>
						${fn:replace(post.contents, newLine, "<br>") }
					<p>
				</c:forEach>
					
				</div>
				<ul class="blog-list">
				<c:forEach var="post" items="${postList }" varStatus='status'>
					<li>
						<a href="${pageContext.request.contextPath }/${blogVo.blogID}/${post.categoryNo}/${post.no}">${post.title }</a> 
						<span>${post.regDate }</span>	
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logoURL}">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach var='category' items="${category }" varStatus='satus'>
					<li><a href="${pageContext.request.contextPath }/${blogVo.blogID}/${category.no}">${category.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>