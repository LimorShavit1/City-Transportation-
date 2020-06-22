package graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import vehicles.BenzineEngine;
import vehicles.Bike;
import vehicles.Car;
import vehicles.Carriage;
import vehicles.Engine;
import vehicles.HasEngine;
import vehicles.Location.Orientation;
import vehicles.Vehicle;
import vehicles.Vehicle.Color;
import vehicles.PackAnimal;
import vehicles.SolarEngine;

public class CityPanel extends JPanel implements ActionListener {

	public JButton botton[];
	BufferedImage img;
	JPanel mYpanel;
	JFrame frame = new JFrame();
	String categories[] = { "Add vehicle", "Clear", "Fuel / Food", "Lights", "Info", "Exit" };
	Object[] options_FuelORFood = { "Benzine", "Solar", "Food" };
	String[] vehicleType = { "Benzine car", "Solar car", "Bike", "Carriage" };
	String[] colorType = { "Red", "Green", "Silver", "White" };
	public JRadioButton radioButton[] = new JRadioButton[4];
	public JRadioButton radioButtonright[] = new JRadioButton[4];
	// String[] columnNames = { "Vehicle", "ID", "Color", "Wheels", "Speed", "Fuel
	// Amount", "Distance", "Full Consumption","Lights" };
	public Vehicle[] vehicle_arr = new Vehicle[10]; // vehicle_arr[vehicle_index]
	// private Vehicle vehicle2= null;
	private int vehicle_index = -1;
	AddVehicleDialog addV;
	private boolean clear = false;
	private static final int threadsinPanel = 5;
	public static final int total_threads = 10;
	Thread[] myThreads = new Thread[total_threads];
	Executor executor = Executors.newFixedThreadPool(threadsinPanel); // run only 5 threads parallel

	/**
	 * CityPanel constructor
	 */
	public CityPanel() {

		mYpanel = new JPanel();
		setLayout(null);
		setBounds(0, 0, 1800, 1700);
		try { // set background
			img = ImageIO.read(new File("C:\\Users\\smach\\Desktop\\cityBackground.png"));
		} catch (IOException e) {
			System.out.println("Cannot load image");
		}

		mYpanel.setLayout(new GridLayout(1, 6));
		botton = new JButton[categories.length];

		for (int i = 0; i < categories.length; ++i) {
			botton[i] = new JButton(categories[i]);
			botton[i].addActionListener(this);
			mYpanel.add(botton[i]);
		}

		setLayout(new BorderLayout());
		add("South", mYpanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == botton[0]) {
			// Add vehicle option
			Add_v_dialog();
		} else if (e.getSource() == botton[1]) {
			// clear option
			clear();
		} else if (e.getSource() == botton[2]) {
			// Fuel/Food option
			Fuel_Food();
		} else if (e.getSource() == botton[3]) {
			// lights option
			Lights_option();
		} else if (e.getSource() == botton[4]) {
			// Info option
			Get_Info();
		} else {// Exit option
			exitSystem(e);
			/*
			 * clear(); System.out.println("clear"); JComponent comp = (JComponent)
			 * e.getSource(); Window win = SwingUtilities.getWindowAncestor(comp);
			 * win.dispose();
			 */

		}
	}

	/**
	 * function call AddVehicleDialog class constructor to create new vehicle object
	 */
	private void Add_v_dialog() {
		// botton[0].setEnabled(false);// ---> Enable the creation of only one object
		addV = new AddVehicleDialog(this);
	}

