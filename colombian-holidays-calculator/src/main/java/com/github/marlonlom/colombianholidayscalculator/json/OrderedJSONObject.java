package com.github.marlonlom.colombianholidayscalculator.json;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * {@code OrderedJSONObject} extends {@link JSONObject} to provide JSON objects
 * that maintain the insertion order of their keys.
 * <p>
 * By default, {@code JSONObject} does not guarantee the order of its keys. This
 * class modifies the internal map of {@code JSONObject} to a
 * {@link LinkedHashMap} using Java Reflection, thereby ensuring that the order
 * in which key-value pairs are added is preserved when the JSON object is
 * serialized (e.g., using {@code toString()}).
 * </p>
 * <p>
 * <b>Warning:</b> This implementation uses Java Reflection to modify a private
 * field of {@code JSONObject}. This approach can be fragile and might break if
 * the internal implementation of the {@code org.json} library changes in future
 * versions. For production environments where strict ordering is crucial and
 * stability is paramount, consider using more robust JSON libraries like
 * Jackson or Gson, which offer more official and stable ways to control key
 * order.
 * </p>
 * 
 * @author marlonlom
 * @version 2.0.0
 */
public class OrderedJSONObject extends JSONObject {
	/**
	 * Constructs an empty {@code OrderedJSONObject}. The internal map used to store
	 * key-value pairs is replaced with a {@link LinkedHashMap} to ensure that the
	 * order of insertion is preserved.
	 * <p>
	 * If the reflection operation fails (e.g., due to security restrictions or a
	 * change in {@code JSONObject}'s internal implementation), an error message is
	 * printed to {@code System.err}, and the object might fall back to the default
	 * unordered behavior of {@code JSONObject}.
	 * </p>
	 */
	public OrderedJSONObject() {
		super();
		try {
			java.lang.reflect.Field field = JSONObject.class.getDeclaredField("map");
			field.setAccessible(true);
			field.set(this, new LinkedHashMap<String, Object>());
		} catch (Throwable e) {
			System.err.println("Error al modificar el mapa interno de JSONObject: " + e.getMessage());
		}
	}

	/**
	 * Constructs an {@code OrderedJSONObject} by copying all of the mappings from
	 * the specified {@code Map}.
	 * <p>
	 * The order of keys in the resulting JSON object will correspond to the
	 * iteration order of the provided {@code Map}'s entry set. If the provided map
	 * is a {@link LinkedHashMap}, its insertion order will be preserved. If it's a
	 * {@link TreeMap}, its sorted order will be preserved. If it's a regular
	 * {@link java.util.HashMap}, the initial order might not be guaranteed before
	 * copying, but subsequent additions to this {@code OrderedJSONObject} will
	 * respect insertion order.
	 * </p>
	 *
	 * @param map A {@code Map} whose mappings are to be placed in this
	 *            {@code OrderedJSONObject}.
	 */
	public OrderedJSONObject(Map<?, ?> map) {
		this();
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			this.put(String.valueOf(entry.getKey()), entry.getValue());
		}
	}

}
