package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	// Get all posts rest API
	@GetMapping
	public List<PostDto> getAllPosts(){
		
		return postService.getAllPosts();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable(name="id") long id){
		
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	// Update post by id rest API
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="id") long id){
		PostDto postResponse = postService.updatePost(postDto, id);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	
	}
}
