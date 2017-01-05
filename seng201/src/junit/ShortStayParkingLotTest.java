package junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import airport.ShortStayParkingLot;
import airport.Vehicle;

public class ShortStayParkingLotTest {
	private ShortStayParkingLot testShortStayParkingLot;
	private Vehicle vechCar = new Vehicle("Test Car");
	private Vehicle vechCar1 = new Vehicle("Test Car");
	private ShortStayParkingLot lot;
	


	@Before
	public void setUp() throws Exception {
		lot = new ShortStayParkingLot("", 3);	

	}
	
	@Test
	public void testEconoParkingLot() {
		
		String testName = "test";
		int testInt = 2;
		testShortStayParkingLot = new ShortStayParkingLot(testName,testInt);
		assertEquals(testInt, testShortStayParkingLot.capacity());

	}
	
	@Test
	public void testAdmit() {
	
		assertTrue(lot.occupants().size() == 0);
		lot.admit(vechCar);
		assertTrue(lot.occupants().size()==1);
		assertTrue(lot.occupants().contains(vechCar));
		lot.admit(vechCar1);
		assertTrue(lot.occupants().size()==1);
		lot.release(vechCar);
		for(int i = 0; i < 10; ++i ){
			lot.admit(new Vehicle(Integer.toString(i)));
		}
		assertEquals(lot.occupancy(), 10);// Check if the number of vehicles are 10
		assertFalse(vechCar1 == vechCar);
		assertTrue(vechCar1.equals(vechCar));
		
		
	
	}
	
	@Test
	public void testRelease() {
		
		assertTrue( lot.occupants().size() == 0);
		lot.release(vechCar);
		assertFalse(lot.occupants().contains(vechCar));
		for(int i = 0; i < 10; ++i ){
			lot.admit(new Vehicle(Integer.toString(i)));
		}
		assertEquals(lot.occupancy(), 10);// Check if the number of vehicles are 10
		
		for(int i = 0; i < 10; ++i ){
			lot.release(new Vehicle(Integer.toString(i)));
		}
		assertEquals(lot.occupancy(), 0);
		Vehicle vehCar3 = new Vehicle("Test Car 3");
		Vehicle vechCar2 = new Vehicle("Test Car2");
		lot.admit(vechCar2);
		assertTrue( lot.occupancy() == 1);
		lot.release(vehCar3);// Check releasing a car that doesn't exist
		assertTrue( lot.occupancy() == 1);
		
		
	
	}
	
	
	@Test
	public void testOccupancy() {
		assertTrue( lot.occupancy() == 0);
		lot.admit(vechCar);
		assertTrue( lot.occupancy() == 1);
		//Vehicle vechCar1 = new Vehicle("Test Car");// equals() method return true even when the objects are stored in 
		lot.admit(vechCar1);// in different memory space. Therefore, equals method is working fine.
		assertTrue( lot.occupancy() == 1);
		assertFalse(vechCar1 == vechCar);
		assertTrue(vechCar1.equals(vechCar));// Vehicle vechCar1 is equals to Vehicle vechCar
		Vehicle vechCar2 = new Vehicle("Test Car1");
		lot.admit(vechCar2);
		assertTrue( lot.occupancy() == 2);
		lot.release(vechCar2);
		assertTrue( lot.occupancy() == 1);
		Vehicle vehCar3 = new Vehicle("Test Car 3");
		lot.release(vechCar2);
		assertTrue( lot.occupancy() == 1);
		lot.release(vehCar3);// Check releasing a car that doesn't exist
		assertTrue( lot.occupancy() == 1);
		
		
		
	
	}
	
	@Test
	public void testOccupants() {
		
		assertNotNull( lot.occupants());
		assertTrue( lot.occupants().size() == 0);
		for(int i = 0; i < 10; ++i ){
			lot.admit(new Vehicle(Integer.toString(i)));
		}
		assertEquals(lot.occupants().size(), 10);// Check if the number of vehicles are 10
		lot.admit(vechCar);
		assertTrue("Test Car", lot.occupants().contains(vechCar));
		
		lot.release(vechCar);
		assertFalse("Test Car", lot.occupants().contains(vechCar));

		

	
	}

	

	
}