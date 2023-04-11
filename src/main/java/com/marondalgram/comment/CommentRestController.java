package com.marondalgram.comment;

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

import com.marondalgram.comment.bo.CommentBO;

@RequestMapping("/comment")
@RestController
public class CommentRestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommentBO commentBO;

	@RequestMapping("/create")
	public Map<String, String> commentCreate(
			@RequestParam("comment") String content,
			@RequestParam("postId") int postId,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		Integer userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> result = new HashMap<>();
		if (userId == null || userName == null) {
			result.put("result", "fail");
			logger.error("[댓글쓰기] 로그인 세션이 없습니다.");
			return result;
		}
		
		int row = commentBO.addComment(postId, userId, userName, content);
		if (row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	@RequestMapping("/delete")
	public Map<String, String> commentDelete(
			@RequestParam("commentId") int commentId,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		Integer userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> result = new HashMap<>();
		if (userId == null || userName == null) {
			result.put("result", "fail");
			logger.error("[댓글쓰기] 로그인 세션이 없습니다.");
			return result;
		}
		
		int row = commentBO.deleteComment(commentId);
		if (row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
}
