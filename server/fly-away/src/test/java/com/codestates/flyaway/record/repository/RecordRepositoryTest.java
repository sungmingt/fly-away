package com.codestates.flyaway.record.repository;

import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.record.entity.Record;
import com.codestates.flyaway.domain.record.repository.RecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecordRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecordRepository recordRepository;

    @DisplayName("회원 ID와 날짜로 운동 기록 조회 (findByMemberIdAndDate)")
    @Test
    void findRecordTest() {
        //given
        Member member = new Member("김코딩", "kimcode@gmail.com", "password123@");
        Record record = new Record(now(), 10);
        record.setMember(member);

        memberRepository.save(member);
        recordRepository.save(record);

        //when
        Record findRecord = recordRepository.findByMemberIdAndDate(member.getId(), now())
                .orElseThrow(() -> new RuntimeException("###"));

        //then
        assertThat(findRecord.getMember().getId()).isEqualTo(member.getId());
        assertThat(findRecord.getRecord()).isEqualTo(10);
    }
}
