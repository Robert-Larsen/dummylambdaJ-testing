package no.robert.lambda;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class AuthorDAOTest
{
    AuthorDAO authorDAO;

    @Before
    public void setUp()
    {
        authorDAO = new AuthorDAO();
    }
    
    @Test
    public void emptyDAO()
    {
        assertThat( authorDAO.getNumberOfAuthors(), is( 0 ) );
    }

    @Test
    public void add()
    {        
        authorDAO.add( new Author( "Some author" ) );
        authorDAO.add(  new Author( "Some other author" ) );
        assertThat( authorDAO.getNumberOfAuthors(), is( 2 ) );        
    }

    @Test
    public void remove()
    {
        Author author = new Author( "Another" );
        authorDAO.add( author );
        assertThat( authorDAO.getNumberOfAuthors(), is( 3 ) );
        
        authorDAO.remove( author );
        assertThat( authorDAO.getNumberOfAuthors(), is( 2 ) );
    }

}
