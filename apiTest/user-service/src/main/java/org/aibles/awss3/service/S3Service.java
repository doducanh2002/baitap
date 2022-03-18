package org.aibles.awss3.service;

import org.aibles.awss3.dto.req.S3PreSignedUrlReqDto;
import org.aibles.awss3.dto.res.S3PreSignedUrlResDto;

public interface S3Service {
    S3PreSignedUrlResDto createUploadPreSignedUrl(final S3PreSignedUrlReqDto s3PreSignedUrlReqDto);
    S3PreSignedUrlResDto createDownloadPreSignedUrl(final String s3ObjectKey);
}
