package com.example.coursemanagementsystem.exception;


import com.example.coursemanagementsystem.common.BaseResponse;
import com.example.coursemanagementsystem.common.ResultUtils;
import com.example.coursemanagementsystem.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(CourseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<?>> businessExceptionHandler(CourseException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 默认 400
        if (e.getCode() == ErrorCode.NOT_FOUND_ERROR.getCode()) {
            status = HttpStatus.NOT_FOUND;
        } else if (e.getCode() == ErrorCode.PERMISSION_ERROR.getCode()) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e.getCode() == ErrorCode.SYSTEM_ERROR.getCode()) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e.getCode() == ErrorCode.NOT_LOGIN_ERROR.getCode()) {
            status = HttpStatus.FORBIDDEN;
        } else if (e.getCode() == ErrorCode.OPERATION_ERROR.getCode()) {
            status = HttpStatus.NOT_IMPLEMENTED;
        }
        return ResponseEntity.status(status).body(ResultUtils.error(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {

        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}
