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
		spaceList.add(new Space("My Tasks"));
	}
	
	public void addSpace(Space aParent, String n) {
		Space finalParent = aParent;
		if(finalParent == null)
		{
			finalParent = spaceList.get(0);
		}
		spaceList.add(new Space(finalParent, n));
	}
	
	public void editSpace(Space aParent, int position, String n) {
		Space tempSpace = spaceList.get(position);
		tempSpace.setName(n);
		spaceList.set(position, tempSpace);
	}
	
	public void deleteSpace(int position) {
		
		if (position == 0)
			System.out.println("My Tasks!");
			// throw
		else {
			// check that there are no sub-spaces
			for (Space s : spaceList) {
				
				// if current space has target space as parent
				if (s.getParentName().equals(spaceList.get(position).toString()))
					System.out.println("Did something!");
					// throw exception that space has sub-spaces
			}
			
			/*
			 * Reassign all tasks that have the space to be deleted as parent to My Tasks
			 */
			spaceList.remove(position);
		}
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
