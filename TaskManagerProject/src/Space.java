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
	 * Default constructor
	 * @param name the name of space to be created
	 */
	public Space(String n) {
		this.name = n;
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
		return parentSpace.toString();
	}
	
	public void delete() {
		this.name = null;
		this.parentSpace = null;
	}
}
