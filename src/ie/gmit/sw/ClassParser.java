package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Oliver
 *	ClassParser is an implementation of the parserable interface
 *	it is used to parse a class
 */
public class ClassParser implements Parserable{
	private List<Class<?>> classes;
	private Class<?> c;
	/**
	 * Constructor
	 * @param c - The Class to be parsed
	 */
	public ClassParser(Class<?> c) {
		this.c = c;
		classes = new ArrayList<Class<?>>();
	}
	/**
	 * Method called to parse a class for any interfaces, constructors, fields and methods
	 * Method calls individual private methods at once, and stores them into a list of classes
	 */
	public void parse()
	{
		classes.addAll(parseInterfaces(c));
		classes.addAll(parseConstructors(c));
		classes.addAll(parseFields(c));
		classes.addAll(parseMethods(c));
	}
	public List<Class<?>> getResult()
	{
		return classes;
	}
	/**
	 * 
	 * @param currentClass - Will be checked for interfaces
	 * @return - A list of classes of interfaces in the currentClass
	 */
	private List<Class<?>> parseInterfaces(Class<?> currentClass)
	{
		return Arrays.asList(currentClass.getInterfaces()); // Get the set of interface it implements
	}
	/**
	 * 
	 * @param currentClass - Will be checked for constructor parameters
	 * @return a list of classes pertaining to constructor parameters
	 */
	private List<Class<?>> parseConstructors(Class<?> currentClass)
	{
		List <Class<?>> classes = new ArrayList<Class<?>>();
		//loop over all constructors, check for params, add to list if params exist
		Constructor<?>[] cons = currentClass.getConstructors(); // Get the set of constructors
		for (int i = 0; i < cons.length; i++) {
			Class<?>[] constructorParams = cons[i].getParameterTypes(); // Get the parameters
			classes.addAll(Arrays.asList(constructorParams));
		}
		return classes;
	}
	/**
	 * 
	 * @param currentClass - Will be checked for fields
	 * @return a list of classes pertaining to the fields
	 */
	private List<Class<?>> parseFields(Class<?> currentClass)
	{
		Field[] fields = currentClass.getFields(); // Get the fields / attributes
		Class<?>[] fieldTypes  = new Class<?>[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldTypes[i] = fields[i].getDeclaringClass();
		}
		return Arrays.asList(fieldTypes);
	}
	/**
	 * 
	 * @param currentClass - Will be checked for methods
	 * @return a list of classes pertaining to any parameters in methods, and return types
	 */
	private List<Class<?>> parseMethods(Class<?> currentClass)
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
