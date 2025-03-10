/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

import java.awt.Insets;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
    public static ArrayList<String> colors = new ArrayList<>(Arrays.asList("#E6F0FA", "#F39C12"));
    public int colorCount = 0;
    public static VBox flowPaneVbox1;
    public static VBox titleVBox;

    public Project(){
        f = new File("E:\\GithubStuff\\ToDoList\\project\\src\\main\\java\\org\\yourcompany\\yourproject\\Resources\\JSON_Files\\toDoList.json");
        stackPane = new StackPane();
        stackPaneBackground = new StackPane();
        
        flowPane = new FlowPane();
        flowPane2 = new FlowPane();
        flowPaneVbox1 = new VBox();
        titleVBoxSetup();
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

    public static void titleVBoxSetup(){
        VBox vbox = new VBox();
        Text text = new Text("To Do List");
        text.setStyle("-fx-font-size: 5em;");

        vbox.setStyle( 
            "-fx-background-color: #F39C12;" +
            "-fx-background-radius: 3em;" +
            "-fx-border-color: #E6F0FA;" +
            "-fx-border-width: .5em; " + 
            "-fx-border-radius: 2em;" + 
            "-fx-padding: 4em;"
        );

        vbox.setAlignment(Pos.CENTER);
        // vbox.setMaxSize(200, 120);
        vbox.getChildren().add(text);
        flowPaneVbox1.getChildren().add(vbox);
    }   

    public void setupInnerStackPane1(){
        stackPane.setStyle("-fx-background-color:rgb(160, 198, 240);" + 
        "-fx-padding-left: 3em;" + 
        "-fx-padding-right: 3em;" +
        "-fx-border-radius: 3em;" +
        "-fx-background-radius: 3.4em;" +
        "-fx-border-color: #2C3E50;" + 
        "-fx-border-width: .5em; " +        
        "-fx-border-style: solid;" );
        // normalize(CornerRadii var0, Insets var1, double var2, double var4)
        CornerRadii cornerRadii = new CornerRadii(200);
        Insets insets = new Insets(1,1,1,1);

        // BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("808080"), cornerRadii, null);
        // stackPane.setBackground(new Background(backgroundFill));
    }

    public void setStackPaneBackground(){
        stackPaneBackground.setStyle("-fx-background-color: black;" +
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
        "-fx-font-size: 2em;" 
        );

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefSize(100, 100);

        Button button = new Button("Add");
        button.setOnAction(e -> {
            HBox hbox2 = (HBox) flowPane2.getChildren().get(0);
            TextArea textArea2 = (TextArea) hbox2.getChildren().get(1);
            if(textArea2.getText() != ""){
                addTaskToScreen(textArea2.getText());
                addToJSONFile(textArea2.getText());
            }
        });

        button.setStyle("-fx-padding: 1em;" +
        "-fx-font-size: 2em;" + 
        "-fx-border-radius: 3em;");

        hbox.getChildren().addAll(label, textArea, button);
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.setSpacing(5);
        hbox.setStyle("-fx-padding: 1em;");

        flowPane2.setStyle("-fx-border-color: #E6F0FA;" +
        "-fx-border-width: 1em; " +        
        "-fx-border-style: solid;" +
        "-fx-border-radius: 1em;");
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
        button.setAlignment(Pos.CENTER);
        button.setPrefSize(75, 75);

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

        hbox.setAlignment(Pos.CENTER);
        hbox.setMinWidth(150);
        hbox.setMinHeight(50);

        Text txt = new Text();

        txt.setText(taskText);
        txt.setTextAlignment(TextAlignment.CENTER);

        hbox.setBackground(Background.fill(Paint.valueOf("#F39C12")));
        hbox.setStyle(
            "-fx-padding: 2; " +
            "-fx-border-color: #ECF0F1;" + 
            "-fx-border-width: .5em; " +        
            "-fx-border-style: solid;" +
            "-fx-font-size: 2em;" + 
            "-fx-border-radius: 0.5em;"
        );
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