	/**
	 * while vehicle object!=null we can refuel tank / give pack animal eat for
	 * energy
	 * 
	 * @return 'true' if car got fuel / Carriage animal ate
	 */
	public boolean Fuel_Food() {
		/** fuel/food option dialog */
		//System.out.println("here");
		/** Method when listen to action -> what action want to perform? */
		UIManager.put("OptionPane.minimumSize", new Dimension(350, 100)); // window size

		for (int i = 0; i < total_threads; i++) {
			if (vehicle_arr[i] != null) {
				// vehicle_arr[i].getClass().getName();
				if (vehicle_arr[i] instanceof HasEngine) {
					// cast to "Hasengine"

					if (((HasEngine) vehicle_arr[i]).getEngine().getVehicleName().equals("Benzine car")) {// Benzene car
						((HasEngine) vehicle_arr[i]).Refuel();
						//System.out.println("after fuel");
						if (clear == true) {
							repaint();
						}

					} else if (((HasEngine) vehicle_arr[i]).getEngine().getVehicleName().equals("Solar car")) { // Solar
																												// car
						((HasEngine) vehicle_arr[i]).Refuel();
						if (clear == true) {
							repaint();
						}
					}

				} else if (vehicle_arr[i] instanceof Carriage) { // ((vehicle_arr[i].getVehicleName()).equals("Carriage"))
																	// { // Carriage
					//System.out.println("in 174");
					PackAnimal p = ((Carriage) vehicle_arr[i]).getPackAnimal();
					p.eat();
					p.setCarriage((Carriage) vehicle_arr[i]);
					((Carriage) vehicle_arr[i]).setanimal(p);
					// System.out.println("\n"+((Carriage) vehicle_arr[i]).getPackAnimal());
					if (clear == true) {
						repaint();
					}
				}
			} else
				break;
		}
		return true;
	}

	/**
	 * function get 3 parameters and according to the selected vehicle build an
	 * object through "addvehicledialog" class constructor + add vehicle to panel
	 * view
	 * 
	 * @param userV     - user chosen vehicle
	 * @param userColor - user chosen vehicle color
	 * @param userGear  - in case user chose "Bike" vehicle, user choose bike gear
	 *                  too, to build Bike vehicle object
	 */
	public void add_v(String userV, String userColor, int userGear) {

		vehicle_index++;
		Vehicle.Color v_col = Vehicle.convert_StringToEnum_Color(userColor);
		if (vehicle_index < total_threads) {

			if (userV == "Benzine car") {
				vehicle_arr[vehicle_index] = new Car(v_col, this, new BenzineEngine(Car.car_MaxfuelTank));
				myThreads[vehicle_index] = new Thread(vehicle_arr[vehicle_index]);
			}

			else if (userV.equals("Bike")) {
				// this.vehicle=new Bike(v_col,this,userGear);
				vehicle_arr[vehicle_index] = new Bike(v_col, this, userGear);
				myThreads[vehicle_index] = new Thread(vehicle_arr[vehicle_index]);
			}

			else if (userV.equals("Carriage")) {
				vehicle_arr[vehicle_index] = new Carriage(v_col, this); // ---
				PackAnimal CarriageAnimal = new PackAnimal();
				((Carriage) vehicle_arr[vehicle_index]).setanimal(CarriageAnimal); // set PackAnimal for Carriage class
				CarriageAnimal.setCarriage((Carriage) vehicle_arr[vehicle_index]); // set carriage for animal
				myThreads[vehicle_index] = new Thread(vehicle_arr[vehicle_index]);
				// vehicle2.loadImages("s");
			} else { // Solar car
				vehicle_arr[vehicle_index] = new Car(v_col, this, new SolarEngine(Car.car_MaxfuelTank));
				myThreads[vehicle_index] = new Thread(vehicle_arr[vehicle_index]);
			}
			// System.out.println("index before run method: " + vehicle_index);
			// System.out.println("vehicle type before run method: " + userV);

			vehicle_arr[vehicle_index].v_indx = vehicle_index;
			vehicle_arr[vehicle_index].loadImages("s");
			// myThreads[vehicle_index].start();
			executor.execute(myThreads[vehicle_index]);
		} else {
			return;
		}
		// this.repaint();
		// vehicle_index++;
	}

	/**
	 * function update all vehicles in panel lights
	 */
	public void Lights_option() {
		for (int i = 0; i < Vehicle.runningVehicleArr.length; i++) {
			if (vehicle_arr[i] != null && Vehicle.runningVehicleArr[i] == true) {
				if (this.vehicle_arr[i].isLights() == false) {
					this.vehicle_arr[i].setLights(true);
				} else {
					this.vehicle_arr[i].setLights(false);
				}
			}
		}
	}

