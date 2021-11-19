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
		spaceList = new ArrayList<Space>();
		Space MY_TASKS = new Space("My Tasks");
		spaceList.add(MY_TASKS);
	}
	
	public void addSpace(Space aParent, String n) {
		Space finalParent = aParent;
		if(finalParent == null)
		{
			finalParent = spaceList.get(0);
		}
		spaceList.add(new Space(finalParent, n));
	}
	
	public void editSpace(int position, String n) {
		Space tempSpace = spaceList.get(position);
		tempSpace.setName(n);
		spaceList.set(position, tempSpace);
	}
	
	public void deleteSpace(int position) {
		
		// Check that this is a leaf space
		
		// Retrieve all tasks that belong to this space
		// and set them equal to MY_TASKS space
		
		//spaceList.get(position).delete();
		//Space oldSpace = spaceList.get(position);
		//oldSpace = null;
		spaceList.remove(position);
	}
	
	/* Hold on this
	public void selectSpace() {
		
	}
	*/
	
	/**
	 * Returns a copy of the current task spaces
	 * @return ArrayList, a copy of the spaces in the task application
	 */
	public ArrayList<Space> getSpaceList() {
		ArrayList<Space> tempList = spaceList;
		return tempList;
	}
}
