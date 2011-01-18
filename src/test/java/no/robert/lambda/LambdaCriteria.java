package no.robert.lambda;

import java.lang.reflect.Method;

import org.hibernate.criterion.DetachedCriteria;

public class LambdaCriteria
{

    public static final ThreadLocal<Method> lastMethod = new ThreadLocal<Method>();
    private final Method method;
    
    public LambdaCriteria( Method method )
    {
        this.method = method;
        // TODO Auto-generated constructor stub
    }

    public static LambdaCriteria having( Object expression )
    {
        return new LambdaCriteria( lastMethod.get() );
    }

    public DetachedCriteria eq( String string )
    {
        System.out.println( method.getName() + " = " + string );
        //System.out.println( string );
        // TODO Auto-generated method stub
        return null;
    }
    
    

}
