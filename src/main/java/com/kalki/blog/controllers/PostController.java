package com.kalki.blog.controllers;

import com.kalki.blog.config.AppConstants;
import com.kalki.blog.config.MessageConstants;
import com.kalki.blog.payloads.*;
import com.kalki.blog.services.FileService;
import com.kalki.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<ApiResponseWrapper<PostDto>> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto created = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(ApiResponseWrapper.success(MessageConstants.POST_CREATED, created), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<ApiResponseWrapper<List<PostDto>>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POSTS_FETCHED_BY_USER, posts));
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<ApiResponseWrapper<List<PostDto>>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POSTS_FETCHED_BY_CATEGORY, posts));
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponseWrapper<PostResponse>> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR) String sortDir) {

        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POSTS_FETCHED, postResponse));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseWrapper<PostDto>> getPostById(@PathVariable Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POST_FETCHED, postDto));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POST_DELETED, null));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseWrapper<PostDto>> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updated = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POST_UPDATED, updated));
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<ApiResponseWrapper<List<PostDto>>> searchPostByTitle(@PathVariable("keywords") String keywords) {
        List<PostDto> result = postService.searchPosts(keywords);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.POSTS_SEARCHED, result));
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<ApiResponseWrapper<PostDto>> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updated = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.IMAGE_UPLOADED, updated));
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
