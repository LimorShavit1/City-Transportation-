package vehicles;

import java.awt.image.BufferedImage;

import graphics.CityPanel;
import vehicles.Vehicle.Color;
import vehicles.Vehicle.Orientation;

public abstract class HasEngine extends Vehicle {
	private Engine engine;
	//private float fuel_amount;
	float v_fuel;
	static int call_refuel_func=1;
	/**
	 * 
	 * @param vehicle_Id vehicle Id number
	 * @param vehicle_color vehicle color- enum object, 4 options-Red / Green / White / Silver.
	 * @param wheel_num- number of vehicle wheels
	 * @param v_location -vehicle location- point + orientation
	 * @param total_mileage= total km vehicle has
	 * @param lights= boolean data member :true- lights on, else false
	 * @param engine= tank capacity + fuel per km
	 * @param fuel_amount= current fuel in vehicle
	 */

	/**
	 * HasEngine class constructor
	 */
	public HasEngine(int ID_v, Color vehicle_color, int wheel_num, Location v_location,float total_mileage,
			Engine engine, float fuel_amount) {
		
		super(ID_v,vehicle_color, wheel_num, v_location,total_mileage); // activate super class constructor
		this.engine = engine.clone();
		//this.fuel_amount = fuel_amount;
	}
	
	/**
	 * HasEngine class constructor
	 */
	public HasEngine(Color vehicle_color,CityPanel pan, Engine engine) {
		super(vehicle_color,pan);
		this.engine = engine;	
		this.vehiclefuel_amount=this.engine.getFuel_Tank(); //at the beginning fuel amount is full
		this.fuelConsumption=this.engine.getFuel_Tank();
	}
	
		

    // getters and setters methods:
	
	/**
	 * function return Engine object type
	 * @return engine object
	 */
	public Engine getEngine() {
		return this.engine.clone();
	}

	/**
	 * function set Engine 
	 * @param engine vehicle engine
	 * @return true if setting success
	 */
	public boolean setEngine(Engine engine) {
		this.engine.setFuel(engine.getfuel_Per_KM());
		this.engine.setFuel_Tank(engine.getFuel_Tank());
		return true;
	}

	/**
	 * function return vehicle fuel amount
	 * @return vehiclefuel_amount
	 */
	public float getfuel_amount() {
		setfuel_amount(this.current_km*this.engine.getfuel_Per_KM());
		return vehiclefuel_amount;
	}

	/**
	 * function set fuel_amount-->current amount
	 * @param fuel_amount
	 * @return true if setting success
	 */
	public boolean setfuel_amount(float fuel_amount) {
		this.vehiclefuel_amount -= fuel_amount;
		return true;
	}

	/**
	 * function refuel vehicle
	 * @return true--> while vehicle got fuel, false-->else.
	 */
	public boolean Refuel() { 
		
		if (this.engine.getFuel_Tank()==this.vehiclefuel_amount) {
			return false;// if equal no need to refuel
		}
		this.vehiclefuel_amount=engine.getFuel_Tank();
		call_refuel_func++;
		this.fuelConsumption= call_refuel_func*this.engine.getFuel_Tank();
		
		synchronized (emptyFuel) {
			emptyFuel.notifyAll();
		}
		
		return true; 
	}
	
	/**
	 * P_obj- drive get a point object
	 * return- false if this and P_obj has the same point and not mooving , true- point is lligal+ change location + km+ fuel.
	 */
	@Override
	public boolean drive(Point P_obj)
	{
		
		// distance calculations : km=abs(x1-x2)+abs(y1-y2)
		
		int cal_X=Math.abs((this.getV_location().getPoint().getX()-P_obj.getX()));
		int cal_Y=Math.abs(this.getV_location().getPoint().getY()-P_obj.getY());
		current_km=Math.abs(cal_X+cal_Y); 
		
		// calculate how much fuel is needed
		this.v_fuel= this.engine.getfuel_Per_KM() * current_km;
		this.tmp_vehialefuel=this.vehiclefuel_amount;
		//check if we have enough fuel
		
		if(v_fuel>this.vehiclefuel_amount) {
			this.vehiclefuel_amount=tmp_vehialefuel;
			return false;
		}
		
		
		if(super.drive(P_obj)) {
			do {	
				if(v_fuel>this.vehiclefuel_amount) {
					return false;
				}
				if(0>=this.vehiclefuel_amount) {
					return false;
				}
			 this.vehiclefuel_amount-= v_fuel;
			}while(0>this.vehiclefuel_amount);	
			//this.vehiclefuel_amount-= v_fuel;
			return true;
		}
		
		return false;		
	}
	

	@Override
	public String toString() {
		return super.toString()+ "\nEngine: Fuel= " + engine.getfuel_Per_KM()+" , Fuel Tank= "+engine.getFuel_Tank()+
				", Quantity of fuel= "+this.vehiclefuel_amount;
	}
}
