
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


/**
 * A class that stores all task objects, being able to
 * add, edit, and delete them
 * @author Calen
 */
public class TaskManager {
	
	private ArrayList<Task> taskList;
	private int selectedTaskIndex;
	
	/**
	 * Default constructor
	 */
	public TaskManager() {
		taskList = new ArrayList<Task>();
	}
	
	/**
	 * Method to add a task to the task list
	 * @param n String that is the task name
	 * @param dd LocalDate that is the task due date
	 * @param aSpace Space that is the parent space
	 * @param statusDesc String that is the description of the task
	 * @param currentStatus Status.progress value that indicates task progress
	 * @param priority Task.priority value that indicates task priority
	 */
	public void addTask(String n, LocalDate dd, Space aSpace, String statusDesc,
			Status.progress currentStatus, Task.Priority priority) {
		
		// throws Exception
		
		boolean nameExists = false;
		
		// check that name isn't already used by another task
		for (Task t: taskList) {
			if (t.toString().equals(n)) {
				nameExists = true;
			}
		}
		
		// add task only if the name is unique
		if (!nameExists) {
			Task newTask = new Task(n, dd, aSpace);
			newTask.setPriority(priority);
			newTask.setCurrent(currentStatus);
			newTask.setDescription(statusDesc);
			taskList.add(newTask);
			System.out.println(newTask.toString());
		}
		//else 
			//throw new Exception("Must give new space a unique name");
		
	}
	
	/**
	 * Method to edit a task
	 * @param aTask the task that will be edited
	 */
	public void EditTask(Task oldTask, Task newTask) {
		
		// breaks encap but works
		for (Task t : taskList) {
			// assume name exists
			if (t.equals(oldTask)) {
				// t = newTask;
				// or
				
				 t.setName(newTask.toString());
				 t.setDate(newTask.getDate());
				 t.setCurrent(newTask.getCurrent());
				 t.setDescription(newTask.getDescription());
				 t.setPriority(newTask.getPriority());
				 t.moveTo(newTask.getParentSpace());
				 
			}	
		}
	
	}
	
	/**
	 * Method to delete a task
	 * @param aTask the task that will be deleted
	 */
	public void deleteTask(Task aTask) {
		taskList.removeIf(task -> task.equals(aTask));
	}
	
	/**
	 * Method to return an array list of tasks with given
	 * parent space
	 * @param aParent
	 * @return filteredList and array list
	 */
	public ArrayList<Task> getTaskList(Space selectedSpace) {
		
		// implement all tasks that have a particular parent
		ArrayList<Task> filteredList = new ArrayList<Task>();
		
		for (Task t : taskList) {
			Space tempPSpace = t.getParentSpace();
			while (tempPSpace != null) {
				if (tempPSpace.toString().equals(selectedSpace.toString())) {
					filteredList.add(t);
					tempPSpace = null;
				}
				else {
					tempPSpace = tempPSpace.getParentSpace();
				}
			}
		}
		
		return filteredList;
	}
	
	/**
	 * Returns the index of the currently selected task in
	 * the list of all tasks.
	 * 
	 * @return	Index of the selected task in taskList.
	 */
	public int getSelectedTaskIndex()
	{
		return selectedTaskIndex;
	}
	
	/*
	 * Sets the index of a newly selected task within
	 * the task list.
	 * 
	 * @param	newIndex	The index of the newly selected task.
	 */
	public void setSelectedTaskIndex(int newIndex)
	{
		selectedTaskIndex = newIndex;
	}
}