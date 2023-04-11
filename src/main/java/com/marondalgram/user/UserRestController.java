package com.marondalgram.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marondalgram.comment.bo.CommentBO;
import com.marondalgram.common.EncryptUtils;
import com.marondalgram.post.bo.PostBO;
import com.marondalgram.user.bo.UserBO;
import com.marondalgram.user.model.User;

@RequestMapping("/user")
@RestController
public class UserRestController {
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	/**
	 * 로그인 아이디 중복확인
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String, Boolean> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// DB 조회
		User user = userBO.getUserByLoginId(loginId);
		
		// 리턴
		Map<String, Boolean> result = new HashMap<>();
		
		if (user == null) {
			result.put("result", false);	// 중복x
		} else {
			result.put("result", true);		// 중복o
		}
		
		return result;
	}
	
	/**
	 * DB 회원가입 넣기
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, String> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// 암호화
		String enryptPassword = EncryptUtils.md5(password);
		
		// DB 추가
		userBO.addUser(loginId, enryptPassword, name, email);
		
		// 리턴
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
	
	/**
	 * 로그인 하기
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign_in")
	public Map<String, String> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// password md5로 해싱한다.
		String enryptPassword = EncryptUtils.md5(password);
		
		// loginId, password로 user를 가져와 있으면 로그인 성공
		User user = userBO.getUserByLoginIdAndPassword(loginId, enryptPassword);
		
		Map<String, String> result = new HashMap<>();
		
		if (user != null) {						
			// 성공: 세션에 저장(로그인 상태 유지)
			HttpSession session = request.getSession();
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("userId", user.getId());
			
			// DB 조회
			int postCount = postBO.getPostCountByUserId(user.getId());
			int commentCount = commentBO.getCommentCountByUserId(user.getId());
			int sessionTime = 60 - postCount*10 - commentCount;
			
			// 세션 유지시간 지정(단위: 초)
			session.setMaxInactiveInterval(sessionTime);
			
			result.put("result", "success");
			result.put("sessionTime", Integer.toString(sessionTime));
			
		} else {
			//	실패: 에러 리턴
			result.put("result", "fail");
		}
		
		return result;
	}
}
