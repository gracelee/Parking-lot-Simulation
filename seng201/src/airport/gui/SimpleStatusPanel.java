package airport.gui;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


import airport.EconoParkingLot;
import airport.ExpressParkingLot;
import airport.LongStayParkingLot;
import airport.Parkable;
import airport.ParkingSystem;
import airport.ShortStayParkingLot;
import airport.Vehicle;


/**
 * Simple panel to test arrival and departure of vehicles.
 * 
 * @author dil15
 *
 */
@SuppressWarnings("serial")
public class SimpleStatusPanel extends JPanel  {
	
	 
	/**
	 * Registration number to charge
	 */
	private JTextField jtr;

	private ParkingSystem company = new ParkingSystem();
	/** 
	 * Will need to access this elsewhere
	 */
	private JComboBox<Parkable> jcb;

	/**
	 * Big red buttons to process transaction details
	 */
	private JButton admitButton = new JButton("Admit");
	private JButton checkoutButton = new JButton("Checkout");
	private JButton averageButton = new JButton("Occupancy");

	// Some labels
	private final JLabel location = new JLabel("Location:");
	private final JLabel capacity = new JLabel("Capacity:");
	private final JLabel occupied = new JLabel("Occupancy:");
	private final JLabel available = new JLabel("Available:");
	private final JLabel jlr = new JLabel("Reg. No:");
	
	private JTextField jte;
	private JTextField jtex;
	private JTextField jts;
	private JTextField jtl;
	
/**
 * Constructor for this class
 */
	public SimpleStatusPanel() {
		super();
		company.addLot(new ShortStayParkingLot("Short Stay Lot #1", 100));
		company.addLot(new LongStayParkingLot("Long Stay Lot #1", 100));
		company.addLot(new ExpressParkingLot("Express Lot #1", 100));
		company.addLot(new EconoParkingLot("EconoParkingLot #1", 100));

		jcb = new JComboBox<Parkable>();
		/*jcb.addItem(null);*/
		
		for (Parkable p : company.lots()) {
			
			jcb.addItem(p);
		}
		add(buildOccupancyStatus());
		

	}
	
	/**
	 * Arrange the various bits and pieces.
	 */

