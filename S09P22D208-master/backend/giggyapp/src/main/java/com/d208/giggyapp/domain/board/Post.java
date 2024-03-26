package com.d208.giggyapp.domain.board;

import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.dto.BaseTimeEntity;
import com.d208.giggyapp.dto.board.PostCreateDto;
import com.d208.giggyapp.dto.board.PostUpdateDto;
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
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int viewCount;

    private String picture;

    public void increaseViewCnt() {
        this.viewCount ++;
    }



    public static Post toEntity(PostCreateDto postCreateDto, User user){
        return Post.builder()
                .user(user)
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .postType(postCreateDto.getPostType())
                .category(postCreateDto.getCategory())
                .picture(postCreateDto.getPicture())
                .build();
    }

    public void update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        this.postType = postUpdateDto.getPostType();
        this.category = postUpdateDto.getCategory();
    }


    public void setImageUrl(String imageUrl) {
        this.picture = imageUrl;
    }
}
