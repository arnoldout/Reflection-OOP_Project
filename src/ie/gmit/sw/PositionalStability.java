package ie.gmit.sw;
/**
 * 
 * @author Oliver
 *	PositionalStability is a singleton class that is used to calculate the
 *	Positional Stability of 2 doubles.
 *
 */
public class PositionalStability {
	private static PositionalStability ps = new PositionalStability();
	private PositionalStability()
	{
		
	}
	public static PositionalStability getInstance()
	{
		return ps;
	}
	/**
	 * 
	 * @param sizeAfferent - The number of afferent pairings
	 * @param sizeEfferent - The number of efferent pairings
	 * @return the calculated Positional Stability
	 */
	public double getStability(double sizeAfferent, double sizeEfferent)
	{
		double afferent = sizeAfferent;
		double efferent = sizeEfferent;
		
		double positionalStability = efferent / (afferent + efferent);
		
		//to avoid NAN results, from dividing 0 by 0, positional Stability set to 0 if both efferent and afferent are also 0
		if (afferent == 0.0 && efferent == 0.0) {
			positionalStability = 0.0;
		}
		return positionalStability;
	}
}
