package help.mygod.query.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class NativeJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID>{

	public NativeJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Returns a {@link RepositoryFactorySupport}.
	 *
	 * @param entityManager
	 * @return
	 */
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new NativeJpaRepositoryFactory(entityManager);
	}
}
