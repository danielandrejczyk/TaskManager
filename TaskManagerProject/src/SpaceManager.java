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
	
	/**
	 * Add space to space list
	 * 
	 * @param aParent, the parent class of the new space
	 * @param n, the name of the new space
	 */
	public void addSpace(Space aParent, String n) {
		Space finalParent = aParent;
		if(finalParent == null)
		{
			finalParent = spaceList.get(0);
		}
		spaceList.add(new Space(finalParent, n));
	}
	
	/**
	 * Edit space in space list
	 * 
	 * @param aParent, the parent class (new or same) of the edited space
	 * @param position, the position of the target edited space in spaceList
	 * @param n, the new (or old) name of the space
	 */
	public void editSpace(Space aParent, int position, String n) {
		Space tempSpace = spaceList.get(position);
		tempSpace.setName(n);
		tempSpace.moveTo(aParent);
		spaceList.set(position, tempSpace);
	}
	
	/**
	 * Delete space in space list
	 * 
	 * @param position, the position of the target deleted space in spaceList
	 */
	public void deleteSpace(int position) {
		
		if (position == 0)
			System.out.println("My Tasks!");
			// throw
		else {
			// check that there are no sub-spaces
			for (Space s : spaceList) {
				
				// if current space has target space as parent
				if (s.getParentName().equals(spaceList.get(position).toString())) {
					s.moveTo(spaceList.get(0)); // by default, reassign subspaces to have parent space My Tasks
				}
			}
			spaceList.remove(position);
		}
	}
	
	
	/**
	 * Method to return the parent index of a particular space
	 * @param position, the location of the target space in spaceList
	 * @return int, the index of the parent space of the target space
	 */
	public int getParentIndex(int position) { 
		
		// cycle through until current space parent = some space in the list
		for (int i = 0; i < spaceList.size(); i++) {
			if (spaceList.get(position).getParentName().equals(spaceList.get(i).toString()))
				return i;
		}
		
		// if space has no parent
		return 0;
	}
	
	/**
	 * Returns a copy of the current task spaces
	 * @return ArrayList, a copy of the spaces in the task application
	 */
	public ArrayList<Space> getSpaceList() {
		return new ArrayList<Space>(spaceList);
	}
}
