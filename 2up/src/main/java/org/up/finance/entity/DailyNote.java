package org.up.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.up.finance.dto.request.DailyNoteRequest;
import org.up.finance.entity.base.BaseEntityWithUpdater;

@Entity
@Table(name = "daily_note")
@Data
public class DailyNote extends BaseEntityWithUpdater {
    @Column(nullable = false)
    private Integer date;
    @Column(nullable = false)
    private String title;
    private String content;
    private String userId;

    public static DailyNote from(String userId, DailyNoteRequest request) {
        DailyNote dailyNote = new DailyNote();
        dailyNote.setDate(request.getDate());
        dailyNote.setTitle(request.getTitle());
        dailyNote.setContent(request.getContent());
        dailyNote.setUserId(userId);
        return dailyNote;
    }

}
