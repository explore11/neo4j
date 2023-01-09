package com.hr.neo4j.hadler;


import com.hr.neo4j.exception.MicroServiceException;
import com.hr.neo4j.util.Result;
import com.hr.neo4j.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author swq
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 处理业务的自定义异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MicroServiceException.class)
    public Result handleMicroServiceApiException(MicroServiceException ex) {
        Result<Object> failureResult = Result.failure(ResultCode.getItemByCode(ex.getCode()));
        failureResult.setMessage(ex.getMessage());
        return failureResult;
    }


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error(requestURI, e);
        return Result.failure(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error(requestURI, e);
        return Result.failure(e.getMessage());
    }
}
