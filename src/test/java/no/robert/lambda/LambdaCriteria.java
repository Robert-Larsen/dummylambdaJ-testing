package no.robert.lambda;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Subquery;

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
    
    public LambdaCriteria( Class<T> type )
    {
        this.type = type;
        this.method = null;
        entityManagerFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
    }

    public static <T, R> LambdaCriteria<T, R> having( Class<T> type, R expression )
    {
        if( DefaultInvocationHandler.lastMethod.get().size() < 1 )
            return new LambdaCriteria( type );        
        else
            return new LambdaCriteria( 
            DefaultInvocationHandler.lastMethod.get().get( DefaultInvocationHandler.lastMethod.get().size()-1 ),
            type );
    }

    public CriteriaQuery<T> greaterThan( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        
        if ( expression instanceof java.lang.Number )
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
        
        if ( expression instanceof java.lang.Number )
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
    /*
    public CriteriaQuery<T> eq( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery( type );
        
        if ( DefaultInvocationHandler.lastType.get().size() > 1 )
        {            
            Root<T> from = criteria.from( type );
            CriteriaQuery<T> select = criteria.select( from );
            
            List<Class<?>> lastTypes = DefaultInvocationHandler.lastType.get();
            List<Method> lastMethods = DefaultInvocationHandler.lastMethod.get();
   
            for( int i = 0; i < lastTypes.size(); i++ )
            {   
                CriteriaQuery<T> criteria = builder.createQuery( type );
                Root<T> from = criteria.from( type );
                CriteriaQuery<T> select = criteria.select( from );
                Path<Object> path = from.get("publisher");
                
                Method lastMethod = DefaultInvocationHandler.lastMethod.get().get( DefaultInvocationHandler.lastMethod.get().size()-1 );
                Class lastType = DefaultInvocationHandler.lastType.get().get( 1 );
              
                Subquery c = criteria.subquery( lastType );
                Root innerFrom = c.from( lastType );
                String property = asProperty( lastMethod, lastType ).getName();
                c.select( innerFrom );
                c.where( builder.equal( innerFrom.get( property ), expression ) );
                            
                select.where( builder.in( path ).value( c ) );

                return select;
            }
            return select;
        }
        else
        {
            criteria = builder.createQuery( type );
            Path<Object> property = criteria.from( type ).get( asProperty( method ).getName() );
            return criteria.where( builder.equal( property, expression ) );
        }
    }
    */
    
    public CriteriaQuery<T> eq( R expression )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        if ( DefaultInvocationHandler.lastType.get().size() > 1 )
        {
            CriteriaQuery<T> criteria = builder.createQuery( type );
            Root<T> from = criteria.from( type );
            CriteriaQuery<T> select = criteria.select( from );
            
            Method lastMethod = DefaultInvocationHandler.lastMethod.get().get( DefaultInvocationHandler.lastMethod.get().size()-1 );
            Class lastType = DefaultInvocationHandler.lastType.get().get( 1 );
            
            String fieldname = asProperty( DefaultInvocationHandler.lastMethod.get().get( 0 ), 
                DefaultInvocationHandler.lastType.get().get( 0 ) ).getName();
            Path<Object> path = from.get(fieldname);
            
            Subquery c = criteria.subquery( lastType );
            Root innerFrom = c.from( lastType );
            String property = asProperty( lastMethod, lastType ).getName();
            c.select( innerFrom );
            c.where( builder.equal( innerFrom.get( property ), expression ) );
                        
            select.where( builder.in( path ).value( c ) );

            return select;
        }
        else
        {
            CriteriaQuery<T> criteria = builder.createQuery( type );
            Path<Object> property = criteria.from( type ).get( asProperty( method ).getName() );
            return criteria.where( builder.equal( property, expression ) );
        }
    }
    

    private Field asProperty( Method method )
    {
        for ( FieldResolver fieldResolver : fieldResolverStrategies )
        {
            Field field = fieldResolver.resolveFrom( type, method );
            if ( field != null )
                return field;
        }
        throw new RuntimeException( "Unable to resolve field from " + method.getName() + "() in " + type );
    }

    private Field asProperty( Method method, Class t )
    {
        for ( FieldResolver fieldResolver : fieldResolverStrategies )
        {
            Field field = fieldResolver.resolveFrom( t, method );
            if ( field != null )
                return field;
        }
        throw new RuntimeException( "Unable to resolve field from " + method.getName() + "() in " + t );
    }
}
