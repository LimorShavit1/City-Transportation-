package vehicles;

public class SolarEngine extends Engine {
	
	static final int SolarF = 1; // static value
	/**
	 * 
	 * @param fuel_Tank= capacity of fuel tank
	 */
	
	public SolarEngine(int fuel_Tank) {
		super(SolarF, fuel_Tank);
		
	}
	
	/**
	 * clone method- deep copy
	 */
	@Override
	protected Engine clone() {
		return new SolarEngine(this.getFuel_Tank());
	}

	public String toString() {
		return "Solar Engine: "+super.toString();
	}
	
	@Override
	public String getVehicleName() {
		return "Solar car";
	}
	

}
