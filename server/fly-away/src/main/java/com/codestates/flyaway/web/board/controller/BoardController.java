package com.codestates.flyaway.web.board.controller;

import com.codestates.flyaway.domain.board.service.BoardService;
import com.codestates.flyaway.domain.boardimage.service.BoardImageService;
import com.codestates.flyaway.web.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.codestates.flyaway.web.board.dto.BoardDto.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardImageService boardImageService;

    @ResponseStatus(CREATED)
    @PostMapping(value = "/{categoryId}",consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public BoardResponseDto create(@PathVariable("categoryId") Long categoryId,
                                   @RequestPart(value = "image",required = false) List<MultipartFile> images,
                                   @Validated @RequestPart Create createDto) {
        createDto.setCategoryId(categoryId);
        return boardService.create(images, createDto);
    }

    @PatchMapping("/{boardId}")
    public BoardResponseDto update(@NotEmpty @PathVariable("boardId") Long boardId,
                                   @RequestPart(value = "image",required = false) List<MultipartFile> images,
                                   @Validated @RequestPart Update updateDto) {
        updateDto.setBoardId(boardId);
        return boardService.update(images, updateDto);
    }

    @GetMapping("/{boardId}")
    public BoardResponseDto read(@NotEmpty @PathVariable("boardId") Long boardId) {
        return boardService.read(boardId);
    }

    @GetMapping("/all")
    public List<MultiBoardDto> readAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.readAll(pageable);
    }

    @GetMapping
    public List<MultiBoardDto> readByCategory(@RequestParam Long categoryId, Pageable pageable) {
        return boardService.readByCategory(categoryId, pageable);
    }

    @DeleteMapping("/{boardId}")
    public HttpStatus delete(@NotEmpty @PathVariable("boardId") Long boardId,
                             @RequestBody Delete deleteDto) {
        deleteDto.setBoardId(boardId);

        boardService.delete(deleteDto);
        return NO_CONTENT;
    }

    @GetMapping("/image/{imageId}")
    public String getImage(@NotEmpty @PathVariable Long imageId) {
        return boardImageService.getImage(imageId);
    }

    @PostMapping("/{boardId}/like")
    public Boolean doLike(@PathVariable("boardId") Long boardId,
                          @RequestParam Long memberId) {
        return boardService.doLike(boardId, memberId);
    }

    @GetMapping("/{boardId}/checklike")
    public Boolean checkLike(@NotEmpty @PathVariable("boardId") Long boardId,
                           @RequestParam Long memberId) {
        return boardService.checkLike(boardId, memberId);
    }
}