package airport.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * <p>Provides a <CODE>javax.swing.JFrame</CODE> with a
 * menu bar (<CODE>mb</CODE>) containing a File menu (<CODE>fm</CODE>)
 * with a Quit item and a screen dump item.
 * </p>
 * <p>The easiest way to use this class is to <CODE>add</CODE> your GUI
 * components into the main panel (<CODE>mainPanel</CODE>).  You can also
 * add new menu items and menus.
 * </p>
 * <p>For more complex applications you will probably want to extend this
 * class to provide additional functionality.
 * </p>
 * @author Dain Lee  
 */

public class NosyParker extends JFrame {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The application's menu bar.  You need to know its name in case you
     * decide to add new menus etc later
     */
    protected JMenuBar mb = new JMenuBar();
    /**
     * The File menu.  You need to know its name in case you
     * decide to add new menu items etc later
     */
    protected JMenu fm = new JMenu("File");
    /**
     * This menu item generates a PostScript screen dump which you can
     * print now or save to disc (preferred) for later printing
     */
    protected JMenuItem fmp = new JMenuItem("Screen Dump...");
    /**
     * This menu item kills off the application
     */
    protected JMenuItem fmq = new JMenuItem("Quit -    Esc");
    /**
     * This is the main application area.  You need to know its name so
     * you can create your own components (text areas, buttons etc) and
     * add them in.  Clients will use getContentPane() to add in GUI
     * components as needed.
     */
    protected static JPanel mainPanel = new JPanel();
    // Joff's way is better as it includes allowance for daylight saving
    private static SimpleTimeZone NZTime = 
	new SimpleTimeZone(12*60*60*1000, "NZT",
			   Calendar.OCTOBER, 1, Calendar.SUNDAY,
			   2*60*60*1000,
			   Calendar.MARCH, 3, Calendar.SUNDAY,
			   2*60*60*1000);
    private final JMenu mnShortcut = new JMenu("Shortcut");
    private final JMenuItem mntmAdmitEnter = new JMenuItem("Admit - Enter");
    private final JMenuItem mntmReportCtrl = new JMenuItem("Report - Ctrl");
    private final JMenuItem mntmChargeShift = new JMenuItem("Charge - Shift");
    private final JMenuItem mntmReleaseDelete = new JMenuItem("Checkout(Paid) - Delete");
    private final JMenuItem mntmClearAlt = new JMenuItem("Clear - Alt");
   
    /**
     * The standard <CODE>main</CODE> method creates the frame and contents.
     * A title for the main window may be supplied as a command line argument.
     */
    public static void main(String Args[]) {
	NosyParker me = new NosyParker(Args.length > 0 ?
						     Args[0] : "Christchurch Airport Parking Lot");
	
	me.buildGui();
	
	me.setSize(250,150);
	me.setMinimumSize(new Dimension(250, 150));
	
	me.pack();
	me.setVisible(true);

    }
    
    /**
     * Put together the main panel content as required.
     */
    public void buildGui() {
    	JTabbedPane jtp = new JTabbedPane();
    	jtp.addTab("Status", new SimpleStatusPanel());
    	jtp.addTab("Calculator", new SimpleParkingCalculatorPanel());
    	jtp.addTab("StatusTranscript", new SimpleStatusTranscriptPanel());
    	jtp.addTab("Park Smart", new ParkSmart());
    	jtp.addTab("FreeTracker", new FreeTracker());
    	
   
    	
    	mainPanel.add(jtp);
    	
    }
    
    /*
     * Constructor for class.
     */
    public NosyParker(String title) {
    	super(title);
    	this.setJMenuBar(mb);
    	
    	mb.add(fm);
    	fm.add(fmp);
    	fm.addSeparator();
    	fm.add(fmq);
    	
    	mb.add(mnShortcut);
    	mnShortcut.add(mntmAdmitEnter);
    	
    	mnShortcut.add(mntmReleaseDelete);
    	
    	mnShortcut.add(mntmChargeShift);
    	
    	mnShortcut.add(mntmReportCtrl);
    	
    	mnShortcut.add(mntmClearAlt);
    	

    	// How to print...
    	fmp.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    		dumpAppImage();
    	    }
    	});
    	
    	// How to quit...
    	fmq.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	    int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit the application?", null, JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION){
			dispose();
    		System.exit(0);
    	    }
    	    }
    	});	


    	// When the window manager tries to get rid of us...
    	addWindowListener(new WindowAdapter() {
    	    public void windowClosing(WindowEvent we) {
    		dispose();
    		System.exit(0);
    	    }
    	});
    	
    	getContentPane().add(mainPanel, "Center");

    }
    
    public NosyParker() {
	this("Christchurch Airport Parking Lot");
    }
    
    private void dumpAppImage() {
	PrintJob pj =
	    getToolkit().getPrintJob(this, "Print Application Image",
				    (Properties)null);
	if(pj != null) {
	    Graphics pg = pj.getGraphics();
	    if(pg != null) {
		header(pg); // do last so it stays on top or translate graphics coords?
		printAll(pg);
		pg.dispose();
	    }
	    pj.end();
	}
    }
    private void header(Graphics pg) {
	Font f = new Font("SansSerif", Font.BOLD, 12);
	pg.setFont(f);
	pg.drawString(System.getProperty("user.name"), 20, 50);
	pg.drawString(ourTimeJoffsWay(), 100, 50);
	pg.translate(50, 120);
    }

    @SuppressWarnings("unused")
	private String ourTime() {
	TimeZone here = TimeZone.getDefault();
	here.setRawOffset(12 * 60 * 60 * 1000);
	Calendar now = new GregorianCalendar(here);//was Gregorian

	return now.getTime().toString();
    }

    private String ourTimeJoffsWay() {
	GregorianCalendar now = new GregorianCalendar(NZTime);

	return now.getTime().toString();
    }

    
}
