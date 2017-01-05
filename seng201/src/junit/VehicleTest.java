package junit;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import airport.BillableEntity;
import airport.Vehicle;

public class VehicleTest {
	private Vehicle testVehicle;
	private BillableEntity testBill;
	

	@Before
	public void setUp() throws Exception {
		testVehicle = new Vehicle("Test regNo",testBill);
		
	}

	@Test
	public void testSetOwner() {
		BillableEntity testBill = new BillableEntity(null, null);
		assertEquals("Owner should be none",null, testBill.customer);
		BillableEntity testBill1 = new BillableEntity("Customer 1", "Test Address");
		testBill.customer = "Customer 1";
		testVehicle.setOwner(testBill1);
		assertEquals("Owner should be Test Name",testBill.customer, testBill1.customer);
		

	}
	
	@Test
	public void testHasOwner() {
		testVehicle.setOwner(null);
		assertTrue(testVehicle.hasOwner() == false);

	}
	
	@Test
	public void testCharge() {
		BillableEntity testBill = new BillableEntity(null, null);
		assertEquals("Owner should be none",null, testBill.customer);
		BillableEntity testBill1 = new BillableEntity("Test Name", "Test Address");
		assertEquals("Owner should be Test Name","Test Name", testBill1.customer);
	}
	
	@Test
	public void testRegNo() {
		BillableEntity testBill = new BillableEntity(null, null);
		assertEquals("Owner should be none",null, testBill.customer);
		BillableEntity testBill1 = new BillableEntity("Test Name", "Test Address");
		assertEquals("Owner should be Test Name","Test Name", testBill1.customer);

	}
	
	
	@Test
	public void testToString() {
		testVehicle.setOwner(null);
		assertEquals("toString should be","The registration number is Test regNo" +"\n" +"Cost:" +" NZD 0.00", testVehicle.toString());
		BillableEntity testTo = new BillableEntity("Grace", "Canterbury");
		testVehicle.setOwner(testTo);
		Money cost = Money.parse("NZD 0.00");
		testVehicle.charge(cost);
		assertEquals("toString should be","The registration number is Test regNo "+ "\n" +"Name: Grace"+"\n"+
		"Address: Canterbury"+"\n"+ "Cost:" + " NZD 0.00" , testVehicle.toString());

	}
	
	@Test
	public void testEquals() {
		BillableEntity testBill = new BillableEntity(null, null);
		assertEquals("Owner should be none",null, testBill.customer);
		BillableEntity testBill1 = new BillableEntity("Test Name", "Test Address");
		assertEquals("Owner should be Test Name","Test Name", testBill1.customer);

	}
	
	

}
