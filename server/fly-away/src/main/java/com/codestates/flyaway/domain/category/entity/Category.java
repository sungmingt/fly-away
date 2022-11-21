package com.codestates.flyaway.domain.category.entity;

import com.codestates.flyaway.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = ALL)
    private List<Board> boards = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public void update(String categoryName) {
        this.categoryName = categoryName;
    }
}