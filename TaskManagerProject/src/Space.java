/**
 * 
 * @author tteper
 * 
 * TaskManager Space class
 *
 */
public class Space {
	
	private Space parentSpace;
	private String name;
	
	/**
	 * Default constructor. Used to construct the 
	 * "My Tasks" space.
	 * @param name the name of space to be created
	 */
	public Space(String n) {
		this.name = n;
		this.parentSpace = null;
	}
	
	/**
	 * Special constructor
	 * @param parent the name of the target parent space
	 * @param name the name of space to be created
	 */
	public Space(Space parent, String n) {
		this.parentSpace = parent;
		this.name = n;
	}
	
	/**
	 * Setter method for modifying Space name
	 * @param n the name of the space to be modified
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Getter method for retrieving Space name
	 * @return String name of space
	 */
	@Override
	public String toString() {
		return name;
	}
	 
	/**
	 * Method to change the parent space of this space
	 * @param aSpace the target space
	 */
	public void moveTo(Space aSpace) {
		parentSpace = aSpace;
	}
	
	/**
	 * Getter method for retrieving parent Space name
	 * @return String name of parent space
	 */
	public String getParentName() {
		if(parentSpace == null)
		{
			return "";
		}
		return parentSpace.toString();
	}
	
	public Space getParentSpace() {
//		Space tPSpace = new Space(parentSpace.parentSpace, parentSpace.toString());
//		return tPSpace; // def breaks encap
		return parentSpace;
	}
}