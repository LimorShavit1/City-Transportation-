package vehicles;

import vehicles.Location.Orientation;


public class Location {

	public enum Orientation {
		North, South, East, West;
	}

	private Point point;
	private Orientation orientation;
	
	/**
	 * class location:
	 * @param point- point object in defined limits
	 * @param orientation - defined as Enum- has 4 options, tells us where is the user
	 */
	
	
	/**
	 * Location class constructor
	 */
	public Location(Point point, Orientation orientation) {
		
		this.point = new Point(point.getX(), point.getY());
		this.orientation = orientation;

	}
  
	// getters and setters methods:
	
	/**
	 * function set Point object
	 * @param p1 point object
	 * @return true if setting success
	 */
	public Boolean setPoint(Point p1) {
		this.point.setX(p1.getX());
		this.point.setY(p1.getY());
		return true;
	}
	
	/**
	 * function returns Point object--> deep copy, activating Point copy constructor
	 * @return deep copy of Point object
	 */
	public Point getPoint() {
		return new Point(this.point);
	}

	/**
	 * function set Orientation
	 * @param orientation Orientation enum type
	 * @return true if setting success
	 */
	public Boolean setOrientation(Orientation orientation) {
		this.orientation = orientation;
		return true;
	}

	/**
	 * function returns Orientation enum type
	 * @return current orientation
	 */
	public Orientation getOrientation() {
		return this.orientation;
	}

	/**
	 * string of this data members
	 */
	public String toString() {
		return "Location:\n " + this.point.toString() + "\nOrientation= " + this.orientation;
	}
	
}
