// Author: Daniel Andrejczyk

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
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
    	
    	// Set title
        primaryStage.setTitle("Task Manager");
        
        BorderPane border = new BorderPane();
        
        // Top panel
        HBox topSection = new HBox();
        topSection.setPadding(new Insets(15, 12, 15, 12));
        topSection.setSpacing(10);
        
        Button editSpace = new Button("Edit Space");
        editSpace.setPrefSize(40, 40);
        
        Button deleteSpace = new Button("Delete Space");
        deleteSpace.setPrefSize(40, 40);
        
        ComboBox<Space> spaceFilter = new ComboBox<Space>();
        spaceFilter.getItems().addAll(
        		new Space("My Tasks")
        );
        
        Button editTask = new Button("Edit Task");
        editSpace.setPrefSize(40, 40);
        
        Button deleteTask = new Button("Delete Task");
        deleteSpace.setPrefSize(40, 40);
        
        topSection.getChildren().addAll(editSpace, deleteSpace, spaceFilter, editTask, deleteTask);
        
        /*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        */
        
        // Left side overview-switch buttons
        VBox leftSection = new VBox();
        
        Button homeToggle = new Button("Home");
        editSpace.setPrefSize(40, 40);
        
        Button dailyToggle = new Button("Daily");
        deleteSpace.setPrefSize(40, 40);
        
        Button weeklyToggle = new Button("Weekly");
        deleteSpace.setPrefSize(40, 40);
        
        Button monthlyToggle = new Button("Monthly");
        deleteSpace.setPrefSize(40, 40);
        
        leftSection.getChildren().addAll(homeToggle, dailyToggle, weeklyToggle, monthlyToggle);
        
        border.setTop(topSection);
        border.setLeft(leftSection);
        
        primaryStage.setScene(new Scene(border));
        // Show the scene
        primaryStage.show();
    }
}
