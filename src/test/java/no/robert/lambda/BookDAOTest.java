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
    
    
}
