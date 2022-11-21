package com.codestates.flyaway.domain.video.service;

import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.service.MemberService;
import com.codestates.flyaway.domain.video.entity.Video;
import com.codestates.flyaway.domain.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.codestates.flyaway.web.video.dto.VideoDto.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final MemberService memberService;

    /**
     * 영상 시청 -> 최근 영상 목록 업데이트
     */
    public void addRecent(AddRequest request) {
        Member member = memberService.findById(request.getMemberId());

        Video video = new Video(request.getVideoId(), request.getTitle(), request.getUrl(), member);

        //동일 영상 시청 기록이 있는 경우 -> 중복 제거를 위해 기존 기록 삭제
        videoRepository.findByVideoIdAndMemberId(request.getVideoId(), request.getMemberId())
                        .ifPresent(videoRepository::delete);

        videoRepository.save(video);
    }

    /**
     * 최근 영상 목록 조회
     * @param memberId
     */
    public List<VideoList> getRecent(long memberId) {
        return videoRepository.findRecent(memberId)
                .stream()
                .map(VideoList::toVideoList)
                .collect(Collectors.toList());
    }
}
