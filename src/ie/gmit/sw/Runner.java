package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Runner {
	private static String jarFile = "src/reflectAss1.jar";

	public static void main(String[] args) throws IOException {

		Map<String, TypeCoupler> adjacencyList = new HashMap<String, TypeCoupler>();
		try {
			JarInputStream in = new JarInputStream(new FileInputStream(new File(jarFile)));
			JarEntry next = in.getNextJarEntry();
			while (next != null) {
				System.out.println(next);
				if (next.getName().endsWith(".class")) {
					String name = next.getName().replaceAll("/", "\\.");
					name = name.replaceAll(".class", "");
					if (!name.contains("$"))
						name.substring(0, name.length() - ".class".length());
					try {
						Class<?> cls = ClassParser.getInstance().findClass(name, jarFile);
						System.out.println(name);
						TypeCoupler cnp = new TypeCoupler(cls);
						if (!adjacencyList.containsKey(name)) {
							adjacencyList.put(name, cnp);
						}
					} catch (NoClassDefFoundError | ClassNotFoundException e) {
						System.out.println("Bombed out");
					}
				}
				next = in.getNextJarEntry();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, TypeCoupler> entry : adjacencyList.entrySet()) {

			entry.getValue().doParsing(adjacencyList);
		}

		for (Map.Entry<String, TypeCoupler> entry : adjacencyList.entrySet()) {
			{
				System.out.println("----------------------------------------");
				System.out.println(entry.getValue().getBaseClass().getName());
				double afferent = entry.getValue().getAfferentCouplings().size();
				double efferent = entry.getValue().getEfferentCouplings().size();
				double positionalStability = efferent / (afferent + efferent);
				if(afferent==0.0&&efferent==0.0)
				{
					positionalStability  = 0.0;
				}
				
				System.out.println("Positional Stability :"+positionalStability);
			}

		}
	}
}
