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
import javax.persistence.Persistence;
import javax.persistence.criteria.*;

public class BookDAO
{
    private EntityManagerFactory entityManagerFactory;
       
    
    public void add( Book bok )
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist( bok );
        em.getTransaction().commit();
        em.close();
        
    }
    
    public Book getBook( String bookname )
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );
        Root<Book> book = criteria.from( Book.class );
        criteria.select( book );
        criteria.where( builder.equal( book.get( "title" ), bookname ) );
        
        return em.createQuery( criteria ).getSingleResult();
        
    }

    public int getNumberOfBooks()
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        CriteriaQuery<Book> criteria = builder.createQuery( Book.class );

        Root<Book> cat = criteria.from( Book.class );

        criteria.select( cat );        

        int number = em.createQuery( criteria ).getResultList().size();
        em.close();
    
        return number;
    }

    public void remove( Book book )
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove( em.merge( book ) );
        em.getTransaction().commit();
        em.close();
    }

    public void setEntityManagerFactory( EntityManagerFactory entityManagerFactory )
    {
        this.entityManagerFactory = entityManagerFactory;
    }
}
