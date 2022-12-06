package com.codestates.flyaway.domain.memberimage.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.memberimage.entity.MemberImage;
import com.codestates.flyaway.domain.memberimage.repository.MemberImageRepository;
import com.codestates.flyaway.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.codestates.flyaway.global.exception.ExceptionCode.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberImageService {

    private final AmazonS3 amazonS3;
    private final MemberImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}/member")
    private String bucket;

    @Value("${cloud.aws.s3.default}")
    private String defaultUrl;

    /**
     * 이미지 저장
     */
    public void save(MultipartFile multipartFile, Member member) {
        if (multipartFile == null) {
            return;
        }

        MemberImage image = upload(multipartFile);
        delete(image.getFileName());
        image.setMember(member);
    }

    /**
     * S3 이미지 업로드
     * @param multipartFile
     * @return 생성된 memberImage
     */
    public MemberImage upload(MultipartFile multipartFile) {
        String fileOriName = multipartFile.getOriginalFilename();
        String s3FileName = UUID.randomUUID().toString() + "-" + fileOriName;

        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
            log.info("### 파일 업로드 성공 = {}", s3FileName);

        } catch (IOException e) {
            throw new BusinessLogicException(FILE_CANNOT_SAVE);
        }

        String s3Url = amazonS3.getUrl(bucket, s3FileName).toString();
        return new MemberImage(fileOriName, s3Url, s3FileName);
    }

    /**
     * 이미지 반환
     * @return 이미지 url
     */
    @Transactional(readOnly = true)
    public String getImageUrl(Member member) {
        return Optional.ofNullable(member.getMemberImage())
                .map(MemberImage::getFileUrl)
                .orElseGet(() -> defaultUrl);
    }

    /**
     * 이미지 삭제
     */
    public void delete(String fileName) {
        try {
            amazonS3.deleteObject(this.bucket, fileName);
        } catch (AmazonServiceException e) {
            log.info("### 파일 삭제 실패 - {}", e.getErrorMessage());
            throw new BusinessLogicException(FILE_DELETE_FAILED);
        }
    }
}
