package vehicles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

import graphics.CityPanel;
import graphics.IDrawable;
import graphics.IMoveable;

public abstract class Vehicle implements IMoveable, IDrawable, Runnable {
	public enum Color {
		red, green, white, silver;
	}

	public enum Orientation {
		North, South, East, West;
	}

	protected int size;
	protected int passengers;
	protected int ID_v;
	protected Boolean Lights;
	protected Color col;
	protected int wheel_num;
	protected Location v_location;
	protected Orientation orien;
	protected int fuelConsumption;
	protected CityPanel pan;
	protected float total_mileage;
	protected BufferedImage img1, img2, img3, img4;
	protected int speed;
	static final float min_age = 18;
	static int vehicle_Id = 1000;
	static final int size_pixel = 65;
	static int total_vehicles = 0;
	Orientation shift;
	protected float vehiclefuel_amount;
	protected boolean canMove = true;
	protected float current_km;
	protected float tmp_vehialefuel;
	private static final int tot_threads = 10;
	public static boolean runningVehicleArr[] = new boolean[tot_threads]; // ??
	static int runningVehicleArr_num = 0; // ???
	public int v_indx;
	public boolean isRunning;
	public static Object emptyFuel = new Object();
    public static final int collisions=20;
	public static String[] crushEvents = new String[collisions]; // ??
	public static int crushEvents_index = -1; // ??

	/**
	 * Vehicle constructor
	 */
	public Vehicle(int ID_v, Color col, int wheel_num, Location v_location, float total_mileage) {

		// this.vehicle_Id = vehicle_Id;
		this.col = col;
		this.wheel_num = wheel_num;
		this.v_location = v_location;
		this.total_mileage = total_mileage;
		this.Lights = false;

	}

	/**
	 * Vehicle constructor
	 */
	public Vehicle(Color vehicle_color, CityPanel pan) {

		this.size = size_pixel;
		this.col = vehicle_color;
		this.v_location = new Location(new Point(0, 0), vehicles.Location.Orientation.East);
		this.pan = pan;
		this.total_mileage = 0;
		this.Lights = false;
		this.fuelConsumption = 0;
		this.vehiclefuel_amount = 0;
		this.ID_v = vehicle_Id++;
		total_vehicles++;
		v_indx = 0;
		isRunning = false;
		/*
		 * for(int i=0;i<pan.total_threads;i++) { runningVehicleArr[i]=false; }
		 */
	}
	// setters and getters methods:

	public static int getVehicle_Id() {
		return vehicle_Id;
	}

	public Color getVehicle_color() {
		return this.col;
	}

	public int getWheel_num() {
		return this.wheel_num;
	}

	public float getvehiclefuel_amount() {
		return this.vehiclefuel_amount;
	}

	public Location getV_location() {
		return this.v_location;
	}

	public float getTotal_mileage() {
		return this.total_mileage;
	}

	public boolean isLights() {
		return this.Lights;
	}
	/*
	 * public Boolean setVehicle_Id(int vehicle_Id) { this.vehicle_Id = vehicle_Id;
	 * return true; }
	 */

	public Boolean setVehicle_color(Color vehicle_color) {
		this.col = vehicle_color;
		return true;
	}

	public Boolean setWheel_num(int wheel_num) {
		this.wheel_num = wheel_num;
		return true;
	}

	public Boolean setV_location(Location v_location) {
		this.v_location = v_location;
		return true;
	}

	public CityPanel getCityPanel() {
		return this.pan;
	}

	/**
	 * function return vehicle unique number
	 * 
	 * @return vehicle number unique
	 */
	public int getv_indx() {
		return this.v_indx;
	}

	/**
	 * function return vehicle individual durability
	 */
	public abstract int getDurability();

	public Boolean setTotal_mileage(float total_mileage) {
		this.total_mileage = total_mileage;
		return true;
	}

	public Boolean setLights(boolean lights) {
		this.Lights = lights;
		return true;
	}

	/**
	 * P_obj- point object return- false if "this" and P_obj has the same
	 * point(=vehicle not moving, no change), true- vehicle moved and if we can
	 * update vehicle location(point limits) + update total vehicle km+ fuel.
	 * 
	 */
	public boolean drive(Point p) {

		int cal_X = Math.abs((this.getV_location().getPoint().getX() - p.getX()));
		int cal_Y = Math.abs(this.getV_location().getPoint().getY() - p.getY());
		int km = cal_X + cal_Y;
		// calculate how much fuel is needed
		if ((this.getV_location().getPoint().getX() - p.getX() == 0)
				&& (this.getV_location().getPoint().getY() - p.getY() == 0)) { // if the same point return false- and
																				// not mooving
			return false;
		}
		this.setTotal_mileage(this.getTotal_mileage() + km);
		this.getV_location().setPoint(p);
		// System.out.println(total_mileage);
		// System.out.println(vehiclefuel_amount+" 155");
		return true;

	}
	/*
	 * int cal_X = Math.abs(0 - p.getX()); int cal_Y = Math.abs(0 - p.getY());
	 * this.current_km = cal_X + cal_Y; if(vehiclefuel_amount<=0) {
	 * this.total_mileage-=this.current_km; this.current_km=0; return false; }
	 * this.total_mileage = total_mileage + this.current_km;
	 * System.out.println(total_mileage); System.out.println(vehiclefuel_amount);
	 * this.getV_location().setPoint(p); return true;
	 * 
	 * }
	 */

