package com.codestates.flyaway.video.large;

import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.video.entity.Video;
import com.codestates.flyaway.domain.video.repository.VideoRepository;
import com.codestates.flyaway.domain.video.service.VideoService;
import com.codestates.flyaway.web.video.dto.VideoDto.AddRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static com.codestates.flyaway.web.video.dto.VideoDto.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest
class VideoLargeTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @DisplayName("영상 시청 기록 관리 테스트")
    @TestFactory
    Stream<DynamicTest> joinAndLogin() {
        Member member = new Member("jason", "jason12@gmail.com", "zxcv123@");
        memberRepository.save(member);

        final String videoId = "videoId";
        final String title = "videoTitle";
        final String url = "videoUrl";

        return Stream.of(
                dynamicTest("영상 시청 기록 추가", () -> {
                    //given
                    AddRequest request = new AddRequest(1L, videoId, title, url);

                    //when
                    videoService.addRecent(request);

                    //then
                    Video findVideo = videoRepository.findById(1L).get();

                    assertThat(findVideo.getVideoId()).isEqualTo(videoId);
                    assertThat(findVideo.getTitle()).isEqualTo(title);
                    assertThat(findVideo.getUrl()).isEqualTo(url);
                }),

                dynamicTest("영상 시청 기록 조회", () -> {
                    //given
                    Long memberId = 1L;

                    //when
                    List<VideoList> recentVideos = videoService.getRecent(memberId);

                    //then
                    assertThat(recentVideos).hasSize(1);
                    assertThat(recentVideos.get(0).getTitle()).isEqualTo("videoTitle");
                    assertThat(recentVideos.get(0).getUrl()).isEqualTo("videoUrl");
                }),

                dynamicTest("동일 영상 시청 시 기존 기록 삭제", () -> {
                    //given
                    Long memberId = 1L;
                    AddRequest request = new AddRequest(1L, videoId, title, url);

                    //when
                    videoService.addRecent(request);

                    //when
                    List<VideoList> recentVideos = videoService.getRecent(memberId);

                    //then
                    assertThat(recentVideos).hasSize(1);
                    assertThat(recentVideos.get(0).getTitle()).isEqualTo("videoTitle");
                    assertThat(recentVideos.get(0).getUrl()).isEqualTo("videoUrl");
                }));
    }
}