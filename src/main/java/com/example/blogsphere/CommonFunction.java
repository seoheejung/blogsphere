package com.example.blogsphere;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommonFunction {
     
     // 실행중인 클래스명 추출
     public static String getClassName() {
          return Thread.currentThread().getStackTrace()[2].getClassName();
     }

     // 32바이트 랜덤 문자열 생성
     public static String generateRandomString() {
          SecureRandom random = new SecureRandom();
          byte[] values = new byte[32]; // 32 바이트 크기로 설정
          random.nextBytes(values);
          return Base64.getEncoder().encodeToString(values);
     }
}