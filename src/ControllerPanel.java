import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;

public class ControllerPanel extends JPanel
{
  // Attribute
  private Polygon dreieck;
  
  private static int dreieckWeite = 32;
  private static int dreieckHoehe = 32;
  private static int dreieckXOffset = -10;
  
  private Polygon[] kreisTeile;
  private Color[] kreisColors;
  
  private int polygonNumber = 64;
  private static int polygonLength = 256;
  
  private double angle;
  
  private double angleVel;
  
  private static double ANGLEACC = 0.00125d;
  
  // Konstruktor
  public ControllerPanel( int pWert )
  {
    this.polygonNumber = pWert;
  
    this.kreisTeile = new Polygon[ this.polygonNumber ];
    this.kreisColors = new Color[ this.polygonNumber ];
    
    this.erstelleKreis();
    this.erstelleDreieck(); 
    
    // Gluecksrad.WIDTH / 2 - 128 - 10 - 20, Gluecksrad.HEIGHT / 2 - 20, 40, 40
  }
  
  /*
   * Methoden
   */
  public void paintComponent( Graphics g )
  {
    super.paintComponent( g );
    
    BufferedImage bfdImg = new BufferedImage( Gluecksrad.WIDTH, Gluecksrad.HEIGHT, BufferedImage.TYPE_INT_RGB );
    Graphics gImg = bfdImg.getGraphics();
    
    Graphics2D g2D = (Graphics2D) gImg;
    
    // Hintergrund
    gImg.setColor( Color.WHITE );
    gImg.fillRect( 0, 0, Gluecksrad.WIDTH, Gluecksrad.HEIGHT );
    
    // 0|0 zentrieren
    g2D.translate( Gluecksrad.WIDTH / 2, Gluecksrad.HEIGHT / 2 );
    
    // Rad zeichnen
    this.zeichneRad( g2D );
    
    // Cursor zeichnen
    g2D.setColor( Color.BLACK );
    g2D.fillPolygon( this.dreieck );
    
    // Farbselektion zeichnen
    int colorX = (int) ( Gluecksrad.WIDTH / 2 - 10 );
    int colorY = (int) ( Gluecksrad.HEIGHT / 2 );
    
    Color rectColor = new Color( bfdImg.getRGB( colorX, colorY ));
    
    int rectX = -ControllerPanel.polygonLength + ControllerPanel.dreieckXOffset - ControllerPanel.dreieckWeite - 4;
    int rectY = -(int) ( ControllerPanel.dreieckHoehe / 2 );
    
    g2D.setColor( Color.BLACK );
    g2D.fillRect( rectX - 22, rectY - 1, ControllerPanel.dreieckWeite + 23, ControllerPanel.dreieckHoehe + 2 );
    
    g2D.setColor( rectColor );
    g2D.fillRect( rectX - 21, rectY, ControllerPanel.dreieckWeite + 21, ControllerPanel.dreieckHoehe );
    
    // Nummer zeichnen
    g2D.setColor( Color.BLACK );
    
    int farbNummer = -1;
    for ( int i = 0; i < this.polygonNumber; i++ )
    {
      if ( this.kreisColors[ i ].getRGB() == rectColor.getRGB() )
      {
        farbNummer = i + 1;
        break;
      } 
    }
    g2D.setFont( new Font( "arial", Font.BOLD, ControllerPanel.dreieckHoehe ));
    String stringFarbe = "" + farbNummer;                                                        
    g2D.drawString( stringFarbe, (int) ( rectX - (( stringFarbe.length() - 1 ) * g2D.getFont().getSize() * 1/4 )) - 5, -rectY - 4 ); 
    
    // Bild zeichnen
    g.drawImage( bfdImg, 0, 0, null ); 
  }
  
  public void updateLogik()
  {
    this.angle = this.angle + this.angleVel;
    
    if ( this.angleVel > 0 )
    {
      this.angleVel = this.angleVel - ControllerPanel.ANGLEACC;
    } 
    else
    {
      this.angleVel = 0;
    } 
    
    if ( this.angle > ( Math.PI * 2 ))
    {
      this.angle = this.angle - ( Math.PI * 2 );
    } 
  }
  
  public void anstossen()
  {
    if ( this.angleVel == 0 )
    {
      this.angleVel = ( Math.random() / 4 ) + 0.25d;
    } 
  }
  
  private void zeichneRad( Graphics2D g2D )
  {
    AffineTransform transform = g2D.getTransform();
    
    g2D.rotate( this.angle ); 
    
    for ( int i = 0; i < this.polygonNumber; i++ )
    {
      Polygon p = this.kreisTeile[ i ];
      
      g2D.setColor( this.kreisColors[ i ] );
      g2D.fillPolygon( p.xpoints, p.ypoints, 3 );   
    }
    
    g2D.setTransform( transform );
  }
  
  private void erstelleDreieck()
  {
    int[] xPoints = new int[ 3 ];
    int[] yPoints = new int[ 3 ];
    
    xPoints[ 0 ] = -ControllerPanel.polygonLength + ControllerPanel.dreieckXOffset;
    yPoints[ 0 ] = -(int) ( ControllerPanel.dreieckHoehe / 2 );
    
    xPoints[ 1 ] = -ControllerPanel.polygonLength + ControllerPanel.dreieckXOffset;
    yPoints[ 1 ] = (int) ( ControllerPanel.dreieckHoehe / 2 );
    
    xPoints[ 2 ] = ControllerPanel.dreieckWeite - ControllerPanel.polygonLength + ControllerPanel.dreieckXOffset;
    yPoints[ 2 ] = 0;
  
    this.dreieck = new Polygon( xPoints, yPoints, 3 );
  }
  
  private void erstelleKreis()
  {
    for ( double i = 0; i < this.polygonNumber; i = i + 1 )
    {
      int[] xPoints = new int[ 3 ];
      int[] yPoints = new int[ 3 ];
      
      xPoints[ 0 ] = 0;
      yPoints[ 0 ] = 0;
      
      xPoints[ 1 ] = (int) ( Math.sin( Math.toRadians(( i / this.polygonNumber ) * 360 )) * ControllerPanel.polygonLength );
      yPoints[ 1 ] = (int) ( Math.cos( Math.toRadians(( i / this.polygonNumber ) * 360 )) * ControllerPanel.polygonLength );
      
      xPoints[ 2 ] = (int) ( Math.sin( Math.toRadians((( i + 1 ) / this.polygonNumber ) * 360 )) * ControllerPanel.polygonLength );
      yPoints[ 2 ] = (int) ( Math.cos( Math.toRadians((( i + 1 ) / this.polygonNumber ) * 360 )) * ControllerPanel.polygonLength );
    
      this.kreisTeile[ (int) i ] = new Polygon( xPoints, yPoints, 3 );
      
      int r = (int) ( Math.random() * 255 );
      int g = (int) ( Math.random() * 255 );
      int b = (int) ( Math.random() * 255 );
      
      this.kreisColors[ (int) i ] = new Color( r, g, b );
    }
  }
}