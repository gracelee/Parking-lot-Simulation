/**
 * 
 */
package airport;

import java.util.Collection;


import org.joda.money.Money;
import org.joda.time.DateTime;

/**
 * Individual classes (such as ShortStayParkingLot) implement this.
 * Vehicles are admitted if there are spaces available. 
 * @author dil15
 */
public interface Parkable {
	/**
	 * Compute the cost of parking in this lot between these times. 
	 * @param from The time the Vehicle is admitted.
	 * @param to The time the vehicle is released.
	 * @return The cost of parking here.
	 */
	Money computeCharge(DateTime from, DateTime to);
	
	/**
	 * Admit a Vehicle to the lot and update the occupancy.
	 * @param v The vehicle entering the lot.
	 */
	void admit(Vehicle v);
	
	/**
	 * Allow vehicle to depart from the lot and update occupancy.
	 * Charges will have been paid.
	 * @param v The vehicle leaving the lot.
	 */
	void release(Vehicle v);
	
	/**
	 * How many Vehicles can be parked in the lot.
	 * 
	 * @return capacity (non-negative)
	 */
	int capacity();
	/**
	 * How many vehicles are currently parked in the lot.
	 * @return number of vehicles (between 0 and capacity)
	 */
	int occupancy();
	
	/**
	 * The Vehicles currently parked in this lot.
	 * @return Collection of vehicles (no particular order required)
	 */
	
	
	Collection<Vehicle> occupants();

}
