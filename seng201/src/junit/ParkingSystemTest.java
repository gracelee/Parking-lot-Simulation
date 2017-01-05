package junit;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import airport.LongStayParkingLot;
import airport.Parkable;
import airport.ParkingSystem;
import airport.ShortStayParkingLot;
import airport.Vehicle;

public class ParkingSystemTest {
	
	private ParkingSystem ps;
	private ArrayList<Parkable>  parkingLots = new ArrayList<Parkable>();
	private ShortStayParkingLot sho = new ShortStayParkingLot("Short Stay Lot #1", 2);;
	private LongStayParkingLot lon =new LongStayParkingLot("Long Stay Lot #1", 2);;



	@Before
	public void setUp() throws Exception {
		ps = new ParkingSystem();
		ps.addLot(new ShortStayParkingLot("Short Stay Lot #1", 2));

	}

	@Test
	public void testAddLot() {
		parkingLots.add(new ShortStayParkingLot("Short Stay Lot #1", 2));
		assertTrue(parkingLots.size() == 1);
		parkingLots.add(new ShortStayParkingLot("Short Stay Lot #1", 2));
		assertTrue(parkingLots.size() == 2);
		
	}


	@Test
	public void testInLots() {
		Vehicle car = new Vehicle("123");
		ps.addLot(new ShortStayParkingLot("Short Stay Lot #1", 2));
		ps.addLot(new LongStayParkingLot("Long Stay Lot #1", 2));
		sho.admit(car);
		lon.admit(car);
		assertTrue(ps.inLot(car) == false);
		
		
	}
	
	@Test
	public void testVehicleFor() {
		ps.vehicleFor("123");
		Vehicle car = ps.vehicleFor("123");
		Vehicle car2 = ps.vehicleFor("123");
		assertSame(car,car2);
	}

}
