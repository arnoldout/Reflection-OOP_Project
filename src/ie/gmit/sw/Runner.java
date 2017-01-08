package ie.gmit.sw;

import java.io.IOException;

public class Runner {
	private static String jarFile = "src/reflectAss1.jar";

	public static void main(String[] args) throws IOException {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
		
		//parse jarFile
		//JarParser jp = new JarParser();
		//jp.parse(jarFile);
	}
}
