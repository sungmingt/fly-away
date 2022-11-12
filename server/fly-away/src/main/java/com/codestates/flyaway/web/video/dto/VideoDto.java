package com.codestates.flyaway.web.video.dto;

import com.codestates.flyaway.domain.video.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class VideoDto {

    @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class AddRequest{
        private long memberId;
        private String videoId;
        private String title;
        private String url;
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
