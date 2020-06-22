package vehicles;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import graphics.CityPanel;

public class Carriage extends Vehicle {
	private PackAnimal animal;
	private static int carriage_speed=1; // static data member
	static final int carriage_seats=2; // static data member
	static final int wheels=4;
	static final int Carriage_Durability=5;
	

	/**
	 * Carriage class constructor
	 */
	public Carriage(int idV, Color vehicle_color,Location v_location, float total_mileage,PackAnimal animal) {
		
		super(idV,vehicle_color, wheels, v_location, total_mileage); // activate Vehicle class constructor
		this.speed= carriage_speed;
	}
	
	public Carriage(Color vehicle_color,CityPanel pan) {
		super(vehicle_color,pan);
		this.wheel_num=wheels;
		this.passengers=carriage_seats;
		this.speed=carriage_speed;
		this.fuelConsumption=1000;
		this.vehiclefuel_amount=animal.Max_Energy;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return (Carriage) super.clone();
	}
	

	
	
	/**
	 * function set info for PackAnimal object in Carriage class
	 * @param animal1 PackAnimal object type
	 * @return true while set successed
	 */
	public boolean setanimal(PackAnimal animal1)
	{
		animal=animal1;
		return true;
	}

	@Override
	public String getVehicleName() {
		return "Carriage";
	}

	@Override
	public String getColor() {
		return this.col.name();
	}
	
	public static int getWheels() {
		return wheels;
	}
	
	@Override
	public int getDurability() { 
		return Carriage_Durability;
	}
	
	/**
	 * static function
	 * @return Carriage speed
	 */
	public static int getCarriage_speed() {
		return carriage_speed;
	}

	/**
	 * static function set carriage_speed
	 * @param carriage_speed
	 */
    public static void setCarriage_speed(int carriage_speed) {
		Carriage.carriage_speed = carriage_speed;
	}

	/**
	 * function make deep copy of PackAnimal object
	 * @return deep copy of "animal" data member
	 */
	public PackAnimal getPackAnimal() {
		return new PackAnimal(this.animal);
	}
	
	@Override
	public boolean drive(Point p) {

		int cal_X = Math.abs(0 - p.getX());
		int cal_Y = Math.abs(0 - p.getY());
		this.current_km = cal_X + cal_Y;
		
		//this.animal.setcurrEnergy(current_km);
		if(this.animal.getcurrEnergy()<=0) {
			animal.resetcurrEnergy();
			return false;
		}
		this.total_mileage = total_mileage + this.current_km;
		this.vehiclefuel_amount=this.animal.getcurrEnergy();
		this.getV_location().setPoint(p);
		return true;
	}

	
	public boolean canMove() {
		return PackAnimal.getEnergyperkm()<this.animal.getcurrEnergy();
	}
	
}
