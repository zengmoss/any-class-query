package help.mygod.query;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.support.DefaultConversionService;

import help.mygod.query.constant.Constant;
import help.mygod.query.instance.ClassMappedField;
import help.mygod.query.util.BasicTypeUtil;
import help.mygod.query.util.StringUtil;import javassist.expr.Instanceof;

public class AnyClassQuery {

	public AnyClassQuery(EntityManager entityManager) {
		super();
	}

	public static Query createSQLQuery(EntityManager entityManager, String sql, Object... parameters) {
		return createSQLQuery(entityManager, sql, 0, parameters);
	}

	public static Query createSQLQuery(EntityManager entityManager, String sql, int start, Object... parameters) {
		Query nativeQueryImpl = entityManager.createNativeQuery(sql).unwrap(Query.class);
		if (null != parameters && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				nativeQueryImpl.setParameter(i + start, parameters[i]);
			}
		}
		return nativeQueryImpl;
	}

	public static ResultTransformer createSQLQueryClassResult(Class<?> returnType) {
		return chooseFunction(returnType).apply(returnType);

	}

	private static Function<Class<?>, ResultTransformer> chooseFunction(Class<?> clazz) {
		if (BasicTypeUtil.basicType(clazz)) {
			return t -> {
				return new ResultTransformer() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 5195496922224533356L;

					@Override
					public Object transformTuple(Object[] tuple, String[] aliases) {
						if((t.equals(Boolean.class) || t.equals(boolean.class)) && tuple[0] instanceof Number) {
							return ((Number)tuple[0]).intValue() > 0;
						}
						return DefaultConversionService.getSharedInstance().convert(tuple[0], t);
					}

					@SuppressWarnings("rawtypes")
					@Override
					public List transformList(List collection) {
						return collection;
					}

				};
			};
		}
		return t -> {

			return new ResultTransformer() {
				Map<String, PropertyDescriptor> mappedFields = ClassMappedField.INSTANCE.getClassMappedFields(t);
				/**
				 * 
				 */
				private static final long serialVersionUID = -8436313313355511170L;

				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					BeanWrapper beanWrapper = PropertyAccessorFactory
							.forBeanPropertyAccess(BeanUtils.instantiateClass(t));
					beanWrapper.setConversionService(DefaultConversionService.getSharedInstance());
					PropertyDescriptor propertyDescriptor = null;
					for (int i = 0; i < tuple.length; i++) {
						propertyDescriptor = mappedFields.get(
								StringUtil.lowerCaseName(aliases[i].replaceAll(Constant.UNDER_LINE, Constant.EMPTY)));
						if (null != propertyDescriptor && null != tuple[i]) {
							beanWrapper.setPropertyValue(propertyDescriptor.getName(), tuple[i]);
						}
					}
					return beanWrapper.getWrappedInstance();
				}

				@SuppressWarnings("rawtypes")
				@Override
				public List transformList(List collection) {
					return collection;
				}

			};
		};
	}
}
