package com.marondalgram.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marondalgram.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostBO postBO;

	/**
	 * 포스트 입력
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, String> postCreate(
			@RequestParam("content") String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		Integer userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> result = new HashMap<>();
		if (userId == null || userName == null) {
			result.put("result", "fail");
			logger.error("[포스트 쓰기] 로그인 세션이 없습니다.");
			return result;
		}
		
		// insert DB
		int row = postBO.createPost(userId, userName, content, file);
		
		if (row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	@PostMapping("/delete")
	public Map<String, String> postDelete(
			@RequestParam("postId") int postId) {
		
		int row = postBO.deletePost(postId);
				
		Map<String, String> result = new HashMap<>();
		if (row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
}
