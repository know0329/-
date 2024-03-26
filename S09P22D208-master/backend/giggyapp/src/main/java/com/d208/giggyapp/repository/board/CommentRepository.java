package com.d208.giggyapp.repository.board;

import com.d208.giggyapp.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countByPostId(Long id);

    List findAllByPostId(Long postId);
}
