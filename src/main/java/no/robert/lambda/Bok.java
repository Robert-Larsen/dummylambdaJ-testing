package no.robert.lambda;

public class Bok
{
    
    private String tittel;
    private String forfatter;
    private int antallSider;
    
    public Bok( String forfatter )
    {
        this.forfatter = forfatter;

    }

    public Bok()
    {
        // TODO Auto-generated constructor stub
    }

    public String getForfatter()
    {
        return this.forfatter;
    }

}
