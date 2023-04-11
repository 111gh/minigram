<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="timeline">
	<!-- 피드 입력창 => 로그인 상태만 -->
	<c:if test="${not empty userName}">
		<div class="border rounded">
			<textarea class="form-control mt-3 border-0" name="content" rows="3" placeholder="내용을 입력해주세요"></textarea>
			<div class="d-flex justify-content-between">
				<div class="d-flex">
					<input type="file" id="file" class="d-none" name="file" accept=".gif, .jpg, .png, .jpeg">
					
					<a href="#" id="fileUploadBtn">
						<img width="35" src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png">
					</a>
					<div id="fileName" class="ml-2"></div>
				</div>
				<button type="button" class="btn btn-sm btn-primary" id="uploadBtn">업로드</button>
			</div>
		</div>
	</c:if>
	
	<!-- 타임라인 -->
	<c:forEach var="content" items="${contentList}">
	<div class="mt-3">
		<!-- 유저 이름, 삭제버튼 -->
		<div class="timeline-header d-flex justify-content-between mb-2">
			<span class="font-weight-bold ml-3">${content.post.userName}</span>
			
			<!-- 로그인 유저와 포스트 작성 유저가 같아야 버튼이 보인다 -->
			<c:if test="${userName eq content.post.userName}">
				<img src="https://www.iconninja.com/files/860/824/939/more-icon.png"
					class="btn postDelBtn" alt="포스트삭제버튼" height="35px" data-post-id="${content.post.id}">
			</c:if>
		</div>
		
		<!-- 이미지 -->
		<div class="d-flex justify-content-center">
			<img src="${content.post.imagePath}" width="400px">
		</div>
		
		<!-- 좋아요 - 로그인한 상태만 -->
		<div class="d-flex mt-3">
			<c:if test="${not empty userName}">
				<button type="button" class="border-0 likeBtn" data-user-id="${userId}" data-post-id="${content.post.id}">
					<c:choose>
						<c:when test="${content.likeCount eq 0}">
							<img src="https://www.iconninja.com/files/214/518/441/heart-icon.png" width="20px" height="20px">
						</c:when>
						<c:otherwise>
							<img src="https://www.iconninja.com/files/527/809/128/heart-icon.png" width="20px" height="20px">
						</c:otherwise>
					</c:choose>
				</button>
			</c:if>
				
			<span class="font-weight-bold ml-2">좋아요 ${content.likeCount}개</span>
		</div>
		
		
		<!-- 내용 -->
		<div class="d-flex mt-2">
			<span class="font-weight-bold mr-2">${content.post.userName}</span>
			<span>${content.post.content}</span>
		</div>
		
		<!-- 댓글 -->
		<div class="comment-header">
			<span class="font-weight-bold ml-2">댓글</span>
		</div>
		
		<c:forEach var="comment" items="${content.commentList}">
			<div class=" d-flex">
				<span class="font-weight-bold mr-2">${comment.userName}</span>
				<span>${comment.content}</span>
				
				<!-- 로그인 유저와 댓글 작성 유저가 같아야 버튼이 보인다 -->
				<c:if test="${userName eq comment.userName}">
				<img src="https://www.iconninja.com/files/603/22/506/x-icon.png"
					class="btn commentDelBtn" alt="댓글삭제" height="25px" data-comment-id="${comment.id}">
				</c:if>
			</div>
		</c:forEach>
		
		<c:if test="${not empty userName}">
			<div class="d-flex mt-2">
				<input type="text" id="comment${content.post.id}" class="form-control" placeholder="댓글을 입력해주세요">
				<button type="button" class="btn btn-primary commentBtn" data-post-id="${content.post.id}">게시</button>
			</div>
		</c:if>
	</div>
	</c:forEach>
	
	<!-- Button trigger modal -->
	<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
  		Launch demo modal
	</button> -->
	
	<!-- Modal -->
	<!-- <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        ...
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div> -->
</div>

