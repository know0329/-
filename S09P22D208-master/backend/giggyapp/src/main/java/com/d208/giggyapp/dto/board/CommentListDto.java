package com.d208.giggyapp.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListDto {
    private Long id;
    private UUID userId;
    private String nickName;
    private String content;
    private Long createdAt;


}
