<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
			<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath }/${blogVo.blogID}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath }/${blogVo.blogID}/admin/write">글작성</a></li>
				</ul>
				
		      	<table class="admin-cat">
		      	 <tr>
		      	 	<th>번호</th>
		      	 	<th>카테고리명</th>
		      	 	<th>포스트수</th>
		      	 	<th>설명</th>
		      	 	<th>삭제</th>
		      	 </tr>
		      	<c:set var='listcount' value='${fn:length(list) }'/>
		      	<c:forEach var='vo' items='${list }' varStatus='status'>
					<tr>
						<th>${status.index + 1 }</th>
						<th>${vo.name }</th>
						<th>${vo.postNum }</th>
						<th>${vo.description }</th>
						<td>
							<c:choose>
								<c:when test="${vo.postNum == 0 }">
									<a href = "${pageContext.request.contextPath }/${vo.blogID }/admin/categorydel/${vo.no}">
									<img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a>
								</c:when>
								<c:otherwise>
									삭제 불가
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>					  
				</table>
      	
				<form action="${pageContext.request.contextPath }/${blogVo.blogID }/admin/categoryadd" method="post">
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="description"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table>
		      </form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>