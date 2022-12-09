package com.codestates.flyaway.member.controller;

import com.codestates.flyaway.config.WebConfig;
import com.codestates.flyaway.domain.member.service.MemberService;
import com.codestates.flyaway.web.auth.interceptor.AuthInterceptor;
import com.codestates.flyaway.web.member.controller.MemberController;
import com.codestates.flyaway.web.member.dto.MemberDto.*;
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

import static java.time.LocalDateTime.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class, AuthInterceptor.class})
        })
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @DisplayName("web - 회원 가입 테스트")
    @Test
    void joinTest() throws Exception {
        //given
        String name = "jason";
        String email = "jason123@gmail.com";
        String password = "zxczxc12@";

        JoinRequest request = new JoinRequest(name, email, password);
        String content = gson.toJson(request);

        given(memberService.join(any()))
                .willReturn(new JoinResponse(1L, name, email, now()));

        //when
        ResultActions actions = mockMvc.perform(
                post("/members/join")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email))
                .andReturn();
    }

    @DisplayName("web - 회원 정보 수정 테스트")
    @Test
    void updateTest() throws Exception {
        //given
        Long memberId = 1L;
        String newName = "newJason";
        String password = "abcabc345@";

        UpdateRequest request = new UpdateRequest(memberId, newName, password, null);
        String content = gson.toJson(request);

        given(memberService.update(any()))
                .willReturn(new UpdateResponse(memberId, newName, null, now()));

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/{memberId}", memberId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.name").value(newName))
                .andReturn();
    }

    @DisplayName("web - 회원 프로필 조회 테스트")
    @Test
    void profileTest() throws Exception {
        //given
        Long memberId = 1L;
        String name = "newJason";
        String email = "jason123@gmail.com";

        given(memberService.findByIdFetch(anyLong()))
                .willReturn(new MemberProfileResponse(memberId, name, email, null, 0));

        //when
        ResultActions actions = mockMvc.perform(
                get("/members/{memberId}", memberId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email))
                .andReturn();
    }

    @DisplayName("web - 회원 이미지 조회 테스트")
    @Test
    void getImageTest() throws Exception {
        //given
        Long memberId = 1L;
        String imageUrl = "thisIsImageUrl";

        given(memberService.getImageUrl(anyLong()))
                .willReturn(imageUrl);

        //when | then
        mockMvc.perform(
                get("/members/1/image", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(imageUrl))
                .andReturn();
    }

    @DisplayName("web - 회원 탈퇴 테스트")
    @Test
    void deleteTest() throws Exception {
        //given
        Long memberId = 1L;
        String returnMessage = "회원 탈퇴 성공";

        //when
        ResultActions actions = mockMvc.perform(
                delete("/members/{memberId}", memberId));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(returnMessage));
    }
}
