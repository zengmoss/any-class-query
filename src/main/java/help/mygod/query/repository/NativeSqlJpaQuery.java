package help.mygod.query.repository;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.data.repository.query.ReturnedType;

import help.mygod.query.AnyClassQuery;
import help.mygod.query.constant.Constant;

public class NativeSqlJpaQuery extends AbstractJpaQuery {

	private final String querySql;

	public NativeSqlJpaQuery(JpaQueryMethod jpaQueryMethod, EntityManager em, String querySql) {
		super(jpaQueryMethod, em);
		this.querySql = querySql;
	}
	@Override
	public Object execute(Object[] parameters) {
		return getExecution().execute(this, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.query.AbstractJpaQuery#doCreateQuery(
	 * java.lang.Object[])
	 */
	@Override
	public Query doCreateQuery(Object[] values) {
		ResultProcessor resultFactory = getQueryMethod().getResultProcessor();
		ReturnedType returnedType = resultFactory.getReturnedType();
		@SuppressWarnings("rawtypes")
		Class clazz = returnedType.getReturnedType();
		Object o;
		for(int i = 0; i < values.length; i++) {
			o = values[i];
			if(o instanceof Collection) {
				values[i] = ((Collection)o).stream().map(String::valueOf).collect(Collectors.joining(Constant.COMMA));
			}
		}
		return AnyClassQuery.createSQLQuery(getEntityManager(), this.querySql, 1, values)
				.setResultTransformer(AnyClassQuery.createSQLQueryClassResult(clazz));
	}

	@Override
	protected Query doCreateCountQuery(Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

}
