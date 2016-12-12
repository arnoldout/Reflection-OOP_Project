package ie.gmit.sw;

public class ClassNamePair {

	private Class<?> c;
	private String name;

	public ClassNamePair(Class<?> c, String name) {
		super();
		this.c = c;
		this.name = name;
	}
	
	public Class<?> getC() {
		return c;
	}
	public void setC(Class<?> c) {
		this.c = c;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
