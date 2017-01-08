package ie.gmit.sw;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Map;

public class AppWindow {
	private JFrame frame;

	public AppWindow() {
		// Create a window for the application
		frame = new JFrame();
		frame.setTitle("B.Sc. in Software Development - GMIT");
		frame.setSize(550, 500);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());

		// The file panel will contain the file chooser
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		top.setBorder(new javax.swing.border.TitledBorder("Select File to Encode"));
		top.setPreferredSize(new java.awt.Dimension(500, 100));
		top.setMaximumSize(new java.awt.Dimension(500, 100));
		top.setMinimumSize(new java.awt.Dimension(500, 100));

		final JTextField txtFileName = new JTextField(20);
		txtFileName.setPreferredSize(new java.awt.Dimension(100, 30));
		txtFileName.setMaximumSize(new java.awt.Dimension(100, 30));
		txtFileName.setMargin(new java.awt.Insets(2, 2, 2, 2));
		txtFileName.setMinimumSize(new java.awt.Dimension(100, 30));

		JButton btnChooseFile = new JButton("Browse...");
		btnChooseFile.setToolTipText("Select File to Encode");
		btnChooseFile.setPreferredSize(new java.awt.Dimension(90, 30));
		btnChooseFile.setMaximumSize(new java.awt.Dimension(90, 30));
		btnChooseFile.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnChooseFile.setMinimumSize(new java.awt.Dimension(90, 30));
		// A separate panel for the programme output
		JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEADING));
		mid.setBorder(new BevelBorder(BevelBorder.RAISED));
		mid.setPreferredSize(new java.awt.Dimension(500, 300));
		mid.setMaximumSize(new java.awt.Dimension(500, 300));
		mid.setMinimumSize(new java.awt.Dimension(500, 300));
		JTable tbl = new JTable();
		DefaultTableModel dtm = new DefaultTableModel(0, 0);

		btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fc = new JFileChooser("./");
				int returnVal = fc.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().getName().endsWith(".jar")) {
					File file = fc.getSelectedFile();
					String name = file.getAbsolutePath();
					txtFileName.setText(name);
					System.out.println("You selected the following file: " + name);

					JButton btnOther = new JButton("Process File");
					btnOther.setToolTipText("Process File");
					btnOther.setPreferredSize(new java.awt.Dimension(150, 30));
					btnOther.setMaximumSize(new java.awt.Dimension(150, 30));
					btnOther.setMargin(new java.awt.Insets(2, 2, 2, 2));
					btnOther.setMinimumSize(new java.awt.Dimension(150, 30));
					btnOther.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							tbl.clearSelection();
							JarParser jp = new JarParser();
							Map<String, TypeCoupler> adjacencyList = jp.parse(file);

							String header[] = new String[] { "Class", "Score" };
							dtm.setColumnIdentifiers(header);
							tbl.setModel(dtm);
							JScrollPane jsb = new JScrollPane(tbl);
							for (Map.Entry<String, TypeCoupler> entry : adjacencyList.entrySet()) {
								{
									double afferent = entry.getValue().getAfferentCouplings().size();
									double efferent = entry.getValue().getEfferentCouplings().size();
									double positionalStability = efferent / (afferent + efferent);
									if (afferent == 0.0 && efferent == 0.0) {
										positionalStability = 0.0;
									}
									dtm.addRow(new Object[] { entry.getKey(), positionalStability });
								}

							}
							mid.add(jsb);

							mid.validate();
						}
					});
					top.add(btnOther);
					top.validate();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid File Type");
				}
			}
		});

		top.add(txtFileName);
		top.add(btnChooseFile);

		frame.getContentPane().add(top); // Add the panel to the window

		/*
		 * CustomControl cc = new CustomControl(new java.awt.Dimension(500,
		 * 300)); cc.setBackground(Color.WHITE); cc.setPreferredSize(new
		 * java.awt.Dimension(300, 300)); cc.setMaximumSize(new
		 * java.awt.Dimension(300, 300)); cc.setMinimumSize(new
		 * java.awt.Dimension(300, 300)); mid.add(cc);
		 */
		frame.getContentPane().add(mid);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottom.setPreferredSize(new java.awt.Dimension(500, 50));
		bottom.setMaximumSize(new java.awt.Dimension(500, 50));
		bottom.setMinimumSize(new java.awt.Dimension(500, 50));

		JButton btnQuit = new JButton("Quit"); // Create Quit button
		btnQuit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
		bottom.add(btnQuit);

		frame.getContentPane().add(bottom);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new AppWindow();
	}
}