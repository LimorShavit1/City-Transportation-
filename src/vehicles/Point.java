package vehicles;

public class Point implements Cloneable {
	private int x;
	private int y;

	/**
	 * Point class implements Cloneable
	 * @param x= integer value, Position the vehicle on the X axis
	 * @param y= integer value , Position the vehicle on the Y axis
	 */

	/**
	 * Point class constructor
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Point class copy constructor
	 */
	Point(Point obj) { 
		x = obj.x;
		y = obj.y;
	}

	/**
	 * clone method- deep copy
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
    
	// getters and setters methods:
	
	/**
	 * function return x data member
	 * @return x value
	 */
	public int getX() { 
	  return this.x;
	  }
  
	/**
	 * function return y data member
	 * @return y value
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * function set x data member
	 * @param x  X coordinate
	 * @return true if setting success
	 */
	public Boolean setX(int x) {
		this.x = x;
		return true;
	}

	/**
	 * function set y data member
	 * @param y Y coordinate
	 * @return true if setting success
	 */
	public Boolean setY(int y) {
		this.y = y;
		return true;
	}

	/**
	 * string of this data members
	 */
	public String toString() {
		
		return "Point [x=" + x + ", y=" + y + "] ";
	}
	
}
