package org.hemant.linkedin.posts_service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.posts_service.entity.PostLike;
import org.hemant.linkedin.posts_service.exception.BadRequestException;
import org.hemant.linkedin.posts_service.exception.ResourceNotFoundException;
import org.hemant.linkedin.posts_service.repository.PostLikeRepository;
import org.hemant.linkedin.posts_service.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository  postRepository;

    public void likePost(Long postId, Long userId) {

        log.info("Trying To  Like Post with Id: " + postId);

        boolean exists = postRepository.existsById(postId);
        if(!exists) {
            throw  new ResourceNotFoundException("Post Not Found WIth Id: "+ postId);
        }

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId , postId);
        if(alreadyLiked) {
            throw new BadRequestException("Post Like Already Exists" + postId);
        }

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);

        postLikeRepository.save(postLike);

        log.info("Post Liked with Id :{} SuccessFul: " , postId);





    }

    @Transactional
    public void unlikePost(Long postId, Long userId) {


        log.info("Trying To  UN-Like Post with Id: " + postId);

        boolean exists = postRepository.existsById(postId);
        if(!exists) {
            throw  new ResourceNotFoundException("Post Not Found WIth Id: "+ postId);
        }

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId , postId);
        if(!alreadyLiked) {
            throw new BadRequestException("Cannot  Un-Like Post which is not Liked" + postId);
        }


        postLikeRepository.deleteByUserIdAndPostId(userId , postId);
        log.info("Post UN-Liked with Id :{} SuccessFul: " , postId);



    }
}
