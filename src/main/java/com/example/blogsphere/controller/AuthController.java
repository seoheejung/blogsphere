package com.example.blogsphere.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogsphere.CommonFunction;
import com.example.blogsphere.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 인증 요청 및 Authentication code 발급
    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestParam("client_email") String clientEmail,
                                        @RequestParam("redirect_uri") String redirectUri,
                                        @RequestParam("response_type") String responseType,
                                        @RequestParam(value = "state", required = false) String state) {

        String authCode = CommonFunction.generateRandomString();
        String errorRedirectUrl = redirectUri + "?error=invalid_client&error_reason=Invalid client ID" + (state != null ? "&state=" + state : "");
        String redirectUrl = redirectUri + "?code=" + authCode + (state != null ? "&state=" + state : "");

        try {
            // 인증 코드를 데이터베이스에 저장
            boolean isStored = authService.createAndStoreAuthCode(clientEmail, authCode);
    
            if (!isStored) {
                return ResponseEntity.status(HttpStatus.FOUND)
                                .header(HttpHeaders.LOCATION, errorRedirectUrl)
                                .build();
            }
    
            // 리디렉션 URI에 code와 state를 추가하여 리디렉션
            return ResponseEntity.status(HttpStatus.FOUND)
                                .header(HttpHeaders.LOCATION, redirectUrl)
                                .build();
        } catch (Exception e) {
            // 오류 처리
            return ResponseEntity.status(HttpStatus.FOUND)
                                .header(HttpHeaders.LOCATION, errorRedirectUrl)
                                .build();
        }
    }
    // Access Token 발급
    @GetMapping("/access-token")
    public ResponseEntity<?> accessToken(@RequestParam("client_email") String clientEmail,
                                            @RequestParam("client_pass") String clientPass,
                                            @RequestParam("code") String code,
                                            @RequestParam("redirect_uri") String redirectUri,
                                            @RequestParam("grant_type") String grantType) {
        try {
            String accessToken = authService.generateAccessToken(clientEmail, clientPass, code);
            if (accessToken == null) {
                return ResponseEntity.badRequest().body("Invalid request");
            }
            return ResponseEntity.ok("Access token: " + accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Error: " + e.getMessage());
        }
    }
}
