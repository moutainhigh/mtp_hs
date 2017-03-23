/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author Yao.shulin
 * @create Date: 2016年2月26日-下午3:42:16
 */
@Service
public final class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext AC;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     * ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AC = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return AC.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return AC.getBean(beanName, clazz);
    }
}
