/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.service;
/**
 * @author Yao.shulin
 * @create Date: 2016年2月24日-下午4:59:47
 */
public interface IdGenerator {

    /**
     * Generate a new identifier
     * 
     * @return the generated identifier
     */
    long generateId();

    /**
     * Generate a new identifier for a given object type name
     * 
     * @param name
     *            the given object type name
     * @return the generated identify
     */
    long generateId(String name);
}
