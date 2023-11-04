package com.example.OpenSource.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    // RuntimeException 을 상속 받아서 Unchecked Exception으로 활용
    private final ErrorCode errorCode;
}
