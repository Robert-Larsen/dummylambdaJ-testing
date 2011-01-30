package no.robert.lambda;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
/*
 import org.hamcrest.Matcher;
 import org.hamcrest.Matchers;
 */
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BookDAOTest
{
    BookDAO shelf;
    AuthorDAO authors;

    @Before
    public void setUp()
    {
        shelf = new BookDAO();
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
        assertThat( shelf.getNumberOfBooks(), is( 1 ) );
    } 

    @Test
    @Ignore
    public void remove()
    {   
        Author author1 = new Author( "Someone" );
        authors.add( author1 );
        shelf.add( new Book( "A new book", author1, 100 ) );
        int numberofbooks = shelf.getNumberOfBooks();
        Book book = shelf.getBook( "A new book" );
               
        shelf.remove( book );

        assertThat( shelf.getNumberOfBooks(), is( numberofbooks-1 ) );        
    }
    
    
}
