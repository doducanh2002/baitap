package org.squad3.library.documentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.squad3.library.documentservice.dto.CreDTO;
import org.squad3.library.documentservice.dto.DocumentDTO;
import org.squad3.library.documentservice.dto.DocumentRes;
import org.squad3.library.documentservice.model.Document;
import org.squad3.library.documentservice.paging.PagingReq;
import org.squad3.library.documentservice.paging.PagingRes;
import org.squad3.library.documentservice.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody CreDTO documentDTO){
        final DocumentDTO createdDocument = documentService.createDocument(documentDTO);
        return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
    }

    @PutMapping("/{document_id}")
    public ResponseEntity<DocumentDTO> updateDocument(@PathVariable("document_id") int document_id,
                                                      @RequestBody DocumentDTO documentDTO) {
        final DocumentDTO updatedDocument = documentService.updateDocument(document_id, documentDTO);
        return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
    }

    @DeleteMapping("/{document_id}")
    public ResponseEntity<DocumentDTO> deleteDocument(@PathVariable("document_id") int documentId){
        final DocumentDTO documentDto = documentService.deleteDocument(documentId);
        return ResponseEntity.ok(documentDto);
    }


    @GetMapping
    public ResponseEntity<PagingRes<DocumentRes>> listDocuments(@Validated() final PagingReq pagingReq){
        final Page<DocumentRes> documentResDtoPage = documentService.listDocuments(pagingReq.makePageable());
        return ResponseEntity.ok(PagingRes.of(documentResDtoPage));
    }

    @GetMapping("/{document_id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable("document_id") int documentId){
        final DocumentDTO getDocument = documentService.getDocumentById(documentId);
        return new ResponseEntity<>(getDocument, HttpStatus.OK);
    }

}


