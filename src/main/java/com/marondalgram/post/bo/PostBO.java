package com.marondalgram.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marondalgram.comment.bo.CommentBO;
import com.marondalgram.common.FileManagerService;
import com.marondalgram.post.dao.PostDAO;
import com.marondalgram.post.model.Post;

@Service
public class PostBO {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	public List<Post> getPostList() {
		return postDAO.selectPostList();
	}
	
	public int createPost(int userId, String userName, String content, MultipartFile file) {
		// 파일을 image url 생성 후 db 입력
		String imagePath = null;
		if (file != null) {
			try {
				// 서버에 파일 업로드후 웹으로 접근할 수 있는 image url을 얻어낸다.
				imagePath = fileManagerService.saveFile(userName, file);
			} catch (IOException e) {
				logger.error("[파일업로드]" + e.getMessage());
			}
		}
		
		logger.info("####### imagePath: " + imagePath);
		return postDAO.insertPost(userId, userName, content, imagePath);
	}
	
	public int deletePost(int postId) {
		Post post = postDAO.selectPostById(postId);
		if (post == null) {
			logger.error("[포스트 삭제] 포스트가 없음. postId: " + postId);
		}
		String imagePath = post.getImagePath();	// NPE - null pointer exception
		
		if (imagePath != null) {
			try {
				fileManagerService.deleteFile(imagePath);
			} catch (IOException e) {
				logger.error("[파일 삭제] 삭제 중 실패. postId: " + postId);
			}
		}
		
		commentBO.deleteComments(postId);
		
		return postDAO.deletePost(postId);
	}
	
	public int getPostCountByUserId(int userId) {
		return postDAO.selectPostCountByUserId(userId);
	}
}
