package junit;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import airport.LongStayParkingLot;

public class LongStayParkingLotTestCompute {
	private LongStayParkingLot lot;
	private DateTime from = new DateTime(2015, 4, 15, 0, 0 , 0 ,0 );
	
	
		

	@Before
	public void setUp() throws Exception {
		lot = new LongStayParkingLot("", 3);	

	}


	@Test
	public void testMinimumCharge() {
		DateTime to = new DateTime(2015, 4, 15, 0, 0 , 0 ,1 ); // for 1 millisecond
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 25.00");
		assertEquals(expected,actual);

	}

	@Test
	public void testOneDay() {
		DateTime to = new DateTime(2015, 4, 15, 23, 59 , 0 ,0 );// for 1 hour 59 minutes 59 seconds 999 milliseconds
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 25.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 16, 0, 0 , 0 ,0 );// for 1 hour 59 minutes 59 seconds 999 milliseconds
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 25.00");
		assertEquals(expected1,actual1);
		
		
	}

	@Test
	public void testPerHourThereafter() {
		DateTime to = new DateTime(2015, 4, 16, 0, 0 , 0 ,1 ); // for the duration is less than or equal to 3 													
		Money actual = lot.computeCharge(from, to);//and the charge is lower than the maximum daily charge.
		Money expected = Money.parse("NZD 33.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 16, 1, 0 , 0 ,0 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 33.00");
		assertEquals(expected1,actual1);
		
		DateTime to2 = new DateTime(2015, 4, 16, 1, 0 , 0 ,1 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 41.00");
		assertEquals(expected2,actual2);
		
		DateTime to3 = new DateTime(2015, 4, 16, 2, 0 , 0 ,0 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 41.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 16, 2, 0 , 0 ,1 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 49.00");
		assertEquals(expected4,actual4);
		
		DateTime to5 = new DateTime(2015, 4, 16, 3, 0 , 0 ,0 );
		Money actual5 = lot.computeCharge(from, to5);
		Money expected5 = Money.parse("NZD 49.00");
		assertEquals(expected5,actual5);

	}	
	
	@Test
	public void testDailyMaximum() {
		
		DateTime to = new DateTime(2015, 4, 16, 3, 0, 0 , 1 );
		Money actual = lot.computeCharge(from, to);
		Money expected = Money.parse("NZD 50.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 16, 23, 59 , 59 ,999 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 50.00");
		assertEquals(expected1,actual1);
		
		DateTime to2 = new DateTime(2015, 4, 17, 3, 0, 0 , 1 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 75.00");
		assertEquals(expected2,actual2);
		
		DateTime to3 = new DateTime(2015, 4, 17, 23, 59 , 59 ,999 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 75.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 18, 3, 0, 0 , 1 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 100.00");
		assertEquals(expected4,actual4);
		
		DateTime to5 = new DateTime(2015, 4, 18, 23, 59 , 59 ,999 );
		Money actual5 = lot.computeCharge(from, to5);
		Money expected5 = Money.parse("NZD 100.00");
		assertEquals(expected5,actual5);


	}
	
	
	@Test
	public void testWeeklyMaximum() {
		DateTime to = new DateTime(2015, 4, 20, 0, 0 , 0 ,0 ); // for the duration is less than or equal to 3 													
		Money actual = lot.computeCharge(from, to);//and the charge is lower than the maximum daily charge.
		Money expected = Money.parse("NZD 125.00");
		assertEquals(expected,actual);
		
		DateTime to1 = new DateTime(2015, 4, 20, 0, 0 , 0 ,1 );
		Money actual1 = lot.computeCharge(from, to1);
		Money expected1 = Money.parse("NZD 125.00");
		assertEquals(expected1,actual1);
		
		DateTime to2 = new DateTime(2015, 4, 21, 23, 59, 59 , 999 );
		Money actual2 = lot.computeCharge(from, to2);
		Money expected2 = Money.parse("NZD 125.00");
		assertEquals(expected2,actual2);
		
		DateTime to3 = new DateTime(2015, 4, 22, 0, 0 , 0 ,0 );
		Money actual3 = lot.computeCharge(from, to3);
		Money expected3 = Money.parse("NZD 125.00");
		assertEquals(expected3,actual3);
		
		DateTime to4 = new DateTime(2015, 4, 22, 0, 0 , 0 ,1 );
		Money actual4 = lot.computeCharge(from, to4);
		Money expected4 = Money.parse("NZD 133.00");
		assertEquals(expected4,actual4);
		
		DateTime to5 = new DateTime(2015, 4, 22, 1, 0 , 0 , 0  );
		Money actual5 = lot.computeCharge(from, to5);
		Money expected5 = Money.parse("NZD 133.00");
		assertEquals(expected5,actual5);
		
		DateTime to6 = new DateTime(2015, 4, 22, 1, 0 , 0 , 1  );
		Money actual6 = lot.computeCharge(from, to6);
		Money expected6 = Money.parse("NZD 141.00");
		assertEquals(expected6,actual6);
		
		DateTime to7 = new DateTime(2015, 4, 22, 2, 0 , 0 , 1  );
		Money actual7 = lot.computeCharge(from, to7);
		Money expected7 = Money.parse("NZD 149.00");
		assertEquals(expected7,actual7);
		
		DateTime to8 = new DateTime(2015, 4, 22, 3, 0 , 0 , 1  );
		Money actual8 = lot.computeCharge(from, to8);
		Money expected8 = Money.parse("NZD 150.00");
		assertEquals(expected8,actual8);
		
		DateTime to9 = new DateTime(2015, 4, 22, 23, 59 , 59 , 999  );
		Money actual9 = lot.computeCharge(from, to9);
		Money expected9 = Money.parse("NZD 150.00");
		assertEquals(expected9,actual9);
		
		DateTime to10 = new DateTime(2015, 4, 23, 0, 0 , 0 , 0  );
		Money actual10 = lot.computeCharge(from, to10);
		Money expected10 = Money.parse("NZD 150.00");
		assertEquals(expected10,actual10);
		
		DateTime to11 = new DateTime(2015, 4, 23, 0, 0 , 0 , 1  );
		Money actual11 = lot.computeCharge(from, to11);
		Money expected11 = Money.parse("NZD 158.00");
		assertEquals(expected11,actual11);
		
		DateTime to12 = new DateTime(2015, 4, 23, 3, 0 , 0 , 1  );
		Money actual12 = lot.computeCharge(from, to12);
		Money expected12 = Money.parse("NZD 175.00");
		assertEquals(expected12,actual12);
		
		DateTime to13 = new DateTime(2015, 4, 23, 23, 59 , 59 , 999  );
		Money actual13 = lot.computeCharge(from, to13);
		Money expected13 = Money.parse("NZD 175.00");
		assertEquals(expected13,actual13);
		
		DateTime to14 = new DateTime(2015, 4, 24, 0, 0 , 0 , 0  );
		Money actual14 = lot.computeCharge(from, to14);
		Money expected14 = Money.parse("NZD 175.00");
		assertEquals(expected14,actual14);
		
		DateTime to15 = new DateTime(2015, 4, 24, 0, 0 , 0 , 1  );
		Money actual15 = lot.computeCharge(from, to15);
		Money expected15 = Money.parse("NZD 183.00");
		assertEquals(expected15,actual15);
		
		DateTime to16 = new DateTime(2015, 4, 29, 0, 0 , 0 , 0 );
		Money actual16 = lot.computeCharge(from, to16);
		Money expected16 = Money.parse("NZD 250.00");
		assertEquals(expected16,actual16);
		
		DateTime from1 = new DateTime(2016, 4, 1, 0, 0 , 0 , 0 );
		DateTime to17 = new DateTime(2016, 4, 8, 0, 0 , 0 , 0 );
		Money actual17 = lot.computeCharge(from1, to17);
		Money expected17 = Money.parse("NZD 125.00");
		assertEquals(expected17,actual17);

	}	

	@After
	public void after() throws Exception{
		
	}

}