	private JPanel buildOccupancyStatus() {

		JPanel statusPanel = new JPanel();
		JPanel statusInfoPanel = new JPanel(new GridLayout(4, 1));
		statusInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		// And whose reg it's for
		JPanel jpr = new JPanel();
		jtr = new JTextField(8);
		jpr.add(jlr);
		jpr.add(jtr);
		jtr.addKeyListener(new KeyListener(){// Using KeyListener to enable key functions to work 
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if(jtr.getText().equals("")) {
						// reg field is empty
						JOptionPane.showMessageDialog(null, "Please enter the registration number", "Missing Reg",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					Parkable pk = (Parkable) jcb.getSelectedItem();
					int numParksAvalibale = pk.capacity() - pk.occupancy();
					if(numParksAvalibale == 0){
						JOptionPane.showMessageDialog(null, "This Parking Lot is full", "Notice",
									JOptionPane.WARNING_MESSAGE);
						return ;
						
						
					}else {
						Vehicle v  = company.vehicleFor(jtr.getText());
						if (!company.inLot(v)) {
							pk.admit(v);
						} else {
							JOptionPane.showMessageDialog(null, "Already parked", "Notice",
									JOptionPane.WARNING_MESSAGE);
						}
					}
					updateDisplayInfo();
				}
					
				
				if (e.getKeyCode() == KeyEvent.VK_DELETE){
					Parkable pk = (Parkable) jcb.getSelectedItem();
					pk.release(company.vehicleFor(jtr.getText()));
					updateDisplayInfo();
				}
				
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit application?", null, JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						System.exit(0);
						
					}
				
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
			
		});
		
		jcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();
				updateDisplayInfo();
			}
		});
	


		JPanel jcbp = new JPanel();
		JLabel jcbl = new JLabel("Parking Type:");
		((FlowLayout) jcbp.getLayout()).setAlignment(FlowLayout.RIGHT);
		jcbp.add(jcbl);
		jcbp.add(jcb);
		jcbp.setBorder(new EtchedBorder());
		
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(jcbp);
		statusPanel.setBorder(new EtchedBorder());
		

		
		JPanel jpc = new JPanel();
		JLabel jlc = new JLabel("Short Stay:");
		jts = new JTextField(10);
		jts.setEditable(false);
				
		JPanel jpp = new JPanel();
		JLabel jle = new JLabel("EconoPark:");
		jte = new JTextField(10);
		jte.setEditable(false);
				
		JPanel jpp1 = new JPanel();
		JLabel jlex = new JLabel("Express Park:");
		jtex = new JTextField(10);
		jtex.setEditable(false);
				
		JPanel jpp2 = new JPanel();
		JLabel jll = new JLabel("Long Stay:");
		jtl = new JTextField(10);
		jtl.setEditable(false);
				
		
		
		
		statusInfoPanel.add(location);
		statusInfoPanel.add(capacity);
		statusInfoPanel.add(occupied);
		statusInfoPanel.add(available);

		statusPanel.add(statusInfoPanel);
		
		statusPanel.add(jpr);
		
		JPanel buttons = new JPanel();
		buttons.add(admitButton);
		buttons.add(checkoutButton);
		buttons.add(averageButton);
		statusPanel.add(buttons);
		
		
		//Econo Parking lot
		((FlowLayout) jpp1.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpp1.add(jlex);
		jpp1.add(jtex);
		jpp1.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.RED));
		statusPanel.add(jpp1);
				
		//Short Stay Parking lot
		((FlowLayout) jpc.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpc.add(jlc);
		jpc.add(jts);
		jpc.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.RED));
		statusPanel.add(jpc);
				
		//Long stay parking lot
		((FlowLayout) jpp2.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpp2.add(jll);
		jpp2.add(jtl);
		jpp2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.RED));
		statusPanel.add(jpp2);
				
		//Short Stay parking lot
		((FlowLayout) jpp.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpp.add(jle);
		jpp.add(jte);
		jpp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.RED));
		statusPanel.add(jpp);
	
		admitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();
				if(jtr.getText().equals("")) {
					// reg field is empty
					JOptionPane.showMessageDialog(null, "Please enter the registration number", "Missing Reg",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Parkable pk = (Parkable) jcb.getSelectedItem();
				int numParksAvailable = pk.capacity() - pk.occupancy();
				// The number of available parking space is empty 
				if(numParksAvailable == 0){
					JOptionPane.showMessageDialog(null, "This Parking Lot is full", "Notice",
								JOptionPane.WARNING_MESSAGE);
					return ;
					
					
				}else {// otherwise, check if any car is already parked
					Vehicle v  = company.vehicleFor(jtr.getText());
					if (!company.inLot(v)) {// Check if a car is already parked in the other parking lots 
						pk.admit(v);// Check if a car is already parked in the current parking lot
					} else {
						JOptionPane.showMessageDialog(null, "Already parked", "Notice",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				updateDisplayInfo();
			}
		});
		
		checkoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();
				Parkable pk = (Parkable) jcb.getSelectedItem();
				pk.release(company.vehicleFor(jtr.getText()));
				updateDisplayInfo();
			}
		});
		
		
		averageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();

				updateAverage();
				

		}
	});
		return statusPanel;

	}
	
	/**
	 * Refresh the display fields
	 */
	private void updateDisplayInfo() {
		Parkable pk = (Parkable) jcb.getSelectedItem();
		location.setText("Location: " + pk.toString());
		capacity.setText("Capacity: " + pk.capacity());
		occupied.setText("Occupied: " + pk.occupancy());
		available.setText("Available: "
				+ (pk.capacity() - pk.occupancy()));
		

		
	}
	
	/**
	 * Refresh the occupancy fields
	 */
	private void updateAverage() {
		// Getting an average occupancy percentage of each parking lot

		double avgSh = 0;
		double avgLong = 0;
		double avgEx = 0;
		double avgEc = 0;
		
		// Using switch to convert p with  with the matching parking lot 

		for (int i = 0; i < jcb.getItemCount(); i++) {
			Parkable p = jcb.getItemAt(i);

			switch (p.getClass().getName()) {
				case "airport.ShortStayParkingLot":
					if (avgSh > 0) {
						avgSh = (avgSh + p.occupancy()/p.capacity()) / 2;
					} else {
						avgSh = (double) p.occupancy()/p.capacity();
					}
					break;
					
				case "airport.LongStayParkingLot":
					if (avgLong > 0) {
						avgLong = (avgLong + p.occupancy()/p.capacity()) / 2;
					} else {
						avgLong = (double) p.occupancy()/p.capacity();
					}
					break;
					
				case "airport.ExpressParkingLot":
					if (avgEx > 0) {
						avgEx = (avgEx + p.occupancy()/p.capacity()) / 2;
					} else {
						avgEx = (double) p.occupancy()/p.capacity();
					}
					break;
				case "airport.EconoParkingLot":
					if (avgEc > 0) {
						avgEc = (avgEc + p.occupancy()/p.capacity()) / 2;
					} else {
						avgEc = (double) p.occupancy()/p.capacity();
					}
					break;
				
					
			}
		}
		
		/*Using wrapper class to convert double to to String because JTextField takes 
		String as its parameter*/		
		jtex.setText(Double.toString((double) Math.round(avgEx * 100)) + "%");
		jts.setText(Double.toString((double)Math.round( avgSh * 100)) + "%");
		jtl.setText(Double.toString((double) Math.round(avgLong * 100)) + "%");
		jte.setText(Double.toString((double) Math.round(avgEc * 100)) + "%");
		


	}
}