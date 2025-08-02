package com.kalki.blog.services;

import com.kalki.blog.entities.Post;
import com.kalki.blog.payloads.PostDto;
import com.kalki.blog.payloads.PostResponse;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer CategoryId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);

    //Delete
    void deletePost(Integer postId);

    //get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    //get single post
    PostDto getPostById(Integer postId);

    //get all post by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get all post by user
    List<PostDto> getPostsByUser(Integer userId);

    //search posts
    List<PostDto> searchPosts(String keyword);
}
