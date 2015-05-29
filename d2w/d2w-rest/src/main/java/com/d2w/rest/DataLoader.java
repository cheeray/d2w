package com.d2w.rest;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d2w.core.IEntityFinder;

@Singleton
public class DataLoader implements IEntityFinder {

	/** Logger. */
	public static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

	@PersistenceContext
	EntityManager em;

	@Override
	public <T> T find(Class<T> clazz, Object id) {
		return em.find(clazz, id);
	}

	public <T> void persist(T object) {
		em.persist(object);
	}

	public <T> T merge(T object) {
		return em.merge(object);
	}

	/**
	 * Search entities.
	 * 
	 * @param clazz
	 *            The class of the entity.
	 * @param queryName
	 *            The named query name.
	 * @param params
	 *            Query parameters.
	 * @return a list of results.
	 */
	public <T> List<T> search(Class<T> clazz, String queryName,
			Object... params) {
		LOG.info("Execute named query:{} with parameters:{}.", queryName,
				params);
		final TypedQuery<T> query = em.createNamedQuery(queryName, clazz);
		int position = 0;
		for (Object o : params) {
			query.setParameter(position++, o);
		}
		return query.getResultList();
	}

}
