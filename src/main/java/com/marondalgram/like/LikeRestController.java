package com.marondalgram.like;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marondalgram.like.bo.LikeBO;

@RequestMapping("/like")
@RestController
public class LikeRestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LikeBO likeBO;

	@RequestMapping("/create")
	public Map<String, String> likeCreate(
			@RequestParam("userId") int userId,
			@RequestParam("postId") int postId,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		Integer sessionUserId = (Integer)session.getAttribute("userId");
		
		Map<String, String> result = new HashMap<>();
		if (sessionUserId == null || userName == null) {
			result.put("result", "fail");
			logger.error("[좋아요] 로그인 세션이 없습니다.");
			return result;
		}
		
		int row = likeBO.insertLikeByUserIdAndPostId(userId, postId);
		if (row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
}
