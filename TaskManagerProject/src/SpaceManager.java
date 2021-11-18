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
	
	public void AddSpace(Space aParent, String n) {
		spaceList.add(new Space(aParent, n));
	}
	
	public void EditSpace(int position, String n) {
		Space tempSpace = spaceList.get(position);
		tempSpace.SetName(n);
		spaceList.set(1, tempSpace);
	}
	
	public void DeleteSpace(int position) {
		spaceList.get(position).Delete();
		spaceList.remove(position);
	}
	
	public void SelectSpace() {
		
	}
	
	/**
	 * Returns a copy of the current task spaces
	 * @return ArrayList, a copy of the spaces in the task application
	 */
	public ArrayList<Space> GetSpaceList() {
		return spaceList;
	}
}
