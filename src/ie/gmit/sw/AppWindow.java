package ie.gmit.sw;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Map;

/**
 * 
 * @author Oliver/Dr. John Healy
 * Draws a swing program for adding a file and processing it using the positional stability calculating process
 */
public class AppWindow {
	private JFrame frame;
	private File uploadedFile;

	public AppWindow() {
		// Create a window for the application
		frame = new JFrame();
		frame.setTitle("B.Sc. in Software Development - GMIT");
		frame.setSize(550, 550);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());

		// The file panel will contain the file chooser
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		top.setBorder(new javax.swing.border.TitledBorder("Select File to Process"));
		top.setPreferredSize(new java.awt.Dimension(500, 100));
		top.setMaximumSize(new java.awt.Dimension(500, 100));
		top.setMinimumSize(new java.awt.Dimension(500, 100));

		final JTextField txtFileName = new JTextField(20);
		txtFileName.setPreferredSize(new java.awt.Dimension(100, 30));
		txtFileName.setMaximumSize(new java.awt.Dimension(100, 30));
		txtFileName.setMargin(new java.awt.Insets(2, 2, 2, 2));
		txtFileName.setMinimumSize(new java.awt.Dimension(100, 30));

		JButton btnChooseFile = new JButton("Browse...");
		btnChooseFile.setToolTipText("Select File to Process");
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

		String header[] = new String[] { "Class", "Score" };
		dtm.setColumnIdentifiers(header);
		tbl.setModel(dtm);
		JScrollPane jsb = new JScrollPane(tbl);
		jsb.setPreferredSize(new java.awt.Dimension(485, 290));
		mid.add(jsb, BorderLayout.NORTH);

		btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fc = new JFileChooser("./");
				int returnVal = fc.showOpenDialog(frame);
				//file must be .jar
				if (returnVal == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().getName().endsWith(".jar")) {
					uploadedFile = fc.getSelectedFile();
					String name = uploadedFile.getAbsolutePath();
					txtFileName.setText(name);
					System.out.println("You selected the following file: " + name);

				} else {
					JOptionPane.showMessageDialog(null, "Invalid File Type");
				}
			}
		});

		JButton btnOther = new JButton("Process File");
		btnOther.setToolTipText("Process File");
		btnOther.setPreferredSize(new java.awt.Dimension(150, 30));
		btnOther.setMaximumSize(new java.awt.Dimension(150, 30));
		btnOther.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnOther.setMinimumSize(new java.awt.Dimension(150, 30));
		btnOther.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//ensure a file is waiting to be processed first
				if (uploadedFile != null) {
					//parse jar file
					JarParser jp = new JarParser(uploadedFile);
					jp.parse();
					//wipe table if one already exists
					dtm.setRowCount(0);

					for (Map.Entry<String, TypeCoupler> entry : jp.getResult().entrySet()) {
						//get instance of singleton PositionalStability
						double positionalStability = PositionalStability.getInstance().getStability(entry.getValue().getAfferentCouplings().size(), entry.getValue().getEfferentCouplings().size());
						//output result to table
						dtm.addRow(new Object[] { entry.getKey(), positionalStability });

					}
				} else {
					//if no file uploaded yet, tell user to upload one
					JOptionPane.showMessageDialog(frame, "No File to process!", "Upload a file first",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		top.add(txtFileName);
		top.add(btnChooseFile);

		top.add(btnOther);
		frame.getContentPane().add(top); // Add the panel to the window

		frame.getContentPane().add(mid);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottom.setPreferredSize(new java.awt.Dimension(500, 100));
		bottom.setMaximumSize(new java.awt.Dimension(500, 100));
		bottom.setMinimumSize(new java.awt.Dimension(500, 100));

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