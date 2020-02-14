package com.kodilla.cac;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new SceneSetter().SceneSet(1, primaryStage);
        primaryStage.setScene(scene);
        double aspectRatio = 0.666;
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(aspectRatio));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(aspectRatio));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}