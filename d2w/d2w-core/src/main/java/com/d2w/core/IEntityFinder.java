package com.d2w.core;

/**
 * Find entity.
 * 
 * @author Chengwei.Yan
 * 
 */
public interface IEntityFinder {

	/**
	 * Find entity by ID.
	 * 
	 * @param clazz
	 *            The entity class.
	 * @param id
	 *            The identity.
	 * @return an entity.
	 */
	public <T> T find(Class<T> clazz, Object id);
}
