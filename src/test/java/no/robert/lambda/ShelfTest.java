package no.robert.lambda;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
/*
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
*/
import org.junit.Test;

public class ShelfTest
{

    @Test
    public void emptyShelf()
    {
        Shelf shelf = new Shelf();
        assertThat( shelf.getNumberOfBooks(), is( 0 ) );
    }

    @Test
    public void add()
    {
        Shelf shelf = new Shelf();
        shelf.add( new Book( "Something", "Someone", 1 ) );
        shelf.add( new Book( "Something else", "Someone else", 2 ) );

        assertThat( shelf.getNumberOfBooks(), is( 2 ) );
    }

    @Test
    public void remove()
    {
        Shelf shelf = new Shelf();
        Book book = new Book( "Something", "Someone", 1 );
        shelf.add( book );
        
        assertThat( shelf.getNumberOfBooks(), is( 3 ) );

        shelf.remove( book );

        assertThat( shelf.getNumberOfBooks(), is( 2 ) );
    }
}
