package no.robert.lambda;

/*
 import static ch.lambdaj.Lambda.having;
 import static ch.lambdaj.Lambda.on;
 import static ch.lambdaj.Lambda.select;
 import static org.hamcrest.Matchers.is;

 import java.util.ArrayList;
 import java.util.List;
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;

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

    public Book getBook( String bookname )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );
        Root<Book> book = criteria.from( Book.class );
        criteria.select( book );
        criteria.where( builder.equal( book.get( "title" ), bookname ) );

        return entityManager.createQuery( criteria ).getSingleResult();
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
