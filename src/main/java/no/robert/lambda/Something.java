package no.robert.lambda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "Something" )
public class Something
{
    private String value;
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;
    
    public Something()
    {
        
    }
    
    public Something( String value )
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return this.value;
    }
    
    public void setValue( String value )
    {
        this.value = value;
    }
}
