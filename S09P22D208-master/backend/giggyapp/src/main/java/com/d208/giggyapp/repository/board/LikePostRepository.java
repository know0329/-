package com.d208.giggyapp.repository.board;

import com.d208.giggyapp.domain.board.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    int countByPostId(Long id);

    int countByPostIdAndUserId(Long postId, UUID userId);

    void deleteByPostIdAndUserId(Long postId, UUID userId);
}
