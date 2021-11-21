
import java.time.LocalDate;

/**
 * A class that constructs a task object that
 * contains a status, name, due date, and priority level,
 * and methods for setting and getting these values
 * 
 * @author zcste
 * @author justin
 */
public class Task {


	/*
	 * Variables
	 */
	private Status status;
	private String name;
	private LocalDate dueDate;
	private enum Priority
	{
		LOW, MEDIUM, HIGH;
	}
	private Priority currentPriority = Priority.MEDIUM;
	private Space parentSpace;
	
	/**
	 * Default constructor for Task Class
	 * creates a task object with a name, due date, and a new status 
	 * @param n the name of the task
	 * @param dd the due date of task
	 */
	
	public Task(String n, String dd)
	{
		name = n;
		dueDate = LocalDate.parse(dd);
		status = new Status();
	}
	
	/**
	 * Parameterized constructor for Task Class
	 * creates a task object with a name, due date, and parent space to belong to with a new status
	 * @param n the name of the task
	 * @param dd the due date of task
	 * @param aSpace the parent of space of the new task
	 */
	
	public Task(String n, String dd, Space aSpace)
	{
		name = n;
		dueDate = LocalDate.parse(dd);
		parentSpace = aSpace;
		status = new Status();
	}
	
	/**
	 * Setter method for modifying Task name
	 * @param n the name of the task to be modified
	 */
	
	public void setName(String n){
		name = n;
	}
	
	/**
	 * Getter method for retrieving Task name
	 * @return String name of task
	 */	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Setter method for changing the due date of
	 * the task
	 * @param dd a string that contains the date information
	 */
	public void setDate(String dd){
		dueDate = LocalDate.parse(dd);
	}

	/**
	 * Getter method for retrieving task due date
	 * @return dueDate a LocalDate object that is the due date
	 */
	public LocalDate getDate(){
		return dueDate;
	}
	
	/**
	 * Setter method for changing the priority of the task
	 * @param p the priority that will be modified
	 */
	
	public void setPriority(Priority p){
		currentPriority = p;
	}
	
	/**
	 * Getter method for retrieving the priority of task
	 * @return priority of the task
	 */	
	
	public Priority getPriority(){
		return currentPriority;
	}
	
	/**
	 * Method to change the parent space of this task
	 * @param aSpace the target space
	 */
	
	public void moveTo(Space aSpace){
		parentSpace = aSpace;
	}
	
	/**
	 * Getter method for retrieving parent Space name 
	 * that the task belongs to
	 * @return String name of parent space
	 */
	
	public String getParentName(){
		return parentSpace.toString();
	}
	
	/**
	 * Setter method for changing the status of the task
	 * @param newProgress the new status of the task
	 */
	public void setCurrent(Status.progress newProgress) {
		status.setCurrent(newProgress);
	}
	
	/**
	 * Getter method for retrieving the current status
	 * of the task
	 * @return status.getCurrent() the current status 
	 * of the task
	 */
	public Status.progress getCurrent() {
		return status.getCurrent();
	}
	
	/**
	 * Setter method for setting the description
	 * of the task
	 * @param aDesc a string that will be the new
	 * description
	 */
	public void setDescription(String aDesc) {
		status.setDescription(aDesc);
	}
	
	/**
	 * Getter method for retrieving the description
	 * of the task
	 * @return status.getDescription a string that 
	 * represents the description of the task
	 */
	public String getDescription() {
		return status.getDescription();
	}

}
