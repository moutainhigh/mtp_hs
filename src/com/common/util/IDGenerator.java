package com.common.util;

import com.core.service.IdGenerator;
import com.core.util.SpringBeanUtils;

/**
 * @deprecated Use <code>com.muchinfo.tas.core.service.IdGenerator</code> instead
 */
@Deprecated
public class IDGenerator {

    private static IDGenerator instance = new IDGenerator();
    
    IdGenerator                idGenerator = SpringBeanUtils.getBean(IdGenerator.class);

    private IDGenerator() {
    }

    /**
     * @deprecated Use <code>com.muchinfo.tas.core.service.IdGenerator.generateId()</code> instead
     */
    public Long getID() {
        return idGenerator.generateId();
    }

    public static IDGenerator getInstance() {
        return instance;
    }
}
