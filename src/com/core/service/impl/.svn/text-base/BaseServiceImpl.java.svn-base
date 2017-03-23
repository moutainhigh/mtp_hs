package com.core.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.dao.BaseDao;
import com.core.model.RequestPage;
import com.core.model.ResponsePage;
import com.core.service.BaseService;

@Transactional
@Service
public class BaseServiceImpl<E> implements BaseService<E> {
	private static final Logger LOGGER=Logger.getLogger(BaseServiceImpl.class);
    protected BaseDao<E> dao;

    /**
     * 条件查询
     * 
     * @param criteria
     * @return
     */
    @Override
    public ResponsePage<E> queryEntitys(RequestPage page) {
        return dao.queryEntitys(page);
    }

    @Override
    public Serializable persist(E entity) {
        return this.dao.persist(entity);
    }
    /**
     * 合并实体
     * @param entity
     */
    @Override
    public void merge(E entity){
        this.dao.merge(entity);
    }
    @Override
    public void delete(E entity) {
        this.dao.delete(entity);

    }

    @Override
    public void deleteByProperties(Map<String, Object> properties) {
        this.dao.deleteByProperties(properties);
    }

    @Override
    public void update(E entity) {
        this.dao.update(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public E get(Serializable id) {
        return this.dao.get(id);
    }

    @Override
    @Transactional(readOnly = true)
    public E getByProerties(Map<String, Object> properties) {
        return this.dao.getByProerties(properties);
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findByProerties(Map<String, Object> properties, Integer pageNo, Integer pageSize) {
        int start = (pageNo - 1) * pageSize;
        return this.dao.queryByProerties(properties, start, start + pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findByProerties(Map<String, Object> properties) {
        return this.dao.queryByProerties(properties);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountByProerties(Map<String, Object> properties) {
        return this.dao.countByProerties(properties);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountForAll() {
        return this.dao.countAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll() {
        return this.dao.queryAll();
    }
    
}
