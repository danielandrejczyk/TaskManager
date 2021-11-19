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
		spaceList.add(new Space(aParent, n));
	}
	
	public void editSpace(int position, String n) {
		Space tempSpace = spaceList.get(position);
		tempSpace.setName(n);
		spaceList.set(position, tempSpace);
	}
	
	public void deleteSpace(int position) {
		spaceList.get(position).delete();
		spaceList.remove(position);
	}
	
	public void selectSpace() {
		
	}
	
	/**
	 * Returns a copy of the current task spaces
	 * @return ArrayList, a copy of the spaces in the task application
	 */
	public ArrayList<Space> getSpaceList() {
		ArrayList<Space> tempList = spaceList;
		return tempList;
	}
}
