package help.mygod.query.instance;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.BeanUtils;

import help.mygod.query.util.StringUtil;

public enum ClassMappedField {
	INSTANCE;
	private static final ConcurrentMap<Class<?>, Map<String, PropertyDescriptor>> CLASS_CACHE_PROPERTY_DESCRIPTOR_MAP = new ConcurrentHashMap<Class<?>, Map<String, PropertyDescriptor>>(
			64);

	public Map<String, PropertyDescriptor> getClassMappedFields(Class<?> cz) {
		Map<String, PropertyDescriptor> r = CLASS_CACHE_PROPERTY_DESCRIPTOR_MAP.get(cz);
		if (null != r) {
			return r;
		}
		r = new HashMap<String, PropertyDescriptor>(16);
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(cz);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null) {
				r.put(StringUtil.lowerCaseName(pd.getName()), pd);
			}
		}
		CLASS_CACHE_PROPERTY_DESCRIPTOR_MAP.putIfAbsent(cz, r);
		return r;
	}
}
