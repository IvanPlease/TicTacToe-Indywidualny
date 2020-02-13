package com.kodilla.cac;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        setPrimaryStage(primaryStage);
        Scene scene = new SceneSetter().SceneSet(1, primaryStage);
        primaryStage.setScene(scene);
        //Size && resize aspect ratio
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.666)); //do stałych
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.666));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}