package com.codestates.flyaway.web.comment.dto;

import com.codestates.flyaway.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentDto {

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class Write {
        private Long memberId;
        private Long boardId;
        @NotBlank
        private String content;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class Update {
        private Long memberId;
        private Long boardId;
        private Long commentId;
        @NotBlank
        private String content;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class Delete {
        private Long memberId;
        private Long commentId;
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class CommentResponseDto {
        private Long memberId;
        private String memberName;
        private Long commentId;
        private String content;
        private LocalDateTime createdAt;

        public static CommentDto.CommentResponseDto toResponseDto(Comment comment) {
            return new CommentDto.CommentResponseDto(
                    comment.getMember().getId(),
                    comment.getMember().getName(),
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedAt());
        }
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class MultiCommentDto {
        private Long memberId;
        private String memberName;
        private Long commentId;
        private String content;
        private LocalDateTime createdAt;

        public static List<MultiCommentDto> toResponsesDto(List<Comment> comments) {
            return comments.stream()
                    .map(comment -> new MultiCommentDto(
                            comment.getMember().getId(),
                            comment.getMember().getName(),
                            comment.getId(),
                            comment.getContent(),
                            comment.getCreatedAt()))
                    .collect(Collectors.toList());
        }
    }
}
