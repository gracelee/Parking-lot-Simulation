package airport;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.money.*;
import org.joda.time.*;

/**
 * This class represents the Econo Parking Lot.
 * @author dil15
 *
 */

public class EconoParkingLot implements Parkable{
	
	
	private int initialCapacity;
	private String name;
	String  curVehicle;
	private ArrayList<Vehicle> carList = new ArrayList<Vehicle>();

	public EconoParkingLot(java.lang.String name, int initialCapacity){
		this.initialCapacity = initialCapacity;
		this.name = name;
		
	}
	
	@Override
	public Money computeCharge(DateTime from, DateTime to) {
		from= from.withZoneRetainFields(DateTimeZone.UTC);// Daylight Saving
		to = to.withZoneRetainFields(DateTimeZone.UTC);
		double milliseconds = to.getMillis()-from.getMillis();// Convert the interval in to millisecond, since millisecond is the smallest unit
		double days   = (double) ((milliseconds / (1000*60*60 *24)));
		double hours   = (double) ((milliseconds / (1000*60*60)));

		
		int hourlyAmount = 5;
		int dailyAmount = 15;
		int weeklyAmount = 105;
		
		int hourDiv = (int) Math.ceil((hours/24));// Each modulo 
		int dayDiv = (int) Math.ceil((days/7));
		int dayDivDown = (int) Math.floor((days/7));
		int hourRemain = (int) Math.ceil((hours%24));
		int dayRemain = (int) Math.ceil((days%7));
		int dayRoundDown = (int) Math.floor((days%7));
		
		Money money = Money.parse("NZD 0.00");
		Money minMoney = Money.parse("NZD 15.00");
		CurrencyUnit nzd = CurrencyUnit.of("NZD");	
		
		Money moneyHour = Money.of(nzd,hourlyAmount);
		Money moneyDay = Money.of(nzd, dailyAmount);
		Money moneyWeek = Money.of(nzd, weeklyAmount);
		
		Money maxHourCharge = moneyHour.multipliedBy(hourRemain);
		Money maxDayCharge = moneyDay.multipliedBy(dayRemain);
		Money maxWeekCharge = moneyWeek.multipliedBy(dayDivDown);
		Money maxDayDownCharge = moneyDay.multipliedBy(dayRoundDown);
		
		if(milliseconds == 0){
			money = Money.parse("NZD 0.00");
		}
	                             
	
		if(days< 7){
			if (days<=1){
				money = minMoney;
			}else
				if(maxDayCharge.isLessThan(moneyWeek)||maxDayCharge.isEqual(moneyWeek)){
					if(maxHourCharge.isLessThan(moneyDay)){
						money = maxHourCharge.plus(maxDayDownCharge);
					}else
						money = moneyDay.multipliedBy(hourDiv);
			
				}else
					money = moneyWeek.multipliedBy(dayDiv);		
	
		}else if (dayRemain == 0){
			
			money = moneyWeek.multipliedBy(dayDiv);	
			
		}
		else
			if(maxDayCharge.isLessThan(moneyWeek)||maxDayCharge.isEqual(moneyWeek)){
				if(maxHourCharge.isLessThan(moneyDay)){
					if(hourRemain == 0){
						money = maxWeekCharge.plus(maxDayCharge);
					}else
						money = maxWeekCharge.plus(maxDayDownCharge.plus(maxHourCharge));
				
				}else
					money = maxWeekCharge.plus(maxDayCharge);
		
			}else
				money = moneyWeek.multipliedBy(dayDiv);	

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

	@Override
	public int capacity() {
		return initialCapacity ;		

		
	}

	@Override
	public int occupancy() {
	
		return carList.size();
		
			
		}
	

	@Override
	public Collection<Vehicle> occupants() {
		return carList;


	}
	
	/**
	 * Give the name of the parking lot.
	 * @return the name of the parking lot.
	 */
	@Override
	public String toString() {
		return name; // returning the name of parking lots not the memory locations
	}

}
