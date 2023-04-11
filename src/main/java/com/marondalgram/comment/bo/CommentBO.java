package com.marondalgram.comment.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marondalgram.comment.dao.CommentDAO;
import com.marondalgram.comment.model.Comment;

@Service
public class CommentBO {
	
	@Autowired
	private CommentDAO commentDAO;
	
	public List<Comment> getCommentList(int postId) {
		return commentDAO.selectCommentList(postId);
	}
	
	public int addComment(int postId, int userId, String userName, String content) {
		return commentDAO.addComment(postId, userId, userName, content);
	}
	
	public int deleteComment(int commentId) {
		return commentDAO.deleteComment(commentId);
	}
	
	public void deleteComments(int postId) {
		commentDAO.deleteComments(postId);
	}
	
	public int getCommentCountByUserId(int userId) {
		return commentDAO.selectCommentCountByUserId(userId);
	}

}
