/**
 * @author: Justin
 * 
 *  TaskManager Status Class
*/

public class Status{
	
	// Variables
	public static enum progress { TO_DO, IN_PROGRESS , DONE };
	private progress currentStatus = progress.TO_DO;
	private String description;
	
	/**
	 * Default constructor for Status Class
	 * with "To-Do” status with no description
	 */
	
	public Status() {
		currentStatus = progress.TO_DO;
		description = "";
	}
	
	/**
	 * Parameterized constructor for Status Class
	 * @param selectedStatus the status of the task
	 * a status with a designated progress but no description 
	 */
	
	public Status(progress selectedStatus) {
		currentStatus = selectedStatus;
		description = "";
	}
	
	/**
	 * Parameterized constructor for Status Class
	 * @param selectedStatus the status of the task
	 * @param description the description of the task
	 * a status with both a designated progress and description
	 */
	
	public Status(progress selectedStatus, String description) {
		currentStatus = selectedStatus;
		this.description = description;
	}
	
	/**
	 * Setter method for changing the status of the task
	 * @param newProgress the status that will be modified
	 */
	
	public void setCurrent(progress newProgress) {
		currentStatus = newProgress;
	}
	
	/**
	 * Getter method for retrieving the status of task
	 * @return status of the task
	 */	
	
	public progress getCurrent() {
		return currentStatus;
	}
	
	/**
	 * Setter method for modifying description
	 * @param aDesc the description that will be changed
	 */
	
	public void setDescription(String aDesc) {
		description = aDesc;
	}
	
	/**
	 * Getter method for retrieving the description of task
	 * @return the description of the task
	 */
	
	public String getDescription() {
		return description;
	}
}