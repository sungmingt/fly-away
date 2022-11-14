package com.codestates.flyaway.member.large;

import com.auth0.jwt.JWT;
import com.codestates.flyaway.domain.auth.service.AuthService;
import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.member.service.MemberService;
import com.codestates.flyaway.domain.record.entity.Record;
import com.codestates.flyaway.domain.record.repository.RecordRepository;
import com.codestates.flyaway.domain.record.service.RecordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.codestates.flyaway.domain.member.util.MemberUtil.*;
import static com.codestates.flyaway.web.auth.dto.LoginDto.*;
import static com.codestates.flyaway.web.member.dto.MemberDto.*;
import static com.codestates.flyaway.web.record.dto.RecordDto.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

@SpringBootTest
class LargeTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @DisplayName("회원/운동 기능 large test")
    @TestFactory
    Stream<DynamicTest> joinAndLogin() {
        final String name = "jason";
        final String email = "jason12@gmail.com";
        final String password = "asdf1234!";

        return Stream.of(
                dynamicTest("회원 가입 진행", () -> {
                    //given
                    JoinRequest req = new JoinRequest(name, email, password);

                    //when
                    JoinResponse res = memberService.join(req);

                    //then
                    assertThat(res.getEmail()).isEqualTo(req.getEmail());
                    assertThat(res.getName()).isEqualTo(req.getName());
                }),

                dynamicTest("로그인 시도", () -> {
                    //given
                    LoginRequest req = new LoginRequest(email, password);

                    //when
                    String token = authService.login(req).getAccessToken();
                    String accessToken = token.replace("Bearer ", "");

                    //then
                    assertThat(JWT.decode(accessToken)
                            .getClaim("email").asString())
                            .isEqualTo(email);
                }),

                dynamicTest("운동하기", () -> {
                    //given
                    Long memberId = 1L;
                    Long record = 1000L;
                    InsertRequest request = new InsertRequest(record);

                    //when
                    InsertResponse response = recordService.insertRecord(memberId, request);

                    //then
                    List<Record> records = recordRepository.findByMemberId(memberId);

                    assertThat(records).hasSize(1);
                    assertThat(records.get(0).getRecord()).isEqualTo(record);
                }),

                dynamicTest("프로필 조회", () -> {
                    //given
                    Long memberId = 1L;

                    //when
                    MemberProfileResponse response = memberService.findByIdFetch(memberId);

                    //then
                    assertThat(response.getEmail()).isEqualTo(email);
                    assertThat(response.getName()).isEqualTo(name);
                    assertThat(response.getTotalRecord()).isEqualTo(1000L);
                }),

                dynamicTest("회원 정보 수정", () -> {
                    //given
                    UpdateRequest req = new UpdateRequest(1L, "jordan", "newpass123!", null);

                    //when
                    memberService.update(req);

                    //then
                    Member member = memberRepository.findById(1L).get();

                    assertThat(member.getName()).isEqualTo(req.getName());
                    assertThat(member.getPassword()).isEqualTo(encode(req.getPassword()));
                }),

                dynamicTest("회원 탈퇴", () -> {
                    //given
                    long memberId = 1L;

                    //when
                    memberService.delete(memberId);

                    //then
                    Optional<Member> member = memberRepository.findByEmail(email);
                    assertThat(member).isEmpty();
                })
        );
    }
}
