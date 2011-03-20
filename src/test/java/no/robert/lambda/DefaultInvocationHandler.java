package no.robert.lambda;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.InvocationHandler;

public class DefaultInvocationHandler<T> implements InvocationHandler
{       
    protected static final ThreadLocal<Method> lastMethod = new ThreadLocal<Method>();
    protected static final ThreadLocal<Class<?>> lastType = new ThreadLocal<Class<?>>();
    
    public DefaultInvocationHandler( Class<T> type )
    {
        lastType.set( type );
    }
    
    @Override
    public Object invoke( Object arg0, Method method, Object[] arg2 ) throws Throwable
    {
        lastMethod.set( method );            
        if( lastMethod.get().getReturnType().isPrimitive() )
        {            
            String typeName = lastMethod.get().getReturnType().getSimpleName();
            if( typeName.equals( "int" ) )
                return Integer.MIN_VALUE;
            else if( typeName.equals( "double" ) )
                return Double.MIN_VALUE;
            else if( typeName.equals( "long" ) )
                return Long.MIN_VALUE;
            else if( typeName.equals( "float" ) )
                return Float.MAX_VALUE;
            else if( typeName.equals( "short" ) )
                return Short.MIN_VALUE;
            else if( typeName.equals( "boolean" ) )
                return false;
            else if( typeName.equals( "byte" ) )
                return Byte.MIN_VALUE;
            else if( typeName.equals( "char" ) )
                return Character.MIN_VALUE;
        }
        
        return null;
    }          

}
