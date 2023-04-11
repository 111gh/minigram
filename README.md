# minigram
sns 기능을 사용할 수 있는 작은 웹페이지입니다

<br>

## 사용한 기술
- Java
- Spring
- JSP
- JavaScript
- MySQL
- HTML
- CSS

</br>

## ERD 설계
![미니그램](https://user-images.githubusercontent.com/129938274/231094994-30309458-1728-43c8-8145-649c4ce1cf4d.png)

</br>

## 주요 기능
- 로그인한 회원만 접속할 수 있는 기능
- 일정 시간이 지나면 자동 로그아웃하는 기능
- 이미지 업로드 기능
- 글, 댓글 작성, 삭제 기능

<details>
<summary><b>주요 기능 펼치기</b></summary>

## 로그인 주요 기능
비로그인 상태라면 로그인 페이지로 리다이렉트합니다

로그인 상태라면 타임라인 페이지(포스트 페이지)로 리다이렉트합니다

```java
// 세션 확인 -> 있으면 로그인 상태
HttpSession session = request.getSession();
String userLoginId = (String)session.getAttribute("userLoginId");

// URI - url path 확인
String uri = request.getRequestURI();

// 비로그인 && /timeline/... -> 로그인 페이지로 리다이렉트
if (userLoginId == null && uri.startsWith("/timeline")) {
  response.sendRedirect("/user/sign_in_view");
  return false;
}
 
// 로그인 && /user/...	-> 포스트 페이지로 리다이렉트
if (userLoginId != null && uri.startsWith("/user")) {
  response.sendRedirect("/timeline/timeline_list_view");
  return false;
}
```

## 자동 로그아웃 주요 기능
로그인한 사용자의 환경에 따라 자동 로그아웃을 실행합니다

현재 조건은 사용자가 작성한 글과 댓글 수를 종합해서 세션타임을 설정합니다 
```java
// DB 조회
int postCount = postBO.getPostCountByUserId(user.getId());
int commentCount = commentBO.getCommentCountByUserId(user.getId());
int sessionTime = 60 - postCount*10 - commentCount;

// 세션 유지시간 지정(단위: 초)
session.setMaxInactiveInterval(sessionTime);
result.put("result", "success");
result.put("sessionTime", Integer.toString(sessionTime));
```

## 이미지 업로드 주요 기능
이미지 파일은 저장소에 저장되고 주소(url) 생성 후 DB에 입력하는 방식입니다
```java
// 컴퓨터에 저장될 경로
public final static String FILE_UPLOAD_PATH = "/home/ec2-user/images/";
	
// 파일을 컴퓨터에 저장 -> url path 리턴
public String saveFile(int userId, MultipartFile file) throws IOException {
  // 파일 디렉토리 경로 만들기
  String directoryName = userId + "_" + System.currentTimeMillis() + "/";
  String filePath = FILE_UPLOAD_PATH + directoryName;

  File directory = new File(filePath);
  if (directory.mkdir() == false) {	// 업로드할 경로에 폴더 생성
    // 디렉토리 생성 실패
    return null;
  }

  // 파일 업로드 -> byte 단위로 업로드
  byte[] bytes = file.getBytes();
  // getOriginalFilename() => input에서 올린 파일명
  Path path = Paths.get(filePath + file.getOriginalFilename());
  Files.write(path, bytes);

  // 이미지 url 만들어 리턴
  return "/images/" + directoryName + file.getOriginalFilename();
}
```

글 삭제 시 이미지 파일도 삭제합니다
```java
public void deleteFile(String imagePath) throws IOException {
  Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
  
  // 이미지 삭제
  if (Files.exists(path)) {
  Files.delete(path);
  }
  // 디렉토리 삭제
  path = path.getParent();
  if (Files.exists(path)) {
    Files.delete(path);
  }
}
```

## 로그인 여부에 따른 주요 기능
해당 기능은 이 외에도 댓글 입력창, 댓글 삭제 등 다양한 곳에 사용합니다

예시1) 피드 입력창(글 작성하는 곳)은 로그인 상태에서만 보입니다
```jsp
<c:if test="${not empty userName}">
  <div>
    <textarea name="content" rows="3" placeholder="내용을 입력해주세요"></textarea>
    <div>
      <div>
        <input type="file" id="file" name="file" accept=".gif, .jpg, .png, .jpeg">
        <a href="#" id="fileUploadBtn">
          <img width="35" src="???">
        </a>
        <div id="fileName"></div>
      </div>
      <button type="button" id="uploadBtn">업로드</button>
    </div>
  </div>
</c:if>
```

예시2) 댓글 삭제 버튼이 있고 로그인 사용자와 댓글 작성자가 같아야 버튼이 활성화됩니다
```jsp
<c:if test="${userName eq comment.userName}">
  <img src="???" class="commentDelBtn" alt="del_icon" data-comment-id="${comment.id}">
</c:if>
```

</details>
