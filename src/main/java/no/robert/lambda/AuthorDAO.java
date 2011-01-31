package no.robert.lambda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AuthorDAO
{
    EntityManagerFactory entityManagerFactory;

    public AuthorDAO()
    {
        try
        {
            setUp();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private void setUp() throws Exception
    {
        entityManagerFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
    }
    
    public void add( Author author )
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist( author );
        em.getTransaction().commit();
        em.close();
    }
    
    public int getNumberOfAuthors()
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );

        Root<Author> cat = criteria.from( Author.class );

        criteria.select( cat );        

        int number = em.createQuery( criteria ).getResultList().size();
        em.close();
    
        return number;
    }
    
    public Author getAuthor( String authorname )
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        Root<Author> author = criteria.from( Author.class );
        criteria.select( author );
        criteria.where( builder.equal( author.get( "name" ), authorname ) );
        
        return em.createQuery( criteria ).getSingleResult();
    }
    
    public void remove( Author author )
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove( em.merge( author ) );
        em.getTransaction().commit();
        em.close();
    }

}
