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
    private boolean available;
    private boolean isPaperback;

    public Book( String title, Set<Author> authors, int pages )
    {
        this.title = title;
        this.authors = authors;
        this.pages = pages;
    }
    
    public Book( String title, Author author, int pages )
    {
        this.title = title;
        this.authors = new HashSet<Author>();
        this.authors.add( author );
        this.pages = pages;
    }

    public Book()
    {
        // TODO Auto-generated constructor stub
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
    
    public Set<Author> getAuthors()
    {
        return this.authors;
    }
    
    public void setAuthors( Set<Author> authors )
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
    
    public boolean isAvailable()
    {
        return this.available;
    }
    
    public void setAvailable( boolean available )
    {
        this.available = available;
    }
    
    public boolean hasPaperback()
    {
        return this.isPaperback;
    }
    
    public void setPaperback( boolean paperback)
    {
        this.isPaperback = paperback;
    }
 }
