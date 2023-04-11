package com.marondalgram.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.marondalgram.comment.model.Comment;

@Repository
public interface CommentDAO {
	
	public List<Comment> selectCommentList(int postId);
	
	public int addComment(
			@Param("postId") int postId,
			@Param("userId") int userId,
			@Param("userName") String userName,
			@Param("content") String content);
	
	public int deleteComment(int commentId);
	
	public void deleteComments(int postId);
	
	public int selectCommentCountByUserId(int userId);
}
