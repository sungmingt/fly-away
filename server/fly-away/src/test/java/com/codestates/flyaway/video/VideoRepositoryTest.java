package com.codestates.flyaway.video;

import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.record.entity.Record;
import com.codestates.flyaway.domain.record.repository.RecordRepository;
import com.codestates.flyaway.domain.video.entity.Video;
import com.codestates.flyaway.domain.video.repository.VideoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Rollback(value = false)
class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        Member member = new Member("jason", "jason12@gmail.com", "zxcv123@");
        Member member2 = new Member("mike", "mike12@gmail.com", "abcd456@");

        Video video = new Video("videoId", "videoTitle", "videoUrl", member);
        Video video2 = new Video("videoId2", "videoTitle2", "videoUrl2", member);
        Video video3 = new Video("videoId3", "videoTitle3", "videoUrl3", member2);
        Video video4 = new Video("videoId4", "videoTitle4", "videoUrl4", member2);

        memberRepository.save(member);
        memberRepository.save(member2);
        videoRepository.save(video);
        videoRepository.save(video2);
        videoRepository.save(video3);
        videoRepository.save(video4);
    }

    @DisplayName("특정 회원의 특정 영상 시청 기록 조회")
    @Test
    void findByVideoIdAndMemberId() {
        //given
        String videoId = "videoId2";
        Long memberId = 1L;

        //when
        Video findVideo = videoRepository.findByVideoIdAndMemberId(videoId, memberId).get();

        //then
        assertThat(findVideo.getVideoId()).isEqualTo("videoId2");
        assertThat(findVideo.getTitle()).isEqualTo("videoTitle2");
        assertThat(findVideo.getUrl()).isEqualTo("videoUrl2");
    }

    @DisplayName("최근 영상 시청 기록 조회")
    @Test
    void findRecentTest() {
        //given
        Long memberId = 2L;

        //when
        List<Video> recentVideos = videoRepository.findRecent(memberId);

        //then
        assertThat(recentVideos).hasSize(2);
        assertThat(recentVideos.get(0).getVideoId()).isEqualTo("videoId4");
        assertThat(recentVideos.get(1).getVideoId()).isEqualTo("videoId3");
    }
}
