package graphics;

import javax.swing.*;

import vehicles.Carriage;
import vehicles.Point;
import vehicles.Vehicle;
import vehicles.Vehicle.Color;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Limor Shavit, ID: 206227787.
 *
 */

public class CityFrame extends JFrame { // implements ActionListener {

	private static CityPanel city; // change static
	static Vehicle v;//
	JMenuBar menuBar;
	static JMenuBar mb;

	// JMenu
	static JMenu file, help;

	// Menu items
	static JMenuItem exit, hlp;

	// create a frame
	JFrame frame = new JFrame();

	public CityFrame() /* throws HeadlessException */ {
		super("City");
		
		city = new CityPanel();
		setPreferredSize(new Dimension(800,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create a menu bar
		mb = new JMenuBar();

		// create a menu
		file = new JMenu("File");
		help = new JMenu("Help");

		// create menu items
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				city.ExitFrame();
				System.exit(0);
			}
		});
		
		hlp = new JMenuItem("Help");
		hlp.addActionListener(new ActionListener() { /**Added "ears" to button; so can "listen" for something to do*/
            @Override
            public void actionPerformed(ActionEvent e) { 
            	/**Method when listen to action -> what action want to perform?*/
                // TODO Auto-generated method stub
                JOptionPane.showMessageDialog(null, "Home Work 3\nGUI"); 
                /**Creates a JOptionPane (THINK: pop-up window)*/
            }
        }); /**End ActionListener*/

		// add menu items to menu
		file.add(exit);
		help.add(hlp);

		// add menu to menu bar
		mb.add(file);
		mb.add(help);

		// add menu bar to frame
		setJMenuBar(mb);
	}

	/**
	 * System Driver
	 * @param args
	 */
	public static void main(String[] args) {

		//Location l=new Location(new Point(1,2),vehicles.Location.Orientation.East);
		//Vehicle v=new Carriage(1000,Color.red,4, l,10);
		
		CityFrame C_frame = new CityFrame();
		C_frame.getContentPane().add(C_frame.city);
		C_frame.pack();
		C_frame.setVisible(true);
		
		//v=new Carriage(Color.green,city);
		//System.out.println(v.drive(new Point(2, 4)));

	}

}
