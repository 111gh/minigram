<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div>
		<h1 class="p-2">회원 가입</h1>
					
		<form id="signUpForm" method="post" action="/user/sign_up">
			<div class="sign-up-form">
				<span class="m-3">ID</span>
				<div class="d-flex m-3">
					<input type="text" class="form-control col-7 mr-3" name="loginId" id="loginId" placeholder="ID를 입력해주세요">
					<button type="button" class="btn btn-primary btn-sm" id="loginIdCheckBtn">중복확인</button>
				</div>
								
				<div id="idCheckLength" class="small text-danger d-none ml-3">아이디를 4자 이상 입력해주세요.</div>
				<div id="idCheckDuplicated" class="small text-danger d-none ml-3">사용중인 아이디입니다.</div>
				<div id="idCheckOk" class="small text-success d-none ml-3">사용 가능한 아이디입니다.</div>
					
				<span class="m-3">비밀번호</span>
				<input type="password" class="form-control col-7 m-3" name="password" id="password" placeholder="비밀번호를 입력해주세요">
								
				<span class="m-3">비밀번호 확인</span>
				<input type="password" class="form-control col-7 m-3" id="confirmPassword" placeholder="비밀번호를 입력해주세요">
								
				<span class="m-3">이름</span>
				<input type="text" class="form-control col-7 m-3" name="name" id="name" placeholder="이름을 입력해주세요">
								
				<span class="m-3">이메일</span>
				<input type="text" class="form-control col-7 m-3" name="email" id="email" placeholder="이메일을 입력해주세요">
							
				<div class="d-flex justify-content-center">
					<button type="submit" class="btn btn-primary" id="signUpBtn">가입하기</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 아이디 중복확인
		$('#loginIdCheckBtn').on('click', function() {
			let loginId = $('#loginId').val().trim();
			
			// 아이디 4자이상 확인
			if (loginId.length < 4) {
				$('#idCheckLength').removeClass('d-none');
				$('#idCheckDuplicated').addClass('d-none');
				$('#idCheckOk').addClass('d-none');
				return;
			}
			
			// 중복확인
			$.ajax({
				url: "/user/is_duplicated_id"
				, type: "post"
				, data: {"loginId": loginId}
				, success: function(data) {
					if (data.result == true) {
						// 중복o
						$('#idCheckLength').addClass('d-none');
						$('#idCheckDuplicated').removeClass('d-none');
						$('#idCheckOk').addClass('d-none');
					} else {
						// 중복x
						$('#idCheckLength').addClass('d-none');
						$('#idCheckDuplicated').addClass('d-none');
						$('#idCheckOk').removeClass('d-none');
					}
				} , error: function(e) {
					alert("아이디 확인에 실패했습니다.");
				}
			});
		});
		
		$('#signUpForm').submit(function(e) {
			e.preventDefault();
			
			let loginId = $('#loginId').val().trim();
			if (loginId == '') {
				alert("아이디를 입력하세요.")
				return;
			}
			
			let password = $('#password').val();
			let confirmPassword = $('#confirmPassword').val();
			if (password == '' || confirmPassword == '') {
				alert("비밀번호를 입력하세요.")
				return;
			}
			
			// 비밀번호 일치 여부
			if (password != confirmPassword) {
				alert("비밀번호가 일치하지 않습니다. 다시 입력하세요.")
				
				// 텍스트 초기화
				$('#password').val('');
				$('#confirmPassword').val('');
				return;
			}
			
			let name = $('#name').val().trim();
			if (name == '') {
				alert("이름을 입력하세요.")
				return;
			}
			
			let email = $('#email').val().trim();
			if (email == '') {
				alert("이메일을 입력하세요.")
				return;
			}
			
			// 아이디 중복확인이 완료됐는지 확인
			// -- idCheckOk --> d-none이 없으면 사용가능한 아이디라고 가정한다.
			if ($('#idCheckOk').hasClass('d-none')) {
				alert("아이디 중복확인을 해주세요.");
				return;
			}
			
			let url = $(this).attr('action');
			let params = $(this).serialize();
			
			$.post(url, params).done(function(data) {
				if (data.result == 'success') {
					alert("회원가입을 환영합니다!");
					location.href = '/user/sign_in_view';
				} else {
					alert("회원가입에 실패했습니다.");
				}
			});
		});
	});
</script>