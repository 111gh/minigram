<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex">
	<div class="logo">
		<h1 class="p-4"><a href="/timeline/timeline_list_view" style="text-decoration: none">Social</a></h1>
	</div>
	
	<div class="login-info d-flex justify-content-end">
		<!-- 로그인이 된 경우 -->
		<c:if test="${not empty userName}">
			<div class="mt-5 mr-5">
				<span class="text-white">${userName}님 안녕하세요.</span>
				<a href="/user/sign_out" class="text-white font-weight-bold ml-2">로그아웃</a>
			</div>
		</c:if>
		
		<!-- 로그인이 안 된 경우 -->
		<c:if test="${empty userName}">
			<div class="mt-5 mr-5">
				<a href="/user/sign_in_view" class="text-white font-weight-bold ml-2">로그인</a>
			</div>
		</c:if>
	</div>
</div>
