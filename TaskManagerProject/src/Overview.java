// Author: Daniel Andrejczyk

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        addSpace.setPrefSize(40, 40);
        
        Button editSpace = new Button("Edit Space");
        editSpace.setPrefSize(40, 40);
        
        // pop-up for editing space
        editSpace.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    final Stage dialog = new Stage();
                    //dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox(20);
                    //dialogVbox.getChildren().add(new Text("This is a Dialog"));
                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    
                    /*
                     * Functionality to be added
                     */
                }
             });
        
        Button deleteSpace = new Button("Delete Space");
        deleteSpace.setPrefSize(40, 40);
        
        // create space manager
        SpaceManager tm_spaceManager = new SpaceManager();
        
        // Space selection drop-down
        ComboBox<Space> spaceFilter = new ComboBox<Space>();
    	ArrayList<Space> spaceList = tm_spaceManager.GetSpaceList();
        spaceFilter.getItems().addAll(spaceList);
        
        // Task manipulation buttons
        Button addTask = new Button("Add Task");
        addSpace.setPrefSize(40, 40);
        
        Button editTask = new Button("Edit Task");
        editSpace.setPrefSize(40, 40);
        
        Button deleteTask = new Button("Delete Task");
        deleteSpace.setPrefSize(40, 40);
        
        topSection.getChildren().addAll(addSpace, editSpace, deleteSpace, spaceFilter, addTask, editTask, deleteTask);
        
        //
        // Left panel
        //
        
        VBox leftSection = new VBox();
        
        Button homeToggle = new Button("Home");
        editSpace.setPrefSize(40, 40);
        
        // Space onClick actions
        addSpace.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		tm_spaceManager.AddSpace(tm_spaceManager.GetSpaceList().get(0), "Another Space");
        		// Probably not the most efficient method. Re-visit this piece
        		spaceFilter.getItems().clear();
        		spaceFilter.getItems().addAll(spaceList);
        	}
        });
        
        // Overview toggle buttons
        Button dailyToggle = new Button("Daily");
        deleteSpace.setPrefSize(40, 40);
        
        Button weeklyToggle = new Button("Weekly");
        deleteSpace.setPrefSize(40, 40);
        
        Button monthlyToggle = new Button("Monthly");
        deleteSpace.setPrefSize(40, 40);
        
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
    	
    	AnchorPane.setRightAnchor(listView, 10.0);
    	homePane.getChildren().add(listView);
    	
    	b.setCenter(homePane);
    	
    }
}
