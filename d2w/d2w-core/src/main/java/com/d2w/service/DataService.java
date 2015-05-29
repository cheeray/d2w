package com.d2w.service;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.inject.Named;

import com.d2w.core.IEntityFinder;
import com.d2w.core.TowMap;
import com.d2w.core.TowPath;
import com.d2w.core.TypedData;
import com.d2w.exe.TowException;

/**
 * A simple CDI service which is able to provide data.
 * 
 * @author Chengwei.Yan
 * 
 */
@Named
public class DataService {

	@Any
	IEntityFinder entityFinder;

	/**
	 * Find data by path.
	 * 
	 * @param parent
	 *            The parent.
	 * @param path
	 *            The path.
	 * @param id
	 *            The identity.
	 * @return a data with media type.
	 * @throws TowException
	 *             while failed to find.
	 */
	public TypedData find(String parent, String path, Object id)
			throws TowException {
		final TowPath towPath = TowMap.from(parent, path);
		if (towPath == null) {
			throw new TowException("Path " + path + " was not found from "
					+ parent + ".");
		}
		Object target = null;
		if (towPath.isEntity()) {
			target = entityFinder.find(towPath.getParentClass(), id);
			if (target == null) {
				throw new TowException("ID" + id + " from path " + path
						+ " was not found from " + parent + ".");
			}
		}
		try {
			return towPath.parse(target);
		} catch (Throwable e) {
			throw new TowException(e);
		}
	}

	/**
	 * Find all available paths.
	 * 
	 * @return a map of paths to root.
	 */
	public Map<String, List<String>> paths() {
		return TowMap.paths();
	}

	/**
	 * Find all paths for a root.
	 * 
	 * @param root
	 *            The root.
	 * @return a list of paths.
	 */
	public List<String> paths(String root) {
		return TowMap.paths(root);
	}
}
