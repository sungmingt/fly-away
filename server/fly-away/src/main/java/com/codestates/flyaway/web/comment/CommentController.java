package com.codestates.flyaway.web.comment;

import com.codestates.flyaway.domain.comment.service.CommentService;
import com.codestates.flyaway.web.comment.dto.CommentDto;
import com.codestates.flyaway.web.comment.dto.CommentDto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    @ResponseStatus(CREATED)
    public CommentResponseDto write(@PathVariable("boardId") Long boardId,
                                    @RequestBody CommentDto.Write writeDto) {
        writeDto.setBoardId(boardId);
        return commentService.write(writeDto);
    }

    @PatchMapping("/{boardId}/comment/{commentId}")
    public CommentResponseDto update(@PathVariable("boardId") Long boardId,
                                     @PathVariable("commentId") Long commentId,
                                     @RequestBody CommentDto.Update updateDto) {
        updateDto.setCommentId(commentId);
        return commentService.update(updateDto);
    }

    @GetMapping("/{boardId}/comment")
    public List<CommentDto.MultiCommentDto> read(@PathVariable("boardId") Long boardId,
                                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                 Pageable pageable) {
        return commentService.readByBoardId(boardId, pageable);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    public HttpStatus delete(@PathVariable("commentId") Long commentId,
                             @RequestBody CommentDto.Delete deleteDto) {
        deleteDto.setCommentId(commentId);
        commentService.delete(deleteDto);

        return NO_CONTENT;
    }
}
