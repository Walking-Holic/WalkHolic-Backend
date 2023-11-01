package com.example.OpenSource.domain.image.repository;


import com.example.OpenSource.domain.image.domain.Image;
import com.example.OpenSource.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByMember(Member member);
}
