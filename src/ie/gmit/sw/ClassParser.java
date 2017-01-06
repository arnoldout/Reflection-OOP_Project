package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	public void coupleClassArr(List<Class<?>> classes, TypeCoupler tc, Map<String, TypeCoupler>adjacencyList)
	{
		for(Class<?> c : classes)
		{
			if(checkList(adjacencyList, c))
			{
				//add an efferent and afferent only when classes are already populated in map
				tc.addEfferentClass(c);
				adjacencyList.get(c.getName()).addAfferentClass(tc.getBaseClass());
			}
		}
	}
	private boolean checkList(Map<String, TypeCoupler> adjacencyList, Class<?> c)
	{
		if(adjacencyList.containsKey(c.getName()))
		{
			return true;
		}
		return false;
	}
	
	public List<Class<?>> parseInterfaces(Class<?> currentClass)
	{
		return Arrays.asList(currentClass.getInterfaces()); // Get the set of interface it implements
	}
	public List<Class<?>> parseConstructors(Class<?> currentClass)
	{
		List <Class<?>> classes = new ArrayList<Class<?>>();
		Constructor<?>[] cons = currentClass.getConstructors(); // Get the set of constructors
		for (int i = 0; i < cons.length; i++) {
			Class<?>[] constructorParams = cons[i].getParameterTypes(); // Get the parameters
			classes.addAll(Arrays.asList(constructorParams));
		}
		return classes;
	}
	public List<Class<?>> parseFields(Class<?> currentClass)
	{
		Field[] fields = currentClass.getFields(); // Get the fields / attributes
		Class<?>[] fieldTypes  = new Class<?>[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldTypes[i] = fields[i].getDeclaringClass();
		}
		return Arrays.asList(fieldTypes);
	}
	public List<Class<?>> parseMethods(Class<?> currentClass)
	{
		List <Class<?>> classes = new ArrayList<Class<?>>();
		Method[] methods = currentClass.getMethods(); // Get the set of methods
		for (int i = 0; i < methods.length; i++) {
			classes.add(methods[i].getReturnType()); // Get a method return type
			classes.addAll(Arrays.asList(methods[i].getParameterTypes())); // Get method parameters
		}
		return classes;
	}

	public Class<?> findClass(String name, String jarFile) throws MalformedURLException, ClassNotFoundException {
		URL[] urls = { new URL("jar:file:" + jarFile + "!/") };
		URLClassLoader child = new URLClassLoader(urls, this.getClass().getClassLoader());
		return Class.forName(name, true, child);
	}
}
