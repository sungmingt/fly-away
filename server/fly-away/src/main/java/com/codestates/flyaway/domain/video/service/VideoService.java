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
import static com.codestates.flyaway.web.video.dto.VideoDto.AddResponse.toAddResponse;

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
    public AddResponse addRecent(AddRequest request) {
        Long memberId = request.getMemberId();
        String videoId = request.getVideoId();

        Member member = memberService.findById(memberId);
        Video video = new Video(videoId, request.getTitle(), request.getUrl(), member);

        //기존 기록 삭제
        deleteExRecord(videoId, memberId);

        //저장
        Video saved = videoRepository.save(video);
        return toAddResponse(saved);
    }

    /**
     * 최근 영상 목록 조회
     * @param memberId
     */
    @Transactional(readOnly = true)
    public List<VideoList> getRecent(Long memberId) {
        return videoRepository.findRecent(memberId)
                .stream()
                .map(VideoList::toVideoList)
                .collect(Collectors.toList());
    }

    /**
     * 동일 영상 시청 기록이 있는 경우 -> 중복 제거를 위해 기존 기록 삭제
     * @param videoId
     * @param memberId
     */
    private void deleteExRecord(String videoId, Long memberId) {
        videoRepository.findByVideoIdAndMemberId(videoId, memberId)
                .ifPresent(videoRepository::delete);
    }
}
