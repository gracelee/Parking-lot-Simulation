package airport;

import java.util.ArrayList;
import java.util.Collections;


/**
 * 
 * @author dil15
 *
 */
public class ParkingSystem{
	private ArrayList<Parkable>  parkingLots = new ArrayList<Parkable>();
	private ArrayList<Vehicle>  carNoList = new ArrayList<Vehicle>();
	
	/**
	 * Return all the parking lots in the parking system.
	 * @return the list of parking lots.
	 */

	public java.util.Collection<Parkable> lots(){
		return Collections.unmodifiableCollection(parkingLots);

		// Return the parking lots we have
		
	}
	/**
	 * Add a parking lot to the parking system.
	 * @param p the parking lot to add.
	 */
	
	public void addLot(Parkable p ){
		if(!parkingLots.contains(p)){
			parkingLots.add(p);		
		}
	
		// adding parking lots

	}
	
	/**
	 * Return the list of available parking lot types.
	 * @return the list of available parking lot types.
	 */
	public static java.lang.String[] parkOptions(){
		String[] str = {"Short Stay Parking Lot", "Long Stay Parking Lot", 
						"Express Parking Lot", "Econo Parking Lot"};

		return str;
		
		//Provides a list of the parking lot types we offer.
	}
	
	/**
	 * Check if v Vehicle is already parked or not.
	 * @param  v The vehicle entering the lot.
	 * @return false if v Vehicle is already parked, otherwise true.
	 */
	public boolean inLot(Vehicle v){
		
		for (Parkable p : lots()){
			if ( p.occupants().contains(v) ){
				return true;
			}
	
		}
		
		return false;
	}
	
	/**
	 * Return the car if the car is not the same as the reg, otherwise add the car in the carNoList
	 * @param reg car registration number
	 * @return car Vehicle entering the lot 
	 */
	public Vehicle vehicleFor(java.lang.String reg) {
		for (Vehicle car: carNoList){
			if (car.regNo().equals(reg)){
				return car;
			}
		}
		Vehicle car = new Vehicle(reg);
		carNoList.add(car);
		return car;
	}

			
}

						

