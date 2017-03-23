/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.exception;
/**
 * @author Yao.shulin
 * @create Date: 2016年3月3日-上午9:37:24
 */
public class ErrorResult {
    public final int    code;
    public final String message;

    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
