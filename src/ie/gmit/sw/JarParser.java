package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarParser {

	
	public Map<String, TypeCoupler> parse(File file){
		Map<String, TypeCoupler> adjacencyList = extractMap(file);
		parseAdjacencyList(adjacencyList);
		return adjacencyList;
	}
	
	
	private Class<?> findClass(String name, String jarFile) throws MalformedURLException, ClassNotFoundException {
		URL[] urls = { new URL("jar:file:" + jarFile + "!/") };
		URLClassLoader child = new URLClassLoader(urls, this.getClass().getClassLoader());
		return Class.forName(name, true, child);
	}
	
	private Map<String, TypeCoupler> extractMap(File jarFile)
	{
		Map<String, TypeCoupler> adjacencyList = new HashMap<String, TypeCoupler>();
		try {
			JarInputStream in = new JarInputStream(new FileInputStream(jarFile));
			JarEntry next = in.getNextJarEntry();
			while (next != null) {
				System.out.println(next);
				if (next.getName().endsWith(".class")) {
					String name = next.getName().replaceAll("/", "\\.");
					name = name.replaceAll(".class", "");
					if (!name.contains("$"))
						name.substring(0, name.length() - ".class".length());
					try {
						Class<?> cls = findClass(name, jarFile.getAbsolutePath());
						System.out.println(name);
						TypeCoupler cnp = new TypeCoupler(cls);
						if (!adjacencyList.containsKey(name)) {
							adjacencyList.put(name, cnp);
						}
					} catch (NoClassDefFoundError | ClassNotFoundException e) {
						e.printStackTrace();
						System.out.println("Bombed out");
					}
				}
				next = in.getNextJarEntry();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adjacencyList;
	}
	private void parseAdjacencyList(Map<String, TypeCoupler> adjacencyList)
	{
		for (Map.Entry<String, TypeCoupler> entry : adjacencyList.entrySet()) {
			//get value to parse itself and then append to the efferent and afferent sets
			entry.getValue().coupleClassArr(ClassParser.getInstance().parse(entry.getValue()), adjacencyList);
		}
	}
}