	/**
	 * return string with this data members
	 */
	@Override
	public String toString() {

		return "Vehicle: \nVehicle Id=" + vehicle_Id + "\nVehicle color= " + col + "\nWheel num= " + wheel_num + "\n"
				+ v_location.toString() + "\n Total km= " + this.total_mileage + ", Lights= " + Lights + " ";
	}

	/**
	 * function loads images according to the path + image path
	 */
	public void loadImages(String nm) {
		try {
			img1 = ImageIO.read(new File(PICTURE_PATH + col.name() + this.getVehicleName() + "North.png"));
			img2 = ImageIO.read(new File(PICTURE_PATH + col.name() + this.getVehicleName() + "South.png"));
			img3 = ImageIO.read(new File(PICTURE_PATH + col.name() + this.getVehicleName() + "East.png"));
			img4 = ImageIO.read(new File(PICTURE_PATH + col.name() + this.getVehicleName() + "West.png"));
		} catch (IOException e) {
			System.out.println("Exception: problem with " + this.getVehicleName() + " image");
			e.printStackTrace();
		}
	}

	/**
	 * return vehicle color type
	 */
	public String getColor() {
		return this.col.name();
	}

	/**
	 * function draw object according to point and orientation
	 */
	public void drawObject(Graphics g) {
		if (v_location.getOrientation() == Location.Orientation.North)
			g.drawImage(img1, v_location.getPoint().getX(), v_location.getPoint().getY(), size, size * 2, pan);
		else if (v_location.getOrientation() == Location.Orientation.South)// drives to the south side
			g.drawImage(img2, v_location.getPoint().getX(), v_location.getPoint().getY(), size, size * 2, pan);
		else if (v_location.getOrientation() == Location.Orientation.East) { // drives to the east side
			g.drawImage(img3, v_location.getPoint().getX(), v_location.getPoint().getY(), size * 2, size, pan);
		} else if (v_location.getOrientation() == Location.Orientation.West) // drives to the west side
			g.drawImage(img4, v_location.getPoint().getX(), v_location.getPoint().getY(), size * 2, size, pan);
	}

	/**
	 * return vehicle name
	 */
	public String getVehicleName() {
		return " Vehicle abs class ";
	}

	/**
	 * return vehicle speed
	 */
	public int getSpeed() {
		return this.speed;

	}

	public float getcurrent_km() {
		return this.current_km;
	}

	/**
	 * function return boolean value -"Lights" data member
	 * 
	 * @return true/false
	 */
	public boolean getLights() {
		return this.Lights;
	}

	/**
	 * The total amount of liters / energy that the vehicle received. After each
	 * refueling, the amount of liters / energy received is added to the sum. return
	 * vehicle fuel Consumption
	 */
	public int getFuelConsumption() {
		return this.fuelConsumption;

	}

