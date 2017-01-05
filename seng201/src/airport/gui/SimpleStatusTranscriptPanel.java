package airport.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import airport.BillableEntity;
import airport.EconoParkingLot;
import airport.ExpressParkingLot;
import airport.Parkable;
import airport.ParkingSystem;
import airport.ShortStayParkingLot;
import airport.Vehicle;

/**
 * A SimpleStatusPanel that can also show details of vehicles here.
 * @author dil15
 *
 */
public class SimpleStatusTranscriptPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1800061803253858480L;

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
	private JButton reportButton = new JButton("Report");
	
	// Some labels
	private final JLabel location = new JLabel("Location:");
	private final JLabel capacity = new JLabel("Capacity:");
	private final JLabel occupied = new JLabel("Occupancy:");
	private final JLabel available = new JLabel("Available:");
	private final JLabel jlr = new JLabel("Reg. No:");
	
	/**
	 * Constructor for class.
	 */
	public SimpleStatusTranscriptPanel() {
		super();
		company.addLot(new ShortStayParkingLot("Short Stay Lot #1", 2));
		company.addLot(new ShortStayParkingLot("Short Stay Lot #2", 25));
		company.addLot(new ExpressParkingLot("Express Lot #1", 100));
		company.addLot(new EconoParkingLot("EconoParkingLot #1", 100));

		jcb = new JComboBox<Parkable>();
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
		
		statusInfoPanel.add(location);
		statusInfoPanel.add(capacity);
		statusInfoPanel.add(occupied);
		statusInfoPanel.add(available);

		statusPanel.add(statusInfoPanel);
		
		statusPanel.add(jpr);
		
		JPanel buttons = new JPanel();
		buttons.add(admitButton);
		buttons.add(checkoutButton);
		buttons.add(reportButton);
		statusPanel.add(buttons);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final SimpleLogPanel transcript = new SimpleLogPanel();
		transcript.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(transcript);
		
		
jtr.addKeyListener(new KeyListener(){
			
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
					JFrame frame = new JFrame("InputDialog Example #1");
					
					Parkable pk = (Parkable) jcb.getSelectedItem();
					int numParksAvalibale = pk.capacity() - pk.occupancy();
					Vehicle v = company.vehicleFor(jtr.getText());
					if(numParksAvalibale == 0){
						if(company.inLot(v)){
							JOptionPane.showMessageDialog(null, "This Parking Lot is full and Already parked "
									+ "\n Please park your car at other parking lots","Notice",
									JOptionPane.WARNING_MESSAGE);
							
						}else
						JOptionPane.showMessageDialog(null, "This Parking Lot is full. Please park your car at other parking lots ","Notice", 
								JOptionPane.WARNING_MESSAGE);
						
						return;
					}else{
						if(!company.inLot(v)){
							pk.admit(v);
							if (!v.hasOwner()){
								String name = JOptionPane.showInputDialog(frame, "What's your name?");
								String where = JOptionPane.showInputDialog(frame, "What's your address?");
								if (name == null || where == null){
									v.setOwner(null);
									
								}else
									if (!name.isEmpty() && !where.isEmpty()) {
										v.setOwner(new BillableEntity(name, where));
									} else {
										v.setOwner(null);
									}
								}
				
						}else{
							JOptionPane.showMessageDialog(null, "Already parked","Notice",
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
				
				if (e.getKeyCode() == KeyEvent.VK_CONTROL){
					Parkable pk = (Parkable) jcb.getSelectedItem();
					transcript.log(pk.toString() + "contains:");
					for(Vehicle v: pk.occupants()){
						transcript.log(v.toString());
					}
					updateDisplayInfo();
				}
				
				
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit application?", null, JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						System.exit(0);
						
					}
				
					
				}
				
				
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
				
				
				// TODO Auto-generated method stub
				
			}
			
		});

		
		admitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();
				if(jtr.getText().equals("")) {
					// reg field is empty
					JOptionPane.showMessageDialog(null, "Please enter the registration number", "Missing Reg",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				JFrame frame = new JFrame("InputDialog Example #1");
				
				Parkable pk = (Parkable) jcb.getSelectedItem();
				int numParksAvalibale = pk.capacity() - pk.occupancy();
				if(numParksAvalibale == 0){
					
					Vehicle v = company.vehicleFor(jtr.getText());
					if(company.inLot(v)){
						JOptionPane.showMessageDialog(null, "This Parking Lot is full and Already parked "
								+ "\n Please park your car at other parking lots","Notice",
								JOptionPane.WARNING_MESSAGE);
						
					}else
					JOptionPane.showMessageDialog(null, "This Parking Lot is full. Please park your car at other parking lots ","Notice", 
							JOptionPane.WARNING_MESSAGE);
					
					return;
				}else{
					Vehicle v = company.vehicleFor(jtr.getText());
					if(!company.inLot(v)){
						pk.admit(v);
						if (!v.hasOwner()){
							String name = JOptionPane.showInputDialog(frame, "What's your name?");
							String where = JOptionPane.showInputDialog(frame, "What's your address?");
							if (name == null || where == null){
								v.setOwner(null);
								
							}else
								if (!name.isEmpty() && !where.isEmpty()) {
									v.setOwner(new BillableEntity(name, where));
								} else {
									v.setOwner(null);
								}
							}
			
					}else{
						JOptionPane.showMessageDialog(null, "Already parked","Notice",
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
		
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();
				Parkable pk = (Parkable) jcb.getSelectedItem();
				transcript.log(pk.toString() + "contains:");
				for(Vehicle v: pk.occupants()){
					transcript.log(v.toString());
				}
				updateDisplayInfo();
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
}

