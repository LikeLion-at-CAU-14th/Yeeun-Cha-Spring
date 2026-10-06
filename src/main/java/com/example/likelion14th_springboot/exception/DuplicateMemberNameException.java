package com.example.likelion14th_springboot.exception;

public class DuplicateMemberNameException
        extends RuntimeException {

    public DuplicateMemberNameException(String name) {
        super("이미 존재하는 이름입니다: " + name);
    }
}