import java.io.*;
import javax.swing.*;

public class GlueckAnfang
{
  public static void main( String[] args )
  {
    BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    
    System.out.println( "Geben Sie einen positiven Wert ein, der größer als 2 ist." );
    String str = "";
    try
    {
      str = br.readLine();
    }
    catch ( Exception e )
    {
      System.out.println( "\nERROR\nFEHLER BEIM EINLESEN\nPROGRAMMABBRUCH" );
      System.exit( 0 );
    }
    
    int wert = -1;
    
    try
    {
      wert = Integer.parseInt( str );
    }
    catch( Exception e )
    {
      System.out.println( "\nERROR\nFEHLER BEIM KONVERTIEREN\nPROGRAMMABBRUCH" );
      System.exit( 0 );
    }
    
    if ( wert <= 2 )
    {
      System.out.println( "\nERROR\nZAHL ZU KLEIN\nPROGRAMMABBRUCH" );
      System.exit( 0 );
    } 
    else
    {
      new Gluecksrad( wert );
    } 
  }
}