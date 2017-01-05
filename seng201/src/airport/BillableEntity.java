package airport;




/* Class providing contact info for billing**/
/**
 * This class represents the address and the name of the customer.
 * @author dil15
 *
 */
public class BillableEntity {
	public String customer;
	public String address;

/**
 * Constructor for class.
 * @param who name of the customer.
 * @param where address of the customer.
 */
	public BillableEntity(java.lang.String who, java.lang.String where){
		customer=who;
		address = where;

	}
	

	/**
	 * Return the name and the address of the customer.
	 * @return The name and the address of the customer.
	 */
	public java.lang.String toString(){
		return  '\n' + "Name: " + customer + '\n' + "Address: " + address;
		
	}
}


