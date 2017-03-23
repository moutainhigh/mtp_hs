/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.exception;
/**
 * @author Yao.shulin
 * @create Date: 2016年2月27日-上午11:11:53
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -4707937722803181911L;
    public final ErrorCode          errorCode;

    public ServiceException(String message) {
        super(message);
        this.errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
    }
    
    public ServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
