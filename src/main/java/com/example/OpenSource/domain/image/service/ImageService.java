package com.example.OpenSource.domain.image.service;

import static com.example.OpenSource.global.error.ErrorCode.IMAGE_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;

import com.example.OpenSource.domain.image.domain.Image;
import com.example.OpenSource.domain.image.repository.ImageRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadFolder;

    public void uploadImage(MultipartFile file, Long memberId) {
        if (file.isEmpty()) {
            throw new CustomException(IMAGE_NOT_FOUND);
        }
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        //log.info("uploadImage: " + file.getOriginalFilename());

        File destinationFile = new File(uploadFolder + imageFileName);

        try {
            file.transferTo(destinationFile);

            Image image = imageRepository.findByMember(member);
            if (image != null) {
                image.updateImageName(imageFileName);
            } else {
                image = Image.builder()
                        .member(member)
                        .imageName(imageFileName)
                        .build();
            }
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
