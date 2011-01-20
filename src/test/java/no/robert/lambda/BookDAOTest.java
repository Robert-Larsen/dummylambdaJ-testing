package no.robert.lambda;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
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

    @Before
    public void setUp()
    {
        shelf = new BookDAO();
    }

    @Test
    public void emptyShelf()
    {
        assertThat( shelf.getNumberOfBooks(), is( 0 ) );
    }

    @Test
    @Ignore
    public void add()
    {
        Set<Author> authors = new HashSet<Author>();
        authors.add( new Author( "Someone" ) );
        authors.add( new Author( "Someone else" ) );
        shelf.add( new Book( "Something", authors, 1 ) );

        shelf.add( new Book( "Something else", "Another author", 2 ) );

        assertThat( shelf.getNumberOfBooks(), is( 2 ) );
    }

    @Test
    @Ignore
    public void remove()
    {
        Book book = new Book( "Something", "Someone", 1 );
        shelf.add( book );

        assertThat( shelf.getNumberOfBooks(), is( 3 ) );

        shelf.remove( book );

        assertThat( shelf.getNumberOfBooks(), is( 2 ) );
    }
}
