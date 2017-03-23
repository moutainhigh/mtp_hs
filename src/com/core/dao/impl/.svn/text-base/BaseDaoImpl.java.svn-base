package com.core.dao.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.core.dao.BaseDao;
import com.core.model.CriteriaModel;
import com.core.model.RequestPage;
import com.core.model.ResponsePage;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@SuppressWarnings("unchecked")
public class BaseDaoImpl<E> extends SqlSessionDaoSupport implements BaseDao<E> {
	private static Map<String, Method> MAP_METHOD = new HashMap<String, Method>();
	protected final Logger log = Logger.getLogger(BaseDaoImpl.class);

	@Resource(name = "hibernateSessionFactory")
	private SessionFactory sessionFactory;

	protected Class<E> entityClass;

	public Session  getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Resource(name = "sqlSessionTemplate")
	public void setSuperSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	public BaseDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Serializable persist(E entity) {
		return getSession().save(entity);
	}

	public void setCreateras(List<CriteriaModel> criteriaList, Criteria criteria) throws Exception {
		if (null == criteria || null == criteria || null == criteriaList || criteriaList.isEmpty()) {
			return;
		} // 0-equals
			// 1-like
			// %parameter%
		for (CriteriaModel model : criteriaList) {
			String propName = model.getPropName();
			Object value = model.getPropValue();
			Integer operator = model.getFlag();
			String oper = null;
			switch (operator) {
			case 0:
				oper = "eq";
				break;
			case 1:
			case 2:
			case 3:
				oper = "like";
				break;
			case 4:
				oper = "gt";
				break;
			case 5:
				oper = "ge";
				break;
			case 6:
				oper = "lt";
				break;
			case 7:
				oper = "le";
				break;
			case 8:
				oper = "in";
				break;
			case 9:
				oper = "ne";
				break;
			default:
				oper = "eq";
			}
			Method method = getMethod(oper);
			try {
				if ("like".equals(oper)) {
					MatchMode mode = MatchMode.ANYWHERE;
					if (operator == 2) {
						mode = MatchMode.START;
					} else if (operator == 3) {
						mode = MatchMode.END;
					}
					criteria.add((Criterion) method.invoke(Restrictions.class, new Object[] { propName, value, mode }));
				} else if ("in".equals(oper)) {
					if (value instanceof ArrayList) {
						List<Object> list = new ArrayList<Object>();
						for (Object obj : (ArrayList<Object>) value) {
							list.add(transLate(propName, obj));
						}
						criteria.add((Criterion) method.invoke(Restrictions.class, new Object[] { propName, list }));
					}
				} else {
					value = transLate(propName, value);
					criteria.add((Criterion) method.invoke(Restrictions.class, new Object[] { propName, value }));
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public Object transLate(String propName, Object value) throws Exception {
		Object obj = null;
		try {
			Field field = this.entityClass.getDeclaredField(propName);
			if (field.getType().equals(String.class)) {
				obj = value;
			} else if (field.getType().equals(Long.class)) {
				obj = Long.valueOf(value.toString());
			} else if (field.getType().equals(Integer.class)) {
				obj = Integer.valueOf(value.toString());
			} else if (field.getType().equals(Short.class)) {
				obj = Short.valueOf(value.toString());
			} else if (field.getType().equals(Float.class)) {
				obj = Float.valueOf(value.toString());
			} else if (field.getType().equals(Double.class)) {
				obj = Double.valueOf(value.toString());
			} else if (field.getType().equals(Date.class)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				obj = format.parse(value.toString());
			}
		} catch (Exception e) {
			throw e;
		}
		return obj;
	}

	@SuppressWarnings("rawtypes")
	private Method getMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			Class[] paramType = { String.class, Object.class };
			Class[] likeParamType = { String.class, String.class, MatchMode.class };
			// Class[] betweenType = { String.class, Object.class, Object.class
			// };
			Class[] inType = { String.class, Collection.class };
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("in".equals(name)) {
					method = clazz.getMethod(name, inType);
				} else {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MAP_METHOD.get(name);
	}

	/**
	 * 使用mybatis执行通用查询
	 * 
	 * @return
	 */
	public ResponsePage<E> queryByMybatis(String statement, RequestPage reqPage) {

		RequestPage.checkReqHead(reqPage);
		List<E> list = new ArrayList<E>();
		ResponsePage<E> rspPage = new ResponsePage<>();
		rspPage.setPageNo(reqPage.getPageNo());
		rspPage.setPageSize(reqPage.getPageSize());

		try {
			RowBounds rowBounds = null;
			if (reqPage.getIsNeedCount() == 1)
				rowBounds = new PageBounds(reqPage.getPageNo(), reqPage.getPageSize());
			else
				rowBounds = new RowBounds(reqPage.getPageNo(), reqPage.getPageSize());

			Map<String, Object> param = null;
			if (reqPage.getCriteriaList() != null) {
				param = new HashMap<String, Object>();
				for (CriteriaModel c : reqPage.getCriteriaList()) {
					param.put(c.getPropName(), c.getPropValue());
				}
			}
			list = getSqlSession().selectList(statement, param, rowBounds);
			// 设置总条数
			long count = 0;
			if (reqPage.getIsNeedCount() == 1) {
				PageList<E> pageList = (PageList<E>) list;
				count = pageList.getPaginator().getTotalCount();
			}
			rspPage.setCount(count);
			rspPage.setResultList(list);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return rspPage;

	}

	/**
	 * 条件查询
	 * 
	 * @param criteria
	 * @return
	 */
	public ResponsePage<E> queryEntitys(RequestPage page) {
		ResponsePage<E> response = new ResponsePage<E>();
		try {
			Criteria criteria = getSession().createCriteria(this.entityClass);
			setCreateras(page.getCriteriaList(), criteria);
			Integer pageNo = page.getPageNo();
			Integer pageSize = page.getPageSize();
			Integer isNeedCount = page.getIsNeedCount();
			if (null != isNeedCount && isNeedCount == 1) {
				criteria.setProjection(Projections.rowCount());
				response.setCount(Long.valueOf(((Number) criteria.uniqueResult()).longValue()));
			}
			if (null != pageNo && pageNo > 0) {
				response.setPageNo(pageNo);
				response.setPageSize(pageSize);

				criteria.setMaxResults(page.getPageSize());
				criteria.setFirstResult((pageNo - 1) * pageSize);
			}
			criteria.setProjection(null);
			response.setRetCode(1);
			response.setResultList(criteria.list());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			response.setRetCode(1);
			response.setRetDesc(e.getMessage());
		}
		return response;
	}

	public void deleteByProperties(Map<String, Object> properties) {
		if (properties.isEmpty()) {
			return;
		}
		StringBuffer sb = new StringBuffer("delete from " + this.entityClass.getName() + " o where 1=1 ");
		appendQL(sb, properties);
		Query query = getSession().createQuery(sb.toString());
		setParameter(query, properties);
		query.executeUpdate();
	}

	public void delete(E entity) {
		getSession().delete(entity);
	}

	public void merge(E entity) {
		getSession().merge(entity);
	}

	public void update(E entity) {
		getSession().update(entity);
	}

	public E get(Serializable id) {
		return getSession().get(entityClass, id);
	}

	public E getByProerties(Map<String, Object> properties) {
		if (properties.isEmpty()) {
			return null;
		}
		List<E> list = this.queryByProerties(properties);
		if ((list != null) && (((List<E>) list).size() > 0)) {
			return list.get(0);
		}
		return null;
	}

	public List<E> queryByProerties(Map<String, Object> properties, Integer start, Integer top) {
		StringBuffer sb = new StringBuffer("select o from " + this.entityClass.getName() + " o where 1=1 ");
		appendQL(sb, properties);
		Query query = getSession().createQuery(sb.toString());
		setParameter(query, properties);
		if (start != null && top != null) {
			query.setFirstResult(start);
			query.setMaxResults(top.intValue());
		}
		return query.list();
	}

	public List<E> queryByProerties(Map<String, Object> properties) {
		StringBuffer sb = new StringBuffer("select o from " + this.entityClass.getName() + " o where 1=1 ");
		appendQL(sb, properties);
		Query query = getSession().createQuery(sb.toString());
		setParameter(query, properties);
		return query.list();
	}

	public Long countByProerties(Map<String, Object> properties) {
		StringBuffer sb = new StringBuffer("select count(*) from " + this.entityClass.getName() + " o where 1=1 ");
		appendQL(sb, properties);
		Query query = getSession().createQuery(sb.toString());
		setParameter(query, properties);
		return (Long) query.uniqueResult();
	}

	public Long countAll() {
		return (Long) getSession().createQuery("select count(*) from " + this.entityClass.getName()).uniqueResult();
	}

	public List<E> queryAll() {
		return getSession().createCriteria(this.entityClass).list();
	}

	private void appendQL(StringBuffer sb, Map<String, Object> properties) {
		/*properties.entrySet().forEach(entry -> {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				sb.append(" and o." + name + " is null ");
			} else if ((value instanceof Object[] && ((Object[]) value).length > 0) || (value instanceof Collection && !((Collection<?>) value).isEmpty())) {
				sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
			} else {
				sb.append(" and o." + name + "=:" + name.replace(".", ""));
			}
		});*/
	}

	private void setParameter(Query query, Map<String, Object> properties) {
		/*properties.entrySet().forEach(entry -> {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				if ((value instanceof Object[])) {
					query.setParameterList(name.replace(".", ""), (Object[]) value);
				} else if ((value instanceof Collection)) {
					query.setParameterList(name.replace(".", ""), (Collection<?>) value);
				} else {
					query.setParameter(name.replace(".", ""), value);
				}
			}
		});*/
	}
}