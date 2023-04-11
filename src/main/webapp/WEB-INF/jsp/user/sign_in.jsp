<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="login-box">
		<h1>로그인</h1>
		
		<form id="loginForm" method="post" action="/user/sign_in">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
				</div>
				<input type="text" class="form-control" id="loginId" name="loginId" placeholder="아이디">
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">PW</span>
				</div>
				<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호">
			</div>
			
			<div class="d-flex">
				<a href="/user/sign_up_view" class="btn btn-secondary w-100 mr-2">회원가입</a>
				<button type="submit" class="btn btn-primary w-100">로그인</button>
			</div>
		</form>
	</div>
</div>

<script>
	$(document).ready(function() {
		$('#loginForm').submit(function(e) {
			e.preventDefault();	// submit 수행 중단
			
			// validation
			let loginId = $('#loginId').val().trim();
			if (loginId == '') {
				alert("아이디를 입력해주세요.");
				return;
			}
			
			let password = $('#password').val().trim();
			if (password == '') {
				alert("비밀번호를 입력해주세요.");
				return;
			}
			
			// AJAX로 submit
			let url = $(this).attr('action');
			let params = $(this).serialize();
			
			$.post(url, params).done(function(data) {
				if (data.result == 'success') {
					alert("로그인이 " + Math.floor(data.sessionTime/60) + "분 " + data.sessionTime%60 + "초 동안 유지됩니다.");
					location.href = '/timeline/timeline_list_view';
				} else {
					alert("로그인에 실패했습니다.");
				}
			});
		});
	});
</script>