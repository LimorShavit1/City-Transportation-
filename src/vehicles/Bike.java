package vehicles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.CityPanel;
import vehicles.Vehicle.Color;

public class Bike extends Vehicle{
	private int Gear;
	private static int Bike_speed=2;
	static final int seats=1;
	static final int Bike_wheels=2;
	static final int Bike_Durability=2;
	
	/**
	 * Bike class constructor
	 */
	public Bike(int id_v, Color vehicle_color, int wheel_num, Location v_location,Orientation orientation,CityPanel pan,
			float total_mileage,int speed, int Gear)
	{
		/**
		 * activate Vehicle constructor  */
		super(id_v,vehicle_color,wheel_num,v_location, total_mileage); 
		this.Gear=Gear;
		
	}
	
	/**
	 * Bike class constructor.
	 * constructor inputs: vehicle_color, pan (CityPanel object), Bike Gear.
	 */
	public Bike(Color vehicle_color,CityPanel pan, int Gear) {
		super(vehicle_color,pan);
		this.Gear=Gear;
		this.speed=Bike_speed;
		this.wheel_num=Bike_wheels;
	}
	
	// getters and setters methods:
	
	/**
	 * function return bike gear number
	 * @return bike gear
	 */
	public int getGear() {
		return this.Gear;
	}
	
	/**
	 * function sets bike gear
	 * @param gear - bike gear number
	 * @return true is setting successes
	 */
	public boolean setGear(int gear) {
		Gear = gear;
		return true;
	}
	
	/**
	 * return vehicle speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	@Override
	public int getDurability() { 
		return Bike_Durability;
	}
	
	@Override
	public String toString() {
		return "Bike: Gear=" + Gear + ", " +super.toString();
	}
	
	@Override
	public String getVehicleName() {
		return "Bike";
	}

	/**
	 * static function 
	 * @return return Bike speed value
	 */
	public static int getBike_speed() {
		return Bike_speed;
	}

	/**
	 * static function- set Bike speed
	 * @param bike_speed
	 */
	public static void setBike_speed(int bike_speed) {
		Bike_speed = bike_speed;
	}

	/**
	 * static function
	 * @return vehicle seats number
	 */
	public static int getSeats() {
		return seats;
	}

	/**
	 * static function
	 * @return return bike number of wheels
	 */
	public static int getBikeWheels() {
		return Bike_wheels;
	}

	@Override
	protected boolean canMove() {
		return true;
	}

	
	
}
