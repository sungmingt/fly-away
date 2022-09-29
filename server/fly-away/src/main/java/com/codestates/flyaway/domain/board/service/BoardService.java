package com.codestates.flyaway.domain.board.service;

import com.codestates.flyaway.domain.board.entity.Board;
import com.codestates.flyaway.domain.board.repository.BoardRepository;
import com.codestates.flyaway.domain.boardimage.repository.BoardImageRepository;
import com.codestates.flyaway.domain.boardimage.service.BoardImageService;
import com.codestates.flyaway.domain.category.entity.Category;
import com.codestates.flyaway.domain.category.service.CategoryService;
import com.codestates.flyaway.global.exception.BusinessLogicException;
import com.codestates.flyaway.web.board.dto.BoardDto;
import com.codestates.flyaway.web.board.dto.BoardImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

import static com.codestates.flyaway.global.exception.ExceptionCode.*;
import static com.codestates.flyaway.web.board.dto.BoardDto.*;
import static com.codestates.flyaway.web.board.dto.BoardDto.BoardResponseDto.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryService categoryService;
    private final BoardImageRepository boardImageRepository;
    private final BoardImageService boardImageService;

    @Transactional
    public BoardResponseDto create(List<MultipartFile> images, BoardDto.Create createDto) {

        Category category = categoryService.findById(createDto.getCategoryId());
        Board board = new Board(createDto.getTitle(), createDto.getContent());
        board.setCategory(category);
        boardRepository.save(board);
        boardImageService.saveFiles(images, board);

        return toResponseDto(board);
    }

    @Transactional
    public BoardResponseDto update(BoardDto.Update updateDto) {

        Category category = categoryService.findById(updateDto.getCategoryId());
        final Board board = boardRepository.getReferenceById(updateDto.getBoardId());
        board.setCategory(category);
        board.update(updateDto.getTitle(), updateDto.getContent());

        return toResponseDto(board);
    }

    @Transactional
    public BoardResponseDto read(Long boardId) {

        Board board = findById(boardId);
        board.addViewCount();

        return toResponseDto(board);
    }

    public Page<Board> readAll(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public Page<Board> readByCategory(Long categoryId, Pageable pageable) {

        return boardRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Transactional
    public void delete(Long boardId) {

        Board board = findById(boardId);
        boardRepository.delete(board);
    }

    public Board findById(Long boardId) {

        return boardRepository.findById(boardId).orElseThrow(() ->
                new BusinessLogicException(ARTICLE_NOT_FOUND));
    }

    public Resource getImage(Long imageId) {

        BoardImageDto boardImageDto = boardImageService.findByImageId(imageId);
        String path = "file:" + boardImageService.getFullPath(boardImageDto.getFileName());

        try {
            return new UrlResource(path);
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> getImageId(Long boardId) {
        return boardImageService.findByBoard(boardId);
    }
}