package org.up.finance.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.up.finance.dto.request.DailyNoteRequest;
import org.up.finance.dto.response.DailyNoteResponse;
import org.up.finance.entity.DailyNote;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.DailyNoteRepository;
import org.up.finance.service.DailyNoteService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DailyNoteServiceImpl extends BaseServiceImpl<DailyNote> implements DailyNoteService {

    private final DailyNoteRepository repository;

    public DailyNoteServiceImpl(DailyNoteRepository repository) {
        super(repository);
        this.repository = repository;

    }

    @Override
    @Transactional
    public DailyNoteResponse create(String userId, DailyNoteRequest request) {
        DailyNote dailyNote = DailyNote.from(userId, request);
        dailyNote = create(dailyNote);
        return DailyNoteResponse.from(dailyNote);
    }

    @Override
    public DailyNoteResponse getByDailyNoteId(String dailyNoteId, String userId) {
        var dailyNote = repository.findByIdAndUserId(dailyNoteId, userId)
                .orElseThrow(() -> {
                    log.error("(getByDailyNoteId)dailyNote : {} ->Not Found", dailyNoteId);
                    throw new NotFoundException(dailyNoteId, DailyNote.class.getSimpleName());
                });
        return DailyNoteResponse.from(dailyNote);
    }

    @Override
    public void deleteById(String dailyNoteId) {
        validateExistDailyNote(dailyNoteId);
        delete(dailyNoteId);
    }

    @Override
    public void validateExistDailyNote(String dailyNoteId) {
        if (!repository.existsById(dailyNoteId)) {
            log.error("(validateExistDailyNote)dailyNoteId: {}", dailyNoteId);
            throw new NotFoundException(dailyNoteId, DailyNote.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public DailyNoteResponse update(String dailyNoteId, DailyNoteRequest request, String userId) {
        DailyNote dailyNoteUpdate = DailyNote.from(userId, request);
        DailyNote result = repository.findByUserIdAndId(userId, dailyNoteId)
                .map(dailyNote -> {
                    dailyNote.setDate(dailyNoteUpdate.getDate());
                    dailyNote.setTitle(dailyNoteUpdate.getTitle());
                    dailyNote.setContent(dailyNoteUpdate.getContent());
                    return dailyNote;
                })
                .map(repository::save)
                .orElseThrow(() -> {
                    log.error("(update)dailyNoteId : {} --> NOT FOUND EXCEPTION", dailyNoteId);
                    throw new NotFoundException(dailyNoteId, DailyNote.class.getSimpleName());
                });
        return DailyNoteResponse.from(result);

    }

    @Override
    @Transactional
    public List<DailyNoteResponse> getAllByUserId(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(DailyNoteResponse::from)
                .collect(Collectors.toList());
    }
}
