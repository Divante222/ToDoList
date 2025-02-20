/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 *
 * @author divante
 */
public class Project extends Application {

    public static File f;
    public static StackPane stackPaneBackground;
    public static StackPane stackPane;
    public static Scene scene;
    public static FlowPane flowPane;
    public static FlowPane flowPane2;
    public static ArrayList<String> colors = new ArrayList<>(Arrays.asList("808080", "e0f504"));
    public int colorCount = 0;
    public static VBox flowPaneVbox1;

    public Project(){
        f = new File("E:\\GithubStuff\\ToDoList\\project\\src\\main\\java\\org\\yourcompany\\yourproject\\Resources\\JSON_Files\\toDoList.json");
        stackPane = new StackPane();
        stackPaneBackground = new StackPane();
        
        flowPane = new FlowPane();
        flowPane2 = new FlowPane();
        flowPaneVbox1 = new VBox();
        setFlowPane(flowPane);
        setFlowPane(flowPane2);
        flowPane2Setup();
        addVBoxesToStackPane();
        setStackPaneBackground();
        setupInnerStackPane1();
        scene = new Scene(stackPaneBackground, 800, 800);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setupInnerStackPane1(){
        stackPane.setStyle("-fx-background-color:rgb(197, 199, 197);" + 
        "-fx-padding-left: 3em;" + 
        "-fx-padding-right: 3em;" +
        "-fx-border-radius: 25px;");
    }

    public void setStackPaneBackground(){
        stackPaneBackground.setStyle("-fx-background-color: grey;" +
        "-fx-padding: 2em;");
        stackPaneBackground.getChildren().add(stackPane);
    }

    public void setFlowPane(FlowPane flowPaneToSet){
        flowPaneToSet.setHgap(5);
        flowPaneToSet.setVgap(5);
        flowPaneToSet.setPrefWrapLength(300);
        flowPaneToSet.setMaxSize(300, 300);
        flowPaneToSet.setBackground(Background.fill(Paint.valueOf(colors.get(colorCount))));
        flowPaneVbox1.getChildren().add(flowPaneToSet);
        colorCount+=1;
    }

    public void flowPane2Setup(){
        HBox hbox = new HBox(1);
        hbox.minHeight(200);
        hbox.minWidth(200);

        Label label = new Label("Enter new Task");
        label.setStyle("-fx-padding: 1em;" +
        "-fx-text-align: center;" +
        "-fx-font-size: 2em;");

        TextField textField = new TextField();
        textField.setStyle("-fx-padding: 3em;");
        textField.setMinWidth(100);
        textField.setMaxWidth(100);

        Button button = new Button("Add");
        button.setOnAction(e -> {
            // addTaskToScreen(taskText);
            System.out.println(flowPane2.getChildren().get(0));
            HBox hbox2 = (HBox) flowPane2.getChildren().get(0);
            System.out.println(hbox2.getChildren().get(1));
            TextField textfield2 = (TextField) hbox2.getChildren().get(1);
            System.out.println(textfield2.getText());
            if(textfield2.getText() != ""){
                addTaskToScreen(textfield2.getText());
                addToJSONFile(textfield2.getText());
            }
        });

        button.setStyle("-fx-padding: 20;" +
        "-fx-font-size: 2em;");

        hbox.getChildren().addAll(label, textField, button);
        flowPane2.getChildren().add(hbox);
    }
    
    public void addToJSONFile(String newTask){
        try {
            FileReader fileReader = new FileReader(f.getPath());

            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("TaskList").getAsJsonArray();
            jsonArray.add(newTask);
           
            jsonObject.add("TaskList", jsonArray);
            
            FileWriter fileWriter = new FileWriter("E:\\GithubStuff\\ToDoList\\project\\src\\main\\java\\org\\yourcompany\\yourproject\\Resources\\JSON_Files\\toDoList.json");
            System.out.println(jsonObject.toString());
            System.out.println(jsonObject);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
            System.out.println("File updated");
        } catch (Exception e) {
            System.out.println("I broke when trying to add to the json file");
        }
    }

    public static void deleteFromJSONFile(String taskToRemove){
        
        try {
            FileReader fileReader = new FileReader(f.getPath());

            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("TaskList").getAsJsonArray();
            // jsonArray.add(newTask);

            int indexToRemove = 0;
            
            for(int i = 0; i < jsonArray.size(); i++){
                String jsonString = (String) jsonArray.get(i).toString().replace("\"", "");
                if(jsonString.equals(taskToRemove)){
                    indexToRemove = i;
                    System.out.println("I make it here");
                    break;
                }
            }
            jsonArray.remove(indexToRemove);
           
            jsonObject.add("TaskList", jsonArray);
            
            FileWriter fileWriter = new FileWriter("E:\\GithubStuff\\ToDoList\\project\\src\\main\\java\\org\\yourcompany\\yourproject\\Resources\\JSON_Files\\toDoList.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
            System.out.println("File updated");
        } catch (Exception e) {
            System.out.println("I broke when trying to add to the json file");
        }
    }

    public void addVBoxesToStackPane(){
        flowPaneVbox1.minHeight(1500);
        flowPaneVbox1.minWidth(1500);
        flowPaneVbox1.setAlignment(Pos.CENTER);
        for(Node element : flowPaneVbox1.getChildren()){
            System.out.println(element); 
        }
        flowPaneVbox1.setSpacing(1);
        stackPane.getChildren().add(flowPaneVbox1);
    }

    public static void showMainMenu(Stage primaryStage){
        primaryStage.setTitle("Task Master");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Button addDeleteButton(){
        Button button = new Button();
        button.setText("Delete");
        button.setAlignment(Pos.TOP_RIGHT);
        button.setOnAction(e -> {
            HBox hboxParent = (HBox) button.getParent();
            flowPane.getChildren().indexOf(hboxParent);
            flowPane.getChildren().remove(flowPane.getChildren().indexOf(hboxParent));

            HBox task = (HBox) hboxParent.getChildren().get(0);
            Text txt = (Text) task.getChildren().get(0);


            deleteFromJSONFile(txt.getText());
            System.out.println("Deleted");
        });
        return button;
        // flowPane.getChildren().add(button);
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
        hbox.setMinWidth(150);
        hbox.setMinHeight(50);
        // hbox.setMaxSize(160, 150);

        Text txt = new Text();

        txt.setText(taskText);
        txt.setTextAlignment(TextAlignment.CENTER);

        // System.out.println(Node.getClassCssMetaData());
        // hbox.setMargin(, null);
        hbox.setBackground(Background.fill(Paint.valueOf("#f6fdf6")));
        hbox.setStyle(
            "-fx-padding: 2; " +
            "-fx-border-color: purple;" + 
            "-fx-border-width: .5em; " +        
            "-fx-border-style: solid;" +
            "-fx-font-size: 2em;" + 
            "-fx-border-radius: 0.5em;"
        );
        
        // hbox.setSpacing(0);
        // hbox.setStyle("-fx-background-color: lightblue;" +
        //         "-fx-border: 22px solid green;");
        hbox.getChildren().add(txt);

        HBox container = new HBox();
        Button buttonToAdd = addDeleteButton();
        container.getChildren().addAll(hbox, buttonToAdd);
        flowPane.getChildren().add(container);
    }

    //Initialized the UI with information
    public static void initializeList(File f){
        try {
            FileReader fileReader = new FileReader(f.getPath());

            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("TaskList").getAsJsonArray();
            for(JsonElement element: jsonArray){
                addTaskToScreen(element.getAsString());
                // addDeleteButton();
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
