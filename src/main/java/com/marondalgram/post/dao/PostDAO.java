package com.marondalgram.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.marondalgram.post.model.Post;

@Repository
public interface PostDAO {
	
	public List<Post> selectPostList();
	
	public Post selectPostById(int postId);
	
	public int insertPost(
			@Param("userId") int userId,
			@Param("userName") String userName,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public int deletePost(int postId);
	
	public int selectPostCountByUserId(int userId);
}
