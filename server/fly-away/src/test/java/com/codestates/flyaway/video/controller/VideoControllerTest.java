package com.codestates.flyaway.video.controller;

import com.codestates.flyaway.config.WebConfig;
import com.codestates.flyaway.domain.video.service.VideoService;
import com.codestates.flyaway.web.auth.interceptor.AuthInterceptor;
import com.codestates.flyaway.web.video.controller.VideoController;
import com.codestates.flyaway.web.video.dto.VideoDto.AddRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import static com.codestates.flyaway.web.video.dto.VideoDto.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VideoController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class, AuthInterceptor.class})
        })
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private VideoService videoService;

    @DisplayName("web - 시청 기록 저장 테스트")
    @Test
    void saveViewRecordTest() throws Exception {
        //given
        Long memberId = 1L;
        String videoId = "thisIsVideoId";
        String title = "thisIsTitle";
        String url = "thisIsUrl";

        AddRequest request = new AddRequest(memberId, videoId, title, url);
        String content = gson.toJson(request);

        AddResponse response = new AddResponse(videoId, title, url);
        given(videoService.addRecent(any(AddRequest.class)))
                .willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                post("/videos")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.videoId").value(videoId))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.url").value(url))
                .andReturn();
    }

    @DisplayName("web - 시청 기록 조회 테스트")
    @Test
    void getRecentTest() throws Exception {
        //given
        Long memberId = 1L;

        List<VideoList> videoList = List.of(
                new VideoList("videoId1", "videoTitle1", "videoUrl1"),
                new VideoList("videoId2", "videoTitle2", "videoUrl2"),
                new VideoList("videoId3", "videoTitle3", "videoUrl3")
        );

        given(videoService.getRecent(memberId))
                .willReturn(videoList);

        //when
        ResultActions actions = mockMvc.perform(
                get("/videos/{memberId}", memberId)
                        .accept(APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].videoId").value("videoId1"))
                .andExpect(jsonPath("$.data[1].title").value("videoTitle2"))
                .andExpect(jsonPath("$.data[2].url").value("videoUrl3"))
                .andReturn();
    }
}
