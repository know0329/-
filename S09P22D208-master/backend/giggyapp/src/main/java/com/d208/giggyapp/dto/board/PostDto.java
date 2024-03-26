package com.d208.giggyapp.dto.board;

import com.d208.giggyapp.domain.board.Category;
import com.d208.giggyapp.domain.board.Post;
import com.d208.giggyapp.domain.board.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private UUID userId;

    private String nickName;

    private String title;

    private String content;

    private PostType postType;

    private Category category;

    private int viewCount;

    private boolean isLiked;

    private int likeCnt;

    private int commentCnt;

    private Long createdAt;

    private String picture;


    public PostDto(Post post, int likeCnt, boolean isLiked, int commentCnt){
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postType = post.getPostType();
        this.category = post.getCategory();
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        this.isLiked = isLiked;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.picture = post.getPicture();

    }


}
