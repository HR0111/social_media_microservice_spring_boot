package org.hemant.linkedin.posts_service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.posts_service.DTO.PersonDTO;
import org.hemant.linkedin.posts_service.DTO.PostRequestDTO;
import org.hemant.linkedin.posts_service.DTO.PostResponseDTO;
import org.hemant.linkedin.posts_service.auth.UserContextHolder;
import org.hemant.linkedin.posts_service.clients.ConnectionClient;
import org.hemant.linkedin.posts_service.entity.Post;
import org.hemant.linkedin.posts_service.exception.ResourceNotFoundException;
import org.hemant.linkedin.posts_service.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionClient connectionClient;


    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, Long userId) {

        Post post = modelMapper.map(postRequestDTO, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponseDTO.class);


    }

    public PostResponseDTO getPostById(Long postId) {
        log.info("Getting post with Id {}", postId);

        Long userId = UserContextHolder.getCurrentUserId();

        List<PersonDTO> firstConnections = connectionClient.getFirstDegreeConnections();

        // todo send notification to all connections

        Post post =  postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post not found with Id: " + postId));

        return modelMapper.map(post , PostResponseDTO.class);
    }

    public List<PostResponseDTO> getAllPostsOfUser(Long userId) {

       List<Post> posts =  postRepository.findByUserId(userId);
       return posts.stream().map((post) -> modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
    }
}
