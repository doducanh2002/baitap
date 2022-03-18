package org.aibles.awss3.service.iml;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.aibles.awss3.dto.req.S3PreSignedUrlReqDto;
import org.aibles.awss3.dto.res.S3PreSignedUrlResDto;
import org.aibles.awss3.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3ServiceIml implements S3Service {

    private final AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String S3_BUCKET;

    @Override
    public S3PreSignedUrlResDto createUploadPreSignedUrl(final S3PreSignedUrlReqDto s3PreSignedUrlReqDto) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(S3_BUCKET, this.generateS3ObjectKey(s3PreSignedUrlReqDto.getFileName()))
                .withMethod(HttpMethod.PUT)
                .withExpiration(this.getPreSignedUrlExpiration());
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return new S3PreSignedUrlResDto(url.toString());
    }

    @Override
    public S3PreSignedUrlResDto createDownloadPreSignedUrl(final String s3ObjectKey) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(S3_BUCKET, s3ObjectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(this.getPreSignedUrlExpiration());
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return new S3PreSignedUrlResDto(url.toString());
    }

    private String generateS3ObjectKey(final String fileName){
        return String.format("%s_%s",
                Instant.now(Clock.systemDefaultZone()).toString(),
                fileName.replace(" ", "_"));
    }

    private Date getPreSignedUrlExpiration(){
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
