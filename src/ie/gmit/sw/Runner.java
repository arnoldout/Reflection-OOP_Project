package ie.gmit.sw;

import java.io.IOException;

public class Runner {

	public static void main(String[] args) throws IOException {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
	}
}
