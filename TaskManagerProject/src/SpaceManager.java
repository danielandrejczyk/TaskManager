/**
 * @author: Tom
 * 
 * This class is for Space Manager
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SpaceManager {
	
	private int selectedSpaceIndex;
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
	
	@SuppressWarnings("unchecked")
	public void loadSpaces()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("spaces.dat"));
			spaceList = (ArrayList<Space>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file!");
		} catch (IOException e) {
			System.out.println("Error writing out task objects!");
		} catch (ClassNotFoundException e) {
			System.out.println("File not in the right format!");
		}
	}
	
	public void storeSpaces()
	{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("spaces.dat"));
			out.writeObject(spaceList);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file!");
		} catch (IOException e) {
			System.out.println("Error writing out task objects!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Add space to space list
	 * 
	 * @param aParent, the parent class of the new space
	 * @param n, the name of the new space
	 * @throws Exception, thrown when user performs action that is not permitted
	 */
	public void addSpace(Space aParent, String n) throws Exception {
		
		boolean nameExists = false;
		int parentIndex = 0;
		String prefix = "";
		//check that name isn't already used by another space
		for (Space s: spaceList) {
			if (s.toString().equals(n)) {
				nameExists = true;
			}
		}
		
		// if no parent is passed, default parent space to My Tasks
		if(aParent == null)
		{
			aParent = spaceList.get(0);
		}
		
//		for (Space s : spaceList) {
//			if (s.equals(aParent) && parentIndex == 0) {
//				//prefix = "-";
//				break;
//			}
//			else if (s.equals(aParent)) {
//				//prefix = aParent.toString().substring(0, aParent.toString().lastIndexOf("-") + 1) + "-";
//				break;
//			}
//			else
//				parentIndex++;
//		}
		
		// add space only if the name is unique
		if (!nameExists) {
			spaceList.add(new Space(aParent, n));
		}
		else 
			throw new Exception("Must give new space a unique name");
		
		return;
	}
	
	/**
	 * Edit space in space list
	 * 
	 * @param aParent, the parent class (new or same) of the edited space
	 * @param position, the position of the target edited space in spaceList
	 * @param n, the new (or old) name of the space
	 * @throws Exception, thrown when user performs action that is not permitted
	 */
	public void editSpace(Space aParent, int position, String n) throws Exception{
		
		Space tempSpace = spaceList.get(position);
		boolean nameExists = false;
		int parentIndex = 0;
		String prefix = "";
		
		// My Tasks
		if (position == 0) {
			throw new Exception("My Tasks cannot be modified! Try editing another space");
		}
		
		// check that name isn't already used by another space AND that it is actually another space and not this space
		for (Space s: spaceList) {
			if (s.toString().equals(n) && !spaceList.get(position).toString().equals(n))
				nameExists = true;
		}
		
		for (Space s : spaceList) {
			if (s.equals(aParent) && parentIndex == 0) {
				//prefix = "-";
				break;
			}
			else if (s.equals(aParent)) {
				//prefix = aParent.toString().substring(0, aParent.toString().lastIndexOf("-") + 1) + "-";
				break;
			}
			else
				parentIndex++;
		}
		
		// if name is unique, update space in space list
		if (!nameExists) {
			tempSpace.setName(prefix + n);
			tempSpace.moveTo(aParent);
			spaceList.set(position, tempSpace);
		}
		else
			throw new Exception("Must give space a unique name");
		
		return;
	}
	
	/**
	 * Delete space in space list
	 * 
	 * @param position, the position of the target deleted space in spaceList
	 * @throws Exception, thrown when user performs action that is not permitted
	 */
	public void deleteSpace(int position) throws Exception {
		
		if (position == 0)
			throw new Exception("My Tasks cannot be deleted! Try deleting a different space");
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
		
		return;
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
	
	/**
	 * Returns the index of the currently selected space in
	 * the list of all spaces.
	 * 
	 * @return	Index of the selected space in spaceList.
	 */
	public int getSelectedSpaceIndex()
	{
		if (selectedSpaceIndex < 0)
			return 0;
		else
			return selectedSpaceIndex;
	}
	
	/**
	 * Sets the index of a newly selected space within
	 * the space list.
	 * 
	 * @param newIndex, The index of the newly selected space.
	 */
	public void setSelectedSpaceIndex(int newIndex)
	{
		selectedSpaceIndex = newIndex;
	}
}