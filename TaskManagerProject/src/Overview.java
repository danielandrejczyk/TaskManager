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
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(topSection);
        
        primaryStage.setScene(new Scene(root, 300, 250));
        // Show the scene
        primaryStage.show();
    }
}
