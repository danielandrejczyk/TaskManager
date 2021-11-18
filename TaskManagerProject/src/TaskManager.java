/**
*
* @author calen4
* 
* 
*
*/
import java.util.ArrayList;

public class TaskManager {
	
	private ArrayList<Task> taskList;
	
	
	/**
	 * Default constructor
	 */
	public TaskManager() {
		taskList = new ArrayList<Task>();
	}
	
	/**
	 * Method to add a task to a space
	 * @param aParent the space that will contain the task
	 */
	public void AddTask(Task aTask) {
		taskList.add(aTask);
	}
	
	/**
	 * Method to edit a task
	 * @param aTask the task that will be edited
	 */
	public void EditTask(Task aTask, Task eTask) {
		aTask.SetName(eTask.GetName());
		aTask.SetDate(eTask.GetDate());
		aTask.SetPriority(eTask.GetPriority());
		aTask.MoveTo(eTask.GetParentName());
	}
	
	/**
	 * Method to delete a task
	 * @param aTask the task that will be deleted
	 */
	public void DeleteTask(Task aTask) {
		taskList.removeIf(task -> task.equals(aTask));
	}
	
	/**
	 * Method to select a task
	 */
	public void SelectTask() {
		
	}
}