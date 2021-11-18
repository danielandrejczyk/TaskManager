import java.util.Date;

public class Task {

	// Justin Wilson
	
	// calen 2
	
	 private String name;
	 private Date dueDate;
	 private enum Priority
	 {
		 LOW, MEDIUM, HIGH;
	 }
	 private Priority currentPriority = Priority.MEDIUM;
	 private Space parentSpace;
	 public Task(String n, Date dd){
		 name = n;
		 dueDate = dd;
	 }
	 public Task(String n, Date dd, Space aSpace){
		 
	 }
	 public void SetName(String n){
		 name = n;
	 }
	 public String toString() {
		 return name;
	 }
	 public void SetDate(Date d){
		 dueDate = d;
	 }
	 //public String GetDate(){
		// 
	 //}
	 public void SetPriority(Priority p){
		 
	 }
	 public Priority GetPriority(){
		 return currentPriority;
	 }
	 public void MoveTo(Space aSpace){
		 
	 }
	 public String GetParentName(){
		 return parentSpace.toString();
	 }

}
