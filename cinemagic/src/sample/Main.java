package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.items.Hall;
import sample.items.Place;
import sample.items.Sessions;

import java.text.SimpleDateFormat;
import java.util.Date;

///TODO 3 алгоритми: сортування шелла, пошук сесії, додавання/видалення відвідувача/сесії



public class Main extends Application {

    static public final int PLACE_NUMB = 64;
    final static int W_WIDTH = 1200, W_HEIGHT = 780;

    static Hall main_hall = new Hall(PLACE_NUMB);
    static Sessions _29_11 = new Sessions();

    static TableView<Place> table;

    @Override
    public void start(Stage primaryStage) throws Exception{

        _29_11.insertEnd(12,00,60,"Shameless");
        _29_11.find("Shameless").hall_set.add_in(1, "Олена В.В.");
        _29_11.find("Shameless").hall_set.add_in(3, "Петро В.В.");
        _29_11.find("Shameless").hall_set.add_in(2, "Василь В.В.");
        _29_11.find("Shameless").hall_set.add_in(10, "Віктор В.В.");
        _29_11.insertEnd(13,30,60,"University");
        _29_11.find("University").hall_set.add_in(9, "Макс В.В.");

        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));

        Label l_time = new Label();
        l_time.setLayoutX(902.0);
        l_time.setLayoutY(9.0);
        l_time.setPrefWidth(297.0);
        l_time.setPrefHeight(24.0);
        l_time.setTextAlignment(TextAlignment.CENTER);
        l_time.setFont(new Font("Brush Script MT Italic", 20));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                l_time.setText("Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        Group tmp = new Group();
        tmp.getChildren().addAll(root, l_time);





        primaryStage.setTitle("KinoHelper");
        primaryStage.setScene(new Scene(tmp, W_WIDTH, W_HEIGHT));
        primaryStage.show();


    }






    public static void main(String[] args) {

        launch(args);
    }
}
