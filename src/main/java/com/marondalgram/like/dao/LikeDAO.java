package com.marondalgram.like.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDAO {
	
	public int selectLikeCountByPostId(int postId);
	
	public int insertLikeByUserIdAndPostId(
			@Param("userId") int userId,
			@Param("postId") int postId);
}
