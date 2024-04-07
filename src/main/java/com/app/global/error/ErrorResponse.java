package com.app.global.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    public static ErrorResponse of(String errorCode, String errorMessage){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }


    public static ErrorResponse of(String errorCode, BindingResult bindingResult){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(createErrorMessage(bindingResult))
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult){
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(!isFirst){
                sb.append(","); //첫번째가 아니면 에러 메시지를 만들 때, 컴마 단위로 연결
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField()); //필드 에러가 있는 필드 명을 가지고 온다.
            sb.append("]");
            sb.append(fieldError.getDefaultMessage()); // 필드 에러에 있는, 에러 메세지를 갖고 오는 DefaultMessage 호출
        }

        return sb.toString();
    }


}
