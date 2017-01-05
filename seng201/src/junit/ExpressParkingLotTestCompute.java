package junit;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import airport.ExpressParkingLot;

public class ExpressParkingLotTestCompute {
	private ExpressParkingLot lot;
	private DateTime from = new DateTime(2015, 4, 15, 0, 0 , 0 ,0 );
	
	
		

	@Before
	public void setUp() throws Exception {
		lot = new ExpressParkingLot("", 3);	

	}


	@Test
	public void test16to40Minute() {
	
		DateTime to = new DateTime(2015, 4, 15, 0, 16 , 0 ,0 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 4.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 15, 0, 40 , 0 ,0 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 4.00");
		assertEquals(expected1,actual1);

	}

	@Test
	public void test41to60Minutes() {
	
		DateTime to = new DateTime(2015, 4, 15, 0, 41 , 0 ,0 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 8.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 15, 1, 0 , 0 ,0 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 8.00");
		assertEquals(expected1,actual1);

	}	
	
	@Test
	public void test1to2Hours() {
	
		DateTime to = new DateTime(2015, 4, 15, 1, 0 , 0 ,1 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 16.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 15, 2, 0 , 0 ,0 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 16.00");
		assertEquals(expected1,actual1);

	}
	
	@Test
	public void testPerhourThereafter() {
	
		DateTime to = new DateTime(2015, 4, 15, 3, 0 , 0 ,0 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 24.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 15, 3, 0 , 0 ,1 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 32.00");
		assertEquals(expected1,actual1);
		
		DateTime to2 = new DateTime(2015, 4, 15, 23, 59 , 59 ,999 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 32.00");
		assertEquals(expected2,actual2);
		
		DateTime to3 = new DateTime(2015, 4, 16, 0, 0 , 0 ,1 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 40.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 27, 4, 0 , 0 ,1 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 320.00");
		assertEquals(expected4,actual4);
		
		DateTime to5 = new DateTime(2015, 4, 26, 0, 0 , 0 ,0 );
		Money actual5 = lot.computeCharge(from, to5);
		Money expected5 = Money.parse("NZD 288.00");
		assertEquals(expected5,actual5);
		
		DateTime to6 = new DateTime(2015, 4, 29, 0, 0 , 0 ,0 );
		Money actual6 = lot.computeCharge(from, to6);
		Money expected6 = Money.parse("NZD 320.00");
		assertEquals(expected6,actual6);
		
		DateTime to7 = new DateTime(2015, 4, 29, 0, 0 , 0 ,1 );
		Money actual7 = lot.computeCharge(from, to7);
		Money expected7 = Money.parse("NZD 328.00");
		assertEquals(expected7,actual7);
		
		DateTime to8 = new DateTime(2019, 4, 29, 0, 0 , 0 ,1 );
		Money actual8 = lot.computeCharge(from, to8);
		Money expected8 = Money.parse("NZD 33760.00");
		assertEquals(expected8,actual8);

	}
	
	
	@After
	public void after() throws Exception{
		
	}

}
