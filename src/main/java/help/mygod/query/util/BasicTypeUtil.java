package help.mygod.query.util;

import java.util.HashSet;
import java.util.Set;

public abstract class BasicTypeUtil {
	private static final Set<Class<?>> PRIMITIVE_WRAPPER_TYPE_AND_STRING = new HashSet<>(9);
	static {
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Boolean.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Byte.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Character.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Double.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Float.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Integer.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Long.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(Short.class);
		PRIMITIVE_WRAPPER_TYPE_AND_STRING.add(String.class);
	}

	public static boolean basicType(Class<?> clazz) {
		return clazz.isPrimitive() || PRIMITIVE_WRAPPER_TYPE_AND_STRING.contains(clazz);
	}
}
