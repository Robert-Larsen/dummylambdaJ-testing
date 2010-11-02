package no.robert.lambda;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class LambdaHibernateTemplate
{

    public LambdaHibernateTemplate( HibernateTemplate hibernateTemplate )
    {
        // TODO Auto-generated constructor stub
    }

    public <T> List<T> find( Class<T> entityType, DetachedCriteria criteria )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
