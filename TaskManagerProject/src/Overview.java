// Author: Daniel Andrejczyk

import javafx.application.Application;
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
import javafx.scene.layout.BorderPane;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
        
        // Space manipulation buttons
        Button addSpace = new Button("Add Space");
        addSpace.setPrefSize(100, 40);
        
        Button editSpace = new Button("Edit Space");
        editSpace.setPrefSize(100, 40);
        
        Button deleteSpace = new Button("Delete Space");
        deleteSpace.setPrefSize(100, 40);
        
        // create space manager
        SpaceManager spaceManager = new SpaceManager();
        
        // Space selection drop-down
        ComboBox<Space> spaceFilter = new ComboBox<Space>();
    	ArrayList<Space> spaceList = spaceManager.getSpaceList();
        spaceFilter.getItems().addAll(spaceList);
        spaceFilter.getSelectionModel().selectFirst();
        spaceFilter.setPrefHeight(40);
        spaceFilter.setPrefWidth(200);
        
        // Task manipulation buttons
        Button addTask = new Button("Add Task");
        addTask.setPrefSize(100, 40);
        
        Button editTask = new Button("Edit Task");
        editTask.setPrefSize(100, 40);
        
        Button deleteTask = new Button("Delete Task");
        deleteTask.setPrefSize(100, 40);
        
        Button testDialog = new Button("Test");
        testDialog.setPrefSize(60, 40);
        
        topSection.getChildren().addAll(addSpace, editSpace, deleteSpace, testDialog, spaceFilter, addTask, editTask, deleteTask);
        
        //
        // Left panel
        //
        
        VBox leftSection = new VBox();
        leftSection.setPadding(new Insets(15,15,15,15));
        leftSection.setSpacing(10);
        leftSection.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
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
        
        /* Add Task Dialog */
        
        // creates new add space dialog
        Dialog<Pair<String, Integer>> addTD = new Dialog<>();
        addTD.setTitle("Add Task");
        addTD.setHeaderText("Add Task");
        GridPane aTGrid = new GridPane();
        
        // adds buttons for dialog
        ButtonType addTConfirmBtnType = new ButtonType("Create", ButtonData.OK_DONE);
        addTD.getDialogPane().getButtonTypes().addAll(addTConfirmBtnType, ButtonType.CANCEL);
        
        // positioning
        aTGrid.setHgap(10);
        aTGrid.setVgap(10);
        aTGrid.setPadding(new Insets(20, 150, 10, 10));
        
        // text field and parent space options
        TextField addTName = new TextField();
        addTName.setPromptText("Task name");
        ComboBox<Space> addTPSpace = new ComboBox<Space>();
        addTPSpace.getItems().clear();
        addTPSpace.getItems().addAll(spaceList);
        addTPSpace.getSelectionModel().selectFirst();
        
        aTGrid.add(new Label("Task Name"), 0, 0);
		aTGrid.add(addTName, 1, 0);
		aTGrid.add(new Label("Parent Space"), 0, 1);
		aTGrid.add(addTPSpace, 1, 1);
		
		// add due date vars and other necessary stuff here @Calen
		aTGrid.add(new Label("Due Date"), 0, 2);
		//aTGrid.add(DateVar, 1, 2);
		aTGrid.add(new Label("Priority"), 0, 3);
		//aTGrid.add(PriorityVar, 1, 3);
        
        Node addTConfirmBtn = addTD.getDialogPane().lookupButton(addTConfirmBtnType);
        addTConfirmBtn.setDisable(true);
        addTD.getDialogPane().setContent(aTGrid);
        addTName.textProperty().addListener((observable, oldValue, newValue) -> {
        	addTConfirmBtn.setDisable(newValue.trim().isEmpty());
        });
        
        // Overview toggle buttons
        Button homeToggle = new Button("Home");
        homeToggle.setPrefSize(80, 40);
        
        Button dailyToggle = new Button("Daily");
        dailyToggle.setPrefSize(80, 40);
        
        Button weeklyToggle = new Button("Weekly");
        weeklyToggle.setPrefSize(80, 40);
        
        Button monthlyToggle = new Button("Monthly");
        monthlyToggle.setPrefSize(80, 40);
        
        // Connect overview toggle buttons to their
        // MouseClick event handlers
        homeToggle.setOnMouseClicked(e -> toggleHome(border, spaceManager));
        dailyToggle.setOnMouseClicked(e -> toggleDaily(border, spaceManager));
        
        Text toggleLabel = new Text("View");
        toggleLabel.setFont(new Font("Arial Bold", 16));
        //toggleLabel.setTextAlignment(TextAlignment.CENTER);
        
        leftSection.getChildren().addAll(toggleLabel, homeToggle, dailyToggle, weeklyToggle, monthlyToggle);
        
        // Set up the default overview (home overview)
        toggleHome(border, spaceManager);
        
        // Set sections to borderpane
        border.setTop(topSection);
        border.setLeft(leftSection);
        
        
        primaryStage.setScene(new Scene(border));
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
    	
    	Calendar newCalendar = Calendar.getInstance();
    	LocalDate today = LocalDate.now();
    	
    	Space myTasks = m.getSpaceList().get(0);
    	
    	Task physCh1 = new Task("Physics Chapter 1", today.toString(), myTasks);
    	physCh1.setCurrent(Status.progress.DONE);
    	physCh1.setDescription("Ask professor about problem 7");
    	Task calcCh1 = new Task("Calculus Chapter 1", today.toString(), myTasks);
    	calcCh1.setCurrent(Status.progress.IN_PROGRESS);
    	calcCh1.setDescription("Help!");
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(physCh1, calcCh1);
    	
    	ListView<Task> listView = new ListView<Task>(tasks);
    	listView.setPrefWidth(400.0);
    	
    	Label taskLabel = new Label("Tasks:");
    	taskLabel.setFont(new Font("Arial", 24));
    	taskLabel.setPrefSize(400.0, 80.0);
    	
    	// current day
    	String now = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(new java.util.Date());
    	
    	Label dateLabel = new Label(now);
    	dateLabel.setFont(new Font("Arial", 24));
    	dateLabel.setPrefSize(400.0, 80.0);
    	
    	// Task information text flow
    	VBox taskInformation = new VBox();
    		
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
    	AnchorPane.setTopAnchor(listView, 90.0);
    	
    	// Task Information
    	AnchorPane.setTopAnchor(taskInformation, 90.0);
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
    	
    	Task physCh1 = new Task("Physics Chapter 1", LocalDate.now().toString(), myTasks);
    	physCh1.setCurrent(Status.progress.DONE);
    	physCh1.setDescription("Ask professor about problem 7");
    	Task calcCh1 = new Task("Calculus Chapter 1",  LocalDate.now().toString(), myTasks);
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
    		break;
    	case 2: // Delete Space + custom alert execution
    		Alert deleteSD = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this space?", ButtonType.YES, ButtonType.CANCEL);
            deleteSD.setTitle("Delete Space");
            deleteSD.setHeaderText("Delete " + sFilter.getSelectionModel().getSelectedItem().toString());
            deleteSD.showAndWait();

            if (deleteSD.getResult() == ButtonType.YES) {
            	sManager.deleteSpace(sFilter.getSelectionModel().getSelectedIndex());
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
    	pSpace.getItems().clear();
    	pSpace.getItems().addAll(sList);
    	pSpace.getSelectionModel().selectFirst();
    	
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
            int pIndex = spaceValue.getValue();
            int i = sFilter.getSelectionModel().getSelectedIndex();
            
            // execute based on current dialog type
            switch(type) {
            case 0:	// Add Space
            	sManager.addSpace(sManager.getSpaceList().get(pIndex), spcStr);
            	break;
            case 1: // Edit Space
            	sManager.editSpace(sManager.getSpaceList().get(pIndex), i, spcStr);
            	break;
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
}