package com.example.OpenSource.domain.member.repository;

import com.example.OpenSource.domain.member.domain.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email); //중복 가입 방지
}
