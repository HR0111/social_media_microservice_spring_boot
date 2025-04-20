package org.hemant.linkedin.posts_service.DTO;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PostResponseDTO {


    private Long id;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;


}
