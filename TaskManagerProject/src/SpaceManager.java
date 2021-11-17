/**
 * @author: Tom
 * 
 * This class is for Space Manager
 */
import java.util.ArrayList;

public class SpaceManager {
	
	private ArrayList<Space> spaceList;
	private int space_num;
	
	/**
	 * Default constructor for SpaceManager class
	 * 
	 * Creates a default My Tasks space and adds to list of spaces
	 */
	public SpaceManager() {
		space_num = 0;
		spaceList = new ArrayList<Space>();
		Space MY_TASKS = new Space("My Tasks");
		spaceList.add(MY_TASKS);
	}
	
	public void AddSpace(Space aParent, String n) {
		spaceList.add(new Space(n));
	}
	
	public void EditSpace(Space aSpace) {
		
	}
	
	public void DeleteSpace(Space aSpace) {
		
	}
	
	public void SelectSpace() {
		
	}
	
	/**
	 * Returns a copy of the current task spaces
	 * @return ArrayList, a copy of the spaces in the task application
	 */
	public ArrayList<Space> GetSpaceList() {
//		return (ArrayList<Space>)spaceList.clone();
		return spaceList;
	}
}