	/**
	 * function call drive method to activate vehicle driving
	 */
	public void move(Point p) {
		//System.out.println("in 294");
		if (canMove()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else
			try {
				synchronized (emptyFuel) {
					//System.out.println("Thread #: " + this.ID_v);
					emptyFuel.wait();
					//System.out.println("After Thread #: " + this.ID_v);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		this.drive(p);
		pan.repaint();
	}

	/**
	 * function check vehicle Orientation and according to that set rectangle bounds
	 * @return rectangle object --> measure of vehicle bounds
	 */
	public Rectangle getBounds() {
		int height = size * 2;
		int width = size;
		if((this.v_location.getOrientation()==Location.Orientation.East) || (this.v_location.getOrientation()==Location.Orientation.West)) {
			height = size;
			width = size * 2;
			return new Rectangle(this.v_location.getPoint().getX(), this.v_location.getPoint().getY(), width, height);
		}
		return new Rectangle(this.v_location.getPoint().getX(), this.v_location.getPoint().getY(), width, height);

	}

	/**
	 * function check if vehicle has enough energy/fuel to keep moving
	 * @return true if vehicle has enough energy/fuel to keep moving, else false
	 */
	protected abstract boolean canMove();

	/**
	 * function converts from string to Enum "Color" type.
	 * 
	 * @param obj string type object
	 * @return wanted Color enum type
	 */
	public static Color convert_StringToEnum_Color(String obj) {
		for (Color c : Color.values()) {
			if (obj.equalsIgnoreCase(c.name())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * function place vehicle at next location and use "nextStop()" function to get
	 * vehicle next Coordinates to make deep copy / cloning we activate "Point"
	 * class copy constructor
	 * 
	 * @return point object to get to the next point-place
	 */
	public Point nextLocation() {

		vehicles.Location.Orientation o = v_location.getOrientation();
		int x = this.v_location.getPoint().getX();
		int y = this.v_location.getPoint().getY();
		Point next_p = null;
		Random rnd = new Random();

		if (x >= pan.getWidth() - this.size && y == 0) {// right top corner

			if (o == vehicles.Location.Orientation.North) {
				v_location.setOrientation(vehicles.Location.Orientation.West);

			} else if (o == vehicles.Location.Orientation.East) {
				v_location.setOrientation(vehicles.Location.Orientation.South);
			}
			next_p = new Point(nextStop());
			return next_p;

		} else if (x >= pan.getWidth() - this.size && y >= pan.getHeight() - this.size) { // right bottom corner
			if (o == vehicles.Location.Orientation.South) {
				v_location.setOrientation(vehicles.Location.Orientation.West);

			} else if (o == vehicles.Location.Orientation.East) {
				v_location.setOrientation(vehicles.Location.Orientation.North);
			}
			next_p = new Point(nextStop());
			return next_p;

		} else if (x == 0 && y == 0) { // left top corner
			if (o == vehicles.Location.Orientation.West) {
				v_location.setOrientation(vehicles.Location.Orientation.South);
			} else if (o == vehicles.Location.Orientation.North) {
				v_location.setOrientation(vehicles.Location.Orientation.East);
			}
			next_p = new Point(nextStop());
			return next_p;

		} else if (x == 0 && y >= pan.getHeight() - this.size) { // left bottom corner
			if (o == vehicles.Location.Orientation.South) {
				v_location.setOrientation(vehicles.Location.Orientation.East);
			} else if (o == vehicles.Location.Orientation.West) {
				v_location.setOrientation(vehicles.Location.Orientation.North);
			}
			next_p = new Point(nextStop());
			return next_p;

		} else if (x == 0 && y == (pan.getHeight() - this.size) / 2) { // middle left frame junction
			if (o == vehicles.Location.Orientation.North || o == vehicles.Location.Orientation.South) {
				if (rnd.nextBoolean()) {
					v_location.setOrientation(vehicles.Location.Orientation.East);

				}
			}
			if (o == vehicles.Location.Orientation.West) {
				if (rnd.nextBoolean()) {
					v_location.setOrientation(vehicles.Location.Orientation.North);
				} else
					v_location.setOrientation(vehicles.Location.Orientation.South);
			}
			next_p = new Point(nextStop());
			return next_p;

		} else if (x >= pan.getWidth() - this.size && y == (pan.getHeight() - this.size) / 2) { // middle right frame
																								// junction
			if (o == vehicles.Location.Orientation.East) {
				if (rnd.nextBoolean()) {
					v_location.setOrientation(vehicles.Location.Orientation.South);
				} else
					v_location.setOrientation(vehicles.Location.Orientation.North);
			}
			if (o == vehicles.Location.Orientation.North || o == vehicles.Location.Orientation.South) {
				if (rnd.nextBoolean()) {
					v_location.setOrientation(vehicles.Location.Orientation.West);
				}
			}
			next_p = new Point(nextStop());
			return next_p;

		}

		next_p = new Point(nextStop());
		// System.out.println(this.v_location);

		return next_p;
	}

	/**
	 * function use current vehicle orientation and according to that makes new
	 * point by checking conditions with "if" blocks
	 * 
	 * @return Point object
	 */
	public Point nextStop() {
		Point p;
		int x = this.v_location.getPoint().getX();
		int y = this.v_location.getPoint().getY();

		if (v_location.getOrientation() == vehicles.Location.Orientation.South) {
			// car's new orientation=South
			p = new Point(x, y + this.speed);
			return p;
		}

		if (v_location.getOrientation() == vehicles.Location.Orientation.North) {
			// car's new orientation=North
			p = new Point(x, y - this.speed);
			return p;
		}

		if (v_location.getOrientation() == vehicles.Location.Orientation.West) {
			// car's new orientation=West
			p = new Point(x - this.speed, y);
			return p;
		}

		if (v_location.getOrientation() == vehicles.Location.Orientation.East) {
			// car's new orientation=East
			p = new Point(x + speed, y);
			return p;
		}

		return new Point(x + speed, y);
	}

	@Override
	public void run() {
		this.isRunning = true;
		//System.out.println("start thread");
		//System.out.println("in run method, index: " + v_indx);
		runningVehicleArr[v_indx] = true;
		while (isRunning) {
			try {
				// System.out.println("thread");
				this.move(this.nextLocation());
				this.pan.repaint();
				Thread.sleep((long) (100 / speed));
				// System.out.println("isRunning " + isRunning + " ID"+ this.ID_v);
			} catch (Exception e) {
				System.out.println("InterruptedException " + this.ID_v);
				e.printStackTrace();
				isRunning = false;
			}
		}
		 System.out.println("Exit Run, Vehicle ID: "+ this.ID_v);
	}

	/**
	 * function turn isRunning data member to false--> if isRunning = false we stop
	 * thread so we can kill him at the end
	 */
	public void stopThread() {
		this.isRunning = false;
	}

}
