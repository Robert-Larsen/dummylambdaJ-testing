package no.robert.lambda;

import static no.robert.lambda.LambdaCriteria.having;
import static no.robert.lambda.LambdaCriteria.on;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import no.robert.lambda.Author;
import no.robert.lambda.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BookTest
{
    private LambdaRepository repository;
    
    private Publisher manning;
    private Publisher addisonWesley;
    private Author author;
    private Editor editor;
    EntityManagerFactory entityMgrFactory;
    EntityManager entityManager; 
    
    @Before
    public void setUp()
    {
        entityMgrFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
        entityManager = entityMgrFactory.createEntityManager();
        
        author = new Author( "Author" );
        editor = new Editor( "Editor" );
        manning = new Publisher("Manning", editor);
        addisonWesley = new Publisher( "Addison-Wesley", editor );
        entityManager.persist( manning );
        entityManager.persist( addisonWesley );
        entityManager.persist( author );
        entityManager.persist( editor );
        
        repository = new LambdaRepository();
        repository.setEntityManager( entityManager );
        entityManager.getTransaction().begin();
    }

    @After
    public void tearDown()
    {
        entityManager.getTransaction().rollback();
    }

    @Test
    public void eq()
    {       
        entityManager.persist( new Book( "A book", author, 12, addisonWesley ) );
        entityManager.persist( new Book( "Another book", author, 12, manning ) );
        
        List<Book> books = repository.find( having( Book.class, on( Book.class ).getTitle() ).eq( "A book" ) );
        
        assertThat( books.size(), is( 1 ) );
        
        Book b = repository.findSingle( having( Book.class, on( Book.class).getTitle() ).eq( "A book" ) );
        assertThat( b.getTitle(), is( "A book" ) );
                 
        entityManager.persist( new Book( "A book", author, 12, addisonWesley ) );
        Book manningBook = repository.findSingle( having( Book.class, on( Book.class ).getPublisher().getName() ).eq( "Manning" ) );
                
        assertThat( manningBook.getPublisher().getName(), is( "Manning" ) );
        /*
        List<Book> editorBook = repository.find( having( Book.class, on( Book.class ).getPublisher().getEditor().getName() ).eq( "Editor" ) );
        
        assertThat( editorBook.get(0).getPublisher().getEditor().getName(), is( "Editor" ) );
         */        
    }

    @Test
    public void greaterThan()
    {
        entityManager.persist( new Book( "A book with more than 10 pages", author, 11, manning ) );
       
        Book b = repository.findSingle( having( Book.class, on( Book.class  ).getPages() ).greaterThan( 10 ) );
        assertThat( b.getTitle(), is( "A book with more than 10 pages" ) );        
        
        entityManager.persist( new Book( "An expensive book", author, 50, 90.0 ) );
        entityManager.persist( new Book( "Another expensive book", author, 40, 100.0 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).greaterThan( 90.0 ) );
        
        assertThat( expensiveBooks.size(), is( 1 ) );   
                    
    }
    
    @Test
    public void greaterThanOrEqualTo()
    {   
        entityManager.persist( new Book( "A book with more than 100 pages", author, 102, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).greaterThanOrEqualTo( 101 ) );
        assertThat( b.getTitle(), is( "A book with more than 100 pages" ) );
                
        entityManager.persist( new Book( "An expensive book", author, 50, 500.01 ) );
        entityManager.persist( new Book( "Another expensive book", author, 40, 1299.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).greaterThanOrEqualTo( 500.01 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );       
    }
    
    @Test
    public void lessThan()
    {
        entityManager.persist( new Book( "A book with less than 100 pages", author, 90, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).lessThan( 100 ) );
        assertThat( b.getTitle(), is( "A book with less than 100 pages" ) );
                
        entityManager.persist( new Book( "A cheap book", author, 50, 29.90 ) );
        entityManager.persist( new Book( "Another cheap book", author, 40, 9.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).lessThan(  50.00 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );            
    }
    
    @Test
    public void lessThanOrEqualTo()
    {
        entityManager.persist( new Book( "A book with less than 100 pages", author, 90, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).lessThanOrEqualTo( 90 ) );
        assertThat( b.getTitle(), is( "A book with less than 100 pages" ) );
                
        entityManager.persist( new Book( "A cheap book", author, 50, 29.90 ) );
        entityManager.persist( new Book( "Another cheap book", author, 40, 9.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).lessThanOrEqualTo(  29.90 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );            
    }
    
    @Test
    public void getAll()
    {
        entityManager.persist( new Book( "A book", author, 12, manning ) );
        entityManager.persist( new Book( "Another book", author, 12, manning ) );
        
        List<Book> allBooks = repository.find( having( Book.class, on( Book.class)).getAll() );
        assertThat( allBooks.size(), is( 2 ) );        
    }
}
