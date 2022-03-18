package org.squad3.library.documentservice.service.impl;

import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.squad3.library.documentservice.converter.MappingHelper;
import org.squad3.library.documentservice.dto.CreDTO;
import org.squad3.library.documentservice.dto.DocumentDTO;
import org.squad3.library.documentservice.dto.DocumentRes;

import org.squad3.library.documentservice.exception.common.DocumentNotFoundException;
import org.squad3.library.documentservice.mapper.DocumentMapper;
import org.squad3.library.documentservice.model.Category;
//import org.squad3.library.documentservice.model.CategoryDocument;
//import org.squad3.library.documentservice.model.CategoryDocumentKey;
//import org.squad3.library.documentservice.model.CategoryDocument;
import org.squad3.library.documentservice.model.CategoryDocument;
import org.squad3.library.documentservice.model.CategoryDocumentKey;
import org.squad3.library.documentservice.model.Document;
import org.squad3.library.documentservice.repository.CategoryRepository;
import org.squad3.library.documentservice.repository.DocumentRepository;
import org.squad3.library.documentservice.service.DocumentService;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final MappingHelper mappingHelper;
    private final CategoryRepository categoryRepository;

//    @Override
//    public DocumentDTO createDocument(CreDTO documentDTO) {
//        Document document = DocumentMapper.getInstance().toEntity(documentDTO);
//        List<Category> categories = categoryRepository.findAllById(documentDTO.getCategoryIds());
//        document.setCategoryDocuments(new LinkedHashSet<>(categories));
//        document.setCreatedDate(Instant.now(Clock.systemDefaultZone()));
//        return DocumentMapper.getInstance().toDTO(documentRepository.save(document));
//    }

    @Override
    public DocumentDTO createDocument(CreDTO documentDTO) {
        Document document = mappingHelper.map(documentDTO, Document.class);
        document.setCreatedDate(Instant.now(Clock.systemDefaultZone()));
        document.setDocumentCategories(this.buildSetDocumentCategories(documentDTO.getCategoryIds()));
        return mappingHelper.map(documentRepository.save(document), DocumentDTO.class);
    }

    @Override
    public DocumentDTO updateDocument(int documentId, DocumentDTO documentDTO) {
        Document document = documentRepository.findById(documentId)
                .map(doc -> {
                    doc.setTitle(documentDTO.getTitle());
                    doc.setDescription(documentDTO.getDescription());
                    doc.setFileS3ObjectKey(documentDTO.getFileS3ObjectKey());
                    doc.setThumbS3ObjectKey(documentDTO.getThumbS3ObjectKey());
                    doc.setAuthor(documentDTO.getAuthor());
                    doc.setType(documentDTO.getType());
                    doc.setLastUpdatedDate(Instant.now(Clock.systemDefaultZone()));
                    return doc;
                })
                .orElseThrow(DocumentNotFoundException::new);
        return mappingHelper.map(documentRepository.save(document), DocumentDTO.class);
    }

    @Override
    public Page<DocumentRes> listDocuments(final Pageable pageable) {
        final Page<Document> documentPage = documentRepository.findAll(pageable);
        return mappingHelper.mapPage(documentPage, DocumentRes.class);
    }

    @Override
    public DocumentDTO deleteDocument(int documentId) {
        return documentRepository.findById(documentId)
                .map(document -> {
                    documentRepository.delete(document);
                    return mappingHelper.map(document, DocumentDTO.class);
                })
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public DocumentDTO getDocumentById(int documentId){
        Document document = documentRepository.findById(documentId)
                .orElseThrow(DocumentNotFoundException::new);
        return mappingHelper.map(document, DocumentDTO.class);
    }

    private List<CategoryDocument> buildSetDocumentCategories(List<Integer> categoryIds){
        final List<Category> categories = categoryRepository.findByIdIn(categoryIds);
        final List<CategoryDocument> documentCategories = categories.stream()
                .map(category -> {
                    CategoryDocumentKey documentCategoryKey = new CategoryDocumentKey(category.getId());
                    CategoryDocument documentCategory = new CategoryDocument(documentCategoryKey, category);
                    return documentCategory;
                })
                .collect(Collectors.toList());
        return documentCategories;
    }
}