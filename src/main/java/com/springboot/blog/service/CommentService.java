package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
}
