package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;

public interface PostService {
	
	public PostDto createPost(PostDto postDto);

	public List<PostDto> getAllPosts();

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postDto, long id);
}
