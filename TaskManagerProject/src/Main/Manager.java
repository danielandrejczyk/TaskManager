package Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

/**
 * Initiates and manages the whole application. Makes calls to Overview to update
 * which overview UI is currently visible on the application.
 *
 */
public class Manager extends Application {

	/* Class Variables */
	private TaskManager taskManager;
	private SpaceManager spaceManager;
	private int currentView;
	private static boolean sFilterUpdated;

	public static void main(String[] args) {
		
		sFilterUpdated = false;
		launch(args);
		
	}

	/**
	 * Saves tasks and spaces before closing application
	 */
	@Override
	public void stop(){

		taskManager.storeTasks();
		spaceManager.storeSpaces();

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
		
		taskManager = new TaskManager();
		spaceManager = new SpaceManager();
		taskManager.loadTasks();
		spaceManager.loadSpaces();
		
		currentView = 1;
		
		// Set the title
		primaryStage.setTitle("Task Manager");
		primaryStage.centerOnScreen();

		// Create a border pane to lay out all the items
		BorderPane border = new BorderPane();
		border.setPrefSize(1000, 600);
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

		// Space selection drop-down
		ComboBox<Space> spaceFilter = new ComboBox<Space>();
		ComboBox<Task> taskFilter = new ComboBox<Task>();
		ArrayList<Space> spaceList = SpaceManager.getSpaceList();
		ArrayList<Task> taskList = TaskManager.getTaskList(spaceList.get(0));
		spaceFilter.getItems().addAll(spaceList);
		spaceFilter.getSelectionModel().selectFirst();
		spaceFilter.setPrefHeight(40);
		spaceFilter.setPrefWidth(200);
		taskFilter.getItems().addAll(taskList);
		taskFilter.getSelectionModel().selectFirst();
		taskFilter.setPrefHeight(40);
		taskFilter.setPrefWidth(200);

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
				spaceDialog(0, spaceFilter);
				sFilterUpdated = false;
			}
		});

		// Edit Space
		editSpace.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				spaceDialog(1, spaceFilter);
				sFilterUpdated = false;
			}
		});

		// Delete space action
		deleteSpace.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				spaceDialog(2, spaceFilter);
				sFilterUpdated = false;
			}
		});

		// Task options

		// Add Task
		addTask.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				taskDialog(0, spaceList, spaceFilter, spaceManager,
						taskManager, taskList, taskFilter);
				sFilterUpdated = false;
			}
		});

		// Edit Task
		editTask.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				taskDialog(1, spaceList, spaceFilter, spaceManager,
						taskManager, taskList, taskFilter);
				sFilterUpdated = false;
			}
		});

		// Delete Task action
		deleteTask.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				taskDialog(2, spaceList, spaceFilter, spaceManager,
						taskManager, taskList, taskFilter);
				sFilterUpdated = true;
			}
		});

		// filter by spaces when the current space is sFilterUpdated
		spaceFilter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				SpaceManager.setSelectedSpaceIndex(spaceFilter.getSelectionModel().getSelectedIndex());
				if (!sFilterUpdated) {
					// somehow need to filter the tasks that we want by the parent space
					Pane newOverview = Overview.toggleOverview(currentView, border.getCenter().getBoundsInLocal().getMaxX(), 
							border.getCenter().getBoundsInLocal().getMaxY());
					border.setCenter(newOverview);
				}
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
		homeToggle.setOnMouseClicked(e -> {
			Pane newOverview = Overview.toggleOverview(1, border.getCenter().getBoundsInLocal().getMaxX(), 
					border.getCenter().getBoundsInLocal().getMaxY());
			border.setCenter(newOverview);
			currentView = 1;
		});
		dailyToggle.setOnMouseClicked(e -> {
			Pane newOverview = Overview.toggleOverview(2, border.getCenter().getBoundsInLocal().getMaxX(), 
					border.getCenter().getBoundsInLocal().getMaxY());
			border.setCenter(newOverview);
			currentView = 2;
		});
		weeklyToggle.setOnMouseClicked(e -> {
			Pane newOverview = Overview.toggleOverview(3, border.getCenter().getBoundsInLocal().getMaxX(), 
					border.getCenter().getBoundsInLocal().getMaxY());
			border.setCenter(newOverview);
			currentView = 3;
		});
		monthlyToggle.setOnMouseClicked(e -> {
			Pane newOverview = Overview.toggleOverview(4, border.getCenter().getBoundsInLocal().getMaxX(), 
					border.getCenter().getBoundsInLocal().getMaxY());
			border.setCenter(newOverview);
			currentView = 4;
		});


		Text toggleLabel = new Text("View");
		toggleLabel.setFill(Color.WHITE);
		toggleLabel.setFont(new Font("Arial Bold", 16));
		//toggleLabel.setTextAlignment(TextAlignment.CENTER);

		leftSection.getChildren().addAll(toggleLabel, homeToggle, dailyToggle, weeklyToggle, monthlyToggle);

		// Set up the default overview (home overview)
		Pane newOverview = Overview.toggleOverview(1, border.getWidth(), border.getHeight());
		border.setCenter(newOverview);
		
		// Set sections to borderpane
		border.setTop(topSection);
		border.setLeft(leftSection);

		primaryStage.setHeight(700);
		primaryStage.setWidth(1000);
		Scene scene = new Scene(border);
		scene.getStylesheets().add("/tmStyle.css");
		primaryStage.setScene(scene);
		
		
		// Load stored data
		taskManager.loadTasks();		
		spaceManager.loadSpaces();
		
		// Show the scene
		primaryStage.show();


	}
	
	/**
     * @author Calen
     * 
     * Helper method to create space dialogs for adding, editing, and deleting spaces
     * 
     * @param type, the type of space modification (0 = add, 1 = edit, 2 = delete)
     * @param sList, the list of spaces from overview
     * @param sFilter, the combobox of spaces from overview
     * @param sManager, the space manager object from overview
     * @param tManager, the task manager object from overview
     * @param tList, the list of spaces from overview
     */
    private void taskDialog(int type, ArrayList<Space> sList, ComboBox<Space> sFilter, 
    		SpaceManager sManager, TaskManager tManager, ArrayList<Task> tList, ComboBox<Task> tFilter) {
    	
    	// create dialog and naming
    	sFilterUpdated = false;
    	Dialog<Results> tDialog = new Dialog<>();
    	GridPane tGrid = new GridPane();
    	ButtonType confirmBtnType = new ButtonType("Confirm", ButtonData.OK_DONE);
    	TextField tName = new TextField();
    	DatePicker datePicker = new DatePicker();
    	TextField desc = new TextField();
    	
    	ComboBox<Space> pSpace = new ComboBox<Space>();
        ComboBox<Task.Priority> tPriority = new ComboBox<Task.Priority>();
        ComboBox<Status.progress> tProgress = new ComboBox<Status.progress>();
        
        //Task dummyTask = new Task("null", LocalDate.now(), spaceManager.getSpaceList().get(0));
    
    	// naming
    	switch (type) {
    	case 0:	// Add Task 
    		tDialog.setTitle("Add Task");
    		tDialog.setHeaderText("Add a new task");
    		break;
    	case 1: // Edit Task
    		tDialog.setTitle("Edit Task");
    		tDialog.setHeaderText("Choose a task and edit its properties: ");
    		break;
    	case 2: // Delete Task + custom alert execution
    		Alert deleteTD = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this task?", ButtonType.YES, ButtonType.CANCEL);
            deleteTD.setTitle("Delete Task");
            deleteTD.setHeaderText("Delete task: " + TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()));
            deleteTD.showAndWait();

            if (deleteTD.getResult() == ButtonType.YES) {
            	try {
            		taskManager.deleteTask(TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()));
            	} catch (Exception e) {
            		systemAlert(e);
            	}
                sFilter.getItems().clear();
                sFilterUpdated = true;
                sFilter.getItems().addAll(SpaceManager.getSpaceList());
                sFilter.getSelectionModel().selectFirst();
            }
            return; 
    	default:
    		System.out.println("No type");
    		break;
    	}
    	
    	// add buttons
    	tDialog.getDialogPane().getButtonTypes().addAll(confirmBtnType, ButtonType.CANCEL);
    	
    	// parent space
    	int pIndex = spaceManager.getParentIndex(sFilter.getSelectionModel().getSelectedIndex());
    	pSpace.getItems().clear();
    	pSpace.getItems().addAll(SpaceManager.getSpaceList());
    	pSpace.getSelectionModel().select(pIndex);
    	
    	tPriority.getItems().clear();
    	tPriority.getItems().add(Task.Priority.LOW);
    	tPriority.getItems().add(Task.Priority.MEDIUM);
    	tPriority.getItems().add(Task.Priority.HIGH);
    	tPriority.getSelectionModel().select(Task.Priority.MEDIUM);
    
    	tProgress.getItems().clear();
    	tProgress.getItems().add(Status.progress.TO_DO);
    	tProgress.getItems().add(Status.progress.IN_PROGRESS);
    	tProgress.getItems().add(Status.progress.DONE);
    	tProgress.getSelectionModel().select(Status.progress.IN_PROGRESS);
    	
    	int i = 0;
    	if (type == 1) {
    		i = 1;
    		tGrid.add(new Label("Choose task to edit"), 0, 0);
    	}
    	// positioning
    	tGrid.setHgap(10);
    	tGrid.setVgap(10);
    	tGrid.setPadding(new Insets(20, 150, 10, 10));
    	tGrid.add(new Label("Task Name"), 0, 0 + i);
    	tGrid.add(tName, 1, 0+ i);
    	tGrid.add(new Label("Parent Space"), 0, 1+ i);
    	tGrid.add(pSpace, 1, 1+ i);
    	tGrid.add(new Label("Due Date"), 0, 2+ i);
    	tGrid.add(datePicker, 1, 2+ i);
    	tGrid.add(new Label("Description"), 0, 3+ i);
    	tGrid.add(desc, 1, 3+ i);
    	tGrid.add(new Label("Priority"), 0, 4+ i);
    	tGrid.add(tPriority, 1, 4+ i);
    	tGrid.add(new Label("Progress"), 0, 5+ i);
    	tGrid.add(tProgress, 1, 5+ i);
    
    	// pre-fill fields with values of selected task
    	if (type == 1) {
    		tName.setText(TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()).toString());
    		pSpace.getSelectionModel().select((TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()).getParentSpace()));
    		datePicker.setValue(TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()).getDate());
    		desc.setText(TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()).toString());
    		tPriority.getSelectionModel().select((TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()).getPriority()));
    		tProgress.getSelectionModel().select((TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()).getCurrent()));
    		
    	}
    	
    	
    	// disable confirm button until information is entered
    	Node confirmBtn = tDialog.getDialogPane().lookupButton(confirmBtnType);
    	confirmBtn.setDisable(true);
    	tDialog.getDialogPane().setContent(tGrid);
    	
    	tName.textProperty().addListener((observable, oldValue, newValue) -> {
    	    confirmBtn.setDisable(newValue.trim().isEmpty());
    	});
    	tName.setPromptText(sFilter.getSelectionModel().getSelectedItem().toString());

    	desc.textProperty().addListener((observable, oldValue, newValue) -> {
    	    confirmBtn.setDisable(newValue.trim().isEmpty());
    	});
    	desc.setPromptText(sFilter.getSelectionModel().getSelectedItem().toString());
    	
    	// return tDialog contents as Pair once confirmBtn is pressed
    	tDialog.setResultConverter(tDialogBtn -> {
            
	        if (tDialogBtn == confirmBtnType) {
	        	if (type == 0) {
	        	return new Results(tName.getText(), datePicker.getValue(), 
	        			pSpace.getSelectionModel().getSelectedItem(), desc.getText(), 
	        			tProgress.getSelectionModel().getSelectedItem(), 
	        			tPriority.getSelectionModel().getSelectedItem());
	        	}
	        	else if (type == 1) {
	        		return new Results(tName.getText(), datePicker.getValue(), 
		        			pSpace.getSelectionModel().getSelectedItem(), desc.getText(), 
		        			tProgress.getSelectionModel().getSelectedItem(), 
		        			tPriority.getSelectionModel().getSelectedItem());
	        	}

	        }
	            return null;
        });
        
    	// create not-null result object whose contents are the Pair of values
    	Optional<Results> result = tDialog.showAndWait();
        
        // execute once tDialog is completed and result is not null
        result.ifPresent((Results results) -> {
            
        	// pull variables from pair object
        	String name = results.n;
        	LocalDate d = results.dd;
        	if (d == null) {
        		d = LocalDate.now();
        	}
        	Space aSpace = results.pSpace;
        	String description = results.desc;
        	Status.progress taskProgress = results.tProgress;
        	Task.Priority taskPriority = results.tPriority;
            
            // execute based on current dialog type
            switch(type) {
            case 0:	// Add task
            	try {
            	taskManager.addTask(name, d, aSpace, description, taskProgress, taskPriority);
            	}
            	catch (Exception e) {
            		systemAlert(e);
            	}
            	break;
            case 1: // Edit task

            	Task nTask = new Task(name, d, aSpace);
                nTask.setCurrent(taskProgress);
                nTask.setDescription(description);
                nTask.setPriority(taskPriority);
                
            	taskManager.EditTask(TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)).get(TaskManager.getSelectedTaskIndex()), nTask);
            	
            	break;
            	
            }
            
            // update space filter list
            int CurrentIndex = SpaceManager.getSelectedSpaceIndex();
            sFilter.getItems().clear();
            sFilter.getItems().addAll(SpaceManager.getSpaceList());
            sFilterUpdated = true;
            sFilter.getSelectionModel().select(CurrentIndex);
            
            tFilter.getItems().clear();
            tFilter.getItems().addAll(TaskManager.getTaskList(SpaceManager.getSpaceList().get(0)));
            tFilter.getSelectionModel().selectLast();
            
        });
        
        // reset text and null values in textfield
        tName.setText("");
        tName.setPromptText("");
     
        desc.setText("");
        desc.setPromptText("");
        return;
    }

	/**
	 * @author Thomas Teper
	 * 
	 * Helper method to create space dialogs for adding, editing, and deleting spaces
	 * 
	 * @param type, the type of space modification (0 = add, 1 = edit, 2 = delete)
	 * @param sList, the list of spaces from overview
	 * @param sFilter, the combobox of spaces from overview
	 * @param sManager, the space manager object from overview
	 */
	private void spaceDialog(int type, ComboBox<Space> sFilter) {

		// create dialog and naming
		sFilterUpdated = false;
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
			sDialog.setHeaderText("Edit space: " + SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex()).toString());
			sDialog.setContentText("Parent space: " + SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex()).getParentName());
			break;
		case 2: // Delete Space + custom alert execution
			Alert deleteSD = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this space?", ButtonType.YES, ButtonType.CANCEL);
			deleteSD.setTitle("Delete Space");
			deleteSD.setHeaderText("Delete space: " + SpaceManager.getSpaceList().get(SpaceManager.getSelectedSpaceIndex()).toString());
			deleteSD.showAndWait();

			if (deleteSD.getResult() == ButtonType.YES) {
				try {
					spaceManager.deleteSpace(SpaceManager.getSelectedSpaceIndex());
				} catch (Exception e) {
					systemAlert(e);
				}
				sFilter.getItems().clear();
				sFilter.getItems().addAll(SpaceManager.getSpaceList());
				sFilterUpdated = true;
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
		int pIndex = spaceManager.getParentIndex(SpaceManager.getSelectedSpaceIndex());
		pSpace.getItems().clear();
		pSpace.getItems().addAll(SpaceManager.getSpaceList());
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
					spaceManager.addSpace(SpaceManager.getSpaceList().get(newPIndex), spcStr);
					systemSuccess(0, spcStr);
				}
				catch (Exception e) {
					systemAlert(e);
				}
				break;
			case 1: // Edit Space
				try {
					spaceManager.editSpace(SpaceManager.getSpaceList().get(newPIndex), i, spcStr);
					systemSuccess(1, spcStr);
				}
				catch (Exception e) {
					systemAlert(e);
				}
				break;
			}

			// update space filter list
			sFilter.getItems().clear();
			sFilter.getItems().addAll(SpaceManager.getSpaceList());
			sFilterUpdated = true;
			sFilter.getSelectionModel().selectLast();
		});

		// reset text and null values in textfield
		sName.setText("");
		sName.setPromptText("");

		return;
	}

	/**
	 * @author Thomas Teper
	 * 
	 * Helper method to notify user of actions that are not permitted
	 * 
	 * @param e, the exception message thrown by the calling method
	 */
	private void systemAlert(Exception e) {
		Alert badName = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK); 
		badName.setTitle("Alert");
		badName.showAndWait();
	}

	/**
	 * @author Thomas Teper
	 * 
	 * Helper method to notify user of success in certain event
	 * 
	 * @param e, the exception message thrown by the calling method
	 */
	private void systemSuccess(int type, String n) {
		Alert success;
		switch (type ) {
		case 0: // create
			success = new Alert(AlertType.INFORMATION, n + " created successfully!", ButtonType.OK);
			success.setTitle("Space / Task Creation Confirmation");
			break;
		case 1: // edit
			success = new Alert(AlertType.INFORMATION, n + " edited successfully!", ButtonType.OK);
			success.setTitle("Edit Confirmation");
			break;
		default: 
			success = new Alert(AlertType.INFORMATION, n, ButtonType.OK);
			success.setTitle("Success!");
			break;
		}
		success.showAndWait();
	}

	/**
	 * @author Calen
	 * 
	 * Class to load values from task dialog
	 *
	 */
	public static class Results {

		String n;
		Space pSpace;
		LocalDate dd;
		String desc;
		Task.Priority tPriority;
		Status.progress tProgress;

		public Results(String n, LocalDate dd, Space pSpace, String desc, 
				Status.progress tProgress, Task.Priority tPriority) {
			this.n = n;
			this.pSpace = pSpace;
			this.dd = dd;
			this.desc = desc;
			this.tProgress = tProgress;
			this.tPriority = tPriority;

		}
	}
}