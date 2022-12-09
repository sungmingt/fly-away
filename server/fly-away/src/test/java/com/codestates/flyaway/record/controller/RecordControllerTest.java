package com.codestates.flyaway.record.controller;

import com.codestates.flyaway.config.WebConfig;
import com.codestates.flyaway.domain.record.service.RecordService;
import com.codestates.flyaway.web.auth.interceptor.AuthInterceptor;
import com.codestates.flyaway.web.record.controller.RecordController;
import com.codestates.flyaway.web.record.dto.RecordDto.InsertRequest;
import com.codestates.flyaway.web.record.dto.RecordDto.InsertResponse;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RecordController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class, AuthInterceptor.class})
        })
class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private RecordService recordService;

    @DisplayName("web - 운동 시간 기록 테스트")
    @Test
    void saveRecordTest() throws Exception {
        //given
        Long memberId = 1L;
        LocalDate date = LocalDate.now();
        Long record = 1500L;

        InsertRequest request = new InsertRequest(record);
        String content = gson.toJson(request);

        InsertResponse response = new InsertResponse(memberId, date, record);
        given(recordService.save(anyLong(), any(InsertRequest.class)))
                .willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                post("/record/{memberId}", memberId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.date").value(date.toString()))
                .andExpect(jsonPath("$.record").value(record))
                .andReturn();
    }
}
