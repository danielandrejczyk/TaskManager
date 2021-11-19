/**
*
* @author calen4
* 
* 
*
*/
import java.util.ArrayList;
import java.util.Date;

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
	public void addTask(Task aTask) {
		taskList.add(aTask);
	}
	
	/**
	 * Method to edit a task
	 * @param aTask the task that will be edited
	 */
	public void EditTask(Task updatedTask) {
		for(Task t: taskList)
		{
			if(updatedTask.equals(t))
			{
				t = updatedTask;
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
	 * Method to select a task
	 */
	/*
	public void selectTask() {
		
	}
	*/
}