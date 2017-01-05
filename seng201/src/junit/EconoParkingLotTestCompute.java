package junit;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import airport.EconoParkingLot;

public class EconoParkingLotTestCompute {
	private EconoParkingLot lot;
	private DateTime from = new DateTime(2015, 4, 15, 0, 0 , 0 ,0 );
	
	
		

	@Before
	public void setUp() throws Exception {
		lot = new EconoParkingLot("", 3);	

	}



	@Test
	public void testFirstOneMinute() {
	
		DateTime to = new DateTime(2015, 4, 15, 0, 0 , 0 ,1 );//1 millisecond
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 15.00");
		assertEquals(expected,actual);
		
		

	}

	@Test
	public void testFirstOneDay() {
	
		DateTime to = new DateTime(2015, 4, 15, 23, 59 , 59 ,999 );// 23.9 hours
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 15.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 16, 0, 0 , 0 ,0 );//1day
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 15.00");
		assertEquals(expected1,actual1);

	}	
	
	@Test
	public void testFirstOneDayAfter() {
		
		DateTime to = new DateTime(2015, 4, 16, 0, 0 , 0 ,1 );//1day 1 millisecond
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 20.00");
		assertEquals(expected,actual);
		
		DateTime to2 = new DateTime(2015, 4, 16, 1, 0 , 0 ,0 );//1day 1hour 
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 20.00");
		assertEquals(expected2,actual2);
	
		DateTime to1 = new DateTime(2015, 4, 16, 1, 0 , 0 ,1 );//1day 1hour 1 millisecond
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 25.00");
		assertEquals(expected1,actual1);
		
		DateTime to3 = new DateTime(2015, 4, 16, 2, 0 , 0 ,0 );//1day 2 hours
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 25.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 16, 23, 59 , 59 ,999 );//1 day 23.9 hours
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 30.00");
		assertEquals(expected4,actual4);
		
		

	}
	
	@Test
	public void testFirstOneWeek() {
	
		DateTime to = new DateTime(2015, 4, 25, 0, 0 , 0 ,0 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 150.00");
		assertEquals(expected,actual);

	}
	
	
	@Test
	public void testOtherCases() {
	
		DateTime to = new DateTime(2015, 4, 22, 0, 0 , 0 ,0 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 105.00");
		assertEquals(expected,actual);
		
		
		DateTime to1 = new DateTime(2015, 4, 21, 0, 0 , 0 ,1 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 95.00");
		assertEquals(expected1,actual1);
		
		
		DateTime to2 = new DateTime(2015, 4, 21, 1, 0 , 0 ,0 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 95.00");
		assertEquals(expected2,actual2);
		
		
		DateTime to3 = new DateTime(2015, 4, 21, 1, 0 , 0 ,1 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 100.00");
		assertEquals(expected3,actual3);
		
		
		DateTime to4 = new DateTime(2015, 4, 21, 23, 59 , 59 ,999 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 105.00");
		assertEquals(expected4,actual4);
		
		
		DateTime to5 = new DateTime(2015, 4, 22, 0, 0 , 0 ,1 );
		Money actual5 = lot.computeCharge(from, to5);
		Money expected5 = Money.parse("NZD 110.00");
		assertEquals(expected5,actual5);
		
		
		DateTime to6 = new DateTime(2015, 11, 22, 0, 0 , 0 ,0 );
		Money actual6 = lot.computeCharge(from, to6);
		Money expected6 = Money.parse("NZD 3315.00");
		assertEquals(expected6,actual6);
		
		
		DateTime to7 = new DateTime(2015, 11, 22, 0, 0 , 0 ,1 );
		Money actual7 = lot.computeCharge(from, to7);
		Money expected7 = Money.parse("NZD 3320.00");
		assertEquals(expected7,actual7);
		
		DateTime to8 = new DateTime(2016, 11, 22, 0, 0 , 0 ,1 );
		Money actual8 = lot.computeCharge(from, to8);
		Money expected8 = Money.parse("NZD 8810.00");
		assertEquals(expected8,actual8);
		
		DateTime to9 = new DateTime(2019, 11, 22, 0, 0 , 0 ,0 );
		Money actual9 = lot.computeCharge(from, to9);
		Money expected9 = Money.parse("NZD 25230.00");
		assertEquals(expected9,actual9);

	}
	
	

	
	
	@After
	public void after() throws Exception{
		
	}

}