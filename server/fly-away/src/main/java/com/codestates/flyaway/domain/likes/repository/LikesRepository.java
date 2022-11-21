package com.codestates.flyaway.domain.likes.repository;


import com.codestates.flyaway.domain.board.entity.Board;
import com.codestates.flyaway.domain.likes.entity.Likes;
import com.codestates.flyaway.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByBoardAndMember(Board board, Member member);
}
