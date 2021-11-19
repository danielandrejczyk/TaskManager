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
	
	/*
	 * Constructors
	 */
	
	public Task(String n, Date dd)
	{
		name = n;
		dueDate = dd;
		status = new Status();
	}
	
	public Task(String n, Date dd, Space aSpace)
	{
		name = n;
		dueDate = dd;
		parentSpace = aSpace;
		status = new Status();
	}
	
	public void setName(String n){
		name = n;
	}
	
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
	
	public void setPriority(Priority p){
		currentPriority = p;
	}
	
	public Priority getPriority(){
		return currentPriority;
	}
	
	public void moveTo(Space aSpace){
		parentSpace = aSpace;
	}
	
	public String getParentName(){
		return parentSpace.toString();
	}

}
