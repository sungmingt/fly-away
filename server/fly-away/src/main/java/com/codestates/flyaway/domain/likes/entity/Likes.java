package com.codestates.flyaway.domain.likes.entity;

import com.codestates.flyaway.domain.board.entity.Board;
import com.codestates.flyaway.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public Likes(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}
