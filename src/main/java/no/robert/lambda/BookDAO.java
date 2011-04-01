package no.robert.lambda;

/*
 import static ch.lambdaj.Lambda.having;
 import static ch.lambdaj.Lambda.on;
 import static ch.lambdaj.Lambda.select;
 import static org.hamcrest.Matchers.is;

 import java.util.ArrayList;
 import java.util.List;
 */
import static no.robert.lambda.LambdaCriteria.having;
import static no.robert.lambda.LambdaCriteria.on;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;

import no.robert.lambda.LambdaRepository;

public class BookDAO
{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public void setEntityManagerFactory( EntityManagerFactory entityManagerFactory )
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        return this.entityManagerFactory;
    }

    public void setEntityManager( EntityManager entityManager )
    {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    public void add( Book bok )
    {
        entityManager.persist( bok );
    }
    
    public void addSomething( Something something )
    {
        entityManager.persist( something );
    }

    public int getNumberOfBooks()
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );

        Root<Book> cat = criteria.from( Book.class );

        criteria.select( cat );

        int number = entityManager.createQuery( criteria ).getResultList().size();
        
        return number;
    }

    public void remove( Book book )
    {
        entityManager.remove( entityManager.merge( book ) );
    }
}
