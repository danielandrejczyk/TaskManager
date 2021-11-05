/**
 * @author: Justin
 * 
 *  TaskManager Status Class
*/


public class Status{
	
	public enum progress { TO_DO, IN_PROGRESS , DONE };
	private progress currentStatus = progress.TO_DO;
	private String description;
	
	
	/**
	 * Default constructor for Status Class
	 */
	
	public Status() {
		
	}
	
	public Status(progress selectedStatus) {
		
	}
	
	public Status(progress selectedStatus, String description) {
		
	}
	
	public void SetCurrent() {
		
	}
	
	public void GetCurrent() {
		
	}
	
	public void SetDescription(String aDesc) {
		
	}
	
	public void GetDescription() {
		
	}
}