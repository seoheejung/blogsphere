package com.example.blogsphere.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage { // 기본 응답 데이터 객체
	
	private Integer code;  // 응답 코드
	private String msg;    // 응답 메시지
	private Object result; // 응답 데이터
	
}
