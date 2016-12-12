package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.lang.reflect.*;

public class Runner {
	public static void main(String[] args) {
		Map<ClassNamePair, List<Class<?>>> adjacencyList = new HashMap<ClassNamePair, List<Class<?>>>();

		try {
			JarInputStream in = new JarInputStream(new FileInputStream(new File("src/string-service.jar")));
			JarEntry next = in.getNextJarEntry();
			while (next != null) {
				if (next.getName().endsWith(".class")) {
					Class<?> c = next.getClass();
					ClassNamePair cnp = new ClassNamePair(c, c.getName());
					if (!adjacencyList.containsKey(cnp)) {
						adjacencyList.put(cnp, new ArrayList<Class<?>>());
					}
				}
				next = in.getNextJarEntry();
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Map.Entry<ClassNamePair, List<Class<?>>> entry : adjacencyList.entrySet()) {
			System.out.println("FARTTTT"+entry.getKey().getName() + "/" + entry.getValue());
		}
	}
}