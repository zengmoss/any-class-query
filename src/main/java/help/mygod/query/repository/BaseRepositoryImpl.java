/**  
* Description: 
*
*
*
* @author Moss Tsang  
* @date 2019年8月28日 下午5:18:59
*/
package help.mygod.query.repository;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 *
 *
 *
 * @author Moss Tsang
 * @date 2019年8月28日 下午5:18:59
 */

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T,ID> {
	private final EntityManager em;
	private final JpaEntityInformation<T, ?> entityInformation;
	public static final String DELETE_ALL_QUERY_STRING = "delete from %s x";
	/**
	 * @param domainClass
	 * @param em
	 */
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	/**
	 * @param entityInformation
	 * @param entityManager
	 */
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.em = entityManager;
	}

	@Transactional
	@Override
	public void deleteByIds(Collection<ID> ids) {
		if(null != ids && !ids.isEmpty()) {
			em.createQuery("delete from " + entityInformation.getEntityName() + " where id in ("
					+ ids.stream().map(String::valueOf).collect(Collectors.joining(","))
					+ ")").executeUpdate();
		}
		
	}

}
