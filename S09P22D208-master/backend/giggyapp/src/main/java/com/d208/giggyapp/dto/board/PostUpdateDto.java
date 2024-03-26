package com.d208.giggyapp.dto.board;


import com.d208.giggyapp.domain.board.Category;
import com.d208.giggyapp.domain.board.Post;
import com.d208.giggyapp.domain.board.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {
//    private UUID userId;

    private String title;

    private String content;

    private PostType postType;

    private Category category;

//    private String picture;

//    public PostUpdateDto(Post post) {
//        this.title= post.getTitle();
//        this.content = post.getContent();
//        this.postType = post.getPostType();
//        this.category = post.getCategory();
//        this.picture = post.getPicture();
//    }

}
