<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script type="text/javascript">

/* if(localStorage.getItem("newCount") != null){
	var count2 = JSON.parse(localStorage.getItem("newCount"));
}else {
	var count2 = 2;
} */
var messageBox = function(title, message, callback){
	$('#dialog-message p').text(message);
	$('#dialog-message')
		.attr("title", title)
		.dialog({
			modal:true,
			buttons:{
				"확인":function(){
					$(this).dialog("close");
				}
			},
			close : callback
		})
};

var listItemTemplate = new EJS({
	url : "${pageContext.request.contextPath}/assets/js/ejs/list-item-template.ejs"
})

var listTemplate = new EJS({
	url : "${pageContext.request.contextPath}/assets/js/ejs/list-template.ejs"
});

var fetchList = function(){
	$.ajax({
		url : "${pageContext.request.contextPath}/${id}/api/admin/category",
		async: true,
		type: 'get',
		dataType:'json',
		data: '',
		success: function(response){
			if(response.result != "success"){
				console.error(response.message);
				return;
			}
			
			var imageUrl = '${pageContext.request.contextPath}/assets/images/delete.jpg';
			
			response.data.image = imageUrl;
			
			var html = listTemplate.render(response);
			$('.admin-cat').append(html);
		}
	})	
};


$(function(){
	
	//console.log(count2);
	fetchList();
	$(document).on('click', '.admin-cat td a',(function(){
		event.preventDefault();
		var no = $(this).data('no');
		//count2 = Number(count2) - 1;
		//localStorage.setItem("newCount", JSON.stringify(count2));
		//console.log("delete : " + count2);
		$.ajax({
			url : '${pageContext.request.contextPath}/${id}/api/admin/categorydel/'+no,
			async:true,
			type:'delete',
			dataType:'json',
			data: '',
			success:function(response){
				if(response.result != "success"){
					console.err(response.message);
					return;
				}
				
				if(response.data != -1){
					$('.admin-cat tr td').remove();
					fetchList();
					return;
				}
			},
			error:function(xhr, status, e){
				console.log(status + ":" + e);
			}
		})
	}));
	
	$('#add-form').submit(function(event){
		event.preventDefault();
		var vo = {};
		vo.name = $('#input-name').val();
		if(vo.name == ''){
			messageBox("카테고리 추가하기", "카테고리명은 필수 항목입니다.", function(){
				$('#input-name').focus();
			});
			return;
		} 
		
		vo.description = $('#input-desc').val();
		if(vo.description == ''){
			messageBox("카테고리추가하기", '설명은 필수 항목입니다.', function(){
				$('#input-desc').focus();
			})
			return;
		}
		
		$.ajax({
			url : '${pageContext.request.contextPath}/${id}/api/admin/categoryadd',
			async:true,
			type:'post',
			dataType:'json',
			contentType: 'application/json',
			data:JSON.stringify(vo),
			success:function(response){
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				
				var imageUrl = '${pageContext.request.contextPath}/assets/images/delete.jpg';
				response.data.image = imageUrl;
				//response.data.count = count2;
				/* console.log($('.admin-cat tr:last-child td')[0].innerText); */
				response.data.index = Number($('.admin-cat tr:last-child td')[0].innerText)+1;
				
				var html = listItemTemplate.render(response.data);
				
				
				$('.admin-cat tr#list-tr').last().after(html);
				
				//count2 = Number(count2) + 1;
				//localStorage.setItem("newCount", JSON.stringify(count2));
				//console.log("add : "+count2);
				$('#add-form')[0].reset();
			},
			
			error: function(xhr, status, e){
				console.error(status + ":" + e);
			}			
		});
	})
});

</script>
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
<%-- 					<th>${status.index + 1 }</th>
						<th>${vo.name }</th>
						<th>${vo.postNum }</th>
						<th>${vo.description }</th>
						<td>
							<c:choose>
								<c:when test="${vo.postNum !=0 || listcount == 1 }">
									삭제 불가
								</c:when>
								<c:when test="${vo.postNum == 0 }">
									<a href = "${pageContext.request.contextPath }/${vo.blogID }/admin/categorydel/${vo.no}">
									<img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a>
								</c:when>
							</c:choose>
						</td> --%>
				</table>
      	
      			<form id='add-form'>
	      			<h4 class="n-c">새로운 카테고리 추가</h4>
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input id ='input-name' type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class='t'>설명</td>
			      			<td><input id="input-desc" type="text" name="description"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>
			      	</table>
			      	<div id="dialog-message" title="" style="display:none">
  						<p></p>
					</div>	
		      	</form>      		      		
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>