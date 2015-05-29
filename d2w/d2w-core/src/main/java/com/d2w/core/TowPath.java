package com.d2w.core;

import java.lang.invoke.MethodHandle;

import com.d2w.exe.TowException;

/** Path to fetch data. */
public class TowPath {
	private final String parent;
	private final Class<?> parentClass;
	private final String path;
	private final String type;
	private final MethodHandle handle;
	private final boolean entity;

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            The path of parent.
	 * @param parentClass
	 *            The class of parent.
	 * @param path
	 *            The path.
	 * @param type
	 *            The media type.
	 * @param handle
	 *            The handler of field.
	 * @param entity
	 *            Is this an entity?
	 */
	public TowPath(String parent, Class<?> parentClass, String path,
			String type, MethodHandle handle, boolean entity) {
		this.parent = parent;
		this.parentClass = parentClass;
		this.path = path;
		this.type = type;
		this.handle = handle;
		this.entity = entity;
	}

	/**
	 * Return the full path.
	 * 
	 * @param towPath
	 * @return a full path string.
	 */
	public String fullPath() {
		return fullPath(parent, path);
	}

	public static String fullPath(String parent, String path) {
		return parent + ":" + path;
	}

	public String getParent() {
		return parent;
	}

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}

	public Class<?> getParentClass() {
		return parentClass;
	}

	public MethodHandle getHandle() {
		return handle;
	}

	public boolean isEntity() {
		return entity;
	}

	public TypedData parse(Object target) throws Throwable {
		if (this.parentClass.isEnum()) {
			for (Object a : parentClass.getEnumConstants()) {
				if (a.toString().equalsIgnoreCase(path)) {
					return new TypedData(type, a);
				}
			}
			throw new TowException("Enum value " + path + " is not found.");
		} else {
			return new TypedData(type, handle.invoke(target));
		}
	}
}
