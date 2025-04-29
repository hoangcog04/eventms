package com.example.eventms.hub.controller;

import com.example.eventms.hub.dto.RefundRequestPayload;
import com.example.eventms.hub.service.IOesRefundRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.eventms.common.enums.ResultCode.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AutoConfigureMockMvc
@SpringBootTest
public class OesRefundRequestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IOesRefundRequestService refundRequestService;

    Long orderId;

    RefundRequestPayload payload;

    @BeforeEach
    void initData() {
        orderId = 123456L;
        payload = new RefundRequestPayload();
    }

    @Test
    void requestRefund_shouldReturnSuccessWhenRefundIsRequested() throws Exception {
        // arr
        String content = objectMapper.writeValueAsString(payload);

        when(refundRequestService.requestRefund(anyLong(), any()))
                .thenReturn(true);

        // act and expect
        mockMvc.perform(post("/orders/{orderId}/request_refund", orderId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("message").value(SUCCESS.getMessage()))
                .andExpect(jsonPath("data").value(true));
    }
}
