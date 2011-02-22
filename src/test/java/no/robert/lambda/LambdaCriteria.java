package no.robert.lambda;

import static org.apache.commons.lang.StringUtils.uncapitalize;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class LambdaCriteria<T>
{
    private static final ThreadLocal<Method> lastMethod = new ThreadLocal<Method>();
    private static final ThreadLocal<Class<?>> lastType = new ThreadLocal<Class<?>>();
    private final Method method;
    private final Class<T> type;
    
    private final EntityManagerFactory entityManagerFactory;
    
    public static <T> T on(final Class<T> type) {
        return (T)Enhancer.create( type, new InvocationHandler() {

            @Override
            public Object invoke( Object arg0, Method method, Object[] arg2 ) throws Throwable
            {
                //System.out.println( type.getSimpleName() + "." + method.getName() );
                
                LambdaCriteria.lastMethod.set( method );
                LambdaCriteria.lastType.set( type );
                
                return null;
            }
            
        });
    }
    
    
    public LambdaCriteria( Method method, Class<T> type )
    {
        this.method = method;
        this.type = type;
        entityManagerFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
    }

    public static <T> LambdaCriteria<T> having( Class<T> type, Object expression )
    {
        return new LambdaCriteria( lastMethod.get(), lastType.get() );
    }

    public CriteriaQuery<T> eq( String string )
    {
        //System.out.println( method.getName() + " = " + string );
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        CriteriaQuery<T> criteria = builder.createQuery( type );
        Path<Object> property = criteria.from( type ).get( asProperty( method ) );
        return criteria.where( builder.equal( property, string ) );
    }


    private String asProperty( Method method )
    {
        return uncapitalize( method.getName().substring( 3 ) );
    }
}
