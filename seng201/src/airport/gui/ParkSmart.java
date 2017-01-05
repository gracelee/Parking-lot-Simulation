package airport.gui;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.freixas.jcalendar.*;
import airport.Advisor;




// ParkSmart is to display the cheapest parking lot. 
@SuppressWarnings("serial")
public class ParkSmart extends JPanel {

	/**
	 * Date listeners for calendars
	 */

	MyDateListener arriveListener = new MyDateListener();
	MyDateListener departListener = new MyDateListener();

	/**
	 * The choosers for arrival and departure info
	 */
	private JCalendarCombo arriveTime;
	private JCalendarCombo departTime;

	/**
	 * The duration we'll be charging for. Computed from arrive/depart times or
	 * just entered
	 */
	private JTextField jtf;

	
	
	/**
	 * The charge we computed goes here
	 */
	private JTextField jte;
	private JTextField jtex;
	private JTextField jts;
	private JTextField jtl;
	
	// Endless messing around with dates
	private PeriodFormatter pfmt = PeriodFormat.getDefault();
	
	private static DateFormat dfmt = new SimpleDateFormat(
			"HH:mm EEE dd MMM, yyyy");


	private Advisor ads = new Advisor();
	public Map<String, Money> testMap = new HashMap<String, Money>();

	/**
	 * Big red buttons to process transaction details
	 */
	

	
	/**
	 * Constructor for class.
	 */
	public ParkSmart() {
		super();

		add(buildCalculator());
	}

	
	/**
	 * Arrange the various bits and pieces.
	 */
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
		jtf = new JTextField(64);
		jtf.setEditable(false);

		// Place for the charge
		JPanel jpc = new JPanel();
		JLabel jlc = new JLabel("Short Stay:");
		jts = new JTextField(20);
		jts.setEditable(false);
		
		JPanel jpp = new JPanel();
		JLabel jle = new JLabel("EconoPark:");
		jte = new JTextField(20);
		jte.setEditable(false);
		
		JPanel jpp1 = new JPanel();
		JLabel jlex = new JLabel("Express Park:");
		jtex = new JTextField(20);
		jtex.setEditable(false);
		
		JPanel jpp2 = new JPanel();
		JLabel jll = new JLabel("Long Stay:");
		jtl = new JTextField(20);
		jtl.setEditable(false);

		// And whose reg it's for
		

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
		
		// parking lots
		
		//Econo Parking lot
		((FlowLayout) jpp1.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpp1.add(jlex);
		jpp1.add(jtex);
		jpp1.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.RED));
		calculatorPanel.add(jpp1);
		
		//Short Stay Parking lot
		((FlowLayout) jpc.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpc.add(jlc);
		jpc.add(jts);
		jpc.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.RED));
		calculatorPanel.add(jpc);
		
		//Long stay parking lot
		((FlowLayout) jpp2.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpp2.add(jll);
		jpp2.add(jtl);
		jpp2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.RED));
		calculatorPanel.add(jpp2);
		
		//Short Stay parking lot
		((FlowLayout) jpp.getLayout()).setAlignment(FlowLayout.RIGHT);
		jpp.add(jle);
		jpp.add(jte);
		jpp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.RED));
		calculatorPanel.add(jpp);
		

		
		return calculatorPanel;
	}

	/**
	 * Make sure the departure time is after the arrival time etc
	 */
	private void updateDuration() {
		/* Date from = arriveTime.getDate();
		Date to = departTime.getDate();
		DateTime fdt = new DateTime(from);
		DateTime tdt = new DateTime(to);
		Period p = new Period(fdt, tdt);
		*/
		DateTime from = new DateTime(arriveTime.getDate());
		DateTime to = new DateTime(departTime.getDate());
		Period p = new Period(from, to);

		if (DateTimeComparator.getInstance().compare(from, to) >= 0) {
			JOptionPane.showMessageDialog(null,
					"Can't leave before you arrive!", "Negative time interval",
					JOptionPane.WARNING_MESSAGE);
			jtex.setText(Money.parse("NZD 0.00").toString());
			jts.setText(Money.parse("NZD 0.00").toString());
			jtl.setText(Money.parse("NZD 0.00").toString());
			jte.setText(Money.parse("NZD 0.00").toString());
			System.out.println("Difference violates causality, ignoring : ");
		} else {
			// All's well
			updateParkSmart();
			jtf.setText(p.toString(pfmt));
		}
	}

	/**
	 * Make the charge field reflect the currently selected dates
	 */
	private void updateParkSmart() {
		
	
		DateTime from = new DateTime(arriveTime.getDate());
		DateTime to = new DateTime(departTime.getDate());
		
		Map<String, Money> info = ads.compareMoney(from, to);// Calling compareMoney method from Advisor by having Advisor ads
		DefaultHighlighter.DefaultHighlightPainter highlightPainter = // Set highlight default colour as Yellow
		        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		jtex.setText(info.get("Express Parking Lot").toString());
		jts.setText(info.get("Short Stay Parking Lot").toString());
		jtl.setText(info.get("Long Stay Parking Lot").toString());
		jte.setText(info.get("Econo Parking Lot").toString());
		
		arr.add(info.get("Express Parking Lot").getAmountMajorInt());// Convert String to integer data type
		arr.add(info.get("Short Stay Parking Lot").getAmountMajorInt());
		arr.add(info.get("Long Stay Parking Lot").getAmountMajorInt());
		arr.add(info.get("Econo Parking Lot").getAmountMajorInt());
		
		Money money = Money.parse("NZD 0.00");// Intialize money 

		int min = Collections.min(arr);// Get the minimum parking fee
		money = money.plus(min);// Convert int to Money data type

		JTextField ab = new JTextField();
		
	
		
		if (money.toString().equals(jtex.getText())){
			ab = jtex;
			
		}else if (money.toString().equals(jts.getText())){
			ab = jts;
			
		}else if (money.toString().equals(jtl.getText())){
			ab = jtl;
			
		}else if((money.toString().equals(jte.getText()))){
			ab = jte;
			
		}
		
		Highlighter hilite = ab.getHighlighter();// HighLight the minimum money 
	

		try {
			jte.getHighlighter().removeAllHighlights();
			jtl.getHighlighter().removeAllHighlights();
			jts.getHighlighter().removeAllHighlights();
			jtex.getHighlighter().removeAllHighlights();
			
			hilite.addHighlight(0, 10, highlightPainter);

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		

	}
	
	

	/**
	 * Internal class to respond to change events on the date chooser
	 * @author nic11
	 *
	 */
	class MyDateListener implements DateListener {
		String origin = "Unknown: ";

		public void dateChanged(DateEvent de) {
			Calendar c = de.getSelectedDate();

			if (c != null) {
				System.out.println(c.getTime());
			} else {
				System.out.println("No time selected.");
			}

			updateDuration();
		}
	}
}