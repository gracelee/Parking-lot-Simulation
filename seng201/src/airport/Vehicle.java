package airport;

import org.joda.money.Money;

/**
 * 
 * @author dil15
 *
 */
public class Vehicle{ 
	private BillableEntity owner;
	private String regNo;
	private Money cost =  Money.parse("NZD 0.00");
	

	/**
	 * Constructor for this class.
	 * @param regNo car registration number.
	 * @param owner owner of the current car.
	 */
	
	public Vehicle(java.lang.String regNo, BillableEntity owner){
		this.regNo= regNo;
		this.owner = owner;
	}
	
	/**
	 * Constructor for this class.
	 * @param regNo car registration number.
	 */
	public Vehicle(java.lang.String regNo){
		this.regNo= regNo;
	}
	
	
	
	/* Provides info about who to send charges to etc**/
	
	/**
	 * The owner for a car currently parked in this lot.
	 * @param owner owner of the current car
	 */
	public void setOwner(BillableEntity owner){
		this.owner = owner;			
	}
	
	/**
	 * Return true if owner BillableEntity is not null, otherwise false
	 * @return return true if owner BillableEntity is not null, otherwise false
	 */
	public boolean hasOwner() {
		return owner != null;
	}
	
	/**
	 *  The parking fee for a car currently parked in this lot.
	 * @param cost parking fee
	 */
	public void charge(Money cost){
		this.cost = cost;
	}
	
	/**
	 * Return the parking fee
	 * @return return the parking fee
	 */
	public Money charge(){
		return cost;
		
		
	}
	
	/**
	 * Return the registration number
	 * @return the registration number
	 */
	public java.lang.String regNo(){
		return regNo;
		
	}
	
	/**
	 * 
	 * Return the registration number, name, address and parking fee of the car.
	 * @return the registration number, name, address and parking fee of the car.
	 */
	public String toString(){
		if (owner != null) {
			return "The registration number is " +  regNo + " " + owner.toString()+'\n' + "Cost: " + cost;
			
		}else {
			return "The registration number is " + regNo + '\n'+
					"Cost: " +cost;
		}
	}
	
	
	
	//OverLoading
	/**
	 * Return true if v Vehicle is the same as the regNo, otherwise false
	 * @param v The vehicle entering the lot
	 * @return return true if v Vehicle is already parked, otherwise false
	 */
	public boolean equals(Vehicle v) {
		return v.regNo().equals(regNo);
	}
	

}
	



