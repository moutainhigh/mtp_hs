/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.exception;
import java.io.Serializable;

/**
 * @author Yao.shulin
 * @create Date: 2016年2月27日-上午11:19:44
 */
public interface ErrorCode extends Serializable{
    public int getCode();

    public int getHttpStatus();
}