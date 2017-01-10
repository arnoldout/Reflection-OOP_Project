package ie.gmit.sw;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Oliver
 *
 *Type coupler stores the base class and the afferent, efferent couplings of that class together
 */
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
	
	/**
	 * add a class to the efferent set, send a request to the class to add this base class as afferent 
	 * @param classes - list of classes to be processed as efferent and afferent
	 * @param adjacencyList - check if class appears in the jar file (will prevent java library objects from being added)
	 */
	public void coupleClassArr(List<Class<?>> classes, Map<String, TypeCoupler>adjacencyList)
	{
		for(Class<?> c : classes)
		{
			if(checkList(adjacencyList, c))
			{
				//add an efferent and afferent only when classes are already populated in map
				this.addEfferentClass(c);
				adjacencyList.get(c.getName()).addAfferentClass(this.getBaseClass());
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