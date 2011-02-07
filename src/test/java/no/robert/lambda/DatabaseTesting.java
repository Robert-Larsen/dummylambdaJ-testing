package no.robert.lambda;

import static no.robert.lambda.LambdaCriteria.having;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class DatabaseTesting
{
    @Test
    public void testname()
    {
        LambdaHibernateTemplate hibernateTemplate = new LambdaHibernateTemplate( new HibernateTemplate() );
        
        DetachedCriteria criteria = having(on( Book.class ).getTitle() ).eq( "Forfatter1" );
        
        //List<Bok> boker = hibernateTemplate.find( Bok.class, criteria );
    }
    
    private <T> T on(Class<T> type) {
        return (T)Enhancer.create( type, new InvocationHandler() {

            @Override
            public Object invoke( Object arg0, Method method, Object[] arg2 ) throws Throwable
            {
                System.out.println( method.getName() );
                
                LambdaCriteria.lastMethod.set( method );
                
                return null;
            }
            
        });
    }


}
