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
/**
 * 
 * @author Oliver
 *	Jar parser is an implementation of the parserable interface runs through a jarfile, extracts any classes from it
 *	it stores the classes in TypeCoupler objects, and stores those typecouplers into a hashmap
 */
public class JarParser implements Parserable {

	private Map<String, TypeCoupler> adjacencyList;
	private File jarfile;
	/**
	 * Constructor
	 * @param jarfile - A file, should be of type .jar
	 */
	public JarParser(File jarfile)
	{
		this.jarfile = jarfile;
		adjacencyList = new HashMap<String, TypeCoupler>();
	}
	/**
	 * parse method calls private methods to build the adjacency list from the jar file
	 */
	public void parse(){
		adjacencyList = extractMap(jarfile);
		parseAdjacencyList(adjacencyList);
	}
	
	public Map<String, TypeCoupler> getResult()
	{
		return adjacencyList;
	}
	
	/**
	 * loads a class from a jar file
	 * @param name - name of class
	 * @param jarFile - jar file name
	 * @return - the requested class file
	 * @throws MalformedURLException - can occur if jar file url is not right
	 * @throws ClassNotFoundException - triggered if a class isn't found in the jar file
	 */
	private Class<?> findClass(String name, String jarFile) throws MalformedURLException, ClassNotFoundException {
		URL[] urls = { new URL("jar:file:" + jarFile + "!/") };
		URLClassLoader child = new URLClassLoader(urls, this.getClass().getClassLoader());
		return Class.forName(name, true, child);
	}
	/**
	 * Will parse file to populate a map with all of the class files in a jar
	 * 
	 * @param jarFile - jar file to be parsed
	 * @return - adjacency list
	 */
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
						System.out.println("Problem reading the class");
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
	/**
	 * loops through the map and populates the efferant and afferent pairings
	 * 
	 * @param adjacencyList - Map of classes, and typecouplers containing classes
	 */
	private void parseAdjacencyList(Map<String, TypeCoupler> adjacencyList)
	{
		
		for (Map.Entry<String, TypeCoupler> entry : adjacencyList.entrySet()) {
			//get value to parse itself and then append to the efferent and afferent sets
			ClassParser cp = new ClassParser(entry.getValue().getBaseClass());
			cp.parse();
			entry.getValue().coupleClassArr(cp.getResult(), adjacencyList);
		}
	}
}
