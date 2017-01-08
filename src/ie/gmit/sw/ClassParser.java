package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassParser{

	private static ClassParser cp = new ClassParser();
	private ClassParser() {
	}
	public static ClassParser getInstance()
	{
		return cp;
	}
	
	public List<Class<?>> parse(TypeCoupler tc)
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.addAll(parseInterfaces(tc.getBaseClass()));
		classes.addAll(parseConstructors(tc.getBaseClass()));
		classes.addAll(parseFields(tc.getBaseClass()));
		classes.addAll(parseMethods(tc.getBaseClass()));
		return classes;
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
		return classes; //return all params and return types
	}
}
