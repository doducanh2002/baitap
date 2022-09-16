package org.aibles.library.document.controller;

import org.aibles.library.document.dto.BasicDocumentResDTO;
import org.aibles.library.document.dto.DetailDocumentResDTO;
import org.aibles.library.document.dto.DocumentReqDTO;
import org.aibles.library.document.paging.PagingReq;
import org.aibles.library.document.paging.PagingRes;
import org.aibles.library.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService){
        this.documentService = documentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DetailDocumentResDTO> createDocument(@RequestBody @Valid DocumentReqDTO documentReqDTO){
       return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(documentReqDTO));
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserResponse created(@RequestBody @Valid CreateUserRequest createUserRequest) {
//        return service.created(createUserRequest);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailDocumentResDTO> updateDocument(@PathVariable("id") int documentId,
                                                               @RequestBody DocumentReqDTO documentReqDTO){
        return ResponseEntity.status(HttpStatus.OK).body(documentService.updateDocument(documentId, documentReqDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DetailDocumentResDTO> deleteDocument(@PathVariable("id") int documentId){
        return ResponseEntity.status(HttpStatus.OK).body(documentService.deleteDocument(documentId));
    }


    @GetMapping
    public ResponseEntity<PagingRes<BasicDocumentResDTO>> listDocuments(@Validated() final PagingReq pagingReq){
        final Page<BasicDocumentResDTO> documentResDtoPage = documentService.listDocuments(pagingReq.makePageable());
        return new ResponseEntity<>(PagingRes.of(documentResDtoPage) , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailDocumentResDTO> getDocumentById(@PathVariable("id") int documentId){
        return ResponseEntity.status(HttpStatus.OK).body(documentService.getDocumentById(documentId));
    }

}


