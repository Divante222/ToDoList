/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

import java.io.File;
import java.io.FileReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 *
 * @author divante
 */
public class Project extends Application {

    public static File f;
    public static StackPane stackPane;
    public static Scene scene;
    public static FlowPane flowPane;

    public static FlowPane flowPane2;

    public Project(){
        f = new File("E:\\GithubStuff\\ToDoList\\project\\src\\main\\java\\org\\yourcompany\\yourproject\\Resources\\JSON_Files\\toDoList.json");
        stackPane = new StackPane();
        scene = new Scene(stackPane, 320, 320);
        flowPane = new FlowPane();
        // flowPane2 = new FlowPane();
        setFlowPane(flowPane);
        // setFlowPane(flowPane2);
    }

    public void setFlowPane(FlowPane flowPaneToSet){
        flowPane.setHgap(5);
        flowPane.setVgap(5);
        flowPane.setPrefWrapLength(400);
        flowPane.setMaxSize(400, 50);
        flowPane.setBackground(Background.fill(Paint.valueOf("808080")));
        flowPane.setAlignment(Pos.TOP_LEFT);
        stackPane.getChildren().add(flowPane);
        // flowPane.setStyle("-fx-margin: 10, 10;");
    }

    // public void setSceneCSS(){
        
    //     stackPane.setBackground(Background.fill(Paint.valueOf("#U+1F44C")));
    // }

    public static void main(String[] args) {
        // Stage primaryStage = new Stage();
        launch(args);
    }

    public static void showMainMenu(Stage primaryStage){
        primaryStage.setTitle("Task Master");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        if(f.exists() && !f.isDirectory()){
            System.out.println("the file exists");
        } else{
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            ArrayNode tasksList = objectMapper.createArrayNode();

            try {
                //Adding as a regular key value pair
                jsonNode.put("Task1", "Sweep");
                jsonNode.put("Task2", "Dishes");
                jsonNode.put("Task3", "Laundry");

                //adding to an arrayNode
                tasksList.add("Sweep");
                tasksList.add("Dishes");
                tasksList.add("Laundry");

                //adding the ArrayNode to the ObjectNode
                jsonNode.set("TaskList", tasksList);
                
                objectMapper.writeValue(new File("E:\\GithubStuff\\ToDoList\\project\\src\\main\\java\\org\\yourcompany\\yourproject\\Resources\\JSON_Files\\toDoList.json"), jsonNode);
                System.out.println("file created");
            } catch (Exception e) {
                System.out.println("something went wrong");
            }
        }
        
        //Initialize the List on the screen
        initializeList(f);

        //Set Screen size to full
        // primaryStage.setFullScreen(true);

        //allows the program to run
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                showMainMenu(primaryStage);
            }
        });
    }

    public static void addTaskToScreen(String taskText){
        
        HBox hbox = new HBox();
        // hbox.setId("default-hbox");
        hbox.setAlignment(Pos.CENTER);
        hbox.setMinWidth(50);
        hbox.setMinHeight(50);
        hbox.setMaxSize(150, 150);

        Text txt = new Text();

        txt.setText(taskText);
        txt.setTextAlignment(TextAlignment.CENTER);

        // System.out.println(Node.getClassCssMetaData());
        // hbox.setMargin(, null);
        hbox.setBackground(Background.fill(Paint.valueOf("#0000FF")));
        hbox.setStyle(
            "-fx-padding: 2; " +
            "-fx-border-color: green;" + 
            "-fx-border-width: .5em; " +        
            "-fx-border-style: solid;" +
            "-fx-font-size: 2em;"
        );
        
        // hbox.setSpacing(0);
        // hbox.setStyle("-fx-background-color: lightblue;" +
        //         "-fx-border: 22px solid green;");
        hbox.getChildren().add(txt);
        flowPane.getChildren().add(hbox);

    }

    //Initialized the UI with information
    public static void initializeList(File f){
        try {
            FileReader fileReader = new FileReader(f.getPath());
            System.out.println("I made it here 1");

            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("TaskList").getAsJsonArray();
            for(JsonElement element: jsonArray){
                System.out.println("I am before the addition");
                System.out.println(element.getAsString());
                addTaskToScreen(element.getAsString());
                System.out.println("I am after the addition");
                
                // System.out.println(element.getAsString());
                
            }

            // this cycles over the Entire JSonOBject
            // for(Entry<String, JsonElement> entry: jsonObject.entrySet()){
            //     String key = entry.getKey();
            //     String value = entry.getValue().getAsString(); 

            //     System.out.println(key + ": " + value);
            // }
            System.out.println("I made it here 2");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("this broke");
        }
    }
}
