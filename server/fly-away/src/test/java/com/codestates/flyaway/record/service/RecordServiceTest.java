package com.codestates.flyaway.record.service;

import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.service.MemberService;
import com.codestates.flyaway.domain.record.entity.Record;
import com.codestates.flyaway.domain.record.repository.RecordRepository;
import com.codestates.flyaway.domain.record.service.RecordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.codestates.flyaway.web.record.dto.RecordDto.*;
import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private RecordService recordService;

    @DisplayName("운동 시간 기록 - 해당 날짜 첫 기록")
    @Test
    void newRecordTest() {
        //given
        Member member = new Member(1L, "kim", "member1@gmail.com", "pw");
        InsertRequest request = new InsertRequest(1800L);

        given(recordRepository.findByMemberIdAndDate(anyLong(), eq(now())))
                .willReturn(Optional.empty());

        given(memberService.findById(anyLong()))
                .willReturn(member);

        //when
        InsertResponse response = recordService.save(1L, request);
        long totalRecord = member.getRecords().stream().mapToLong(Record::getRec).sum();

        //then
        assertThat(response.getRecord()).isEqualTo(request.getRecord());
        assertThat(totalRecord).isEqualTo(request.getRecord());
    }

    @DisplayName("운동 시간 기록 - 누적 기록 추가")
    @Test
    void addRecordTest() {
        //given
        Member member = new Member(1L, "kim", "member1@gmail.com", "pw");
        Record record = new Record(now(), 500);

        InsertRequest request = new InsertRequest(1800L);

        given(recordRepository.findByMemberIdAndDate(anyLong(), eq(now())))
                .willReturn(Optional.of(record));

        given(memberService.findById(anyLong()))
                .willReturn(member);

        //when
        recordService.save(1L, request);
        long totalRecord = member.getRecords()
                .stream()
                .mapToLong(Record::getRec)
                .sum();

        //then
        assertThat(totalRecord).isEqualTo(2300);
    }
}
