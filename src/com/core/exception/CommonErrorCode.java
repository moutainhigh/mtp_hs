/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.exception;

/**
 * @author Yao.shulin
 * @create Date: 2016年2月27日-上午11:13:40
 */
public enum CommonErrorCode implements ErrorCode {
    BAD_REQUEST(400, 400), UNAUTHORIZED(401, 401), FORBIDDEN(403, 403), INTERNAL_SERVER_ERROR(500, 500)
    ,LOGIN_ERROR(10000, 500),USER_NOTEXIST(10001,500),ERROR_PASSWORD(10002,500),ERROR_TAPASSWORD(10003,500);
    
    private int code;
    private int httpStatus;

    private CommonErrorCode(int code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
