import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gluecksrad
{
  // Attribute
  public static int WIDTH = 1280;
  public static int HEIGHT = 720;
  
  private JFrame frame;
  private ControllerPanel panel;
  
  private Timer timer;
  private static int timerIntervall = 12;
  
  public boolean anstoss;
  
  // Konstruktor
  public Gluecksrad( int pWert )
  {
    this.anstoss = false;
  
    this.erstelleGUI( pWert );
    this.erstelleKeyBindings();
    this.erstelleTimer();
    
    JOptionPane.showMessageDialog( null, "G zum Drehen" );
  }
  
  /*
   * Methoden
   */ 
  public void updateLogik()
  {
    this.panel.requestFocus();
  
    if ( this.anstoss == true )
    {
      this.panel.anstossen();
      
      this.anstoss = false;
    } 
  
    this.panel.updateLogik();
        
    this.panel.repaint();
  } 
   
  private void erstelleTimer()
  {
    this.timer = new Timer( Gluecksrad.timerIntervall, new AbstractAction()
    {
      @Override
      public void actionPerformed( ActionEvent a )
      {
        updateLogik();
      }
    } );
    this.timer.setInitialDelay( Gluecksrad.timerIntervall );
    this.timer.start();
  } 
   
  private void erstelleKeyBindings()
  {
    InputMap iM = this.panel.getInputMap();
    ActionMap aM = this.panel.getActionMap();
    
    iM.put( KeyStroke.getKeyStroke( KeyEvent.VK_G, 0, true ), "gRelease" );
    
    aM.put( "gRelease", new AbstractAction()
    {
      @Override
      public void actionPerformed( ActionEvent a )
      {
        anstoss = true;
      }
    } );
  } 
   
  private void erstelleGUI( int pWert )
  {
    this.frame = new JFrame( "Glücksrad" );
    this.frame.setSize( Gluecksrad.WIDTH, Gluecksrad.HEIGHT );
    this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    this.frame.setLocationRelativeTo( null );
    this.frame.setResizable( false );
    
    this.panel = new ControllerPanel( pWert );
    this.panel.setBackground( Color.WHITE );
    this.frame.getContentPane().add( this.panel );
    
    this.frame.setVisible( true );
  }
}