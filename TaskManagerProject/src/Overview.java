// Author: Daniel Andrejczyk

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.swing.*;

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
        
        // Create a border pane to lay out all the items
        BorderPane border = new BorderPane();
        
        //
        // Top panel
        //
        
        HBox topSection = new HBox();
        topSection.setPadding(new Insets(15, 12, 15, 12));
        topSection.setSpacing(10);
        
        // Space manipulation buttons
        Button addSpace = new Button("Add Space");
        addSpace.setPrefSize(100, 40);
        
        Button editSpace = new Button("Edit Space");
        editSpace.setPrefSize(100, 40);
        
        Button deleteSpace = new Button("Delete Space");
        deleteSpace.setPrefSize(40, 40);
        
        // create space manager
        SpaceManager tm_spaceManager = new SpaceManager();
        
        // Space selection drop-down
        ComboBox<Space> spaceFilter = new ComboBox<Space>();
    	ArrayList<Space> spaceList = tm_spaceManager.GetSpaceList();
        spaceFilter.getItems().addAll(spaceList);
        spaceFilter.getSelectionModel().selectFirst();
        
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
        
        Button homeToggle = new Button("Home");
        homeToggle.setPrefSize(60, 40);
        
     // input dialog variables
        TilePane a = new TilePane();
        TextInputDialog addTD = new TextInputDialog();
        addTD.setHeaderText("Add Space");
        addTD.setContentText("Enter space name");
        addTD.setTitle("Add Space");
        addTD.setGraphic(null);
        
        // Add Space Actions
        addSpace.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		addTD.showAndWait();
        		String n = addTD.getEditor().getText();
        		
        		try {
        			tm_spaceManager.AddSpace(tm_spaceManager.GetSpaceList().get(0), n);
        			spaceFilter.getItems().clear();
            		spaceFilter.getItems().addAll(spaceList);
            		spaceFilter.getSelectionModel().selectLast();
        		}
        		catch (Exception e) {
        			// do something
        		}
        		// Probably not the most efficient method. Re-visit this piece
        	}
        });
        
        // Overview toggle buttons
        Button dailyToggle = new Button("Daily");
        dailyToggle.setPrefSize(60, 40);
        
        Button weeklyToggle = new Button("Weekly");
        weeklyToggle.setPrefSize(60, 40);
        
        Button monthlyToggle = new Button("Monthly");
        monthlyToggle.setPrefSize(60, 40);
        
        leftSection.getChildren().addAll(homeToggle, dailyToggle, weeklyToggle, monthlyToggle);
        
        // Home view
        toggleHome(border);
        
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
    private void toggleHome(BorderPane b)
    {
    	
    	AnchorPane homePane = new AnchorPane();
    	
    	Calendar newCalendar = Calendar.getInstance();
    	Date today = newCalendar.getTime();
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(new Task("Physics Chapter 1", today), new Task("Calculus Chapter 1", today));
    	
    	ListView<Task> listView = new ListView<Task>(tasks);
    	listView.setPrefWidth(400.0);
    	
    	Label taskLabel = new Label("Tasks:");
    	taskLabel.setFont(new Font("Arial", 24));
    	taskLabel.setPrefSize(400.0, 80.0);
    	
    	Label dateLabel = new Label(newCalendar.getTime().toString());
    	dateLabel.setFont(new Font("Arial", 24));
    	dateLabel.setPrefSize(400.0, 80.0);
    	
    	// Task information text flow
    	VBox taskInformation = new VBox();
    		
    	// Get the selected task
    	listView.setOnMouseClicked(event -> {
    		ObservableList<Task> selectedIndices = listView.getSelectionModel().getSelectedItems();
    		
    		Task selectedTask = selectedIndices.get(0);
    		
    		taskInformation.getChildren().clear();
    		
    		Text priority = new Text("Priority: " + selectedTask.GetPriority());
    		Text status = new Text("Status: ");
    		Text statusDesc = new Text("Status Description: ");
    		Text parentSpace = new Text("Parent Space: ");// + selectedTask.GetParentName());
    		
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
}
