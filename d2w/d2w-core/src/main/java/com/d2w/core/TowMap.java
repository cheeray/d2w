package com.d2w.core;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.d2w.exe.TowException;

/**
 * Map of path to data.
 * 
 * @author Chengwei.Yan
 * 
 */
public final class TowMap {

	/** Available paths. */
	private static final Map<String, List<String>> PATHS = new ConcurrentHashMap<>();

	private static final Map<String, TowPath> TOWS = new ConcurrentHashMap<>();

	/**
	 * Map a path.
	 * 
	 * @param parent
	 *            The root.
	 * @param parentClass
	 *            The parent class.
	 * @param path
	 *            The path.
	 * @param type
	 *            The media type.
	 * 
	 * @param handle
	 *            The data handle.
	 * @param isEntity
	 *            Is this an entity?
	 * @throws TowException
	 */
	public static void map(String parent, Class<?> parentClass, String path,
			String type, MethodHandle handle, boolean isEntity)
			throws TowException {
		final TowPath towPath = new TowPath(parent, parentClass, path, type,
				handle, isEntity);
		List<String> paths = PATHS.get(parent);
		if (paths == null) {
			PATHS.put(parent, paths = new ArrayList<>());
		}
		if (paths.contains(path)) {
			throw new TowException("Path " + path + " exists already in "
					+ parent);
		}
		paths.add(path);

		TOWS.put(towPath.fullPath(), towPath);
	}

	/**
	 * Obtains all available paths.
	 * 
	 * @return a map of paths.
	 */
	public static Map<String, List<String>> paths() {
		return Collections.unmodifiableMap(PATHS);
	}

	/**
	 * Obtains all available paths for a root.
	 * 
	 * @return a list of paths.
	 */
	public static List<String> paths(String parent) {
		return Collections.unmodifiableList(PATHS.get(parent));
	}

	public static TowPath from(String parent, String path) {
		return TOWS.get(TowPath.fullPath(parent, path));
	}
}