	// @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		if (clear) {
			clear = false;
			return;
		}
		for (int i = 0; i < total_threads; i++) {
			if (Vehicle.runningVehicleArr[i] == true) {
				vehicle_arr[i].drawObject(g);
				// check 2 vehicle durability and clear from screen
				checkCollisions(vehicle_arr[i]);
				// System.out.println(v_colide);
				// System.out.println("collide");
			}
		}

	}

	/**
	 * function open new frame, show all relevant information for collision between
	 * vehicles in system
	 */
	public void Get_Info() {
		int count = 0;
		int collision_num = 0;
		int size_f = 400;
		JFrame info_frame = new JFrame("Vehicle Information");
		JPanel info_panel = new JPanel();
		info_frame.setSize(500, 200);
		for (Vehicle v : vehicle_arr) {
			// check if we have vehicle created in system
			if (v != null) {
				count++;
			}
		}
		// At least one vehicle exists in the system
		if (count != 0) {
			JPanel btnPnl = new JPanel();
			JPanel mainPanel = new JPanel(new BorderLayout());
			if (numOfCollision() != 0) {
				info_panel.setBorder(BorderFactory.createTitledBorder("Collisions Details"));
				collision_num = numOfCollision();
				JLabel[] label = new JLabel[collision_num];
				BoxLayout boxlayout = new BoxLayout(info_panel, BoxLayout.Y_AXIS);
				info_panel.setLayout(boxlayout);
				for (int i = 0; i < label.length; i++) {
					label[i] = new JLabel("No." + (i + 1) + ": " + Vehicle.crushEvents[i] + ".");
					// info_frame.setSize(size_f+200,150);
					info_panel.add(label[i]);
				}

			} else {
				JLabel label = new JLabel("There were no collisions between the vehicles created in the system.");
				info_frame.setSize(800, 150);
				info_panel.add(label);
			}
			JButton jB = new JButton("OK");
			jB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/** Method when listen to action -> what action want to perform? */
					info_frame.setVisible(false);
				}
			}); /** End ActionListener */
			// JPanel jp=new JPanel();

			// jp.add(jB);
			if (collision_num != 0) {
				info_frame.setSize(600, size_f);
			}
			btnPnl.add(jB);
			mainPanel.add(info_panel, BorderLayout.NORTH);
			mainPanel.add(btnPnl, BorderLayout.SOUTH);
			info_frame.add(mainPanel);
			info_frame.setVisible(true);
		} else // No vehicles were created in the system
			JOptionPane.showMessageDialog(null, "No vehicles Added");/** Creates a JOptionPane (pop-up window) */

	}

	/**
	 * function clear vehicles from screen, by kill thread's
	 */
	private void clear() {
		this.clear = true;
		// notify all threads threads in system
		for (int i = 0; i < total_threads; i++) {
			if (vehicle_arr[i] != null) {
				vehicle_arr[i].stopThread();
				Vehicle.runningVehicleArr[i] = false;
			} else
				break;
		}
		//System.out.println("After flag");
		// wake-up all threads that are in wait status
		// ונמצאים במצב של WAIT להעיר את כל הטרדים שנמצאים במערכת
		synchronized (Vehicle.emptyFuel) {
			Vehicle.emptyFuel.notifyAll();
		}
		//System.out.println("After notifyAll");
		// make sure thread is dead
		for (int i = 0; i < total_threads; i++) {
			try {
				if (vehicle_arr[i] != null) {
					//System.out.println("myThreads[i].join(");
					myThreads[i].join();
					//System.out.println("after myThreads[i].join(");

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		repaint();

	}

	/**
	 * The function exits program. Before we exit the program we kill all the treads
	 * that running in program.
	 */
	public void exitSystem(ActionEvent e) {
		clear();
		//System.out.println("clear");
		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		win.dispose();
	}

	/**
	 * function check if we have any collision between vehicles in system and
	 * according to each vehicle durability decide if vehicle stays or disappear
	 * 
	 * @param v - Vehicle object
	 * @return true if had collision, else--> false
	 */
	public boolean checkCollisions(Vehicle v) {

		Rectangle r1 = v.getBounds();
		for (Vehicle vehicle_obj : vehicle_arr) {
			if (vehicle_obj != v && vehicle_obj != null && vehicle_obj.isRunning == true) { // ?
				Rectangle r2 = vehicle_obj.getBounds();

				if (r1.intersects(r2)) { // --> 2 vehicle's collide
					// System.out.println("in checkCollisions method- return true");
					// check 2 vehicle durability and clear from screen:

					if (v.getDurability() > vehicle_obj.getDurability()) {
						// vehicle-"v" stays on jPanel--> clearing vehicle-"vehicle_obj"
						Vehicle.runningVehicleArr[vehicle_obj.v_indx] = false;
						Vehicle.crushEvents_index++;
						Vehicle.crushEvents[Vehicle.crushEvents_index] = V_getInstance(vehicle_obj)
								+ " collide with--> " + V_getInstance(v);
						//System.out.println(Vehicle.crushEvents[Vehicle.crushEvents_index]);
						vehicle_obj.isRunning = false; // kill thread
						synchronized (Vehicle.emptyFuel) {
							Vehicle.emptyFuel.notifyAll();
						}
						break;

					} else if (v.getDurability() < vehicle_obj.getDurability()) {
						// vehicle-"vehicle_obj" stays on jPanel--> clearing vehicle-"v"
						v.isRunning = false;// kill thread for specific vehicle
						Vehicle.runningVehicleArr[v.v_indx] = false;
						Vehicle.crushEvents_index++;
						Vehicle.crushEvents[Vehicle.crushEvents_index] = V_getInstance(v) + " collide with--> "
								+ V_getInstance(vehicle_obj);
						//System.out.println(Vehicle.crushEvents[Vehicle.crushEvents_index]);
						synchronized (Vehicle.emptyFuel) {
							Vehicle.emptyFuel.notifyAll();
						}
						break;

					} else { // --->v.getDurability()==vehicle_obj.getDurability() clear both vehicles from
								// JPanel
						Vehicle.crushEvents_index++;
						Vehicle.crushEvents[Vehicle.crushEvents_index] = V_getInstance(v) + " collide with--> "
								+ V_getInstance(vehicle_obj);
						//System.out.println(Vehicle.crushEvents[Vehicle.crushEvents_index]);
						// kill thread for specific 2 vehicles
						vehicle_obj.isRunning = false;
						v.isRunning = false;
						Vehicle.runningVehicleArr[v.v_indx] = false;
						Vehicle.runningVehicleArr[vehicle_obj.v_indx] = false;
						synchronized (Vehicle.emptyFuel) {
							Vehicle.emptyFuel.notifyAll();
						}
						break;
					}

				}
			}
		}
		// System.out.println("in checkCollisions method- return false");
		return false;

	}

	/**
	 * function counts total collisions that happened in system
	 * 
	 * @return number of total collisions
	 */
	private int numOfCollision() {
		int count_totalCollisions = 0;
		for (int i = 0; i < Vehicle.crushEvents.length; i++) {
			if (Vehicle.crushEvents[i] != null)
				count_totalCollisions++;
		}
		return count_totalCollisions;
	}

	/**
	 * function find vehicle type
	 * 
	 * @param v "Vehicle" object
	 * @return string--> pacific object name
	 */
	private String V_getInstance(Vehicle v) {
		if (v instanceof HasEngine) {
			if (((HasEngine) v).getEngine().getVehicleName().equals("Benzine car")) {// Benzene car
				return "Benzine car";
			} else
				return "Solar car";

		} else if (v instanceof Carriage) {
			return ((Carriage) v).getVehicleName();
		}
		return ((Bike) v).getVehicleName();
	}

	void ExitFrame() {
		clear();
	}
}
