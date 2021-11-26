// Author: Daniel Andrejczyk

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.stage.Popup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class Overview extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Overrides start from Application class
     * to set-up JavaFX stage, scene, and scene graph.
     * 
     * @param	primaryStage	The primary stage to build on.
     */
    @Override
    public void start(Stage primaryStage) {
    	
    	////////////////////////////
    	//	Initial Scene Set-up  //
    	////////////////////////////
    	
    	// Set the title
        primaryStage.setTitle("Task Manager");
        primaryStage.centerOnScreen();
       
        
        // Create a border pane to lay out all the items
        BorderPane border = new BorderPane();
        
        //
        // Top panel
        //
        
        HBox topSection = new HBox();
        topSection.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        topSection.setPadding(new Insets(15, 12, 15, 12));
        topSection.setSpacing(10);
        topSection.setAlignment(Pos.CENTER);
        
        // Space manipulation buttons
        Button addSpace = new Button("Add Space");
        addSpace.setPrefSize(100, 40);
        
        Button editSpace = new Button("Edit Space");
        editSpace.setPrefSize(100, 40);
        
        Button deleteSpace = new Button("Delete Space");
        deleteSpace.setPrefSize(100, 40);
        
        // create space manager
        SpaceManager spaceManager = new SpaceManager();
        TaskManager taskManager = new TaskManager();
        
        // Space selection drop-down
        ComboBox<Space> spaceFilter = new ComboBox<Space>();
    	ArrayList<Space> spaceList = spaceManager.getSpaceList();
    	ArrayList<Task> taskList = taskManager.getTaskList(spaceList.get(0));
        spaceFilter.getItems().addAll(spaceList);
        spaceFilter.getSelectionModel().selectFirst();
        spaceFilter.setPrefHeight(40);
        spaceFilter.setPrefWidth(200);
        
//        Text progName = new Text("TASK MANAGER");
//        progName.setFont(new Font("Arial Bold", 20));
//        progName.setX(20);
        
        // Task manipulation buttons
        Button addTask = new Button("Add Task");
        addTask.setPrefSize(100, 40);
        
        Button editTask = new Button("Edit Task");
        editTask.setPrefSize(100, 40);
        
        Button deleteTask = new Button("Delete Task");
        deleteTask.setPrefSize(100, 40);
        
        topSection.getChildren().addAll(addSpace, editSpace, deleteSpace, spaceFilter, addTask, editTask, deleteTask);
        
        //
        // Left panel
        //
        
        VBox leftSection = new VBox();
        leftSection.setPadding(new Insets(15,15,15,15));
        leftSection.setSpacing(10);
        leftSection.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        leftSection.setAlignment(Pos.TOP_CENTER);
        
        /*
         * Top Button Action Events
         */
        
        // Add Space Dialog
        addSpace.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		spaceDialog(0, spaceList, spaceFilter, spaceManager);
        	}
        });
        
        // Edit Space
        editSpace.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		spaceDialog(1, spaceList, spaceFilter, spaceManager);
        	}
        });
        
        // Delete space action
        deleteSpace.setOnAction(new EventHandler<ActionEvent>() {
            
        	@Override
            public void handle(ActionEvent event) {
               
        		spaceDialog(2, spaceList, spaceFilter, spaceManager);
            }
         });
        
        // Task options
        
        // Add Task
        addTask.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		// add task method
        	}
        });
        
        // Edit Space
        editTask.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		// edit task method
        	}
        });
        
        // Delete space action
        deleteTask.setOnAction(new EventHandler<ActionEvent>() {
            
        	@Override
            public void handle(ActionEvent event) {
        		// delete task method
            }
         });
        
        // filter by spaces when the current space is changed
        spaceFilter.setOnAction(new EventHandler<ActionEvent> () {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		// somehow need to filter the tasks that we want by the parent space
        		ArrayList<Task> filteredTasks = taskManager.getTaskList(spaceFilter.getSelectionModel().getSelectedItem());
        	}
        });
        
        // Overview toggle buttons
        Button homeToggle = new Button("Home");
        Button dailyToggle = new Button("Daily"); 
        Button weeklyToggle = new Button("Weekly");
        Button monthlyToggle = new Button("Monthly");
        homeToggle.setPrefSize(80, 40);
        dailyToggle.setPrefSize(80, 40);
        weeklyToggle.setPrefSize(80, 40);
        monthlyToggle.setPrefSize(80, 40);
        
        // Connect overview toggle buttons to their
        // MouseClick event handlers
        homeToggle.setOnMouseClicked(e -> toggleHome(border, spaceManager));
        dailyToggle.setOnMouseClicked(e -> toggleDaily(border, spaceManager));
        //weeklyToggle
        monthlyToggle.setOnMouseClicked(e -> toggleMonthly(border, spaceManager));
        
        Text toggleLabel = new Text("View");
        toggleLabel.setFill(Color.WHITE);
        toggleLabel.setFont(new Font("Arial Bold", 16));
        //toggleLabel.setTextAlignment(TextAlignment.CENTER);
        
        leftSection.getChildren().addAll(toggleLabel, homeToggle, dailyToggle, weeklyToggle, monthlyToggle);
        
        // Set up the default overview (home overview)
        toggleHome(border, spaceManager);
        
        // Set sections to borderpane
        border.setTop(topSection);
        border.setLeft(leftSection);
        
        Scene scene = new Scene(border);
        scene.getStylesheets().add("/tmStyle.css");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1000);
        // Show the scene
        primaryStage.show();
        
        
    }
    
    /**
     * Sets up the home task overview and sets it to the center
     * of the border pane that is passed in.
     * 
     * @param	b	The borderpane to anchor the home overview to
     */
    private void toggleHome(BorderPane b, SpaceManager m)
    {
    	
    	AnchorPane homePane = new AnchorPane();
    	homePane.setPadding(new Insets(20, 20, 20, 20));
    	
    	Calendar newCalendar = Calendar.getInstance();
    	LocalDate today = LocalDate.now();
    	
    	Space myTasks = m.getSpaceList().get(0);
    	
    	Task physCh1 = new Task("Physics Chapter 1", today, myTasks);
    	physCh1.setCurrent(Status.progress.DONE);
    	physCh1.setDescription("Ask professor about problem 7");
    	Task calcCh1 = new Task("Calculus Chapter 1", today, myTasks);
    	calcCh1.setCurrent(Status.progress.IN_PROGRESS);
    	calcCh1.setDescription("Help!");
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(physCh1, calcCh1);
    	
    	ListView<Task> listView = new ListView<Task>(tasks);
    	listView.setPrefWidth(450);
    	
    	Label taskLabel = new Label("Tasks");
    	taskLabel.setFont(new Font("Arial", 24));
    	taskLabel.setPrefWidth(450);
    	
    	// current day
    	String now = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(new java.util.Date());
    	
    	Label dateLabel = new Label(now);
    	dateLabel.setFont(new Font("Arial Bold", 24));
    	dateLabel.setPrefWidth(350);
    	
    	// Task information text flow
    	VBox taskInformation = new VBox();
    	taskInformation.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
    	taskInformation.setSpacing(10);
    	taskInformation.setMaxWidth(350); 
    	
    	Text noTaskSelected = new Text("Select a task from the right list to see more information");
    	taskInformation.getChildren().add(noTaskSelected);
    	taskInformation.setPrefSize(350, 400);
		taskInformation.setPadding(new Insets(20,20,20,20));
    		
    	// Get the selected task
    	listView.setOnMouseClicked(event -> {
    		ObservableList<Task> selectedIndices = listView.getSelectionModel().getSelectedItems();
    		
    		Task selectedTask = selectedIndices.get(0);
    		
    		taskInformation.getChildren().clear();
    		
    		Text priority = new Text("Priority: " + selectedTask.getPriority());
    		Text status = new Text("Status: " + selectedTask.getCurrent());
    		Text statusDesc = new Text("Status Description: " + selectedTask.getDescription());
    		Text parentSpace = new Text("Parent Space: " + selectedTask.getParentName());
    		
    		taskInformation.getChildren().addAll(priority, status, statusDesc, parentSpace);
    	});
    	
    	//
    	// Anchoring
    	//
    	
    	// Task Label 
    	AnchorPane.setTopAnchor(taskLabel, 10.0);
    	AnchorPane.setRightAnchor(taskLabel, 10.0);
    	
    	// Date Label
    	AnchorPane.setTopAnchor(dateLabel, 10.0);
    	AnchorPane.setLeftAnchor(dateLabel, 20.0);
    	
    	// Task List
    	AnchorPane.setRightAnchor(listView, 10.0);
    	AnchorPane.setTopAnchor(listView, 60.0);
    	
    	// Task Information
    	AnchorPane.setTopAnchor(taskInformation, 60.0);
    	AnchorPane.setLeftAnchor(taskInformation, 20.0);
    	
    	homePane.getChildren().addAll(listView, taskLabel, dateLabel, taskInformation);
    	
    	b.setCenter(homePane);
    	
    }
    
    private void toggleDaily(BorderPane b, SpaceManager m)
    {
    	
    	AnchorPane dailyPane = new AnchorPane();
    	
    	VBox taskCol1 = new VBox();
    	taskCol1.setSpacing(10.0);
    	
    	final int WIDTH = (int) b.getCenter().getBoundsInLocal().getMaxX();
    	final int HEIGHT = (int) b.getCenter().getBoundsInLocal().getMaxY();
    	
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
    	
    	Space myTasks = m.getSpaceList().get(0);
    	
    	Task physCh1 = new Task("Physics Chapter 1", LocalDate.now(), myTasks);
    	physCh1.setCurrent(Status.progress.DONE);
    	physCh1.setDescription("Ask professor about problem 7");
    	Task calcCh1 = new Task("Calculus Chapter 1",  LocalDate.now(), myTasks);
    	calcCh1.setCurrent(Status.progress.IN_PROGRESS);
    	calcCh1.setDescription("Help!");
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(physCh1, calcCh1);
    	
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
					case HIGH:	input = new FileInputStream(userDirectory + "/images/HighPriority.png");
					case MEDIUM:input = new FileInputStream(userDirectory + "/images/MediumPriority.png");
					case LOW:	input = new FileInputStream(userDirectory + "/images/LowPriority.png");
					default:	input = new FileInputStream(userDirectory + "/images/LowPriority.png");
				}
				ImageView priority = new ImageView(new Image(input));
				taskButton.setGraphic(priority);
				taskButton.setMinWidth(200);
				taskButton.setAlignment(Pos.CENTER_LEFT);
			} catch (FileNotFoundException e) {
				System.out.println("Unable to find priority graphic for button!");
			}
    		taskCol1.getChildren().add(taskButton);
    		
    	}
    	
    	AnchorPane.setTopAnchor(taskCol1, 70.0);
    	AnchorPane.setLeftAnchor(taskCol1, 50.0);
    	
    	dailyPane.getChildren().addAll(canvas, taskCol1);
    	
    	b.setCenter(dailyPane);
    	
    }
    
    private void toggleWeekly() {
    	
    }
    
    /**
     * Method to display the calendar overview
     * 
     * @param b
     * @param sManager
     */
    private void toggleMonthly(BorderPane b, SpaceManager sManager) {
    	
    	// Note for reader:
    	// Some code based on https://gist.github.com/james-d/ee8a5c216fb3c6e027ea 
    	// However, 95% of it is modified to fit the purposes of this project, which is to display tasks and much is original; still, will credit
    	
    	// variables for the month and the current locale
    	final ObjectProperty<YearMonth> month = new SimpleObjectProperty<>();
    	final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    	
    	// for testing tasks
    	LocalDate today = LocalDate.now();
    	LocalDate tomorrow = today.plusDays(1);
    	Task physCh1 = new Task("Physics Chapter 1", today, sManager.getSpaceList().get(0));
    	physCh1.setCurrent(Status.progress.DONE);
    	physCh1.setPriority(Task.Priority.HIGH);
    	physCh1.setDescription("Ask professor about problem 7");
    	Task calcCh1 = new Task("Calculus Chapter 1", today, sManager.getSpaceList().get(0));
    	calcCh1.setCurrent(Status.progress.IN_PROGRESS);
    	calcCh1.setDescription("Help!");
    	calcCh1.setPriority(Task.Priority.MEDIUM);
    	Task calcCh2 = new Task("Calculus Chapter 2", tomorrow, sManager.getSpaceList().get(0));
    	calcCh2.setCurrent(Status.progress.IN_PROGRESS);
    	calcCh2.setDescription("Help!");
    	calcCh2.setPriority(Task.Priority.LOW);
    	
    	ArrayList<Task> tasks = new ArrayList<Task>();
    	tasks.add(physCh1);
    	tasks.add(calcCh2);
    	tasks.add(calcCh1);
    	
    	// set month to now
    	month.setValue(YearMonth.now());
    	
    	// anchorpane for displaying calendar
    	AnchorPane monthPane = new AnchorPane();
    	
    	// get boundaries of container to draw calendar
    	final int WIDTH = (int) b.getCenter().getBoundsInLocal().getMaxX();
    	final int HEIGHT = (int) b.getCenter().getBoundsInLocal().getMaxY();
   
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
    	prevMonth.setStyle("-fx-border-color: #708090");
    	nextMonth.setStyle("-fx-border-color: #708090");
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
    		drawCalendar(month, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, tasks);
    	});
    	nextMonth.setOnAction(e -> {
    		month.set(month.get().plusMonths(1));
    		calGrid.getChildren().clear();
    		calGrid.add(prevMonth, 0, 0, 1, 1);
        	calGrid.add(nextMonth, 6, 0, 1, 1);
    		drawCalendar(month, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, tasks);
    	});
    	
    	// add buttons to the calendar grid
    	calGrid.add(prevMonth, 0, 0, 1, 1);
    	calGrid.add(nextMonth, 6, 0, 1, 1);
    	
    	// set sizing for buttons
    	prevMonth.setPrefSize(CELL_WIDTH, 25);
    	nextMonth.setPrefSize(CELL_WIDTH, 25);
    	
    	// draw starting calendar
    	drawCalendar(month, locale, calGrid, CELL_WIDTH, CELL_HEIGHT, tasks);
    	
    	// place calendar in the anchor pane
    	monthPane.getChildren().add(calGrid);
    	b.setCenter(monthPane);
    	
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
    private void drawCalendar(ObjectProperty<YearMonth> month, ObjectProperty<Locale> locale, GridPane calGrid, final int CELL_WIDTH, final int CELL_HEIGHT, ArrayList<Task> taskList) {
    	
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
    	    				
    	    				priorityBtn.setOnAction(e -> {
    	    					System.out.println(t.getPriority());
    	    					// in future, do a pop up or something
    	    				});
    	    				
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
    
    /**
     * Helper method to create space dialogs for adding, editing, and deleting spaces
     * 
     * @param type, the type of space modification (0 = add, 1 = edit, 2 = delete)
     * @param sList, the list of spaces from overview
     * @param sFilter, the combobox of spaces from overview
     * @param sManager, the space manager object from overview
     */
    private void spaceDialog(int type, ArrayList<Space> sList, ComboBox<Space> sFilter, SpaceManager sManager) {
    	
    	// create dialog and naming
    	Dialog<Pair<String, Integer>> sDialog = new Dialog<>();
    	GridPane sGrid = new GridPane();
    	ButtonType confirmBtnType = new ButtonType("Confirm", ButtonData.OK_DONE);
    	TextField sName = new TextField();
    	ComboBox<Space> pSpace = new ComboBox<Space>();
    
    	// naming
    	switch (type) {
    	case 0:	// Add Space 
    		sDialog.setTitle("Add Space");
    		sDialog.setHeaderText("Add a new space");
    		break;
    	case 1: // Edit Space
    		sDialog.setTitle("Edit Space");
    		sDialog.setHeaderText("Edit space: " + sFilter.getSelectionModel().getSelectedItem().toString());
    		sDialog.setContentText("Parent space: " + sFilter.getSelectionModel().getSelectedItem().getParentName());
    		break;
    	case 2: // Delete Space + custom alert execution
    		Alert deleteSD = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this space?", ButtonType.YES, ButtonType.CANCEL);
            deleteSD.setTitle("Delete Space");
            deleteSD.setHeaderText("Delete space: " + sFilter.getSelectionModel().getSelectedItem().toString());
            deleteSD.showAndWait();

            if (deleteSD.getResult() == ButtonType.YES) {
            	try {
            		sManager.deleteSpace(sFilter.getSelectionModel().getSelectedIndex());
            	} catch (Exception e) {
            		systemAlert(e);
            	}
                sFilter.getItems().clear();
                sFilter.getItems().addAll(sManager.getSpaceList());
                sFilter.getSelectionModel().selectFirst();
            }
            return; 
    	default:
    		System.out.println("No type");
    		break;
    	}
    	
    	// add buttons
    	sDialog.getDialogPane().getButtonTypes().addAll(confirmBtnType, ButtonType.CANCEL);
    	
    	// parent space
    	int pIndex = sManager.getParentIndex(sFilter.getSelectionModel().getSelectedIndex());
    	pSpace.getItems().clear();
    	pSpace.getItems().addAll(sManager.getSpaceList());
    	pSpace.getSelectionModel().select(pIndex);
    	
    	// positioning
    	sGrid.setHgap(10);
    	sGrid.setVgap(10);
    	sGrid.setPadding(new Insets(20, 150, 10, 10));
    	sGrid.add(new Label("Space Name"), 0, 0);
    	sGrid.add(sName, 0, 1);
    	sGrid.add(new Label("Parent Space"), 1, 0);
    	sGrid.add(pSpace, 1, 1);
    	
    	// disable confirm button until information is entered
    	Node confirmBtn = sDialog.getDialogPane().lookupButton(confirmBtnType);
    	confirmBtn.setDisable(true);
    	sDialog.getDialogPane().setContent(sGrid);
    	sName.textProperty().addListener((observable, oldValue, newValue) -> {
    	    confirmBtn.setDisable(newValue.trim().isEmpty());
    	});
    	sName.setPromptText(sFilter.getSelectionModel().getSelectedItem().toString());
    	
    	// return sDialog contents as Pair once confirmBtn is pressed
    	sDialog.setResultConverter(sDialogBtn -> {
            
	        if (sDialogBtn == confirmBtnType) {
	            return new Pair<>(sName.getText(), pSpace.getSelectionModel().getSelectedIndex());
	        }
	            return null;
        });
        
    	// create not-null result object whose contents are the Pair of values
        Optional<Pair<String, Integer>> result = sDialog.showAndWait();
        
        // execute once sDialog is completed and result is not null
        result.ifPresent(spaceValue -> {
            
        	// pull variables from pair object
        	String spcStr = spaceValue.getKey();
            int newPIndex = spaceValue.getValue();
            int i = sFilter.getSelectionModel().getSelectedIndex();
            
            // execute based on current dialog type
            switch(type) {
            case 0:	// Add Space
            	try {
            		sManager.addSpace(sManager.getSpaceList().get(newPIndex), spcStr);
            	}
            	catch (Exception e) {
            		systemAlert(e);
            	}
            	break;
            case 1: // Edit Space
            	try {
            		sManager.editSpace(sManager.getSpaceList().get(newPIndex), i, spcStr);
            	}
            	catch (Exception e) {
            		systemAlert(e);
            	}
            	break;
            }
            
            // update space filter list
            sFilter.getItems().clear();
            sFilter.getItems().addAll(sManager.getSpaceList());
            sFilter.getSelectionModel().selectLast();
        });
        
        // reset text and null values in textfield
        sName.setText("");
        sName.setPromptText("");
     
        return;
    }
    
    /**
     * Helper method to create space dialogs for adding, editing, and deleting spaces
     * 
     * @param type, the type of space modification (0 = add, 1 = edit, 2 = delete)
     * @param sList, the list of spaces from overview
     * @param sFilter, the combobox of spaces from overview
     * @param sManager, the space manager object from overview
     * @param tManager, the task manager object from overview
     * @param tList, the list of spaces from overview
     */
    private void taskDialog(int type, ArrayList<Space> sList, ComboBox<Space> sFilter, SpaceManager sManager, TaskManager tManager, ArrayList<Task> tList) {
    	
    	// create dialog and naming
    	Dialog<Pair<String, Integer>> sDialog = new Dialog<>();
    	GridPane sGrid = new GridPane();
    	ButtonType confirmBtnType = new ButtonType("Confirm", ButtonData.OK_DONE);
    	TextField sName = new TextField();
    	ComboBox<Space> pSpace = new ComboBox<Space>();
    
    	// naming
    	switch (type) {
    	case 0:	// Add Task 
    		sDialog.setTitle("Add Task");
    		sDialog.setHeaderText("Add a new task");
    		break;
    	case 1: // Edit Task
    		sDialog.setTitle("Edit Task");
    		sDialog.setHeaderText("Edit task: " + sFilter.getSelectionModel().getSelectedItem().toString());
    		sDialog.setContentText("Parent space: " + sFilter.getSelectionModel().getSelectedItem().getParentName());
    		// add stuff
    		//
    		break;
    	case 2: // Delete Space + custom alert execution
    		Alert deleteSD = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this task?", ButtonType.YES, ButtonType.CANCEL);
            deleteSD.setTitle("Delete Task");
            deleteSD.setHeaderText("Delete task: " + sFilter.getSelectionModel().getSelectedItem().toString());
            deleteSD.showAndWait();

            if (deleteSD.getResult() == ButtonType.YES) {
            	//sManager.deleteSpace(sFilter.getSelectionModel().getSelectedIndex());
                sFilter.getItems().clear();
                sFilter.getItems().addAll(sList);
                sFilter.getSelectionModel().selectFirst();
            }
            return; 
    	default:
    		System.out.println("No type");
    		break;
    	}
    	
    	// add buttons
    	sDialog.getDialogPane().getButtonTypes().addAll(confirmBtnType, ButtonType.CANCEL);
    	
    	// parent space
    	int pIndex = sManager.getParentIndex(sFilter.getSelectionModel().getSelectedIndex());
    	pSpace.getItems().clear();
    	pSpace.getItems().addAll(sList);
    	pSpace.getSelectionModel().select(pIndex);
    	
    	// positioning
    	sGrid.setHgap(10);
    	sGrid.setVgap(10);
    	sGrid.setPadding(new Insets(20, 150, 10, 10));
    	sGrid.add(new Label("Space Name"), 0, 0);
    	sGrid.add(sName, 0, 1);
    	sGrid.add(new Label("Parent Space"), 1, 0);
    	sGrid.add(pSpace, 1, 1);
    	
    	// disable confirm button until information is entered
    	Node confirmBtn = sDialog.getDialogPane().lookupButton(confirmBtnType);
    	confirmBtn.setDisable(true);
    	sDialog.getDialogPane().setContent(sGrid);
    	sName.textProperty().addListener((observable, oldValue, newValue) -> {
    	    confirmBtn.setDisable(newValue.trim().isEmpty());
    	});
    	sName.setPromptText(sFilter.getSelectionModel().getSelectedItem().toString());
    	
    	// return sDialog contents as Pair once confirmBtn is pressed
    	sDialog.setResultConverter(sDialogBtn -> {
            
	        if (sDialogBtn == confirmBtnType) {
	            return new Pair<>(sName.getText(), pSpace.getSelectionModel().getSelectedIndex());
	        }
	            return null;
        });
        
    	// create not-null result object whose contents are the Pair of values
        Optional<Pair<String, Integer>> result = sDialog.showAndWait();
        
        // execute once sDialog is completed and result is not null
        result.ifPresent(spaceValue -> {
            
        	// pull variables from pair object
        	String spcStr = spaceValue.getKey();
            int newPIndex = spaceValue.getValue();
            int i = sFilter.getSelectionModel().getSelectedIndex();
            
            // execute based on current dialog type
            switch(type) {
            case 0:	// Add task
            	try {
            		//sManager.addSpace(sManager.getSpaceList().get(newPIndex), spcStr);
            	}
            	catch (Exception e) {
            		//systemAlert(e)
            	}
            	break;
            case 1: // Edit task
            	//sManager.editSpace(sManager.getSpaceList().get(newPIndex), i, spcStr);
            	try {
            		// do something
            	}
            	catch (Exception e) {
            		//systemAlert(e);
            	}
            	break;
            	
            	// copy and change
            	// add stuff for edit task
            }
            
            // update space filter list
            sFilter.getItems().clear();
            sFilter.getItems().addAll(sList);
            sFilter.getSelectionModel().selectLast();
        });
        
        // reset text and null values in textfield
        sName.setText("");
        sName.setPromptText("");
     
        return;
    }
    
    /**
     * Helper method to notify user of actions that are not permitted
     * 
     * @param e, the exception message thrown by the calling method
     */
    private void systemAlert(Exception e) {
    	Alert badName = new Alert(AlertType.ERROR, e.toString(), ButtonType.OK);
        badName.setTitle("Alert");
        badName.showAndWait();
    }
}