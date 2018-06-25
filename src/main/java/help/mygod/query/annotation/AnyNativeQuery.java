/**
 * 
 */
package help.mygod.query.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**  
* Description: 
*
* @author moss  
* @date 2018年6月21日 
*/  
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface AnyNativeQuery {
	String value();
}
