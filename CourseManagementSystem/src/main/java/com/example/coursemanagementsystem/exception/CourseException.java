package com.example.coursemanagementsystem.exception;

import com.example.coursemanagementsystem.enums.ErrorCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CourseException extends RuntimeException{
    private final int code;
    public CourseException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
    public CourseException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
    public int getCode() {
        return code;
    }

}
