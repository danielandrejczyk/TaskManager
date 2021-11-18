import java.util.Date;

public class Task {

	// Justin Wilson
	
	// calen 2
	
	 private String name;
	 private Date duedate;
	 
	 private Priority currentPriority = Priority.MEDIUM;
	 private Space parentSpace;
	 
	 private enum Priority
	 {
		 LOW, MEDIUM, HIGH;
	 }
	 
	 public Task(String n, Date dd){
		 this.name = n;
		 this.duedate = dd;
	 }
	 
	 
	 public Task(String n, Date dd, Space aSpace){
		 this.name = n;
		 this.duedate = dd;
		 this.parentSpace = aSpace;
	 }
	 
	 
	 public void SetName(String n){
		 this.name = n;
	 }
	 
	 
	 public String GetName(){
		 return name;
	 }
	 
	 
	 public void SetDate(Date dd){
		 this.duedate = dd;
	 }
	 
	 
	 public Date GetDate(){
		 return duedate;
	 }
	 
	 
	 public void SetPriority(Priority p){
		 currentPriority = p;
	 }
	 
	 
	 public Priority GetPriority(){
		 return currentPriority;
	 }
	 
	 
	 public void MoveTo(Space aSpace){
		 parentSpace = aSpace;
	 }
	 
	 
	 public String GetParentName(){
		 return parentSpace.toString();
	 }

}
