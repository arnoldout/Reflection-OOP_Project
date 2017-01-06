package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	public void doParsing(Map<String, TypeCoupler> adjacencyList)
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.addAll(ClassParser.getInstance().parseInterfaces(this.baseClass));
		classes.addAll(ClassParser.getInstance().parseConstructors(this.baseClass));
		classes.addAll(ClassParser.getInstance().parseFields(this.baseClass));
		classes.addAll(ClassParser.getInstance().parseMethods(this.baseClass));
		
		ClassParser.getInstance().coupleClassArr(classes, this, adjacencyList);
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
