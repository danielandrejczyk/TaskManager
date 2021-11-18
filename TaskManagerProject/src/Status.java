/**
 * @author: Justin
 * 
 *  TaskManager Status Class
*/


public class Status{
	
	private enum progress { TO_DO, IN_PROGRESS , DONE };
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
		description = "";
		//empty string
	}
	
	public Status(progress selectedStatus, String description) {
		currentStatus = selectedStatus;
		description = "";
	}
	
	public void SetCurrent(progress newProgress) {
		
	}
	
	public progress GetCurrent() {
		return currentStatus;
	}
	
	public void SetDescription(String aDesc) {
		description = aDesc;
	}
	
	public String GetDescription() {
		return description;
	}
}