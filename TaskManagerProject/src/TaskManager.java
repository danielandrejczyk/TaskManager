
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
	
	/**
	 * Default constructor
	 */
	public TaskManager() {
		taskList = new ArrayList<Task>();
	}
	
	/**
	 * Method to add a task to the task list
	 * @param aTask the task that will be added
	 */
	public void addTask(String n, LocalDate dd, Space aSpace, String statusDesc, Status.progress currentStatus, Task.Priority priority) {
		Task newTask = new Task(n, dd, aSpace);
		newTask.setPriority(priority);
		newTask.setCurrent(currentStatus);
		newTask.setDescription(statusDesc);
		taskList.add(newTask);
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
				t = newTask;
				// or
				/*
				 * t.setName(newTask.toString())
				 * [...]
				 */
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
	
	public ArrayList<Task> getTaskList(Space aParent) {
		
		// implement all tasks that have a particular parent
		ArrayList<Task> filteredList = new ArrayList<Task>();
		// add here
		
		return filteredList;
	}
}