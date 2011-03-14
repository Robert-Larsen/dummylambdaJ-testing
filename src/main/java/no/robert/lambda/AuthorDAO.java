package no.robert.lambda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AuthorDAO
{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public void setEntityManagerFactory( EntityManagerFactory entityMgrFactory )
    {
        this.entityManagerFactory = entityMgrFactory;        
    }
    
    public EntityManagerFactory getEntityManagerFactory()
    {
        return this.entityManagerFactory;
    }
    
    public void setEntityManager( EntityManager entityManager )
    {
        this.entityManager = entityManager;
    }
    
    public EntityManager getEntitManager()
    {
        return this.entityManager;
    }
    
    public void add( Author author )
    {
        entityManager.persist( author );
    }
    
    public int getNumberOfAuthors()
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        
        Root<Author> cat = criteria.from( Author.class );
        criteria.select( cat );        

        int number = entityManager.createQuery( criteria ).getResultList().size();
    
        return number;
    }
    
    public Author getAuthor( String authorname )
    {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        
        CriteriaQuery<Author> criteria = builder.createQuery( Author.class );
        Root<Author> author = criteria.from( Author.class );
        criteria.select( author );
        criteria.where( builder.equal( author.get( "name" ), authorname ) );
        
        return entityManager.createQuery( criteria ).getSingleResult();
    }
    
    public void remove( Author author )
    {
        entityManager.remove( entityManager.merge( author ) );
    }
}
