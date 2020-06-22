package vehicles;

import javax.swing.Icon;

public abstract class Engine implements Cloneable {
	private float fuel_Per_KM;
	private int fuel_Tank;
	
	/**
	 * Engine class constructor
	 */
	public Engine(float fuel_Per_KM,int fuel_Tank) {

		this.fuel_Per_KM=fuel_Per_KM;
		this.fuel_Tank=fuel_Tank;
	}
	
	/**
	 * function return vehicle fuel Per KM
	 * @return vehicle fuel_Per_KM
	 */
	public float getfuel_Per_KM() {
		return fuel_Per_KM;
	}
	
	/**
	 * function set vehicle fuel Per KM data member
	 * @param fuel_Per_KM
	 * @return while set fuel success
	 */
	public boolean setFuel(float fuel_Per_KM) {
		this.fuel_Per_KM = fuel_Per_KM;
		return true;
	}
	
	/**
	 * function return vehicle max tank capacity
	 * @return fuel_Tank parameter
	 */
	public int getFuel_Tank() {
		return fuel_Tank;
	}
	
	/**
	 * function set fuel tank max capacity
	 * @param fuel_Tank tank max capacity
	 * @return true if setting success
	 */
	public boolean setFuel_Tank(int fuel_Tank) {
		this.fuel_Tank = fuel_Tank;
		return true;
	}
	
	/**
	 * string of this data members
	 */
	public String toString() {
		return "Fuel Per KM: "+this.fuel_Per_KM+ ", Fuel Tank: "+this.fuel_Tank+" ";
	}
	
	
	protected abstract Engine clone();
	

	/**
	 * return vehicle name
	 */
	public String getVehicleName() {
		return null;
	}
	
}
