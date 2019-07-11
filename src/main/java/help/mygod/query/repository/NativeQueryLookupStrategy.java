package help.mygod.query.repository;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.RepositoryQuery;

import help.mygod.query.annotation.AnyNativeQuery;

public class NativeQueryLookupStrategy implements QueryLookupStrategy {

	private final EntityManager entityManager;

	private QueryLookupStrategy jpaQueryLookupStrategy;

	private QueryExtractor extractor;

	public NativeQueryLookupStrategy(EntityManager entityManager, Key key, QueryExtractor extractor,
			QueryMethodEvaluationContextProvider evaluationContextProvider) {
		this.jpaQueryLookupStrategy = JpaQueryLookupStrategy.create(entityManager, key, extractor,
				evaluationContextProvider);
		this.extractor = extractor;
		this.entityManager = entityManager;
	}

	public static QueryLookupStrategy create(EntityManager entityManager, Key key, QueryExtractor extractor,
			QueryMethodEvaluationContextProvider evaluationContextProvider) {
		return new NativeQueryLookupStrategy(entityManager, key, extractor, evaluationContextProvider);
	}

	@Override
	public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
			NamedQueries namedQueries) {
		AnyNativeQuery anyNativeQuery = method.getAnnotation(AnyNativeQuery.class);
		if (null != anyNativeQuery) {
			return new NativeSqlJpaQuery(new JpaQueryMethod(method, metadata, factory, extractor), entityManager,
					anyNativeQuery.value());
		} else {
			return jpaQueryLookupStrategy.resolveQuery(method, metadata, factory, namedQueries);
		}
	}
}
