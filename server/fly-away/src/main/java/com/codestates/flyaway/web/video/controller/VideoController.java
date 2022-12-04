package com.codestates.flyaway.web.video.controller;

import com.codestates.flyaway.domain.video.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.codestates.flyaway.web.video.dto.VideoDto.*;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @ApiOperation(value = "시청 기록 저장")
    @PostMapping("/video")
    public AddResponse addRecent(@Validated @RequestBody AddRequest request) {
        return videoService.addRecent(request);
    }

    @ApiOperation(value = "최근 시청 기록 조회")
    @GetMapping("/members/{memberId}/video")
    public ListResponse<VideoList> getRecent(@NotEmpty @PathVariable long memberId) {
        List<VideoList> videoList = videoService.getRecent(memberId);
        return new ListResponse<>(videoList);
    }
}
