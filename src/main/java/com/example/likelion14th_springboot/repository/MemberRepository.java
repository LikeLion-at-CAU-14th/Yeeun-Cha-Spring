package com.example.likelion14th_springboot.repository;

import com.example.likelion14th_springboot.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>  {
    Optional<Member> findByEmail(String email);
    Page<Member> findByAgeGreaterThanEqual(Integer age, Pageable pageable);
    List<Member> findByNameStartingWith(String prefix);
}