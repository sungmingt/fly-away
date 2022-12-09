package com.codestates.flyaway.domain.record.service;

import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.service.MemberService;
import com.codestates.flyaway.domain.record.entity.Record;
import com.codestates.flyaway.domain.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codestates.flyaway.web.record.dto.RecordDto.*;
import static com.codestates.flyaway.web.record.dto.RecordDto.InsertResponse.recordToInsertResponse;
import static java.time.LocalDate.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberService memberService;

    /**
     * 운동 시간 기록
     * @return 회원 id, 기록 정보(날짜, 운동시간)
     */
    public InsertResponse save(Long memberId, InsertRequest insertDto) {
        long rec = insertDto.getRecord();
        Record record = recordRepository.findByMemberIdAndDate(memberId, now())
                .orElseGet(() -> new Record(now(), 0));

        addRecord(memberId, rec, record);
        return recordToInsertResponse(memberId, record);
    }

    private void addRecord(Long memberId, long rec, Record record) {
        //운동 시간 추가
        record.addRecord(rec);

        //해당 날짜 첫 운동기록인 경우
        if (record.getMember() == null) {
            Member findMember = memberService.findById(memberId);
            record.setMember(findMember);

            recordRepository.save(record);
        }
    }
}
