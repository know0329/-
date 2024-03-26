package com.d208.giggyapp.dto.board;

import com.d208.giggyapp.domain.board.Category;
import com.d208.giggyapp.domain.board.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDto {

    private UUID userId;

    private String title;

    private String content;

    private PostType postType;

    private Category category;

    private String picture;



}
