package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypeCoupler {
	private Class<?> baseClass;
	private Set<Class<?>> afferentCouplings;
	private Set<Class<?>> efferentCouplings;
	

	public TypeCoupler(Class<?> baseClass) {
		super();
		this.baseClass = baseClass;
		this.afferentCouplings = new HashSet<Class<?>>();
		this.efferentCouplings = new HashSet<Class<?>>();
	}
	
	public void doParsing(Map<Class<?>, TypeCoupler> adjacencyList)
	{
		Class<?> currentClass = this.baseClass;
		Package pack = currentClass.getPackage();// Get the package
		System.out.println(pack);
		boolean iFace = currentClass.isInterface(); // Is it an interface?
		if(iFace){
			Class<?>[] interfaces = currentClass.getInterfaces(); // Get the set of interface it implements
			ClassParser.getInstance().parseClassArr(interfaces, this, adjacencyList);
		}
		Constructor<?>[] cons = currentClass.getConstructors(); // Get the set of constructors
		
		for (int i = 0; i < cons.length; i++) {
			Class<?>[] constructorParams = cons[i].getParameterTypes(); // Get the parameters
			if(adjacencyList.containsKey(constructorParams))
			{
				adjacencyList.get(constructorParams).addAfferentClass(this.baseClass);
				ClassParser.getInstance().parseClassArr(constructorParams, this, adjacencyList);
			}
			else
			{
				System.out.println(adjacencyList.get(constructorParams));
			}
		}
		Field[] fields = currentClass.getFields(); // Get the fields / attributes
		Class<?>[] fieldTypes  = new Class<?>[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldTypes[i] = fields[i].getDeclaringClass();
		}
		ClassParser.getInstance().parseClassArr(fieldTypes, this, adjacencyList);
		
		Method[] methods = currentClass.getMethods(); // Get the set of methods
		for (int i = 0; i < methods.length; i++) {
			Class<?> c = methods[i].getReturnType(); // Get a method return type
			System.out.println(methods[i].getName());
			Class<?>[] methodParams = methods[i].getParameterTypes(); // Get method parameters
			ClassParser.getInstance().parseClassArr(methodParams, this, adjacencyList);
		}
	}
	public void addAfferentClass(Class<?> c)
	{
		if(!afferentCouplings.contains(c))
		{
			afferentCouplings.add(c);
		}
	}
	public void addEfferentClass(Class<?> c)
	{
		if(!efferentCouplings.contains(c))
		{
			efferentCouplings.add(c);
		}
	}
	
	public Class<?> getBaseClass() {
		return baseClass;
	}

	public Set<Class<?>> getAfferentCouplings() {
		return afferentCouplings;
	}
	public void setAfferentCouplings(Set<Class<?>> afferentCouplings) {
		this.afferentCouplings = afferentCouplings;
	}
	public Set<Class<?>> getEfferentCouplings() {
		return efferentCouplings;
	}
	public void setEfferentCouplings(Set<Class<?>> efferentCouplings) {
		this.efferentCouplings = efferentCouplings;
	}
	
	
}
