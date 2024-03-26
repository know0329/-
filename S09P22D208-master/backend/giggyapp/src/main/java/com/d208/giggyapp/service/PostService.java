package com.d208.giggyapp.service;

import com.d208.giggyapp.domain.board.*;
import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.dto.board.*;
import com.d208.giggyapp.domain.board.LikePost;
import com.d208.giggyapp.domain.board.Post;
import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.dto.board.PostCreateDto;
import com.d208.giggyapp.dto.board.PostDto;
import com.d208.giggyapp.dto.board.PostListDto;
import com.d208.giggyapp.dto.board.PostUpdateDto;
import com.d208.giggyapp.repository.board.CommentRepository;
import com.d208.giggyapp.repository.board.LikePostRepository;
import com.d208.giggyapp.repository.board.PostRepository;
import com.d208.giggyapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final S3Service s3Service;

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long createPost(MultipartFile file, PostCreateDto postCreateDto) {
        User user = userRepository.findById(postCreateDto.getUserId()).orElse(null);
        String imageUrl = "";
        if(user != null){
            Post post = Post.toEntity(postCreateDto, user);

            if (file != null) {
                // 파일 저장
                imageUrl = s3Service.uploadFile(file).getBody();
            }
            // 회원 저장
            post.setImageUrl(imageUrl);
            postRepository.save(post);

            return post.getId();
        }
        else {
            throw new IllegalArgumentException("유저 없음");
        }
    }

    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));
        postRepository.delete(post);
    }


    @Transactional
    public PostDto getPost(Long postId, UUID currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));

        // 좋아요수
        int likeCnt = likePostRepository.countByPostId(postId);
        // 좋아요 유무
        int liked = likePostRepository.countByPostIdAndUserId(postId, currentUserId);

        int commentCnt = commentRepository.countByPostId(post.getId());

        // 현재 사용자가 해당 게시글을 좋아요했는지 여부를 판단
        boolean isLiked = false;
        if(liked == 1) {
            isLiked = true;
        }

        // 조회수
        postRepository.increaseViewCnt(postId);
        post.increaseViewCnt();

        return new PostDto(post, likeCnt, isLiked, commentCnt);

    }


    @Transactional
    public Long updatePost(MultipartFile file, PostUpdateDto postUpdateDto, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));

        post.update(postUpdateDto);
        String imageUrl = "";

        // 생성 시 사진 첨부, 수정 시 새로운 사진으로 변경
        if (post.getPicture() != null && file != null) {
            imageUrl = s3Service.uploadFile(file).getBody();
        }
        // 생성 시 사진 첨부, 수정 시 기존 사진 그대로
        if (post.getPicture() != null && file == null) {
            imageUrl = post.getPicture();
        }
        // 생성 시 사진 없음, 수정 시 사진 첨부
        if(post.getPicture() == null && file != null) {
            imageUrl = s3Service.uploadFile(file).getBody();
        }
        // 생성 시 사진 없음, 수정 시에도 없음
        if (post.getPicture() == null && file == null) {
            imageUrl = "";
        }

        post.setImageUrl(imageUrl);
//        postRepository.save(post);

        return post.getId();
    }


//    @Transactional
//    public List<PostListDto> getPostList(String keyword, UUID currentUserId) {
//        List<Post> posts = postRepository.findAllByTitleContainingIgnoreCaseOrderByIdDesc(keyword);
//        List<PostListDto> postListDtos = new ArrayList<>();
//
//        for(Post post : posts) {
//            int likeCnt = likePostRepository.countByPostId(post.getId());
//            int commentCnt = commentRepository.countByPostId(post.getId());
//
//            boolean isLiked = isPostLikedByUser(post.getId(), currentUserId);
////            int liked = likePostRepository.countByPostIdAndUserId(postId, post.getUser().getId());
////
////            boolean isLiked = false;
////            if(liked == 1) {
////                isLiked = true;
////            }
//
//            postListDtos.add(new PostListDto(post, likeCnt, commentCnt, isLiked));
//        }
//        return postListDtos;
//    }

    private boolean isPostLikedByUser(Long postId, UUID currentUserId) {
        int liked = likePostRepository.countByPostIdAndUserId(postId, currentUserId);
        return liked == 1;
    }

    @Transactional
    public void togglePostLike(Long postId, UUID userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));
        User user = userRepository.findById(userId).orElse(null);
    if (likePostRepository.countByPostIdAndUserId(postId, userId)== 0) {
            likePostRepository.save(LikePost.builder()
                    .post(post)
                    .user(user)
                    .build());
        } else {
            likePostRepository.deleteByPostIdAndUserId(postId, userId);
        }
    }

    @Transactional
    public Long createComment(CommentCreateDto commentCreateDto, Long postId) {
        User user = userRepository.findById(commentCreateDto.getUserId()).orElse(null);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));

        if (user != null && post != null) {
            Comment comment = Comment.toEntity(post, commentCreateDto, user);
            commentRepository.save(comment);

            return comment.getId();
        } else {
            throw new IllegalArgumentException("유저나 게시글 없음");
        }
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글 없음"));
        commentRepository.delete(comment);
    }

    private CommentListDto toCommentListDto(Comment comment) {
        return new CommentListDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()
        );
    }

    @Transactional
    public List<CommentListDto> getCommentList(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream()
                .map(this::toCommentListDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostListDto> getPostListType(String keyword, UUID currentUserId, String postType) {

        List<Post> posts = postRepository.findAllByTitleAndpostType(keyword, postType);
        List<PostListDto> postListDtos = new ArrayList<>();

        for(Post post : posts) {
            int likeCnt = likePostRepository.countByPostId(post.getId());
            int commentCnt = commentRepository.countByPostId(post.getId());

            boolean isLiked = isPostLikedByUser(post.getId(), currentUserId);

            postListDtos.add(new PostListDto(post, likeCnt, commentCnt, isLiked));
        }
        return postListDtos;


    }
}
