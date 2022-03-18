package org.aibles.awss3.controller;

import lombok.RequiredArgsConstructor;
import org.aibles.awss3.dto.req.S3PreSignedUrlReqDto;
import org.aibles.awss3.dto.res.S3PreSignedUrlResDto;
import org.aibles.awss3.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload_presigned_url")
    public ResponseEntity<S3PreSignedUrlResDto> createUploadPreSignedUrl(@Validated() @RequestBody S3PreSignedUrlReqDto s3PreSignedUrlReqDto){
        final S3PreSignedUrlResDto preSignedUrl = s3Service.createUploadPreSignedUrl(s3PreSignedUrlReqDto);
        return ResponseEntity.ok(preSignedUrl);
    }

    @PostMapping(value = "/download_presigned_url", params = {"s3ObjectKey"})
    public ResponseEntity<S3PreSignedUrlResDto> downloadUploadPreSignedUrl(@RequestParam("s3ObjectKey") final String s3ObjectKey){
        final S3PreSignedUrlResDto preSignedUrl = s3Service.createDownloadPreSignedUrl(s3ObjectKey);
        return ResponseEntity.ok(preSignedUrl);
    }
}
