package org.up.finance.service;

import org.up.finance.dto.request.DailyNoteRequest;
import org.up.finance.dto.response.DailyNoteResponse;
import org.up.finance.entity.DailyNote;

import java.util.List;

public interface DailyNoteService extends BaseService<DailyNote> {
    /**
     * create a daily note
     * @param userId  - id a user
     * @param request - from client
     * @return response of creating a note
     */
    DailyNoteResponse create(String userId, DailyNoteRequest request);

    /**
     * delete a daily note
     * @param dailyNoteId- id of a daily note - from client
     */
    void deleteById(String dailyNoteId);

    /**
     * validate a daily note exist or not
     * @param dailyNoteId- id of a daily note
     */
    void validateExistDailyNote(String dailyNoteId);

    /**
     * update a dailyNote
     * @param dailyNoteId - id dailyNote
     * @param request     - from client
     * @param userId      - id user
     * @return response dailynote
     */
    DailyNoteResponse update(String dailyNoteId, DailyNoteRequest request, String userId);


    /**
     * get all by userId
     * @param userId - id user
     * @return list dailyNote
     */
    List<DailyNoteResponse> getAllByUserId(String userId);

    /**
     * get dailyNote by Id
     * @param dailyNoteId - id of dailyNote
     * @param userId      - id user
     * @return dailyNote
     */
    DailyNoteResponse getByDailyNoteId(String dailyNoteId, String userId);

}
