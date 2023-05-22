package org.up.finance.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.dto.request.DailyNoteRequest;
import org.up.finance.dto.response.DailyNoteResponse;
import org.up.finance.entity.DailyNote;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.DailyNoteRepository;

import java.util.List;
import java.util.Optional;

@WebMvcTest(DailyNoteService.class)
@ContextConfiguration(classes = {FinanceServiceTestConfiguration.class, RedisConfiguration.class})
public class DailyNoteServiceTest {

    @Autowired
    private DailyNoteService dailyNoteService;
    @MockBean
    private DailyNoteRepository dailyNoteRepository;

    private DailyNoteRequest mockDailyNoteRequest() {
        DailyNoteRequest request = new DailyNoteRequest();
        request.setDate(11012002);
        request.setTitle("ducanh");
        request.setContent("ducanh2002");
        return request;
    }

    private DailyNote mockDailyNoteEntity() {
        return DailyNote.from("userId", mockDailyNoteRequest());
    }

    @Test
    public void test_Create_WhenInputValid_Successful() {
        DailyNoteRequest mockRequest = mockDailyNoteRequest();
        DailyNote mockEntity = mockDailyNoteEntity();
        Mockito.when(dailyNoteRepository.save(mockEntity)).thenReturn(mockEntity);
        DailyNoteResponse response = dailyNoteService.create("userId", mockRequest);
        Assertions.assertEquals(mockRequest.getDate(), response.getDate());
        Assertions.assertEquals(mockRequest.getTitle(), response.getTitle());
        Assertions.assertEquals(mockRequest.getContent(), response.getContent());

    }

    @Test
    public void test_DeleteById_WhenDailyNoteNotFound_ThrowNotFoundException() {
        Mockito.when(dailyNoteRepository.existsById("dailyNoteId")).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                () -> dailyNoteService.deleteById("dailyNoteId"));
    }

    @Test
    public void test_Update_WhenInputValid_Successful() {
        DailyNoteRequest request = mockDailyNoteRequest();
        DailyNote entity = mockDailyNoteEntity();
        Mockito.when(dailyNoteRepository.findByUserIdAndId("userId", "dailyNoteId")).thenReturn(Optional.of(entity));
        Mockito.when(dailyNoteRepository.save(entity)).thenReturn(entity);
        DailyNoteResponse response = dailyNoteService.update("dailyNoteId", request, "userId");
        Assertions.assertEquals(request.getContent(), response.getContent());
        Assertions.assertEquals(request.getDate(), response.getDate());
        Assertions.assertEquals(request.getTitle(), response.getTitle());
    }

    @Test
    public void test_Update_WhenNotFoundDailyNote_ThrowNotFoundException() {
        DailyNoteRequest request = mockDailyNoteRequest();
        Mockito.when(dailyNoteRepository.findById("dailyNoteId"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () ->
                dailyNoteService.update("dailyNoteId", request, "userId"));
    }

    @Test
    public void test_List_Successful() {
        DailyNote dailyNote = mockDailyNoteEntity();
        Mockito.when(dailyNoteRepository.findByUserId("userId")).thenReturn(List.of(dailyNote));
        Assertions.assertEquals(1, dailyNoteService.getAllByUserId("userId").size());
        Assertions.assertEquals(DailyNoteResponse.from(dailyNote), dailyNoteService.getAllByUserId("userId").get(0));
    }

    @Test
    public void test_Get_WhenInputValid_Successful() {
        DailyNote dailyNote = mockDailyNoteEntity();
        Mockito.when(dailyNoteRepository.findByIdAndUserId("dailyNoteId", "userId"))
                .thenReturn(Optional.of(dailyNote));
        DailyNoteResponse response = dailyNoteService.getByDailyNoteId("dailyNoteId", "userId");
        Assertions.assertEquals(dailyNote.getDate(), response.getDate());
        Assertions.assertEquals(dailyNote.getContent(), response.getContent());
        Assertions.assertEquals(dailyNote.getTitle(), response.getTitle());
    }

    @Test
    public void test_Get_WhenDailyNoteNotFound_ThrowNotFoundException() {
        Mockito.when(dailyNoteRepository.findByIdAndUserId("dailyNoteId", "userId"))
                .thenThrow(new NotFoundException("dailyNoteId", DailyNote.class.getSimpleName()));
        Assertions.assertThrows(NotFoundException.class,
                () -> dailyNoteService.getByDailyNoteId("dailyNoteId", "userId"));
    }

}
