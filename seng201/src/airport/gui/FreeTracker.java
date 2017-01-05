package airport.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendarCombo;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import airport.BillableEntity;
import airport.EconoParkingLot;
import airport.ExpressParkingLot;
import airport.Parkable;
import airport.ParkingSystem;
import airport.ShortStayParkingLot;
import airport.Vehicle;

/**
 * A FreeTracker is to work calculator and status jobs together 
 * @author dil15
 *
 */


public class FreeTracker extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1800061803253858480L;

	/**
	 * Registration number to charge
	 */
	private JTextField jtr;

	private ParkingSystem company = new ParkingSystem();

	MyDateListener arriveListener = new MyDateListener();
	MyDateListener departListener = new MyDateListener();
	
	
	// Endless messing around with dates
		private PeriodFormatter pfmt = PeriodFormat.getDefault();
		
		private static DateFormat dfmt = new SimpleDateFormat(
				"HH:mm EEE dd MMM, yyyy");
	/**
	 * Will need to access this elsewhere
	 */
	private JComboBox<Parkable> jcb;
	
	private JTextField jtf;
	
	private JTextField jtc;
	
	
	private JCalendarCombo arriveTime;
	private JCalendarCombo departTime;

	/**
	 * Big red buttons to process transaction details
	 */
	private JButton chargeButton = new JButton("Charge");
	private JButton admitButton = new JButton("Admit");
	private JButton paidButton = new JButton("Paid");
	private JButton reportButton = new JButton("Report");
	private JButton clearButton = new JButton("Clear the transcript");
	
	
	
	
	// Some labels
	private final JLabel location = new JLabel("Location:");
	private final JLabel capacity = new JLabel("Capacity:");
	private final JLabel occupied = new JLabel("Occupancy:");
	private final JLabel available = new JLabel("Available:");
	private final JLabel jlr = new JLabel("Reg. No:");
	final SimpleLogPanel transcript = new SimpleLogPanel();

	
	
	
	
	
	/**
	 * Constructor for class.
	 */
	public FreeTracker() {
		super();
		company.addLot(new ShortStayParkingLot("Short Stay Lot #1", 50));
		company.addLot(new ShortStayParkingLot("Short Stay Lot #2", 25));
		company.addLot(new ExpressParkingLot("Express Lot #1", 100));
		company.addLot(new EconoParkingLot("EconoParkingLot #1", 100));

		jcb = new JComboBox<Parkable>();
		
		transcript.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(transcript);

		for (Parkable p : company.lots()) {
			jcb.addItem(p);
		}
		
		add(buildOccupancyStatus());
		add(buildCalculator());
		
	}
	
	/**
	 * Arrange the various bits and pieces.
	 */
	private JPanel buildOccupancyStatus() {
	
		JPanel jcbp = new JPanel();
		JLabel jcbl = new JLabel("Parking Type:");
		((FlowLayout) jcbp.getLayout()).setAlignment(FlowLayout.RIGHT);
		jcbp.add(jcbl);
		jcbp.add(jcb);
		jcbp.setBorder(new EtchedBorder());

		JPanel statusPanel = new JPanel();
		JPanel statusInfoPanel = new JPanel(new GridLayout(4, 1));
		statusInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(jcbp);

		// And whose reg it's for
		JPanel jpr = new JPanel();
		jtr = new JTextField(8);
		jpr.add(jlr);
		jpr.add(jtr);
		
		jtr.requestFocusInWindow();
	
		jcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtr.requestFocusInWindow();	
				updateDisplayInfo();
			}
		});

		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		
		statusInfoPanel.add(location);
		statusInfoPanel.add(capacity);
		statusInfoPanel.add(occupied);
		statusInfoPanel.add(available);

		statusPanel.add(statusInfoPanel);
		statusPanel.add(jpr);
		
		JPanel buttons = new JPanel();
		buttons.add(admitButton);
		buttons.add(reportButton);
		buttons.add(clearButton);
		
		statusPanel.add(buttons);


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
				
		

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit application?", null, JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						System.exit(0);
						
					}
					

				}
				if(e.getKeyCode() == KeyEvent.VK_ALT){
					Parkable pk = (Parkable) jcb.getSelectedItem();

					transcript.log(pk.toString() + "contains:");
					
					transcript.txt.setText("");
					

				}
						

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
			
		});



		admitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vehicle v = company.vehicleFor(jtr.getText());
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

		});
		
		
		

		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Parkable pk = (Parkable) jcb.getSelectedItem();
				jtr.requestFocusInWindow();	

				transcript.log(pk.toString() + "contains:");
				for(Vehicle v: pk.occupants()){	

				transcript.log(v.toString());


				}
			

			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Parkable pk = (Parkable) jcb.getSelectedItem();
				jtr.requestFocusInWindow();	

				transcript.log(pk.toString() + "contains:");
				

				transcript.txt.setText("");
				

	
				
			

			}
		});

		return statusPanel;

	}
	
	
	private JPanel buildCalculator() {

		
		JPanel calculatorPanel = new JPanel();

		

		// Assemble the arrival time elements
		arriveTime = new JCalendarCombo(JCalendarCombo.DISPLAY_DATE
				| JCalendarCombo.DISPLAY_TIME, true);

		arriveTime.setEditable(false);
		arriveTime.setDateFormat(dfmt);
		arriveTime.addDateListener(arriveListener);

		JPanel arr = new JPanel();
		JLabel jla = new JLabel("Arrival Time:");

		// Assemble the departure time elements
		departTime = new JCalendarCombo(JCalendarCombo.DISPLAY_DATE
				| JCalendarCombo.DISPLAY_TIME, true);

		departTime.setEditable(false);
		departTime.setDateFormat(dfmt);
		departTime.addDateListener(departListener);

		JPanel dep = new JPanel();
		JLabel jld = new JLabel("Departure Time:");

		// Place for the interval
		JPanel jpi = new JPanel();
		JLabel jli = new JLabel("Duration:");
		jtf = new JTextField(30);
		jtf.setEditable(false);

		// Place for the charge
		JPanel jpc = new JPanel();
		JLabel jlc = new JLabel("Charge:");
		jtc = new JTextField(15);
		jtc.setEditable(false);


		// Assemble the whole mess
		calculatorPanel.setLayout(new BoxLayout(calculatorPanel,
				BoxLayout.Y_AXIS));

		

		((FlowLayout) arr.getLayout()).setAlignment(FlowLayout.RIGHT);
		arr.add(jla);
		arr.add(arriveTime);
		arr.setBorder(new EtchedBorder());
		calculatorPanel.add(arr);

		((FlowLayout) dep.getLayout()).setAlignment(FlowLayout.RIGHT);
		dep.add(jld);
		dep.add(departTime);
		dep.setBorder(new EtchedBorder());
		calculatorPanel.add(dep);

		((FlowLayout) jpi.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpi.add(jli);
		jpi.add(jtf);
		jpi.setBorder(new EtchedBorder());
		calculatorPanel.add(jpi);

		// Reg and charge on same row
		JPanel jpcr = new JPanel();
		((FlowLayout) jpcr.getLayout()).setAlignment(FlowLayout.RIGHT);

		((FlowLayout) jpi.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpc.add(jlc);
		jpc.add(jtc);

		jpc.setBorder(new EtchedBorder());

		jpcr.add(jpc);
		calculatorPanel.add(jpcr);

		JPanel buttons = new JPanel();
		buttons.add(chargeButton);
		buttons.add(paidButton);
		calculatorPanel.add(buttons);
		
	
	
		chargeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(jtr.getText().equals("")) {
					// reg field is empty
					JOptionPane.showMessageDialog(null, "Please enter the registration number", "Missing Reg",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				jtr.requestFocusInWindow();		
				Parkable pk = (Parkable) jcb.getSelectedItem();
				Vehicle v = company.vehicleFor(jtr.getText());
				pk.admit(v);
				updateCharge();
			}

		});
		
		paidButton.addActionListener(new ActionListener() {// This is the same as Checkout button 
			public void actionPerformed(ActionEvent e) {
				Parkable pk = (Parkable) jcb.getSelectedItem();
				pk.release(company.vehicleFor(jtr.getText()));
				jtr.requestFocusInWindow();	
				updateDisplayInfo();
			}
	
		});


jtr.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_SHIFT){
					updateCharge();
						
					}
				
				if (e.getKeyCode() == KeyEvent.VK_CONTROL){
					Parkable pk = (Parkable) jcb.getSelectedItem();
					
					
					transcript.log(pk.toString() + "contains:");
					for(Vehicle v: pk.occupants()){
						DateTime from = new DateTime(arriveTime.getDate());
						DateTime to = new DateTime(departTime.getDate());
						Money mon = pk.computeCharge(from, to);
						v.charge(mon);
						transcript.log(v.toString());
						
						
					}
					updateDisplayInfo();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) { }
			
		});
		return calculatorPanel;
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
		
		if (pk.occupancy() == pk.capacity()){
			 JOptionPane.showMessageDialog(null, "This Parking Lot is full", "Notice",
					JOptionPane.WARNING_MESSAGE);
			 return ;
	}
		
}
	/**
	 * Refresh the charge fields
	 */
	private void updateCharge() {
		jtc.setText(jcb.getSelectedItem() + " for " + jtf.getText());


		DateTime from = new DateTime(arriveTime.getDate());
		DateTime to = new DateTime(departTime.getDate());
		Parkable pk = (Parkable) jcb.getSelectedItem();
		Money mon = pk.computeCharge(from, to);

		pk.computeCharge(from, to);	
		Vehicle v = company.vehicleFor(jtr.getText());
		v.charge(mon);
		jtc.setText(pk.computeCharge(from, to).toString());
		System.out.println(jcb.getSelectedItem() + " for " + jtf.getText()
				+ " costs " + mon);

	}
	/**
	 * Refresh the duration fields
	 */
	private void updateDuration() {
		DateTime from = new DateTime(arriveTime.getDate());
		DateTime to = new DateTime(departTime.getDate());
		Period p = new Period(from, to);

		if (DateTimeComparator.getInstance().compare(from, to) >= 0) {
			JOptionPane.showMessageDialog(null,
					"Can't leave before you arrive!", "Negative time interval",
					JOptionPane.WARNING_MESSAGE);
			jtc.setText(Money.parse("NZD 0.00").toString());
			System.out.println("Difference violates causality, ignoring : ");
			
		} else {
			// All's well
			jtf.setText(p.toString(pfmt));
			updateCharge();
			
		}
	}
	
	class MyDateListener implements DateListener {
		String origin = "Unknown: ";
		

		public void dateChanged(DateEvent de) {
			
			Calendar c = de.getSelectedDate();
			

			if (c != null) {
				//System.out.println(c.getTime());
			} else {
				System.out.println("No time selected.");
			}
			
			updateDuration();

		}
		
	}



}
