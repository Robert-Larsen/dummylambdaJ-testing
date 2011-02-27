package no.robert.lambda;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class LambdaRepository
{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public void setEntityManagerFactory( EntityManagerFactory entityManagerFactory )
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void setEntityManager( EntityManager entityManager )
    {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager()
    {
        return entityManager;
    }
    
    public <T> List<T> find( CriteriaQuery<T> query )
    {
        return entityManager.createQuery( query ).getResultList();
    }
    
    public <T> T findSingle( CriteriaQuery<T> query )
    {
        return entityManager.createQuery( query ).getSingleResult();
    }

}
