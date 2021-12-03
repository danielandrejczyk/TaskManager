package Main;
import java.io.Serializable;

/**
 * 
 * @author tteper
 * 
 * Space class for creating space objects
 *
 */
@SuppressWarnings("serial")
public class Space implements Serializable {
	
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
	 * Special constructor with parent space
	 * @param parent the name of the target parent space
	 * @param name the name of space to be created
	 */
	public Space(Space parent, String n) {
		this.parentSpace = parent;
		this.name = n;
	}
	
	/**
	 * Modifies space name
	 * @param n the name of the space to be modified
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Retrieves space name
	 * @return name of space
	 */
	@Override
	public String toString() {
		return name;
	}
	 
	/**
	 * Changes the parent space of this space
	 * @param aSpace, the target space
	 */
	public void moveTo(Space aSpace) {
		parentSpace = aSpace;
	}
	
	/**
	 * Retrieves parent space name
	 * @return name of parent space
	 */
	public String getParentName() {
		if(parentSpace == null)
		{
			return "";
		}
		return parentSpace.toString();
	}
	
	/**
	 * Getter method for retrieving parent space object
	 * @return the parents space of the space
	 */
	public Space getParentSpace() {
		return parentSpace;
	}
}