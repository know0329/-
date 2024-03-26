package com.d208.giggyapp.controller;

import com.d208.giggyapp.domain.board.PostType;
import com.d208.giggyapp.dto.board.*;
import com.d208.giggyapp.dto.board.PostCreateDto;
import com.d208.giggyapp.dto.board.PostDto;
import com.d208.giggyapp.dto.board.PostListDto;
import com.d208.giggyapp.dto.board.PostUpdateDto;
import com.d208.giggyapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/app")
public class PostController {

        private final PostService postService;

        @Operation(summary = "게시글 생성")
        @PostMapping("/post")
        public ResponseEntity<?> createPost(@RequestPart(value = "file", required = false) MultipartFile file, @RequestPart PostCreateDto postCreateDto) {
                Long result = postService.createPost(file, postCreateDto);
                return ResponseEntity.status(201).body(result);
        }

//        @Operation(summary = "게시글 조회")
//        @PostMapping("/post/{currentUserId}")
//        public List<PostListDto> getPosts(@RequestParam(value = "keyword", defaultValue = "") String keyword, @PathVariable UUID currentUserId) {
//                return postService.getPostList(keyword, currentUserId);
//        }

        @Operation(summary = "게시글 상세 조회")
        @PostMapping("/post/{postId}/{currentUserId}")
        public ResponseEntity<?> getPost(@PathVariable Long postId, @PathVariable UUID currentUserId) {
                PostDto postDto = postService.getPost(postId, currentUserId);

                return ResponseEntity.ok(postDto);
        }

        @Operation(summary = "게시글 수정")
        @PutMapping("/post/{postId}")
        public ResponseEntity<Long> updatePost(@RequestPart(value = "file", required = false) MultipartFile file, @PathVariable Long postId, @RequestPart PostUpdateDto postUpdateDto) {
                Long result = postService.updatePost(file, postUpdateDto, postId);
                return ResponseEntity.status(201).body(result);
        }

        // 게시글 삭제
        @DeleteMapping("/post/{postId}")
        public void deletePost(@PathVariable Long postId) {
                postService.deletePost(postId);
        }

        // 게시글 좋아요
        @PostMapping("/post/{postId}/like")
        public void likePost(@PathVariable Long postId, @RequestBody UUID userId) {
                postService.togglePostLike(postId, userId);}

        // 댓글 생성
        @PostMapping("/post/{postId}/comment")
        public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentCreateDto commentCreateDto) {
                Long result = postService.createComment(commentCreateDto, postId);
                return ResponseEntity.status(201).body(result);
        }

        // 댓글 삭제
        @DeleteMapping("/post/{postId}/comment/{commentId}")
        public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
                postService.deleteComment(postId, commentId);
        }

        // 댓글 조회
        @GetMapping("/post/{postId}/comment")
        public List<CommentListDto> getComments(@PathVariable Long postId) {
                return postService.getCommentList(postId);
        }

        @Operation(summary = "게시글 조회(postType별로 필터링)")
        @PostMapping("/post/{currentUserId}")
        public List<PostListDto> getPostsListType(@RequestParam(value = "keyword", defaultValue = "") String keyword, @PathVariable UUID currentUserId, @RequestParam(value = "postType", defaultValue = "") String postType) {
                return postService.getPostListType(keyword, currentUserId, postType);
        }





}
