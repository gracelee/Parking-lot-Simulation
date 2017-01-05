package airport;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;



/**
 * This class represents the Short Stay Parking Lot.
 * @author dil15
 *
 */
public class ShortStayParkingLot implements Parkable{

	private int initialCapacity;
	private String name;
	private ArrayList<Vehicle> carList = new ArrayList<Vehicle>();


	public ShortStayParkingLot(java.lang.String name, int initialCapacity){
		
		this.initialCapacity = initialCapacity;
		this.name = name;
		

	}

	@Override
	public Money computeCharge(DateTime from, DateTime to) {
		from= from.withZoneRetainFields(DateTimeZone.UTC);
		to = to.withZoneRetainFields(DateTimeZone.UTC);
		
		double milliseconds = to.getMillis()-from.getMillis();
		double hours   = (double) ((milliseconds / (1000*60*60)));
		
		Money money = Money.parse("NZD 0.00");
		Money minMoney = Money.parse("NZD 12.00");
		CurrencyUnit nzd = CurrencyUnit.of("NZD");
		
		double hourlyAmount = 8;
		int dailyAmount = 25;
		int hourDiv = (int)Math.ceil(hours/24);
		int hourRoundDown = (int)Math.floor(hours/24);
		int hourRemain = (int) Math.ceil((hours%24));
		
		Money hourAmount = Money.of(nzd,hourlyAmount);
		Money dayAmount = Money.of(nzd, dailyAmount);
		
		Money daily_value = money.plus(dayAmount.multipliedBy(hourDiv));
		Money maxDayAmount = hourAmount.multipliedBy(hourRemain);
		
	    	
		if(hours <= 24){
			if (hours <= 2){
				money = minMoney;
			}else if(hours<=3){
				money = minMoney.plus(hourAmount.multipliedBy(hourDiv));	
			}else{
				money = money.plus(Money.of(nzd,dailyAmount));
		}} else {
			if(maxDayAmount.isLessThan(dayAmount)){
				
				money = (dayAmount.multipliedBy(hourRoundDown)).plus(maxDayAmount);
			} else {
				money = daily_value;
			}
		}
				
	
		return money;
	
		
		}


	@Override
	public void admit(Vehicle v) {
		
		boolean doAdd = true;
		
		// Check if v is already in car_list - Don't add Hint: For loop over car_list
		for(Vehicle car : carList) {
			if (car.equals(v)) {
				doAdd = false;
				
			}
		}
		
		// Check if occupancy less than capacity - Do add
		
		if (doAdd) {
			carList.add(v);
		}
	}
		

	@Override
	public void release(Vehicle v) {
		for(Vehicle car : carList){
			if (car.equals(v)){
				carList.remove(car);
				break;
			}
		}
		//Error
		
	

		}
		// TODO Auto-generated method stub
		
	

	@Override
	public int capacity() {

		return initialCapacity;	
	
		// TODO Auto-generated method
	}

	@Override
	public int occupancy() {
		return carList.size();
			

		// TODO Auto-generated method stub
	}


	@Override
	public Collection<Vehicle> occupants() {
		// TODO Auto-generated method stub
		return carList;
		
	}
	/**
	 * Give the name of the parking lot.
	 * @return the name of the parking lot.
	 */
	@Override
	public String toString() {
		return name;
	}



}
