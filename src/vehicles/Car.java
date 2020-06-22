package vehicles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.CityPanel;
import vehicles.Vehicle.Color;
import vehicles.Vehicle.Orientation;

public class Car extends HasEngine {
	static final int car_speed=4;
	static final int seats=5;
	public static final int car_MaxfuelTank=40;
	static final int Car_Durability=10;
	
	/**
	 * Car class inherits from- HasEngine abstract class
	 * @param vehicle_Id vehicle Id number
	 * @param vehicle_color  vehicle color- enum object, 4 options-Red / Green / White / Silver.
	 * @param v_location vehicle location- point + orientation
	 * @param total_mileage total km vehicle has
	 * @param lights  true= lights on, else false
	 * @param engine  tank capacity + fuel per km
	 * @param Quantity_of_fuel current fuel in vehicle
	 * @param seats  Number of passengers the vehicle can accommodate
	 * @param car_speed car speed
	 * @param car_MaxfuelTank full tank capacity
	 */
	
	/**
	 * Car class constructor
	 */
	public Car(int id,Color vehicle_color,Location v_location,float total_mileage,	Engine engine, float Quantity_of_fuel)
	{
		super(id,vehicle_color,4,v_location,total_mileage,engine,Quantity_of_fuel); // activate HasEngine class constructor
		this.speed= car_speed;
		this.passengers=seats;
	}
	
	/**
	 * Car class constructor
	 */
	public Car(Color vehicle_color,CityPanel pan, Engine engine) { // fix!!!!!!
		super(vehicle_color,pan,engine);
		this.wheel_num=4;
		this.passengers=seats;
		this.speed=car_speed;
		//this.setEngine(engine);
	}
	
	/**
	 * string of class data members
	 */
	@Override
	public String toString() {
		return "Car: \nSeats= " + seats  + ", " + getEngine() + ", speed= "+car_speed+
				"\n" + super.toString() + " ";
	}

	@Override
	public String getVehicleName() {
		return "Car";
	}
	
	/**
	 * function check if vehicle can move
	 * @return true if vehicle has enough fuel for at least 1 km
	 */
	public boolean canMove() {
		return this.getEngine().getfuel_Per_KM()<=this.vehiclefuel_amount;
	}
	
	@Override
	public int getDurability() {
		return Car_Durability;
	}


}
		
