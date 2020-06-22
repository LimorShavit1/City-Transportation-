package vehicles;

import graphics.IAnimale;

public class PackAnimal implements IAnimale, Cloneable {
	private String animal_name;
	private float currEnergy;
	static final int Max_Energy = 1000;
	static int activate_eat_func = 1;
	static final int energyPerkm=20;
	Carriage PA_carriage;
	/**
	 * PackAnimal class--> implements IAnimale, Cloneable
	 * @param animal_name animal name
	 * @param currEnergy animal current energy
	 * @param Max_Energy  static final data member(cls data member) hold max animal energy
	 * @param activate_eat_func count how many times eat() func' activated
	 * @param PA_carriage animal carriage
	 */
	

	/**
	 * PackAnimal copy constructor
	 * @param obj- PackAnimal object
	 */
	PackAnimal(PackAnimal obj) {
		this.animal_name = obj.animal_name;
		this.currEnergy = obj.currEnergy;
		this.PA_carriage=obj.PA_carriage;
		this.PA_carriage.fuelConsumption=1000;
	}

	/**
	 * PackAnimal class constructor
	 */
	public PackAnimal() {
		this.animal_name = "PackAnimal";
		this.currEnergy = Max_Energy;
	}

	/**
	 * function set PA_carriage data member
	 * @param obj Carriage object
	 * @return true if setting success
	 */
	public boolean setCarriage(Carriage obj) {
			this.PA_carriage = obj;
		return true;
	}

	/**
	 * function return String--> animal name
	 */
	public String getAnimalName() {
		return this.animal_name;
	}
	
    /**
     * function set animal name
     * @param animal_name String-->animal name
     * @return true if setting success
     */
	public boolean setAnimalName(String animal_name) {
		this.animal_name = animal_name;
		return true;
	}
 
	/**
	 * function return animal current energy
	 * @return animal current Energy
	 */
	public float getcurrEnergy() {
		 this.currEnergy-=20*PA_carriage.current_km;
		 if(this.currEnergy<0) {
		  this.currEnergy=0;
		  return currEnergy;
		 }
		return this.currEnergy;
	}
 
	/**
	 * return vehicle Fuel Consumption
	 */
	public int getFuelConsumption() {
		// return this.activate_eat_func*Max_Energy;
		setFuelConsumption();
		return this.PA_carriage.getFuelConsumption();

	}
 
	/**
	 * function set current Energy
	 * @param km total km between 2 points
	 * @return true if setting success
	 */
	public boolean setcurrEnergy(float km) {
		// this.currEnergy-=animalEnergy*20;
		//if(currEnergy==0) {
		//	return true;
	//	}
		this.currEnergy-= 20 *km;
		return true;
	}

	/**
	 * update animal energy to Max_Energy
	 */
	public boolean eat() {
		/*
		this.currEnergy += Max_Energy;
		setAcurrEnergy(PA_carriage.getPackAnimal());
		activate_eat_func++;
		setFuelConsumption();
		*/
		if (Max_Energy==this.currEnergy) {
			return false;// if equal no need to refuel
		}
		this.currEnergy=Max_Energy;
		
		synchronized (Vehicle.emptyFuel) {
			Vehicle.emptyFuel.notifyAll();
		}
		return true;
	}
	
	
	
	/**
	 * function set Fuel Consumption
	 * @return true if setting success
	 */
	public boolean setFuelConsumption() {
		this.PA_carriage.fuelConsumption = Max_Energy * activate_eat_func;
		return true;
	}
	
	/**
	 * function reset current Energy
	 * @return true if setting success
	 */
	public boolean resetcurrEnergy(){
		this.currEnergy=0;
		return true;
	}
	
	/**
	 * function set current Energy
	 * @param obj PackAnimal object type
	 * @return true if setting success
	 */
	public boolean setAcurrEnergy(PackAnimal obj){
		obj.currEnergy+=Max_Energy;
		return true;
	}

	@Override
	public String toString() {
		return "PackAnimal: Animal_name=" + animal_name + ", Current animal Energy=" + currEnergy + "\n";
	}

	
	public static int getEnergyperkm() {
		return energyPerkm;
	}

}
