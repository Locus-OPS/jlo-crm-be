package th.co.locus.jlo.common.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/***
 * 
 * @author Mr.BoonOom
 *
 */
public abstract class StringUtil {

	public static <K, V> String mapToString(Map<K, V> map) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<K, V>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<K, V> entry = iter.next();
			sb.append(entry.getKey());
			sb.append('=').append('"');
			sb.append(entry.getValue());
			sb.append('"');
			if (iter.hasNext()) {
				sb.append(',').append(' ');
			}
		}
		return sb.toString();

	}
	
	
	public static boolean isNullOrEmpty(String value){
		if (value != null)
		    return value.length() == 0;
		else
		    return true;
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	/* For Search Criteria */
	public static void nullifyObject(Object o) {
		Class<?> c = o.getClass();
		while (c != Object.class) {
			nullifyObject(o, c);
			c = c.getSuperclass();
		}
	}

	private static void nullifyObject(Object o, Class<?> c) {
		for (Field f : c.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getType().equals(String.class)) {
					String value = (String) f.get(o);
					if (value != null && value.trim().isEmpty()) {
						f.set(o, null);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
