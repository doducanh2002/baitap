package org.aibles.awss3.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class S3PreSignedUrlResDto {
    private String presignedUrl;
}
