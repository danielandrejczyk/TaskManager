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
	public Space(String name) {
		this.name = name;
	}
	
	/**
	 * Special constructor
	 * @param parent the name of the target parent space
	 * @param name the name of space to be created
	 */
	public Space(Space parent, String name) {
		this.parentSpace = parent;
		this.name = name;
	}
	
	/**
	 * Setter method for modifying Space name
	 * @param n the name of the space to be modified
	 */
	public void SetName(String n) {
		this.name = name;
	}
	
	/**
	 * Getter method for retrieving Space name
	 * @return String name of space
	 */
	public String GetName() {
		return name;
	}
	
	/**
	 * Method to change the parent space of this space
	 * @param aSpace the target space
	 */
	public void MoveTo(Space aSpace) {
		parentSpace = aSpace;
	}
	
	/**
	 * Getter method for retrieving parent Space name
	 * @return String name of parent space
	 */
	public String GetParentName() {
		return parentSpace.GetName();
	}
}
