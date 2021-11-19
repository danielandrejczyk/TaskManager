/**
 * @author: Justin
 * 
 *  TaskManager Status Class
*/


public class Status{
	
	public static enum progress { TO_DO, IN_PROGRESS , DONE };
	private progress currentStatus = progress.TO_DO;
	private String description;
	
	
	/**
	 * Default constructor for Status Class
	 */
	
	public Status() {
		currentStatus = progress.TO_DO;
		description = "";
	}
	
	public Status(progress selectedStatus) {
		currentStatus = selectedStatus;
		//empty string
		description = "";
	}
	
	public Status(progress selectedStatus, String description) {
		currentStatus = selectedStatus;
		this.description = description;
	}
	
	public void setCurrent(progress newProgress) {
		currentStatus = newProgress;
	}
	
	public progress getCurrent() {
		return currentStatus;
	}
	
	public void setDescription(String aDesc) {
		description = aDesc;
	}
	
	public String getDescription() {
		return description;
	}
}