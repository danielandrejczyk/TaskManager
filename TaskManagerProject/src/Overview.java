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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        SpaceManager tm_spaceManager = new SpaceManager();
        
        // Space selection drop-down
        ComboBox<Space> spaceFilter = new ComboBox<Space>();
    	ArrayList<Space> spaceList = tm_spaceManager.getSpaceList();
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
        
        topSection.getChildren().addAll(addSpace, editSpace, deleteSpace, spaceFilter, addTask, editTask, deleteTask);
        
        //
        // Left panel
        //
        
        VBox leftSection = new VBox();
        leftSection.setPadding(new Insets(15,15,15,15));
        leftSection.setSpacing(10);
        leftSection.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        Button homeToggle = new Button("Home");
        homeToggle.setPrefSize(80, 40);
        
        /*
         * Top Button Action Events
         */
        
        // Add Space Dialog
        
        // creates new add space dialog
        Dialog<Pair<String, Integer>> addSD = new Dialog<>();
        addSD.setTitle("New Space");
        addSD.setHeaderText("Add Space");
        GridPane aSGrid = new GridPane();
        
        // adds buttons for dialog
        ButtonType addConfirmBtnType = new ButtonType("Confirm", ButtonData.OK_DONE);
        addSD.getDialogPane().getButtonTypes().addAll(addConfirmBtnType, ButtonType.CANCEL);
        
        // positioning
        aSGrid.setHgap(10);
        aSGrid.setVgap(10);
        aSGrid.setPadding(new Insets(20, 150, 10, 10));
        
        // text field and parent space options
        TextField addSName = new TextField();
        addSName.setPromptText("Space name");
        ComboBox<Space> addPSpace = new ComboBox<Space>();
        addPSpace.getItems().clear();
        addPSpace.getItems().addAll(spaceList);
        addPSpace.getSelectionModel().selectFirst();
        
        aSGrid.add(new Label("Space Name"), 0, 0);
		aSGrid.add(addSName, 0, 1);
		aSGrid.add(new Label("Parent Space"), 1, 0);
		aSGrid.add(addPSpace, 1, 1);
        
        Node addConfirmBtn = addSD.getDialogPane().lookupButton(addConfirmBtnType);
        addConfirmBtn.setDisable(true);
        addSD.getDialogPane().setContent(aSGrid);
        addSName.textProperty().addListener((observable, oldValue, newValue) -> {
        	addConfirmBtn.setDisable(newValue.trim().isEmpty());
        });
        
        // Add Space Actions
        addSpace.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		
        		addSD.setResultConverter(addSDBtn -> {
        			
        			if (addSDBtn == addConfirmBtnType) {
        				return new Pair<>(addSName.getText(), addPSpace.getSelectionModel().getSelectedIndex());
        			}
        			return null;
        		});
        		
        		Optional<Pair<String, Integer>> result = addSD.showAndWait();
        		
        		result.ifPresent(newSpaceValue -> {
        			String spcStr = newSpaceValue.getKey();
        			int pIndex = newSpaceValue.getValue();
        			tm_spaceManager.addSpace(tm_spaceManager.getSpaceList().get(pIndex), spcStr);
        			spaceFilter.getItems().clear();
            		spaceFilter.getItems().addAll(spaceList);
            		spaceFilter.getSelectionModel().selectLast();
        		});
        		
        		addPSpace.getItems().clear();
        		addPSpace.getItems().addAll(spaceList);
        		addPSpace.getSelectionModel().selectFirst();
        		
        		addSName.setText("");
        		addSName.setPromptText("");
        	}
        });
       
        // Edit Space
        
        Dialog<Pair<String, Integer>> editSD = new Dialog<>();
        editSD.setTitle("Edit Space");
        GridPane eSGrid = new GridPane();
        
        // adds buttons for dialog
        ButtonType editConfirmBtnType = new ButtonType("Confirm", ButtonData.OK_DONE);
        editSD.getDialogPane().getButtonTypes().addAll(editConfirmBtnType, ButtonType.CANCEL);
        
        // positioning
        eSGrid.setHgap(10);
        eSGrid.setVgap(10);
        eSGrid.setPadding(new Insets(20, 150, 10, 10));
        
        // text field and parent space options
        TextField editSName = new TextField();
        ComboBox<Space> editPSpace = new ComboBox<Space>();
        editPSpace.getItems().clear();
        editPSpace.getItems().addAll(spaceList);
        editPSpace.getSelectionModel().selectFirst();
        
        eSGrid.add(new Label("Space Name"), 0, 0);
        eSGrid.add(editSName, 0, 1);
        eSGrid.add(new Label("Parent Space"), 1, 0);
        eSGrid.add(editPSpace, 1, 1);
        
        Node editConfirmBtn = editSD.getDialogPane().lookupButton(editConfirmBtnType);
        editConfirmBtn.setDisable(true);
        editSD.getDialogPane().setContent(eSGrid);
        editSName.textProperty().addListener((observable, oldValue, newValue) -> {
        	editConfirmBtn.setDisable(newValue.trim().isEmpty());
        });
        
        // Edit Space Actions
        editSpace.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		
        		editSD.setHeaderText("Modify " + spaceFilter.getSelectionModel().getSelectedItem().toString());
        		editSName.setPromptText(spaceFilter.getSelectionModel().getSelectedItem().toString());
        		// add ability to see what the current parent space is
        		editSD.setResultConverter(editSDBtn -> {
        			if (editSDBtn == editConfirmBtnType) {
        				return new Pair<>(editSName.getText(), editPSpace.getSelectionModel().getSelectedIndex());
        			}
        			return null;
        		});
        		
        		Optional<Pair<String, Integer>> result = editSD.showAndWait();
        		
        		result.ifPresent(editSpaceValue -> {
        			// get values from form boxes
        			String spcStr = editSpaceValue.getKey();
        			int pIndex = editSpaceValue.getValue();
        			int i = spaceFilter.getSelectionModel().getSelectedIndex();
        			
        			tm_spaceManager.editSpace(tm_spaceManager.getSpaceList().get(pIndex), i, spcStr);
        			spaceFilter.getItems().clear();
        			spaceFilter.getItems().addAll(spaceList);
            		spaceFilter.getSelectionModel().select(i);
        		});
        		
        		editPSpace.getItems().clear();
        		editPSpace.getItems().addAll(spaceList);
        		editPSpace.getSelectionModel().selectFirst();
        		
        		editSName.setText("");
        		editSName.setPromptText("");
        	}
        });
        
     	// Delete Space Actions
        Alert deleteSD = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this space?", ButtonType.YES, ButtonType.CANCEL);
        deleteSD.setTitle("Delete Space");
        
        // Delete space action
        deleteSpace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
            	deleteSD.setHeaderText("Delete " + spaceFilter.getSelectionModel().getSelectedItem().toString());
            	deleteSD.showAndWait();

                if (deleteSD.getResult() == ButtonType.YES) {
                	tm_spaceManager.deleteSpace(spaceFilter.getSelectionModel().getSelectedIndex());
                    spaceFilter.getItems().clear();
                    spaceFilter.getItems().addAll(spaceList);
                    spaceFilter.getSelectionModel().selectFirst();
                }
            }
         });
        
        // Overview toggle buttons
        Button dailyToggle = new Button("Daily");
        dailyToggle.setPrefSize(80, 40);
        
        Button weeklyToggle = new Button("Weekly");
        weeklyToggle.setPrefSize(80, 40);
        
        Button monthlyToggle = new Button("Monthly");
        monthlyToggle.setPrefSize(80, 40);
        
        Text toggleLabel = new Text("View");
        toggleLabel.setFont(new Font("Arial Bold", 16));
        //toggleLabel.setTextAlignment(TextAlignment.CENTER);
        
        leftSection.getChildren().addAll(toggleLabel, homeToggle, dailyToggle, weeklyToggle, monthlyToggle);
        
        // Home view
        toggleHome(border, tm_spaceManager);
        
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
    	Date today = newCalendar.getTime();
    	
    	Space myTasks = m.getSpaceList().get(0);
    	
    	Task physCh1 = new Task("Physics Chapter 1", today, myTasks);
    	physCh1.setCurrent(Status.progress.DONE);
    	physCh1.setDescription("Ask professor about problem 7");
    	Task calcCh1 = new Task("Calculus Chapter 1", today, myTasks);
    	calcCh1.setCurrent(Status.progress.IN_PROGRESS);
    	calcCh1.setDescription("Help!");
    	
    	ObservableList<Task> tasks = FXCollections.observableArrayList(physCh1, calcCh1);
    	
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
}