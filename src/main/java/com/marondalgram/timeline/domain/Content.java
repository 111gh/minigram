package com.marondalgram.timeline.domain;

import java.util.List;

import com.marondalgram.comment.model.Comment;
import com.marondalgram.post.model.Post;

public class Content {
	// post 1개
	private Post post;
	// post 댓글 n개
	private List<Comment> commentList;
	// post 좋아요 1개
	private int likeCount;
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
	public int getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	/*
	 * // post - 좋아요 n개 private List<Like> likeList;
	 * 
	 * // or post - 좋아요 카운트 private int likeCount;
	 */
	
	/*
	 * private boolean isFilledLike; // userId (나) - post 좋아요
	 */
}
