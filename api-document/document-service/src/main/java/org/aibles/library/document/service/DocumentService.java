package org.aibles.library.document.service;

import org.aibles.library.document.dto.BasicDocumentResDTO;
import org.aibles.library.document.dto.DetailDocumentResDTO;
import org.aibles.library.document.dto.DocumentReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentService {

    DetailDocumentResDTO createDocument(DocumentReqDTO documentDTO);
    DetailDocumentResDTO updateDocument(int documentId, DocumentReqDTO documentDTO);
    DetailDocumentResDTO deleteDocument(int documentId);
    DetailDocumentResDTO getDocumentById(int documentId);
    Page<BasicDocumentResDTO> listDocuments(final Pageable pageable);

}
