package org.up.finance.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.up.finance.entity.DailyNote;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DailyNoteResponse {
    private String id;
    private Integer date;
    private String title;
    private String content;

    public static DailyNoteResponse from(DailyNote dailynote) {
        DailyNoteResponse response = new DailyNoteResponse();
        response.setId(dailynote.getId());
        response.setDate(dailynote.getDate());
        response.setTitle(dailynote.getTitle());
        response.setContent(dailynote.getContent());
        return response;
    }
}
