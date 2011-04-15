package no.robert.lambda;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import net.sf.cglib.proxy.Enhancer;

public class LambdaCriteria<T, R>
{
    private final Method method;

    private final Class<T> type;

    private List<FieldResolver> fieldResolverStrategies = new ArrayList<FieldResolver>();

    private final EntityManagerFactory entityManagerFactory;

    @SuppressWarnings( "unchecked" )
    public static <T> T on( final Class<T> type )
    {
        return (T) Enhancer.create( type, new DefaultInvocationHandler<T>( type ) );
    }

    public LambdaCriteria( Method method, Class<T> type )
    {
        this.method = method;
        this.type = type;
        entityManagerFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
        fieldResolverStrategies.add( new DefaultFieldResolver() );
    }

    public static <T, R> LambdaCriteria<T, R> having( Class<T> type, R expression )
    {
        
        return new LambdaCriteria( DefaultInvocationHandler.lastMethod.get(), DefaultInvocationHandler.lastType.get() );
    }

    public static <T, R> LambdaCriteria<T, R> min( Class<T> type, R expression )
    {
        return new LambdaCriteria( DefaultInvocationHandler.lastMethod.get(), DefaultInvocationHandler.lastType.get() );
    }

    public CriteriaQuery<T> greaterThan( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        if( expression instanceof java.lang.Number )
        {
            Path<Number> property = criteria.from( type ).get( asProperty( method ).getName() );

            return criteria.where( builder.gt( property, (Number) expression ) );
        }
        return null;
    }

    public CriteriaQuery<T> greaterThanOrEqualTo( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        if( expression instanceof java.lang.Number )
        {
            Path<Number> property = criteria.from( type ).get( asProperty( method ).getName() );

            return criteria.where( builder.ge( property, (Number) expression ) );
        }
        return null;
    }

    public CriteriaQuery<T> lessThan( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        if ( expression instanceof java.lang.Number )
        {
            Path<Number> property = criteria.from( type ).get( asProperty( method ).getName() );

            return criteria.where( builder.lt( property, (Number) expression ) );
        }
        return null;
    }

    public CriteriaQuery<T> getAll()
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        Root<T> cat = criteria.from( type );
        criteria.select( cat );

        return criteria;
    }

    public CriteriaQuery<T> lessThanOrEqualTo( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        if ( expression instanceof java.lang.Number )
        {
            Path<Number> property = criteria.from( type ).get( asProperty( method ).getName() );

            return criteria.where( builder.le( property, (Number) expression ) );
        }
        return null;
    }

    public CriteriaQuery<T> eq( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

        CriteriaQuery<T> criteria = builder.createQuery( type );
        // Path<Object> property = criteria.from( type ).get( asProperty( method
        // ) );
        Path<Object> property = criteria.from( type ).get( asProperty( method ).getName() );
        return criteria.where( builder.equal( property, expression ) );
    }

    private Field asProperty( Method method )
    {
        for ( FieldResolver fieldResolver : fieldResolverStrategies )
        {
            Field field = fieldResolver.resolveFrom( type, method );
            if ( field != null )
                return field;
        }
        throw new RuntimeException( "Unable to resolve field from " + method.getName() + "()" );

        // return uncapitalize( method.getName().substring( 3 ) );
    }
}
