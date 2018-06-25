package help.mygod.query.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.lang.Nullable;

public class NativeJpaRepositoryFactory extends JpaRepositoryFactory {
	private final EntityManager entityManager;

	private final PersistenceProvider extractor;
	public NativeJpaRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
		this.extractor = PersistenceProvider.fromEntityManager(entityManager);
	}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getQueryLookupStrategy(org.springframework.data.repository.query.QueryLookupStrategy.Key, org.springframework.data.repository.query.EvaluationContextProvider)
	 */
	@Override
	protected Optional<QueryLookupStrategy> getQueryLookupStrategy(@Nullable QueryLookupStrategy.Key key,
																   EvaluationContextProvider evaluationContextProvider) {
		return Optional.of(NativeQueryLookupStrategy.create(entityManager, key, extractor, evaluationContextProvider));
	}
}
