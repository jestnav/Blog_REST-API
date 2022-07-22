package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto);

	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postDto, long id);

	void deletePostById(long id);
}
