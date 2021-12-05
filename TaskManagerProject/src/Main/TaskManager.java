package Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


/**
 * A class that stores all task objects, being able to
 * add, edit, and delete them
 * @author Calen
 */
public class TaskManager {
	
	private static ArrayList<Task> taskList;
	private static int selectedTaskIndex;
	
	/**
	 * Default constructor
	 */
	public TaskManager() {
		taskList = new ArrayList<Task>();
	}
	
	@SuppressWarnings("unchecked")
	public void loadTasks()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tasks.dat"));
			taskList = (ArrayList<Task>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file!");
		} catch (IOException e) {
			System.out.println("Error writing out task objects!");
		} catch (ClassNotFoundException e) {
			System.out.println("File not in the right format!");
		}
	}
	
	public void storeTasks()
	{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tasks.dat"));
			out.writeObject(taskList);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file!");
		} catch (IOException e) {
			System.out.println("Error writing out task objects!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to add a task to the task list
	 * @param n String that is the task name
	 * @param dd LocalDate that is the task due date
	 * @param aSpace Space that is the parent space
	 * @param statusDesc String that is the description of the task
	 * @param currentStatus Status.progress value that indicates task progress
	 * @param priority Task.priority value that indicates task priority
	 */
	public void addTask(String n, LocalDate dd, Space aSpace, String statusDesc,
			Status.progress currentStatus, Task.Priority priority) throws Exception {
		
		boolean nameExists = false;
		
		// check that name isn't already used by another task
		for (Task t: taskList) {
			if (t.toString().equals(n)) {
				nameExists = true;
			}
		}
		
		// add task only if the name is unique
		if (!nameExists) {
			Task newTask = new Task(n, dd, aSpace);
			newTask.setPriority(priority);
			newTask.setCurrent(currentStatus);
			newTask.setDescription(statusDesc);
			taskList.add(newTask);
			System.out.println(newTask.toString());
		}
		else 
			throw new Exception("Must give new task a unique name");
		
	}
	
	/**
	 * Method to edit a task
	 * @param aTask the task that will be edited
	 */
	public void EditTask(Task oldTask, Task newTask) {
		
		// breaks encap but works
		for (Task t : taskList) {
			// assume name exists
			if (t.equals(oldTask)) {
				// t = newTask;
				// or
				
				 t.setName(newTask.toString());
				 t.setDate(newTask.getDate());
				 t.setCurrent(newTask.getCurrent());
				 t.setDescription(newTask.getDescription());
				 t.setPriority(newTask.getPriority());
				 t.moveTo(newTask.getParentSpace());
				 
			}	
		}
	
	}
	
	/**
	 * Method to delete a task
	 * @param aTask the task that will be deleted
	 */
	public void deleteTask(Task aTask) {
		taskList.removeIf(task -> task.equals(aTask));
	}
	
	/**
	 * Method to return an array list of tasks with given
	 * parent space
	 * @param aParent
	 * @return filteredList and array list
	 */
	public static ArrayList<Task> getTaskList(Space selectedSpace) {
		
		// implement all tasks that have a particular parent
		ArrayList<Task> filteredList = new ArrayList<Task>();
		
		// filter by space
		for (Task t : taskList) {
			Space tempPSpace = t.getParentSpace();
			while (tempPSpace != null) {
				if (tempPSpace.toString().equals(selectedSpace.toString())) {
					filteredList.add(t);
					tempPSpace = null;
				}
				else {
					tempPSpace = tempPSpace.getParentSpace();
				}
			}
		}
		
		return filteredList;
	}
	
	/**
	 * Returns the index of the task by name
	 * 
	 * @return	Index of the selected task in taskList.
	 */
	public static int getTaskIndexByName(String tName)
	{
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).toString().equals(tName))
				return i;
		}
		return -1;
	}
	
	/**
	 * Returns the selected task index out of the list of all tasks
	 * @return the selected task index
	 */
	public static int getSelectedTaskIndex() {
		return selectedTaskIndex;
	}
	
	/**
	 * Sets the index of a newly selected task within
	 * the task list.
	 * 
	 * @param	newIndex	The index of the newly selected task.
	 */
	public static void setSelectedTaskIndex(int newIndex)
	{
		selectedTaskIndex = newIndex;
	}
}