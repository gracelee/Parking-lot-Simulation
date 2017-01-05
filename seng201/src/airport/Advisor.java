package airport;


import java.util.HashMap;
import java.util.Map;
import org.joda.money.Money;
import org.joda.time.DateTime;

/**
 * This class is to give an advice for a given period, 
 * and to find out which parking lot is the cheapest parking lot
 * @author dil15
 *
 */
public class Advisor {
	private EconoParkingLot econoMoney = new EconoParkingLot("Econo", 0);
	private ShortStayParkingLot shortMoney = new ShortStayParkingLot("Short",0);
	private LongStayParkingLot longMoney = new LongStayParkingLot("Long",0);
	private ExpressParkingLot expressMoney = new ExpressParkingLot("Express", 0);
	private Map<String, Money> testMap = new HashMap<String, Money>();

/**
 * Add the name of the parking lot and parking fee in the HashMap.
 * @param from the departure time.
 * @param to the arrival time.
 * @return The map with the name of the parking lot and parking fee. 
 */
	public  Map<String, Money>  compareMoney(DateTime from, DateTime to){		

		testMap.put("Econo Parking Lot", econoMoney.computeCharge(from, to));
		testMap.put("Short Stay Parking Lot", shortMoney.computeCharge(from, to));
		testMap.put("Long Stay Parking Lot", longMoney.computeCharge(from, to));
		testMap.put("Express Parking Lot", expressMoney.computeCharge(from, to));
		
		return testMap;

		
	}

	
}
