package graphics;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSplitPane;

public class AddVehicleDialog extends JDialog {
	String[] vehicleType = { "Benzine car", "Solar car", "Bike", "Carriage" };
	String[] colorType = { "Red", "Green", "Silver", "White" };
	public JRadioButton radioButtonleftV[] = new JRadioButton[4];
	public JRadioButton radioButtonright_col[] = new JRadioButton[4];
	CityPanel pan = new CityPanel();
	String user_car = null;
	String user_color = null;
	int gear_bike;
	int flag_v = 0;
	int flag_c = 0;

	/**
	 * AddVehicleDialog class constructor
	 */
	public AddVehicleDialog(CityPanel citypanel) {

		gear_bike = 0;
		JFrame radioBframe = new JFrame("Add a Vehicle to the city");
		radioBframe.setResizable(false);
		JSplitPane splitPanel = new JSplitPane();

		// This creates one row and two equally divided columns
		GridLayout gridLayout = new GridLayout(3, 0);
		radioBframe.setLayout(gridLayout);
		gridLayout.layoutContainer(radioBframe);

		/** Left panel: Vehicle type */
		JPanel leftPanelV = new JPanel();
		leftPanelV.setLayout(new GridLayout(2, 2));
		ButtonGroup groupleft = new ButtonGroup();
		for (int i = 0; i < vehicleType.length; ++i) {
			/** create radio buttons */
			radioButtonleftV[i] = new JRadioButton();
			radioButtonleftV[i].setText(vehicleType[i]);
			groupleft.add(radioButtonleftV[i]);
			leftPanelV.add(radioButtonleftV[i]);
		}
		splitPanel.setLeftComponent(leftPanelV);
		radioBframe.add(splitPanel);

		/** Right panel: vehicle color */
		JPanel rightPanelcol = new JPanel();
		rightPanelcol.setLayout(new GridLayout(0, 4));
		ButtonGroup groupright = new ButtonGroup();
		for (int i = 0; i < colorType.length; ++i) {
			/** create radio buttons */
			radioButtonright_col[i] = new JRadioButton();
			radioButtonright_col[i].setText(colorType[i]);
			groupright.add(radioButtonright_col[i]);
			rightPanelcol.add(radioButtonright_col[i]);
		}
		splitPanel.setRightComponent(rightPanelcol);
		radioBframe.add(splitPanel);

		/** Slider: */
		// Frame radio buttons
		JPanel panel2b = new JPanel();
		// panel2b.setLayout(new GridBagLayout());
		JButton OK1 = new JButton("  OK    ");
		panel2b.add(OK1);
		JButton cancel1 = new JButton("Cancel");
		panel2b.add(cancel1);
		setLayout(new BorderLayout());
		radioBframe.add(panel2b);
		radioBframe.setSize(700, 250);
		radioBframe.setVisible(true);

		leftPanelV.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Choose a Vehicle type"));
		
		rightPanelcol.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Choose a Color"));

		OK1.addActionListener(new ActionListener() {// choose OK
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < colorType.length; ++i) {
					if (radioButtonright_col[i].isSelected()) {
						user_color = colorType[i];
						flag_c = 1;
					}
				}

				for (int i = 0; i < vehicleType.length; ++i) {
					if (radioButtonleftV[i].isSelected()) {
						user_car = vehicleType[i];
						flag_v = 1;
					}
				}

				if (flag_c == 0 || flag_v == 0) { 
					JOptionPane.showMessageDialog(null, "No value entered,\nYou must chose Vehicle type and color.");
					citypanel.botton[0].setEnabled(true);
					radioBframe.setVisible(false);
					return;
				}

				// build vehicle + add vehicle to panel view:
				citypanel.add_v(user_car, user_color, gear_bike);
				
				
				// Hide "Add_vehicle" frame
				radioBframe.setVisible(false);

			}
		}); /** End ActionListener */

		cancel1.addActionListener(new ActionListener() {// choose cancel
			@Override
			public void actionPerformed(ActionEvent e) {
				/** Method when listen to action -> what action want to perform? */
				citypanel.botton[0].setEnabled(true);
				radioBframe.dispose();

			}
		}); /** End ActionListener */

		//Bike
		radioButtonleftV[2].addActionListener(new ActionListener() {
			/** Added "ears" to button; so can "listen" for something to do */
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame bikeFrame = new JFrame("Bike");
				GridLayout gridLayout = new GridLayout(2, 0);
				bikeFrame.setLayout(gridLayout);
				JPanel panel = new JPanel(new GridLayout(2, 0));
				JLabel sliderLabel = new JLabel("", JLabel.CENTER);
				bikeFrame.add(sliderLabel);
				sliderLabel.setText("Choose Bike Gears\n");
				JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 2);
				slider.setMinorTickSpacing(1);
				slider.setMajorTickSpacing(2);
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
				panel.add(sliderLabel);
				panel.add(slider);
				bikeFrame.add(panel);

				// bike gear chose slider
				JPanel panel2b = new JPanel();
				panel2b.setLayout(new GridBagLayout());

				// OK button slider frame
				JButton OK2 = new JButton("  OK    ");
				panel2b.add(OK2);

				// Cancel button slider frame
				JButton cancel2 = new JButton("Cancel");
				panel2b.add(cancel2);

				bikeFrame.add(panel2b);
				bikeFrame.setSize(350, 200);
				bikeFrame.setVisible(true);

				// cancel button gear frame action listener
				cancel2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						/** Method when listen to action -> what action want to perform? */
						bikeFrame.dispose();
					}
				}); /** End ActionListener */

				// OK button gear frame action listener
				OK2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gear_bike = slider.getValue();
						bikeFrame.dispose();
					}
				}); /** End ActionListener */
			}
		});
	}
}
