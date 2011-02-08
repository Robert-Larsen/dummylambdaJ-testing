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

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BookDAOTest
{
    BookDAO shelf;
    AuthorDAO authors;
    private LambdaRepository repository;
    
    

    @Before
    public void setUp()
    {
        EntityManagerFactory entityMgr = Persistence.createEntityManagerFactory( "no.robert.lambda" );
        repository = new LambdaRepository();
        repository.setEntityManagerFactory( entityMgr );
        shelf = new BookDAO();
        shelf.setEntityManagerFactory( entityMgr );
        authors = new AuthorDAO();
    }

    @Test
    public void emptyShelf()
    {
        assertThat( shelf.getNumberOfBooks(), is( 0 ) );
    }

    @Test   
    public void add()
    {
        Author author1 = new Author( "Someone" );
        authors.add(  author1 );
        Author author2 = new Author( "Someone else" );
        authors.add( author2 );
        Set<Author> authorsSet = new HashSet<Author>();
        shelf.add( new Book( "A book", authorsSet, 100 ) );
        
        shelf.add( new Book( "Another book", authorsSet, 50 ) );
        assertThat( shelf.getNumberOfBooks(), is( 2 ) );
    } 
    
    @Test
    public void get()
    {
        Author author = new Author( "An author" );
        authors.add(  author );
        
        shelf.add( new Book( "A book about books", author, 150 ) );
        Book b = shelf.getBook( "A book about books" );
        assertThat( b.getTitle(), is( "A book about books" ) );
    }

    @Test
    public void remove()
    {   
        Author author1 = new Author( "Someone" );
        authors.add( author1 );
        int numberofbooks = shelf.getNumberOfBooks();
        shelf.add( new Book( "A new book", author1, 100 ) );
        assertThat( numberofbooks, is( shelf.getNumberOfBooks()-1 ) );
        Book book = shelf.getBook( "A new book" );
            
        shelf.remove( book );

        assertThat( numberofbooks, is( shelf.getNumberOfBooks() ) );        
    }
    
    @Test
    public void lambdaTest()
    {
        Author author = new Author( "Someone" );
        authors.add( author );
        shelf.add( new Book( "A book", author, 10 ) );
        
        List<Book> books = repository.find( having(Book.class, on( Book.class ).getTitle() ).eq( "A book" ) );
        assertThat( books.size(), is( 1 ) );
    }
    

    
    
}
