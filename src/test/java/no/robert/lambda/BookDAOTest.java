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
import no.robert.lambda.BookDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BookDAOTest
{
    private LambdaRepository repository;
    
    private BookDAO books;
    private Publisher publisher;
    private Author author;
    
    @Before
    public void setUp()
    {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
        EntityManager entityManager = entityMgrFactory.createEntityManager();

        publisher = new Publisher("Manning");
        author = new Author( "Author" );
        entityManager.persist(publisher);
        entityManager.persist( author );        
        repository = new LambdaRepository();
        repository.setEntityManagerFactory( entityMgrFactory );
        repository.setEntityManager( entityManager );

        books = new BookDAO();
        books.setEntityManagerFactory( entityMgrFactory );
        books.setEntityManager( entityManager );
        books.getEntityManager().getTransaction().begin();
    }

    @After
    public void tearDown()
    {
        books.getEntityManager().getTransaction().rollback();
    }

    @Test
    @Ignore
    public void emptyDAO()
    {
        assertThat( books.getNumberOfBooks(), is( 0 ) );
    }

    @Test
    public void eq()
    {        
        books.add( new Book( "A book", author, 12, publisher ) );
        books.add( new Book( "Another book", author, 12, publisher ) );
        
        List<Book> books = repository.find( having( Book.class, on( Book.class ).getTitle() ).eq( "A book" ) );
        
        assertThat( books.size(), is( 1 ) );

        Book b = repository.findSingle( having( Book.class, on( Book.class).getTitle() ).eq( "A book" ) );
        assertThat( b.getTitle(), is( "A book" ) );       
        
        List<Book> manningBooks = repository.find( having( Book.class, on( Book.class ).getPublisher().getName() ).eq( "Manning" ) );
        System.out.println(manningBooks.getClass());
        
        //assertThat( manningBooks.size(), is( 1 ) );
        //assertThat( manningBooks.get( 0 ).getPublisher().getName(), is( "Manning" ) );
                
    }

    @Test
    @Ignore
    public void greaterThan()
    {
        books.add( new Book( "A book with more than 10 pages", author, 11, publisher ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).greaterThan( 10 ) );
        assertThat( b.getTitle(), is( "A book with more than 10 pages" ) );        
        
        books.add( new Book( "An expensive book", author, 50, 90.0 ) );
        books.add( new Book( "Another expensive book", author, 40, 100.0 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).greaterThan( 90.0 ) );
        
        assertThat( expensiveBooks.size(), is( 1 ) );               
    }
    
    @Test
    @Ignore
    public void greaterThanOrEqualTo()
    {   
        books.add( new Book( "A book with more than 100 pages", author, 101, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).greaterThanOrEqualTo( 101 ) );
        assertThat( b.getTitle(), is( "A book with more than 100 pages" ) );
                
        books.add( new Book( "An expensive book", author, 50, 500.01 ) );
        books.add( new Book( "Another expensive book", author, 40, 1299.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).greaterThanOrEqualTo( 500.01 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );       
    }
    
    @Test
    @Ignore
    public void lessThan()
    {
        books.add( new Book( "A book with less than 100 pages", author, 90, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).lessThan( 100 ) );
        assertThat( b.getTitle(), is( "A book with less than 100 pages" ) );
                
        books.add( new Book( "A cheap book", author, 50, 29.90 ) );
        books.add( new Book( "Another cheap book", author, 40, 9.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).lessThan(  50.00 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );            
    }
    
    @Test
    @Ignore
    public void lessThanOrEqualTo()
    {
        books.add( new Book( "A book with less than 100 pages", author, 90, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).lessThanOrEqualTo( 90 ) );
        assertThat( b.getTitle(), is( "A book with less than 100 pages" ) );
                
        books.add( new Book( "A cheap book", author, 50, 29.90 ) );
        books.add( new Book( "Another cheap book", author, 40, 9.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).lessThanOrEqualTo(  29.90 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );            
    }
    
    @Test
    @Ignore
    public void getAll()
    {
        books.add( new Book( "A book", author, 12, publisher ) );
        books.add( new Book( "Another book", author, 12, publisher ) );
        
        List<Book> allBooks = repository.find( having( Book.class, on( Book.class)).getAll() );
        assertThat( allBooks.size(), is( 2 ) );        
    }

    @Test
    @Ignore
    public void min()
    {     
        books.add( new Book( "A book", author, 12, 99.50 ) );
        books.add( new Book( "Another book", author, 100, 100.00 ) );
    }
}
