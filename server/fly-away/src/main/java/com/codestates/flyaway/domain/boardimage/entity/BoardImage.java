package com.codestates.flyaway.domain.boardimage.entity;

import com.codestates.flyaway.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String fileOriName;
    private String fileUrl;
    private String fileName;

    public BoardImage(String fileOriName, String fileUrl, String fileName) {
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }

    //========================

    public void setBoard(Board board) {
        this.board = board;
        if(!board.getImages().contains(this)) {
            board.getImages().add(this);
        }
    }
}
