package org.hemant.linkedin.posts_service.controller;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.posts_service.DTO.PostRequestDTO;
import org.hemant.linkedin.posts_service.DTO.PostResponseDTO;
import org.hemant.linkedin.posts_service.auth.UserContextHolder;
import org.hemant.linkedin.posts_service.entity.Post;
import org.hemant.linkedin.posts_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO postRequestDTO) {

        PostResponseDTO createPost = postService.createPost(postRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPost);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId ) {

        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting post with id {}", postId);

        PostResponseDTO postResponseDTO = postService.getPostById(postId);
        log.info("Returning post with id {}", postId);

        return ResponseEntity.ok(postResponseDTO);
    }

    @GetMapping("users/{userId}/allPosts")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(@PathVariable Long userId){

        List<PostResponseDTO> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);

    }
}
