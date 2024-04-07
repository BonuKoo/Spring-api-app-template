package com.app.global.error.exception;

import com.app.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode); //부모 생성자를 호출하면서 에러 코드를 넘겨준다.

    }
}
