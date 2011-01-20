package no.robert.lambda;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Books")
public class Book
{
    private String title;
    private int pages;
    @OneToMany
    private Set<Author> authors;
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    public Book( String title, Set<Author> authors, int pages )
    {
        this.title = title;
        this.authors = authors;
        this.pages = pages;
    }
    
    public Book( String title, String authorName, int pages )
    {
        authors = new HashSet<Author>();
        authors.add( new Author( authorName ) );
    }

    public Book()
    {
        // TODO Auto-generated constructor stub
    }

    public Book( Set<Author> authors )
    {
        this.authors = authors;
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
    
    public Set<Author> getAuthor()
    {
        return this.authors;
    }
    
    public void setAuthor( Set<Author> authors )
    {
        this.authors = authors;
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
