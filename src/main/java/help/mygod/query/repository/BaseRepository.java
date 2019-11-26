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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Description:
 *
 *
 *
 * @author Moss Tsang
 * @date 2019年8月28日 下午5:18:59
 */
@NoRepositoryBean
public interface BaseRepository<T, ID>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, ID> {

	void deleteByIds(Collection<ID> ids);
}
