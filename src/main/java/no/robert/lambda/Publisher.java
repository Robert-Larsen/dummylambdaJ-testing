package no.robert.lambda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "Publishers" )
public class Publisher {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String name;

    public Publisher() {
    }

    public Publisher(String name) {
        this.setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
