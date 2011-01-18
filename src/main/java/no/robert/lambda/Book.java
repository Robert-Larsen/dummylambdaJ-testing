package no.robert.lambda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Books")
public class Book
{
    private String title;
    private String author;
    private int pages;
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    public Book( String title, String author, int pages )
    {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public Book()
    {
        // TODO Auto-generated constructor stub
    }

    public Book( String author )
    {
        this.author = author;
    }
    

    public Long getId()
    {
        return id;
    }
    
    @Id
    public void setId( Long id )
    {
        this.id = id;
    }

    public int getPages()
    {
        return this.pages;
    }
    
    public void setPages( int pages )
    {
        this.pages = pages;
    }
    
    public String getAuthor()
    {
        return this.author;
    }
    
    public void setAuthor( String author )
    {
        this.author = author;
    }
   
    public String getTitle()
    {
        return this.title;
    }
    
    public void setTitle( String title )
    {
        this.title = title;
    }

}
