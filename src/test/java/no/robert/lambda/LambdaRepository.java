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


    public <T> List<T> find( CriteriaQuery<T> query )
    {
        EntityManager entityMgr = entityManagerFactory.createEntityManager();
        return entityMgr.createQuery( query ).getResultList();
    }


    public void setEntityManagerFactory( EntityManagerFactory entityManagerFactory )
    {
        this.entityManagerFactory = entityManagerFactory;
    }

}
