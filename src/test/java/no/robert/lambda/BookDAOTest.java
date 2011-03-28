package no.robert.lambda;

import static no.robert.lambda.LambdaCriteria.having;
import static no.robert.lambda.LambdaCriteria.on;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import no.robert.lambda.Author;
import no.robert.lambda.AuthorDAO;
import no.robert.lambda.Book;
import no.robert.lambda.BookDAO;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BookDAOTest
{
    private BookDAO books;
    private AuthorDAO authors;
    private LambdaRepository repository;    

    @Before
    public void setUp()
    {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory( "no.robert.lambda" );
        EntityManager entityManager = entityMgrFactory.createEntityManager();
        repository = new LambdaRepository();
        repository.setEntityManagerFactory( entityMgrFactory );
        repository.setEntityManager( entityManager );
        
        books = new BookDAO();
        books.setEntityManagerFactory( entityMgrFactory );
        books.setEntityManager( entityManager );
        books.getEntityManager().getTransaction().begin();
        
        authors = new AuthorDAO();
        authors.setEntityManagerFactory( entityMgrFactory );
        authors.setEntityManager(  entityManager );
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
    @Ignore
    public void add()
    {
        Author author1 = new Author( "Someone" );
        authors.add(  author1 );
        Author author2 = new Author( "Someone else" );
        authors.add( author2 );
        Set<Author> authorsSet = new HashSet<Author>();
        books.add( new Book( "A book", authorsSet, 100, 59.90 ) );
        
        books.add( new Book( "Another book", authorsSet, 50, 99.90 ) );
        assertThat( books.getNumberOfBooks(), is( 2 ) );
    } 
    
    @Test
    @Ignore
    public void remove()
    {   
        Author author1 = new Author( "Someone" );
        authors.add( author1 );
        int numberofbooks = books.getNumberOfBooks();
        books.add( new Book( "A new book", author1, 100, 50 ) );
        assertThat( numberofbooks, is( books.getNumberOfBooks()-1 ) );
        assertThat( numberofbooks, is( books.getNumberOfBooks() ) );        
    }
    
    @Test
    public void eq()
    {
        Author author = new Author( "Someone" );
        authors.add( author );
        books.add( new Book( "A book", author, 12, 99.50 ) );
 
        
        List<Book> books = repository.find( having( Book.class, on( Book.class ).getTitle() ).eq( "A book" ) );
        assertThat( books.size(), is( 1 ) );
        
        Book b = repository.findSingle(  having( Book.class, on( Book.class).getTitle() ).eq( "A book" ) );
        assertThat( b.getPages(), is( 12 ) );
    }
    
    @Test
    public void greaterThan()
    {   
        Author author = new Author( "Someone" );     
        authors.add(  author );
        books.add( new Book( "A book with more than 100 pages", author, 101, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).greaterThan( 10 ) );
        assertThat( b.getTitle(), is( "A book with more than 100 pages" ) );
        
        
        books.add( new Book( "An expensive book", author, 50, 500.01 ) );
        books.add( new Book( "Another expensive book", author, 40, 1299.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).greaterThan( 500.00 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );       
    }
    
    @Test
    public void greaterThanOrEqualTo()
    {   
        Author author = new Author( "Someone" );     
        authors.add(  author );
        books.add( new Book( "A book with more than 100 pages", author, 101, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).greaterThanOrEqualTo( 101 ) );
        assertThat( b.getTitle(), is( "A book with more than 100 pages" ) );
        
        
        books.add( new Book( "An expensive book", author, 50, 500.01 ) );
        books.add( new Book( "Another expensive book", author, 40, 1299.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).greaterThanOrEqualTo( 500.01 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );       
    }
    
    @Test
    public void lessThan()
    {
        Author author = new Author( "Someone" );     
        authors.add(  author );
        books.add( new Book( "A book with less than 100 pages", author, 90, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).lessThan( 100 ) );
        assertThat( b.getTitle(), is( "A book with less than 100 pages" ) );
                
        books.add( new Book( "A cheap book", author, 50, 29.90 ) );
        books.add( new Book( "Another cheap book", author, 40, 9.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).lessThan(  50.00 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );            
    }
    
    @Test
    public void lessThanOrEqualTo()
    {
        Author author = new Author( "Someone" );     
        authors.add(  author );
        books.add( new Book( "A book with less than 100 pages", author, 90, 100.5 ) );
        Book b = repository.findSingle( having( Book.class, on( Book.class ).getPages() ).lessThanOrEqualTo( 90 ) );
        assertThat( b.getTitle(), is( "A book with less than 100 pages" ) );
                
        books.add( new Book( "A cheap book", author, 50, 29.90 ) );
        books.add( new Book( "Another cheap book", author, 40, 9.99 ) );
        
        List<Book> expensiveBooks = repository.find( having( Book.class, on( Book.class ).getPrice() ).lessThanOrEqualTo(  29.90 ) );
        
        assertThat( expensiveBooks.size(), is( 2 ) );            
    }
}
