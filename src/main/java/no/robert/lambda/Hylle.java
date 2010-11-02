package no.robert.lambda;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

public class Hylle
{
    private List<Bok> boker;
    
    public Hylle()
    {
        boker = new ArrayList<Bok>();
    }

    public void settInn( Bok bok )
    {
        boker.add( bok );
        
    }

    public int getAntall()
    {
        return boker.size();
    }

    public List<Bok> getBokerAv( String forfatter )
    {
        return select( boker, having( on( Bok.class ).getForfatter(), is( forfatter ) ) );
                
        /*
        List<Bok> bøker = new ArrayList<Bok>();
        
        for( Bok b : boker )
            if( b.getForfatter().equals( forfatter ))
                bøker.add(  b );
        
        return bøker;
        */
    }
    
    

    
    

}
