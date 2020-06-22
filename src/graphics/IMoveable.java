package graphics;

import vehicles.Point;

public interface IMoveable {
     public String getVehicleName();
	 public int getSpeed();
	 public int getFuelConsumption();
	 public void move(Point p);
	 public int getDurability();
}
