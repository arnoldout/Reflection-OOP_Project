package ie.gmit.sw;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

public class ClassParser {

	private static ClassParser cp;

	private ClassParser() {
	}

	public static ClassParser getInstance() {
		if (cp == null) {
			cp = new ClassParser();
		}
		return cp;
	}
	public void parseClassArr(Class<?>[] classArr, TypeCoupler tc, Map<Class<?>, TypeCoupler>adjacencyList)
	{
		for(Class<?> c : classArr)
		{
			System.out.println(c.getName());
			tc.addEfferentClass(c);
			if(adjacencyList.containsKey(c))
			{
				adjacencyList.get(c).addAfferentClass(tc.getBaseClass());
			}
		}
	}

	public Class<?> findClass(String name, String jarFile) throws MalformedURLException, ClassNotFoundException {
		URL[] urls = { new URL("jar:file:" + jarFile + "!/") };
		URLClassLoader child = new URLClassLoader(urls, this.getClass().getClassLoader());
		return Class.forName(name, true, child);
	}
}
