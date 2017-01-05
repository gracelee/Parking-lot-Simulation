package airport.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import airport.ParkingSystem;
import java.awt.Color;

/**
 * Simple panel to test arrival and departure of vehicles.
 * 
 * @author dil15
 *
 */

// Make another JFrame as a welcome page
@SuppressWarnings("serial")
public class SimpleWelcome extends JFrame{
	
	private String[] st = new String[4];

	/**
	 * Big red buttons to process transaction details
	 */
	private JButton startButton = new JButton("START");
	private JButton exitButton = new JButton("EXIT");
	
	

	protected static JPanel statusPanel = new JPanel();

	public SimpleWelcome(Object object){
		super();
		exitButton.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SimpleWelcome.this.setVisible(false);
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		startButton.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		st = ParkingSystem.parkOptions();
		getContentPane().add(buildOccupancyStatus());
	
		JPanel statusInfoPanel = new JPanel(new GridLayout(4, 1));
		statusInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		

		JPanel jcbp = new JPanel();
		jcbp.setLayout(new BoxLayout(jcbp, BoxLayout.X_AXIS));
		JLabel jcbl = new JLabel("Welcome to Chirstchurch Airport Parking Lot");
		jcbl.setFont(new Font("Lato Black", Font.BOLD | Font.ITALIC, 16));
		jcbl.setIcon(new ImageIcon("C:/home/cosc/student/dil15/Desktop/lab2/lab4/CIAL/src/CarParkingSign.jpg"));
		jcbp.add(jcbl);
		jcbp.setBorder(new EtchedBorder());
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(jcbp);
		statusPanel.setBorder(new EtchedBorder());
		
		JLabel lot1 = new JLabel(st[0] +"  ShortStay Park is most convenient for short trips away and handy for spending time meeting and farewelling travellers.");
		lot1.setForeground(new Color(255, 140, 0));
		lot1.setFont(new Font("URW Bookman L", Font.BOLD, 14));
		JLabel lot2 = new JLabel(st[1] + " Ideal for business or leisure travellers heading away for three days or longer");
		lot2.setForeground(new Color(0, 0, 255));
		lot2.setFont(new Font("URW Bookman L", Font.BOLD, 14));
		JLabel lot3 = new JLabel(st[2] +"  Express Park is also perfectly located for quick pick-ups or drop-offs and the first 15 minutes are free.");
		lot3.setForeground(new Color(255, 0, 0));
		lot3.setFont(new Font("URW Bookman L", Font.BOLD, 14));
		JLabel lot4 = new JLabel(st[3] + "  Cheerful long stay car park is located at 21 Ron Guthrey Road and only 10 minutes' transfer on the free Airport Hopper bus. ");
		lot4.setForeground(new Color(0, 128, 0));
		lot4.setFont(new Font("URW Bookman L", Font.BOLD, 14));
		
		
		statusInfoPanel.add(lot1);
		statusInfoPanel.add(lot2);
		statusInfoPanel.add(lot3);
		statusInfoPanel.add(lot4);

		statusPanel.add(statusInfoPanel);
		
		JPanel buttons = new JPanel();
		buttons.add(startButton);
		buttons.add(exitButton);
		statusPanel.add(buttons);

		
		


	}
	
	/**
     * The standard <CODE>main</CODE> method creates the frame and contents.
     * A title for the main window may be supplied as a command line argument.
     */	
	public static void main(String Args[]) {
		SimpleWelcome me = new SimpleWelcome(Args.length > 0 ?
			     Args[0] : "Welcome");
		
		
		me.setSize(1100,400);
		me.setMinimumSize(new Dimension(250, 150));
		me.setPreferredSize(new Dimension(1100, 400));// hardCoded sizing
		me.setVisible(true);
		me.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Sisgujhnj");
					((SimpleWelcome) e.getSource()).setVisible(false);
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});



	    }

	private JPanel buildOccupancyStatus()  {

		startButton.addActionListener(new ActionListener() {// NosyParker will pop up once the button is clicked
			public void actionPerformed(ActionEvent e) {

				NosyParker me = new NosyParker();

					me.buildGui();
					
					me.setSize(250,150);
					me.setMinimumSize(new Dimension(250, 150));
					
					me.pack();
					me.setVisible(true);

			}
		});
		
		startButton.setEnabled(true);
		exitButton.addActionListener(new ActionListener() {// quit program
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit application?", null, JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION){
					System.exit(0);
					
				}
				
			
			}
		});
		return statusPanel;

	}



	    

}
	
	

	
	
