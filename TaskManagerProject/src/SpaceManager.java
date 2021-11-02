/**
 * @author: Tom
 * 
 * This class is for Space Manager
 */
import java.util.ArrayList;

public class SpaceManager {
	
	private ArrayList<Space> spaceList;
	
	/**
	 * Default constructor for SpaceManager class
	 * 
	 * Creates a default My Tasks space and adds to list of spaces
	 */
	public SpaceManager() {
		Space MY_TASKS = new Space("My Tasks");
		spaceList.add(MY_TASKS);
	}
	
	public void AddSpace(Space aParent) {
	
	}
	
	public void EditSpace(Space aSpace) {
		
	}
	
	public void DeleteSpace(Space aSpace) {
		
	}
	
	public void SelectSpace() {
		
	}
}
