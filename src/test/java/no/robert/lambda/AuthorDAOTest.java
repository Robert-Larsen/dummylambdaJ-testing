package no.robert.lambda;

import static no.robert.lambda.LambdaCriteria.having;
import static no.robert.lambda.LambdaCriteria.on;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import no.robert.lambda.Author;
import no.robert.lambda.AuthorDAO;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorDAOTest
{
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
        
        authors = new AuthorDAO();
        authors.setEntityManagerFactory( entityMgrFactory );
        authors.setEntityManager(  entityManager );
        authors.getEntitManager().getTransaction().begin();
    }
    
    @After
    public void tearDown()
    {
        authors.getEntitManager().getTransaction().rollback();
    }
    
    @Test
    public void emptyDAO()
    {
        assertThat( authors.getNumberOfAuthors(), is( 0 ) );
    }

    @Test
    public void add()
    {        
        authors.add( new Author( "Some author" ) );
        authors.add(  new Author( "Some other author" ) );
        assertThat( authors.getNumberOfAuthors(), is( 2 ) );        
    }
    
    @Test
    public void get()
    {
        Author author = new Author( "An author" );
        authors.add( author );
        
        Author theAuthor = authors.getAuthor( "An author" );
        
        assertThat( theAuthor.getName(), is( "An author" ) );
    }

    @Test
    public void remove()
    {
        int numberofauthors = authors.getNumberOfAuthors();
        Author author = new Author( "Another" );
        authors.add( author );
        assertThat( authors.getNumberOfAuthors(), is( numberofauthors+1 ) );
        
        authors.remove( author );
        assertThat( authors.getNumberOfAuthors(), is( numberofauthors ) );
    }
    
    @Test
    public void lambdaTest()
    {
        Author author = new Author( "Someone" );
        authors.add( author );
        
        List<Author> authors = repository.find( having(Author.class, on( Author.class ).getName() ).eq( "Someone" ) );
        assertThat( authors.size(), is( 1 ) );
    }

}
