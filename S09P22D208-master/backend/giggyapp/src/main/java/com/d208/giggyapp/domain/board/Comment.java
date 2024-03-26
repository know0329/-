package com.d208.giggyapp.domain.board;

import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.dto.BaseTimeEntity;
import com.d208.giggyapp.dto.board.CommentCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String content;


    public static Comment toEntity(Post post, CommentCreateDto commentCreateDto, User user) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(commentCreateDto.getContent())
                .build();
    }
}
