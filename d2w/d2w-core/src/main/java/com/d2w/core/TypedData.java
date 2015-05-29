package com.d2w.core;

/** Data with type. */
public class TypedData {

	private final String type;
	private final Object data;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The media type.
	 * @param data
	 *            The data.
	 */
	public TypedData(String type, Object data) {
		this.type = type;
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public Object getData() {
		return data;
	}
}
