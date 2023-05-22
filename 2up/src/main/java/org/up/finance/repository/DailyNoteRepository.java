package org.up.finance.repository;

import org.springframework.stereotype.Repository;
import org.up.finance.entity.DailyNote;
import org.up.finance.repository.base.BaseRepository;

import java.util.Optional;
import java.util.List;

@Repository
public interface DailyNoteRepository extends BaseRepository<DailyNote> {

    Optional<DailyNote> findByUserIdAndId(String userId, String dailyNoteId);

    List<DailyNote> findByUserId(String userId);

    Optional<DailyNote> findByIdAndUserId(String dailyNoteId, String userId);

}
