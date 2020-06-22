package vehicles;

public class BenzineEngine extends Engine {
	static final int SolarB = 2;

	/**
	 * BenzineEngine class constructor
	 */
	public BenzineEngine(int fuel_Tank) {
		super(SolarB, fuel_Tank);
	}

	@Override
	public String toString() {
		return "Benzine Engine: " + super.toString();
	}

	@Override
	public String getVehicleName() {
		return "Benzine car";
	}

	/**
	 * clone method- deep copy
	 */
	@Override
	protected Engine clone() {
		return new BenzineEngine(this.getFuel_Tank());
	}


}
