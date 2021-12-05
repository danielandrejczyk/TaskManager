package Main;
// Author: Daniel Andrejczyk

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;


public class Overview {
	
    /**
     * Sets up the home task overview and sets it to the center
     * of the border pane that is passed in.
     * 
     * @param	b	The borderpane to anchor the home overview to
     */
    private static GridPane toggleHome(double centerWidth, double centerHeight)
    {
    	GridPane homePane = new GridPane();
    	
    	//AnchorPane homePane = new AnchorPane();
    	homePane.setPadding(new Insets(20));
    	GridPane.setMargin(homePane, new Insets(20));
    	homePane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex())));
    	
    	ListView<Task> listView = new ListView<Task>(tasks);
    	listView.setPrefWidth(centerWidth - 450 - 20);
    	listView.setPrefHeight(500);
    	listView.getStyleClass().add("list-view");
    	GridPane.setMargin(listView, new Insets(10));
    	
    	Label taskLabel = new Label(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex()).toString());
    	taskLabel.setFont(new Font("Arial", 24));
    	taskLabel.setPrefWidth(450);
    	GridPane.setMargin(taskLabel, new Insets(10));
    	
    	// current day
    	String now = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(new java.util.Date());
    	
    	Label dateLabel = new Label(now);
    	dateLabel.setFont(new Font("Arial Bold", 24));
    	dateLabel.setPrefWidth(350);
    	GridPane.setMargin(dateLabel, new Insets(10));
    	
    	// Task information text flow
    	VBox taskInformation = new VBox();
    	taskInformation.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
    	taskInformation.setSpacing(10);
    	taskInformation.setStyle("-fx-font-size: 16");
    	taskInformation.setPrefWidth(350); 
    	GridPane.setMargin(taskInformation, new Insets(10));
    	
    	Text noTaskSelected;
    	if (listView.getItems().isEmpty()) {
    		noTaskSelected = new Text("No tasks added yet. Add a task by clicking the \"Add Task\" button in the menu bar.");
    		noTaskSelected.setWrappingWidth(300);
        	noTaskSelected.minWidth(350);
    	} else {
    		noTaskSelected = new Text("Select a task from the right list to see more information");
        	noTaskSelected.setWrappingWidth(300);
        	noTaskSelected.minWidth(350);
    	}
    	
    	taskInformation.getChildren().add(noTaskSelected);
		taskInformation.setPadding(new Insets(20,20,20,20));

		// Get the selected task
		listView.setOnMouseClicked(event -> {
			ObservableList<Task> selectedIndices = listView.getSelectionModel().getSelectedItems();
			Task selectedTask;
			
			// try-catch to check if any tasks exist (throws IndexOutOfBoundsException)
			try {
				selectedTask = selectedIndices.get(0);
				
				taskInformation.getChildren().clear();
				
				Text priority = new Text("Priority: " + selectedTask.getPriority());
				Rectangle priorityRect = new Rectangle(0, 0, 300, 20);
				switch(selectedTask.getPriority())
				{
					case HIGH:
						priorityRect.setFill(Color.INDIANRED); break;
					case MEDIUM:
						priorityRect.setFill(Color.ORANGE); break;
					case LOW:
						priorityRect.setFill(Color.LIGHTGOLDENRODYELLOW); break;
					default:
						priorityRect.setFill(Color.ORANGE);	
				}
				//priorityCircle.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
				Text status = new Text("Status: " + selectedTask.getCurrent());
				Text statusDesc = new Text("Status Description: " + selectedTask.getDescription());
				statusDesc.setWrappingWidth(300);
				Text parentSpace = new Text("Parent Space: " + selectedTask.getParentName());
				Text date = new Text("Due Date: " + selectedTask.getDate());
				
				taskInformation.getChildren().addAll(priorityRect, priority, status, statusDesc, parentSpace, date);
			}
			catch (IndexOutOfBoundsException e) {
				selectedTask = null;
				
				Text noTaskExistsMessage = new Text("No tasks added yet. Add a task by clicking the \"Add Task\" button in the menu bar.");
				noTaskExistsMessage.setWrappingWidth(300);
				taskInformation.getChildren().clear();
				taskInformation.getChildren().add(noTaskExistsMessage);
			}
		});

    	homePane.add(dateLabel, 0, 0);
    	homePane.add(taskLabel, 1, 0);
    	homePane.add(taskInformation, 0, 1);
    	homePane.add(listView, 1, 1);
    	
    	return homePane;
    	
    }
    
    private static AnchorPane toggleDaily(double centerWidth, double centerHeight)
    {
    	
    	AnchorPane dailyPane = new AnchorPane();
    	
    	VBox taskCol1 = new VBox();
    	taskCol1.setSpacing(10.0);
    	
    	final int WIDTH = (int) centerWidth;
    	final int HEIGHT = (int) centerHeight;
    	
    	// Create a canvas 
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        // Get the graphics context of the canvas 
        GraphicsContext gc = canvas.getGraphicsContext2D(); 

        // Set line width and fill color 
        gc.setLineWidth(2.0); 
        gc.setFill(Color.BLACK); 

    	gc.strokeRect(10, 10, WIDTH - 20, HEIGHT - 20);
    	
    	// Date Label
    	gc.setFont(new Font("Arial", 24));
    	// current day
    	String dateHeader = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(new java.util.Date());
    	gc.fillText(dateHeader, 20, 40);
    	
    	gc.strokeRect(10, 10, 230, 40);
    	
    	//Space myTasks = SpaceManager.getSpaceList().get(0);
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex())));
    	
    	// Cycle through each task and create a box for each one
    	for(Task task: tasks) 
    	{
    		// If task is due today...
    		Button taskButton = new Button(task.toString());
    		FileInputStream input;
			try {
				String userDirectory = System.getProperty("user.dir");
				System.out.println(userDirectory);
				switch(task.getPriority())
				{
					case HIGH:	input = new FileInputStream(userDirectory + "/images/HighPriority.png"); break;
					case MEDIUM:input = new FileInputStream(userDirectory + "/images/MediumPriority.png"); break;
					case LOW:	input = new FileInputStream(userDirectory + "/images/LowPriority.png"); break;
					default:	input = new FileInputStream(userDirectory + "/images/LowPriority.png"); break;
				}
				ImageView priority = new ImageView(new Image(input));
				taskButton.setGraphic(priority);
				taskButton.setMinWidth(200);
				taskButton.setAlignment(Pos.CENTER_LEFT);
				
				// show task info
				Rectangle rect = new Rectangle(0, 0, 100, 100);
				Tooltip taskInfo = new Tooltip("Parent Space: " + task.getParentName() + "\nPriority: " + task.getPriority() + "\nStatus: " + task.getCurrent());
				Tooltip.install(rect, taskInfo);
				taskButton.setTooltip(taskInfo);
				taskInfo.setShowDelay(null);
			} catch (FileNotFoundException e) {
				System.out.println("Unable to find priority graphic for button!");
			}
    		taskCol1.getChildren().add(taskButton);
    		
    	}
    	
    	AnchorPane.setTopAnchor(taskCol1, 70.0);
    	AnchorPane.setLeftAnchor(taskCol1, 50.0);
    	
    	dailyPane.getChildren().addAll(canvas, taskCol1);
    	
    	return dailyPane;
    	
    }
    
    /**
     * Method to display the weekly overview
     * 
     * @param	b	The borderpane object to which
     * 				the overview will be placed in the
     * 				center of.
     */
    private static AnchorPane toggleWeekly(double centerWidth, double centerHeight) {
    	
    	// Note for reader:
    	// Some code based on https://gist.github.com/james-d/ee8a5c216fb3c6e027ea 
    	// However, 95% of it is modified to fit the purposes of this project, which is to display tasks and much is original; still, will credit
    	
    	// variables for the first day of the week and the current locale
    	final ObjectProperty<LocalDate> weekStartDate = new SimpleObjectProperty<>();
    	final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    	
    	ArrayList<Task> fList = new ArrayList<Task>();
    	fList = TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex()));
    	
    	// set week to previous Sunday
    	LocalDate now = LocalDate.now();
    	weekStartDate.setValue(now.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek())); // normally would uses WeekFields.SUNDAY, but locale can cause week to start on Monday and Sunday is last day
    	
    	// anchorpane for displaying calendar
    	AnchorPane weekPane = new AnchorPane();
    	
    	// get boundaries of container to draw calendar
    	final int WIDTH = (int) centerWidth;
    	final int HEIGHT = (int) centerHeight;
   
    	// create new grid for calendar layout
    	GridPane calGrid = new GridPane();
    	
    	// calculate ideal cell widths and heights
    	final int CELL_WIDTH = WIDTH / 7;
    	final int CELL_HEIGHT = HEIGHT;
    	
    	// create buttons to move to next month and previous; styling
    	Button prevWeek = new Button("<");
    	Button nextWeek = new Button(">");
    	prevWeek.setBackground(new Background(new BackgroundFill(Color.SLATEGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	nextWeek.setBackground(new Background(new BackgroundFill(Color.SLATEGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	prevWeek.getStyleClass().add("month-btn");
    	nextWeek.getStyleClass().add("month-btn");
    	prevWeek.setFont(new Font("Arial Bold", 14));
    	nextWeek.setFont(new Font("Arial Bold", 14));
    	prevWeek.setTextFill(Color.WHITE);
    	nextWeek.setTextFill(Color.WHITE);
    	
    	// methods for moving forward and backward a month; calls drawCalendar(...) to redraw the calendar and populate with next/prev month's contents
    	prevWeek.setOnAction(e -> {
    		weekStartDate.set(weekStartDate.get().minusWeeks(1));
    		calGrid.getChildren().clear();
    		calGrid.add(prevWeek, 0, 0, 1, 1);
        	calGrid.add(nextWeek, 6, 0, 1, 1);
    		drawWeeklyCalendar(weekStartDate, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex())));
    	});
    	nextWeek.setOnAction(e -> {
    		weekStartDate.set(weekStartDate.get().plusWeeks(1));
    		calGrid.getChildren().clear();
    		calGrid.add(prevWeek, 0, 0, 1, 1);
        	calGrid.add(nextWeek, 6, 0, 1, 1);
    		drawWeeklyCalendar(weekStartDate, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex())));
    	});
    	
    	// add buttons to the calendar grid
    	calGrid.add(prevWeek, 0, 0, 1, 1);
    	calGrid.add(nextWeek, 6, 0, 1, 1);
    	
    	// set sizing for buttons
    	prevWeek.setPrefSize(CELL_WIDTH, 25);
    	nextWeek.setPrefSize(CELL_WIDTH, 25);
    	
    	// draw starting calendar
    	drawWeeklyCalendar(weekStartDate, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, fList);
    	
    	// place calendar in the anchor pane
    	weekPane.getChildren().add(calGrid);
    	return weekPane;
    	
    }
    
    /**
     * Method to display the calendar overview
     * 
     * @param b
     */
    private static AnchorPane toggleMonthly(double centerWidth, double centerHeight) {
    	
    	// Note for reader:
    	// Some code based on https://gist.github.com/james-d/ee8a5c216fb3c6e027ea 
    	// However, 95% of it is modified to fit the purposes of this project, which is to display tasks and much is original; still, will credit
    	
    	// variables for the month and the current locale
    	final ObjectProperty<YearMonth> month = new SimpleObjectProperty<>();
    	final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    	
    	// for testing tasks
    	
    	ArrayList<Task> fList = new ArrayList<Task>();
    	fList = TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex()));
    	
    	// set month to now
    	month.setValue(YearMonth.now());
    	
    	// anchorpane for displaying calendar
    	AnchorPane monthPane = new AnchorPane();
    	
    	// get boundaries of container to draw calendar
    	final int WIDTH = (int) centerWidth;
    	final int HEIGHT = (int) centerHeight;
   
    	// create new grid for calendar layout
    	GridPane calGrid = new GridPane();
    	
    	// calculate ideal cell widths and heights
    	final int CELL_WIDTH = WIDTH / 7;
    	final int CELL_HEIGHT = HEIGHT / 5;
    	
    	// create buttons to move to next month and previous; styling
    	Button prevMonth = new Button("<");
    	Button nextMonth = new Button(">");
    	prevMonth.setBackground(new Background(new BackgroundFill(Color.SLATEGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	nextMonth.setBackground(new Background(new BackgroundFill(Color.SLATEGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	prevMonth.getStyleClass().add("month-btn");
    	nextMonth.getStyleClass().add("month-btn");
    	prevMonth.setFont(new Font("Arial Bold", 14));
    	nextMonth.setFont(new Font("Arial Bold", 14));
    	prevMonth.setTextFill(Color.WHITE);
    	nextMonth.setTextFill(Color.WHITE);
    	
    	// methods for moving forward and backward a month; calls drawCalendar(...) to redraw the calendar and populate with next/prev month's contents
    	prevMonth.setOnAction(e -> {
    		month.set(month.get().minusMonths(1));
    		calGrid.getChildren().clear();
    		calGrid.add(prevMonth, 0, 0, 1, 1);
        	calGrid.add(nextMonth, 6, 0, 1, 1);
    		drawCalendar(month, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex())));
    	});
    	nextMonth.setOnAction(e -> {
    		month.set(month.get().plusMonths(1));
    		calGrid.getChildren().clear();
    		calGrid.add(prevMonth, 0, 0, 1, 1);
        	calGrid.add(nextMonth, 6, 0, 1, 1);
    		drawCalendar(month, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, TaskManager.getTaskList(SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex())));
    	});
    	
    	// add buttons to the calendar grid
    	calGrid.add(prevMonth, 0, 0, 1, 1);
    	calGrid.add(nextMonth, 6, 0, 1, 1);
    	
    	// set sizing for buttons
    	prevMonth.setPrefSize(CELL_WIDTH, 25);
    	nextMonth.setPrefSize(CELL_WIDTH, 25);
    	
    	// draw starting calendar
    	drawCalendar(month, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, fList);
    	
    	// place calendar in the anchor pane
    	monthPane.getChildren().add(calGrid);
    	return monthPane;
    	
    }
    
    /**
     * Helper method to redraw the weekly calendar when button is pressed to advance/go back to next/prev week
     * 
     * @param month, YearMonth object that passes current selected month
     * @param locale, Locale object that passes current locale
     * @param calGrid, GridPane object for laying out calendar contents
     * @param CELL_WIDTH, width of cell
     * @param CELL_HEIGHT, height of cell
     */
    private static void drawWeeklyCalendar(ObjectProperty<LocalDate> firstDate, ObjectProperty<Locale> locale, GridPane calGrid, final int CELL_WIDTH, final int CELL_HEIGHT, ArrayList<Task> taskList) {
    	
    	// positioning variables
    	final int HEAD_WIDTH = CELL_WIDTH;
    	final int HEAD_HEIGHT = 25;
    	final int CAL_WIDTH = CELL_WIDTH * 7;
    	final int CAL_HEIGHT = CELL_HEIGHT;
    	final int CAL_PADDING = 5;
    	final int CELL_PADDING = 5;
    	final int CELL_MARGIN = 2;
    	
    	// Get today's date
    	LocalDate now = LocalDate.now();
    	
    	// month and date label
    	Label weekLabel = new Label("Week of: " + firstDate.get().getMonth() + " " 
    									+ firstDate.get().getDayOfMonth() + ", " 
    										+ firstDate.get().getYear());
    	weekLabel.setFont(new Font("Arial Bold", 24));
    	weekLabel.setAlignment(Pos.CENTER);
    	weekLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	weekLabel.setMinSize(HEAD_WIDTH * 5, HEAD_HEIGHT);
    	weekLabel.setTextFill(Color.WHITE);
    	
    	// adding a header and setting its position and styling
    	calGrid.getChildren().remove(weekLabel);
    	calGrid.add(weekLabel, 1, 0, 5, 1);
    	calGrid.setPrefSize(CAL_WIDTH, CAL_HEIGHT);
    	calGrid.setPadding(new Insets(CAL_PADDING));
    	calGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    	calGrid.setAlignment(Pos.CENTER);
    	
    	// getting the names of the weeks and the first day of the week
    	WeekFields weekFields = WeekFields.of(locale.get());
    	LocalDate first = firstDate.get();
    	
    	// get the day of the week of the first day of the month
    	int dayOfWeekOfFirst = first.get(weekFields.dayOfWeek());
    	
    	// add week day name as header
    	for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
    		LocalDate date = first.minusDays(dayOfWeekOfFirst - dayOfWeek);
    		DayOfWeek day = date.getDayOfWeek();
    		Label calDay = new Label(day.getDisplayName(TextStyle.FULL, locale.get()));
    		calDay.setPadding(new Insets(5));
    		calDay.setFont(new Font("Arial Bold", 14));
    		calDay.setPrefSize(HEAD_WIDTH, HEAD_HEIGHT);
    		calDay.setAlignment(Pos.CENTER);
    		calGrid.add(calDay, dayOfWeek - 1, 1);
    	}
    	
    	// set variables for month creation
    	LocalDate firstDisplayedDate = firstDate.get(); // display previous days of the week
    	LocalDate lastDisplayedDate = firstDate.get().plusWeeks(1); // last day of current month
    	
    	// loop to place dates and task contents
    	for (LocalDate date = firstDisplayedDate; !date.isAfter(lastDisplayedDate.minusDays(1)); date = date.plusDays(1)) {
    			
    			// create a smaller grid inside each calendar grid box to show day number and tasks due that day; also positioning
    			GridPane cellGrid = new GridPane();
    			cellGrid.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
    			cellGrid.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
    			cellGrid.setPadding(new Insets(CELL_PADDING));
    			GridPane.setMargin(cellGrid, new Insets(CELL_MARGIN));
    			
    			// cell day number & positioning
    			Label dayNum = new Label(String.valueOf(date.getDayOfMonth()));
    			dayNum.setFont(new Font("Arial Bold", 18));
    			dayNum.setPrefSize(CELL_WIDTH - CELL_PADDING, HEAD_HEIGHT);
    			
    			// task contents based on day
    			int numTasks = 0;
    			String tasks;
    			
    			// show task information
    			for (Task t : taskList) {
    				if (t.getDate().equals(date) && numTasks < 10) {
    					numTasks++;
    					tasks = t.toString();
    					
    					FileInputStream input;
    	    			try {
    	    				String userDirectory = System.getProperty("user.dir");
    	    				switch(t.getPriority())
    	    				{
    	    					case HIGH:	
    	    						input = new FileInputStream(userDirectory + "/images/HighPriority.png");
    								break;
    	    					case MEDIUM:
    	    						input = new FileInputStream(userDirectory + "/images/MediumPriority.png");
    	    						break;
    	    					case LOW:	
    	    						input = new FileInputStream(userDirectory + "/images/LowPriority.png");
    	    						break;
    	    					default:	
    	    						input = new FileInputStream(userDirectory + "/images/LowPriority.png");
    	    						break;
    	    				}
    	    				ImageView priority = new ImageView(new Image(input));
    	    				Button priorityBtn = new Button();
    	    				
    	    				// select task
    	    				priorityBtn.setOnMouseClicked(e -> {
    	    					int setIndex = TaskManager.getTaskIndexByName(t.toString());
    	    					if (setIndex != -1)
    	    						TaskManager.setSelectedTaskIndex(setIndex);
    	    				});
    	    				
    	    				// show task info
	    					Rectangle rect = new Rectangle(0, 0, 100, 100);
	    					Tooltip taskInfo = new Tooltip("Parent Space: " + t.getParentName() + "\nPriority: " + t.getPriority() + "\nStatus: " + t.getCurrent());
	    					Tooltip.install(rect, taskInfo);
	    					priorityBtn.setTooltip(taskInfo);
	    					taskInfo.setShowDelay(null);
    	    				
    	    				priority.setFitHeight(25);
    	    				priority.setFitWidth(5);
    	    				
    	    				// button styling
    	    				priorityBtn.getStyleClass().add("cal-button");
    	    				priorityBtn.setText(tasks);
    	    				priorityBtn.setGraphic(priority);
    	    				priorityBtn.setAlignment(Pos.CENTER_LEFT);
    	    				priorityBtn.setPrefHeight(10);
    	    				priorityBtn.setPrefWidth(CELL_WIDTH);
    	    				cellGrid.add(priorityBtn, 0, numTasks);
    	    			} 
	    				catch (FileNotFoundException e) {
    	    				System.out.println("Unable to find priority graphic for button!");
    	    			}
    				}
    				else if (t.getDate().equals(date) && numTasks >= 10) {
    					tasks = "[...]";
    					cellGrid.add(new Label(tasks), 0, numTasks+1);
    				}
    			}
    			
    			
    			// variables for placement in the calendar grid
    			int dayOfWeek = date.get(weekFields.dayOfWeek()); // gets the column+1 to place the contents into
    			int daysSinceFirstDisplayed = (int) firstDisplayedDate.until(date,  ChronoUnit.DAYS); // local var to calculate number of weeks in the month
    			int weeksSinceFirstDisplayed = daysSinceFirstDisplayed / 7; // calculates the row number to place the cell contents
    			
    			// grey out dates before current day
    			if (date.isBefore(now)) {
    				cellGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    			}
    			
    			if (date.isEqual(now)) {
    				cellGrid.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    			}
    			
    			// place contents into cell
    			cellGrid.setAlignment(Pos.TOP_LEFT);
    			cellGrid.add(dayNum, 0, 0);
    			calGrid.add(cellGrid, dayOfWeek - 1, weeksSinceFirstDisplayed + 2);
    	}
    }
    
    /**
     * Helper method to redraw the calendar when button is pressed to advance/go back to next/prev month
     * 
     * @param month, YearMonth object that passes current selected month
     * @param locale, Locale object that passes current locale
     * @param calGrid, GridPane object for laying out calendar contents
     * @param CELL_WIDTH, width of cell
     * @param CELL_HEIGHT, height of cell
     */
    private static void drawCalendar(ObjectProperty<YearMonth> month, ObjectProperty<Locale> locale, GridPane calGrid, final int CELL_WIDTH, final int CELL_HEIGHT, ArrayList<Task> taskList) {
    	
    	// positioning variables
    	final int HEAD_WIDTH = CELL_WIDTH;
    	final int HEAD_HEIGHT = 25;
    	final int CAL_WIDTH = CELL_WIDTH * 7;
    	final int CAL_HEIGHT = CELL_HEIGHT * 5;
    	final int CAL_PADDING = 5;
    	final int CELL_PADDING = 5;
    	final int CELL_MARGIN = 2;
    	
    	// month and date label
    	LocalDate currentMonth = month.get().atDay(1);
    	Label monthLabel = new Label(currentMonth.getMonth().toString() + " " + currentMonth.getYear());
    	monthLabel.setFont(new Font("Arial Bold", 24));
    	monthLabel.setAlignment(Pos.CENTER);
    	monthLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	monthLabel.setPrefSize(HEAD_WIDTH * 5, HEAD_HEIGHT);
    	monthLabel.setTextFill(Color.WHITE);
    	
    	// adding a header (e.g., November 2021) and setting its position and styling
    	calGrid.getChildren().remove(monthLabel);
    	calGrid.add(monthLabel, 1, 0, 5, 1);
    	calGrid.setPrefSize(CAL_WIDTH, CAL_HEIGHT);
    	calGrid.setPadding(new Insets(CAL_PADDING));
    	calGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    	calGrid.setAlignment(Pos.CENTER);
    	
    	// getting the names of the weeks and the first day of the month
    	WeekFields weekFields = WeekFields.of(locale.get());
    	LocalDate first = month.get().atDay(1);
    	
    	// get the day of the week of the first day of the month
    	int dayOfWeekOfFirst = first.get(weekFields.dayOfWeek());
    	
    	// add week day name as header
    	for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
    		LocalDate date = first.minusDays(dayOfWeekOfFirst - dayOfWeek);
    		DayOfWeek day = date.getDayOfWeek();
    		Label calDay = new Label(day.getDisplayName(TextStyle.FULL, locale.get()));
    		calDay.setPadding(new Insets(5));
    		calDay.setFont(new Font("Arial Bold", 14));
    		calDay.setPrefSize(HEAD_WIDTH, HEAD_HEIGHT);
    		calDay.setAlignment(Pos.CENTER);
    		calGrid.add(calDay, dayOfWeek - 1, 1);
    	}
    	
    	// set variables for month creation
    	LocalDate firstDisplayedDate = first.minusDays(dayOfWeekOfFirst - 1); // display days of previous month
    	LocalDate last = month.get().atEndOfMonth(); // last day of current month
    	LocalDate currentDay = LocalDate.now(); // current day, for setting previous days to show up greyed out
    	int dayOfWeekOfLast = last.get(weekFields.dayOfWeek()); // get the day of the week for the last day of the month
    	LocalDate lastDisplayedDate = last.plusDays(7 - dayOfWeekOfLast); // display days of next month
    	
    	// loop to place dates and task contents
    	for (LocalDate date = firstDisplayedDate; !date.isAfter(lastDisplayedDate); date = date.plusDays(1)) {
    			
    			// create a smaller grid inside each calendar grid box to show day number and tasks due that day; also positioning
    			GridPane cellGrid = new GridPane();
    			cellGrid.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
    			cellGrid.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
    			cellGrid.setPadding(new Insets(CELL_PADDING));
    			GridPane.setMargin(cellGrid, new Insets(CELL_MARGIN));
    			
    			// cell day number & positioning
    			Label dayNum = new Label(String.valueOf(date.getDayOfMonth()));
    			dayNum.setFont(new Font("Arial Bold", 18));
    			dayNum.setPrefSize(CELL_WIDTH - CELL_PADDING, HEAD_HEIGHT);
    			
    			// task contents based on day
    			int numTasks = 0;
    			String tasks;
    			
    			// show task information
    			for (Task t : taskList) {
    				if (t.getDate().equals(date) && numTasks < 4) {
    					numTasks++;
    					tasks = t.toString();
    					
    					FileInputStream input;
    	    			try {
    	    				String userDirectory = System.getProperty("user.dir");
    	    				switch(t.getPriority())
    	    				{
    	    					case HIGH:	
    	    						input = new FileInputStream(userDirectory + "/images/HighPriority.png");
    								break;
    	    					case MEDIUM:
    	    						input = new FileInputStream(userDirectory + "/images/MediumPriority.png");
    	    						break;
    	    					case LOW:	
    	    						input = new FileInputStream(userDirectory + "/images/LowPriority.png");
    	    						break;
    	    					default:	
    	    						input = new FileInputStream(userDirectory + "/images/LowPriority.png");
    	    						break;
    	    				}
    	    				ImageView priority = new ImageView(new Image(input));
    	    				Button priorityBtn = new Button();
    	    				
    	    				// when task button is selected, allow you to edit or delete by setting current selected task index
    	    				priorityBtn.setOnMouseClicked(e -> {
    	    					int setIndex = TaskManager.getTaskIndexByName(t.toString());
    	    					if (setIndex != -1)
    	    						TaskManager.setSelectedTaskIndex(setIndex);
    	    				});
    	    				
    	    				// show task info
	    					Rectangle rect = new Rectangle(0, 0, 100, 100);
	    					Tooltip taskInfo = new Tooltip("Parent Space: " + t.getParentName() + "\nPriority: " + t.getPriority() + "\nStatus: " + t.getCurrent());
	    					Tooltip.install(rect, taskInfo);
	    					priorityBtn.setTooltip(taskInfo);
	    					taskInfo.setShowDelay(null);
    	    				
    	    				priority.setFitHeight(25);
    	    				priority.setFitWidth(5);
    	    				
    	    				// button styling
    	    				priorityBtn.getStyleClass().add("cal-button");
    	    				priorityBtn.setText(tasks);
    	    				priorityBtn.setGraphic(priority);
    	    				priorityBtn.setAlignment(Pos.CENTER_LEFT);
    	    				priorityBtn.setPrefHeight(10);
    	    				priorityBtn.setPrefWidth(CELL_WIDTH);
    	    				cellGrid.add(priorityBtn, 0, numTasks);
    	    			} 
	    				catch (FileNotFoundException e) {
    	    				System.out.println("Unable to find priority graphic for button!");
    	    			}
    				}
    				else if (t.getDate().equals(date) && numTasks >= 4) {
    					tasks = "[...]";
    					cellGrid.add(new Label(tasks), 0, numTasks+1);
    				}
    			}
    			
    			
    			// variables for placement in the calendar grid
    			int dayOfWeek = date.get(weekFields.dayOfWeek()); // gets the column+1 to place the contents into
    			int daysSinceFirstDisplayed = (int) firstDisplayedDate.until(date,  ChronoUnit.DAYS); // local var to calculate number of weeks in the month
    			int weeksSinceFirstDisplayed = daysSinceFirstDisplayed / 7; // calculates the row number to place the cell contents
    			
    			// grey out dates before current day
    			if (date.isBefore(currentDay)) {
    				cellGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    			}
    			
    			if (date.isEqual(currentDay)) {
    				cellGrid.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    			}
    			
    			// place contents into cell
    			cellGrid.setAlignment(Pos.TOP_LEFT);
    			cellGrid.add(dayNum, 0, 0);
    			calGrid.add(cellGrid, dayOfWeek - 1, weeksSinceFirstDisplayed + 2);
    	}
    }

    /*
     * Refreshes the current overview or loads the newly toggled overview
     * depending on the int passed in. Default is home overview.
     * 
     * @param	overviewType	Pass in 1 to view homeoverview, 2 for daily overview,
     * 							3 for weekly overview, and 4 for monthly overview.
     * 							Toggles home overview if none of the above matches.
     */
	public static Pane toggleOverview(int overviewType, double centerWidth, double centerHeight) {

		switch (overviewType) {
		case 2:
			return(toggleDaily(centerWidth, centerHeight));
		case 3:
			return(toggleWeekly(centerWidth, centerHeight));
		case 4:
			return(toggleMonthly(centerWidth, centerHeight));
		default:
			return(toggleHome(centerWidth, centerHeight));
		}
	}
    
   
    

    
}