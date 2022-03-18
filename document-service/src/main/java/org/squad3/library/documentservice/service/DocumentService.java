package org.squad3.library.documentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.squad3.library.documentservice.dto.CreDTO;
import org.squad3.library.documentservice.dto.DocumentDTO;
import org.squad3.library.documentservice.dto.DocumentRes;
import org.squad3.library.documentservice.model.Document;

import java.util.List;

public interface DocumentService {

    DocumentDTO createDocument(CreDTO documentDTO);

    DocumentDTO updateDocument(int documentId, DocumentDTO documentDTO);

    DocumentDTO deleteDocument(int documentId);

    DocumentDTO getDocumentById(int documentId);

    Page<DocumentRes> listDocuments(final Pageable pageable);


}
