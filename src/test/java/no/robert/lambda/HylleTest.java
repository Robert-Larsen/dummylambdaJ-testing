package no.robert.lambda;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;


public class HylleTest
{
    
    @Test
    public void settInn()
    {
        Hylle hylle = new Hylle();
        hylle.settInn( new Bok() );
        
        assertThat( hylle.getAntall(), is( 1 ) );
    }

    @Test
    public void skalHenteUtBøkerMedGittForfatter()
    {
        Hylle hylle = new Hylle();
        hylle.settInn( new Bok( "Forfatter1" ) );
        hylle.settInn( new Bok( "Forfatter1" ) );
        hylle.settInn( new Bok( "Forfatter2" ) );
        
        assertThat( hylle.getBokerAv( "Forfatter1" ).size(), is( 2 )  );
    }
}
