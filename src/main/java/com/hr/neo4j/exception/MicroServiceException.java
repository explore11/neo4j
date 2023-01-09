package com.hr.neo4j.exception;

/**
 * 自定义业务异常
 */
public class MicroServiceException extends RuntimeException {
    private Integer code;

    public MicroServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
