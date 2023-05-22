package org.up.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.up.finance.dto.request.DailyNoteRequest;
import org.up.finance.dto.response.DailyNoteResponse;
import org.up.finance.entity.DailyNote;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.DailyNoteService;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DailyNoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DailyNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DailyNoteController dailyNoteController;

    @MockBean
    private DailyNoteService dailyNoteService;

    @MockBean
    private SecurityUtil securityUtil;

    private DailyNoteRequest dailyNoteRequest() {
        DailyNoteRequest request = new DailyNoteRequest();
        request.setDate(11012002);
        request.setContent("daily Note");
        request.setTitle("duc anh");
        return request;
    }

    private DailyNote dailyNoteEntity() {
        return DailyNote.from("1", dailyNoteRequest());
    }

    @Test
    public void test_Delete_WhenDaiLyNoteNotFound_Return404() throws Exception {
        Mockito.doThrow(new NotFoundException("dailyNoteId", DailyNote.class.getSimpleName()))
                .when(dailyNoteService).deleteById("dailyNoteId");
        mockMvc.perform(
                        delete("/api/v1/daily-notes/{dailyNoteId}", "dailyNoteId")
                                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_WhenInputValid_Return200() throws Exception {
        Mockito.doNothing().when(dailyNoteService).deleteById("dailyNoteId");
        mockMvc.perform(
                        delete("/api/v1/daily-notes/{dailyNoteId}", "dailyNoteId")
                                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Create_WhenInputValid_Return201AndResponseBody() throws Exception {
        DailyNoteRequest request = dailyNoteRequest();
        DailyNote dailyNote = dailyNoteEntity();
        Mockito.when(dailyNoteService.create(securityUtil.getUserId(), request)).
                thenReturn(DailyNoteResponse.from(dailyNote));
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/v1/daily-notes")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(dailyNoteController.create(request)));
    }

    @Test
    public void test_Create_WhenBadRequest_Return400() throws Exception {
        DailyNoteRequest request = dailyNoteRequest();
        request.setTitle(null);
        request.setDate(null);
        mockMvc.perform(
                        post("/api/v1/daily-notes")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_Update_WhenInputValid_Return200AndResponseBody() throws Exception {
        DailyNoteRequest request = dailyNoteRequest();
        DailyNote dailyNote = dailyNoteEntity();
        Mockito.when(dailyNoteService.update("dailyNoteId", request, securityUtil.getUserId())).
                thenReturn(DailyNoteResponse.from(dailyNote));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/v1/daily-notes/{dailyNoteId}", "dailyNoteId")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(dailyNoteController.update("dailyNoteId", request)));
    }

    @Test
    public void test_Update_WhenDailyNoteNotFound_Return404() throws Exception {
        DailyNoteRequest request = dailyNoteRequest();
        Mockito.when(dailyNoteService.update("dailyNoteId", request, securityUtil.getUserId()))
                .thenThrow(new NotFoundException("categoryId", DailyNote.class.getSimpleName()));
        mockMvc.perform(
                put("/api/v1/daily-notes/{dailyNoteId}", "dailyNoteId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void test_List_Return200AndResponseBody() throws Exception {
        DailyNote dailyNote = dailyNoteEntity();
        Mockito.when(dailyNoteService.getAllByUserId(securityUtil.getUserId()))
                .thenReturn(Stream.of(dailyNote).map(DailyNoteResponse::from).collect(Collectors.toList()));
        mockMvc.perform(
                        get("/api/v1/daily-notes")
                                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_WhenInputValid_Return200AndResponseBody() throws Exception {
        DailyNote dailyNote = dailyNoteEntity();
        Mockito.when(dailyNoteService.getByDailyNoteId("dailyNoteId", securityUtil.getUserId()))
                .thenReturn(DailyNoteResponse.from(dailyNote));
        mockMvc.perform(
                        get("/api/v1/daily-notes/{dailyNoteId}", "dailyNoteId"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_WhenDailyNoteNotFound_Return404() throws Exception {
        Mockito.when(dailyNoteService.getByDailyNoteId("dailyNoteId", securityUtil.getUserId()))
                .thenThrow(new NotFoundException("dailyNoteId", DailyNote.class.getSimpleName()));
        mockMvc.perform(
                get("/api/v1/daily-notes/{dailyNoteId}", "dailyNoteId")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}