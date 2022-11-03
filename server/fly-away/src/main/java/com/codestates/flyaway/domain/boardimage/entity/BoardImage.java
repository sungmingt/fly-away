package com.codestates.flyaway.domain.boardimage.entity;

import com.codestates.flyaway.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public void setBoard(Board board) {
        this.board = board;
        if(!board.getImages().contains(this)) {
            board.getImages().add(this);
        }
    }
}
