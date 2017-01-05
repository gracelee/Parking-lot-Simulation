package junit;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import airport.ShortStayParkingLot;

public class ShortStayParkingLotTestCompute {
	private ShortStayParkingLot lot;
	private DateTime from = new DateTime(2015, 4, 15, 0, 0 , 0 ,0 );
	
	
		

	@Before
	public void setUp() throws Exception {
		lot = new ShortStayParkingLot("", 3);	

	}


	@Test
	public void testMinimumCharge() {
		DateTime to = new DateTime(2015, 4, 15, 0, 0 , 0 ,1 ); // for 1 millisecond
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 12.00");
		assertEquals(expected,actual);
		


	}

	@Test
	public void testFirstTwoHours() {
		DateTime to = new DateTime(2015, 4, 15, 1, 59 , 59 ,999 );// for 1 hour 59 minuetes 59 seconds 999 millisecond
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 12.00");
		assertEquals(expected,actual);
		
	}

	@Test
	public void testPerHourThereafter() {
		DateTime to = new DateTime(2015, 4, 15, 2, 0 , 0 ,1 ); // for the duration is less than or equal to 3 													
		Money actual = lot.computeCharge(from, to);//and the charge is lower than the maximum daily charge.
		Money expected = Money.parse("NZD 20.00");
		assertEquals(expected,actual);
		DateTime to1 = new DateTime(2015, 4, 15, 3, 0 , 0 ,0 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 20.00");
		assertEquals(expected1,actual1);

	}	
	
	@Test
	public void testPerHourThereafterAfterThree() {
		DateTime to = new DateTime(2015, 4, 15, 3, 0 , 0 ,1 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 25.00");
		assertEquals(expected,actual);
		DateTime to1 = new DateTime(2015, 4, 15, 23, 59 , 59 ,999 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 25.00");
		assertEquals(expected1,actual1);
		DateTime to2 = new DateTime(2015, 4, 16, 0, 0, 0 ,0 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 25.00");
		assertEquals(expected2,actual2);
		

	}
	
	@Test
	public void testOneDayAfter() {
		DateTime to = new DateTime(2015, 4, 16, 0, 0, 0 ,1 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 33.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 16, 1, 0 , 0 ,0 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 33.00");
		assertEquals(expected1,actual1);
		
		DateTime to2 = new DateTime(2015, 4, 16, 1, 0, 0 ,1 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 41.00");
		assertEquals(expected2,actual2);
		
		DateTime to3 = new DateTime(2015, 4, 16, 1, 0 , 0 ,59 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 41.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 16, 2, 0, 0 , 0 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 41.00");
		assertEquals(expected4,actual4);
		
		DateTime to5 = new DateTime(2015, 4, 16, 2, 0, 0 , 1 );
		Money actual5 = lot.computeCharge(from, to5);
		Money expected5 = Money.parse("NZD 49.00");
		assertEquals(expected5,actual5);
		
		DateTime to6 = new DateTime(2015, 4, 16, 2, 0, 0 , 59 );
		Money actual6 = lot.computeCharge(from, to6);
		Money expected6 = Money.parse("NZD 49.00");
		assertEquals(expected6,actual6);
		
		DateTime to7 = new DateTime(2015, 4, 16, 3, 0, 0 , 0 );
		Money actual7 = lot.computeCharge(from, to7);
		Money expected7 = Money.parse("NZD 49.00");
		assertEquals(expected7,actual7);
		
		DateTime to8 = new DateTime(2015, 4, 16, 3, 0, 0 , 1 );
		Money actual8 = lot.computeCharge(from, to8);
		Money expected8 = Money.parse("NZD 50.00");
		assertEquals(expected8,actual8);
		
		DateTime to9 = new DateTime(2015, 4, 16, 23, 59, 59 , 999 );
		Money actual9 = lot.computeCharge(from, to9);
		Money expected9 = Money.parse("NZD 50.00");
		assertEquals(expected9,actual9);

		
	}
	
	@Test
	public void testWeeklyTest() {
		DateTime to = new DateTime(2015, 4, 17, 0, 0 , 0 ,0 ); // for the duration is less than or equal to 3 													
		Money actual = lot.computeCharge(from, to);//and the charge is lower than the maximum daily charge.
		Money expected = Money.parse("NZD 50.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 17, 0, 0 , 0 ,1 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 58.00");
		assertEquals(expected1,actual1);
		
		DateTime to2 = new DateTime(2015, 4, 17, 1, 0, 0 ,1 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 66.00");
		assertEquals(expected2,actual2);
		
		DateTime to3 = new DateTime(2015, 4, 17, 2, 0 , 0 ,1 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 74.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 17, 3, 0 , 0 ,1 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 75.00");
		assertEquals(expected4,actual4);

	}	
	
	@Test
	public void testWeeklyMaxTest() {
		
		DateTime to = new DateTime(2015, 4, 29, 0, 0 , 0 ,0 ); // for the duration is less than or equal to 3 													
		Money actual = lot.computeCharge(from, to);//and the charge is lower than the maximum daily charge.
		Money expected = Money.parse("NZD 350.00");
		assertEquals(expected,actual);
		

	}
	
	
	@After
	public void after() throws Exception{
		
	}

}