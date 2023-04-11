package com.marondalgram.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marondalgram.comment.bo.CommentBO;
import com.marondalgram.comment.model.Comment;
import com.marondalgram.like.bo.LikeBO;
import com.marondalgram.post.bo.PostBO;
import com.marondalgram.post.model.Post;
import com.marondalgram.timeline.domain.Content;

@Service
public class ContentBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	public List<Content> getContentList() {
		List<Content> contentList = new ArrayList<>();
		
		List<Post> postList = postBO.getPostList();
		for (Post post : postList) {
			Content content = new Content();
			content.setPost(post);
			
			List<Comment> commentList = commentBO.getCommentList(post.getId());
			content.setCommentList(commentList);
			
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			content.setLikeCount(likeCount);
			
			contentList.add(content);
		}
		
		return contentList;
	}
}
