package com.d2w.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtil {

	public static String toJson(Map<String, List<String>> map) {
		final StringBuilder sb = new StringBuilder();
		sb.append('[');
		boolean more = false;
		for (Entry<String, List<String>> entry : map.entrySet()) {
			if (more) {
				sb.append(',');
			}
			sb.append("{\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append(toJson(entry.getValue()));
			sb.append("}");
			more = true;
		}
		sb.append(']');
		return sb.toString();
	}

	public static String toJson(List<String> strs) {
		final StringBuilder sb = new StringBuilder();
		sb.append('[');
		boolean more = false;
		for (String s : strs) {
			if (more) {
				sb.append(',');
			}
			sb.append('\"');
			sb.append(s);
			sb.append('\"');
			more = true;
		}
		sb.append(']');
		return sb.toString();
	}
}