<script>
	$(document).ready(function() {
		// 파일 업로드 이미지 클릭 -> 사용자가 파일 업로드를 할 수 있게
		$('#fileUploadBtn').on('click', function(e) {
			e.preventDefault();	// a 태그의 기본 동작을 중단
			$('#file').click();	// input file 태그를 클릭한 것과 같은 동작
		});
		
		// 사용자가 파일업로드를 했을 때 + 확장자 validation
		$('#file').on('change', function(e) {
			let fileName = e.target.files[0].name;
			
			// 확장자 validation
			let ext = fileName.split('.');
			if (ext.length < 2 ||
					ext[ext.length - 1] != 'gif'
					&& ext[ext.length - 1] != 'png'
					&& ext[ext.length - 1] != 'jpg'
					&& ext[ext.length - 1] != 'jpeg') {
				
				alert("이미지 파일만 업로드 가능합니다");
				$(this).val('');	// input file에 업로드된 상태이므로 비워주어야 한다
				return;
			}
			
			$('#fileName').text(fileName);
		});
		
		$('#uploadBtn').on('click', function() {
			// validation
			let content = $('textarea[name=content]').val();
			if (content == "") {
				alert("내용을 입력해주세요");
				return;
			}
			
			// 폼 생성 -> 서버에 보내기
			let formData = new FormData();
			formData.append("content", content);
			formData.append("file", $('input[name=file]')[0].files[0]);
			
			$.ajax({
				url: '/post/create'
				, type: 'post'
				, data: formData
				
				// 파일 업로드시 필수 파라미터들
				, processData: false
				, contentType: false
				, enctype: 'multipart/form-data'
				
				, success: function(data) {
					if (data.result == "success") {
						alert("피드가 저장되었습니다");
						location.href = '/timeline/timeline_list_view';
					} else {
						alert("피드 저장에 실패했습니다")
					}
				}, error: function(e) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요")
				}
			});
		});
		
		// 포스트 삭제
		$('.postDelBtn').on('click', function() {
			let postId = $(this).data('post-id');
			
			$.ajax({
				url: '/post/delete'
				, type: 'post'
				, data: {'postId':postId}
				, success: function(data) {
					if (data.result == "success") {
						alert("포스트 삭제 성공");
						location.reload();
					} else {
						alert("포스트 삭제 실패");
					}
				}, error: function(e) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요" + e)
				}
			});
		});
		
		// 댓글 입력
		$('.commentBtn').on('click', function() {
			let postId = $(this).data('post-id');
			let comment = $('#comment' + postId).val().trim();
			
			if (comment == "") {
				alert("댓글 내용을 입력해주세요.");
				return;
			}
			
			$.ajax({
				url: '/comment/create'
				, type: 'post'
				, data: {'comment':comment, 'postId':postId}
				, success: function(data) {
					if (data.result == "success") {
						alert("댓글 등록 완료")
						location.reload();
					} else {
						alert("댓글 등록 실패")
					}
				}, error: function(e) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요" + e)
				}
			});
		});
		
		$('.commentDelBtn').on('click', function() {
			let commentId = $(this).data('comment-id');
			
			$.ajax({
				url: '/comment/delete'
				, type: 'post'
				, data: {'commentId':commentId}
				, success: function(data) {
					if (data.result == "success") {
						alert("댓글 삭제 완료")
						location.reload();
					} else {
						alert("댓글 삭제 실패")
					}
				}, error: function(e) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요" + e)
				}
			});
		});
		
		$('.likeBtn').on('click', function() {
			let userId = $(this).data('user-id');
			let postId = $(this).data('post-id');
			
			$.ajax({
				url: '/like/create'
				, type: 'post'
				, data: {'userId':userId, 'postId':postId}
				, success: function(data) {
					if (data.result == "success") {
						location.reload();
					} else {
						alert("좋아요 실패")
					}
				}, error: function(e) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요" + e)
				}
			});
		});
	});
</script>