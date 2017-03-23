package com.core.model;
import java.io.Serializable;

/**
 * 统一查询条件
 * 
 * @author liu.bing
 *
 */
public class CriteriaModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 属性名
     */
    private String            propName;
    /**
     * 属性值
     */
    private Object            propValue;
    /**
     * 查询条件 0->equals parameter 1->like %parameter% 2->like parameter% 3->like %parameter 4->大于 5->大于等于 6->小于 7->小于等于
     * 8->in List 9->不等于
     */
    private Integer           flag;

    public CriteriaModel() {
        super();
    }

    public CriteriaModel(String propName, Object propValue, Integer flag) {
        this.propName = propName;
        this.propValue = propValue;
        this.flag = flag;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public Object getPropValue() {
        return propValue;
    }

    public void setPropValue(Object propValue) {
        this.propValue = propValue;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
