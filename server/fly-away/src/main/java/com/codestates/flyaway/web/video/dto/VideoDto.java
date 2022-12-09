package com.codestates.flyaway.web.video.dto;

import com.codestates.flyaway.domain.video.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class VideoDto {

    @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class AddRequest{
        @NotNull
        @Positive
        private Long memberId;
        @NotEmpty
        private String videoId;
        @NotEmpty
        private String title;
        @NotEmpty
        private String url;
    }

    @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class AddResponse{
        private String videoId;
        private String title;
        private String url;

        public static AddResponse toAddResponse(Video video) {
            return new AddResponse(video.getVideoId(), video.getTitle(), video.getUrl());
        }
    }

    @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class VideoList{
        private String videoId;
        private String title;
        private String url;

        public static VideoList toVideoList(Video video) {
            return new VideoList(video.getVideoId(), video.getTitle(), video.getUrl());
        }
    }

    @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class ListResponse<T> {
        private List<T> data;
    }
}
