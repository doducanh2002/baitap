package org.aibles.library.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.library.document.converter.MappingHelper;
import org.aibles.library.document.dto.BasicDocumentResDTO;
import org.aibles.library.document.dto.DetailDocumentResDTO;
import org.aibles.library.document.dto.DocumentReqDTO;
import org.aibles.library.document.exception.DocumentNotFoundException;
import org.aibles.library.document.exception.common.NotFoundException;
import org.aibles.library.document.model.Category;
import org.aibles.library.document.model.Document;
import org.aibles.library.document.repository.CategoryRepository;
import org.aibles.library.document.repository.DocumentRepository;
import org.aibles.library.document.service.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final MappingHelper mappingHelper;
    private final CategoryRepository categoryRepository;


    @Override
    public DetailDocumentResDTO createDocument(DocumentReqDTO documentReqDTO) {
        log.info("(create)document create: {}", documentReqDTO);
        Document document = mappingHelper.map(documentReqDTO, Document.class);
        document.validate();
//        document.setCreatedDate(Instant.now(Clock.systemDefaultZone()));
        List<Category> categories = categoryRepository.findAllById(documentReqDTO.getCategoryIds());
        document.setCategories(new LinkedHashSet<>(categories));
        return mappingHelper.map(documentRepository.save(document), DetailDocumentResDTO.class);
    }

    @Override
    public DetailDocumentResDTO updateDocument(int documentId, DocumentReqDTO documentReqDTO) {
        return documentRepository.findById(documentId)
                .map(doc -> {
                    doc.setTitle(documentReqDTO.getTitle());
                    doc.setDescription(documentReqDTO.getDescription());
                    doc.setFileS3ObjectKey(documentReqDTO.getFileS3ObjectKey());
                    doc.setThumbS3ObjectKey(documentReqDTO.getThumbS3ObjectKey());
                    doc.setAuthor(documentReqDTO.getAuthor());
//                    doc.setType(documentReqDTO.getType());
                    List<Category> categories = categoryRepository.findAllById(documentReqDTO.getCategoryIds());
                    doc.setCategories(new LinkedHashSet<>(categories));
//                    doc.setLastUpdatedDate(Instant.now(Clock.systemDefaultZone()));
                    return mappingHelper.map(documentRepository.save(doc), DetailDocumentResDTO.class);
                })
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public Page<BasicDocumentResDTO> listDocuments(final Pageable pageable) {
        return documentRepository.findAll(pageable)
                .map(document -> {
                    return mappingHelper.map(document, BasicDocumentResDTO.class);
                });
    }

    @Override
    public DetailDocumentResDTO deleteDocument(int documentId) {
        return documentRepository.findById(documentId)
                .map(document -> {
                    documentRepository.delete(document);
                    return mappingHelper.map(document, DetailDocumentResDTO.class);
                })
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public DetailDocumentResDTO getDocumentById(int documentId) {
        return documentRepository.findById(documentId)
                .map(document -> {
                    return mappingHelper.map(document, DetailDocumentResDTO.class);
                })
                .orElseThrow(NotFoundException::new);
    }

}