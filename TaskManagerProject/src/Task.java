import java.util.Date;

public class Task {

	// Justin Wilson

	// calen 2

	/*
	 * Variables
	 */
	private Status status;
	private String name;
	private Date dueDate;
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
	
	public Task(String n, Date dd)
	{
		name = n;
		dueDate = dd;
		status = new Status();
	}
	
	/**
	 * Parameterized constructor for Task Class
	 * creates a task object with a name, due date, and parent space to belong to with a new status
	 * @param n the name of the task
	 * @param dd the due date of task
	 * @param aSpace the parent of space of the new task
	 */
	
	public Task(String n, Date dd, Space aSpace)
	{
		name = n;
		dueDate = dd;
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
	
	// Can't do: new Date(2021, 11, 18);
	public void setDate(Date d){
		dueDate = d;
	}

	public Date getDate(){
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
	
	public void setCurrent(Status.progress newProgress) {
		status.setCurrent(newProgress);
	}
	
	public Status.progress getCurrent() {
		return status.getCurrent();
	}
	
	public void setDescription(String aDesc) {
		status.setDescription(aDesc);
	}
	
	public String getDescription() {
		return status.getDescription();
	}

}
