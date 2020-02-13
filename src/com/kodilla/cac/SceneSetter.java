package com.kodilla.cac;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.Glyph;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SceneSetter {

    private double xOffset = 0;
    private double yOffset = 0;

    public Scene SceneSet(int sceneNr, Stage primaryStage) throws IOException {
        Scene tScene = null;
        switch (sceneNr) {
            case 0:
                tScene = settingsScene(primaryStage);
                break;
            case 1:
                tScene = menuScene(primaryStage);
                break;
            default:
                System.out.println("Brak sceny o danym numerze");
                break;
        }
        return tScene;
    }

    public Scene settingsScene(Stage primaryStage) throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource("template.fxml")); //do stałych
        Pane dragBar = (Pane) template.lookup("#dragBar"); //do stałych
        defFunctions(primaryStage, true, template);
        Pane backBtn = new Pane();
        backBtn.setPrefHeight(30);
        backBtn.setPrefWidth(45);
        backBtn.setCursor(Cursor.HAND);

        backBtn.setOnMouseEntered(event1 -> backBtn.setBackground(new Background(new BackgroundFill(Color.web("#00000040"), CornerRadii.EMPTY, Insets.EMPTY)))); //do stałych
        backBtn.setOnMouseExited(event1 -> backBtn.setBackground(Background.EMPTY));
        backBtn.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(this.SceneSet(1, primaryStage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Glyph backArrow = new Glyph();
        backArrow.setFontFamily("FontAwesome"); //do stałych
        backArrow.setIcon("long_arrow_left"); //do stałych
        backArrow.setColor(Color.WHITE);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(30);
        borderPane.setPrefWidth(45);
        borderPane.setCenter(backArrow);

        backBtn.getChildren().add(borderPane);
        dragBar.getChildren().add(backBtn);

        return new Scene(template, 750, 500);
    }

    public void defFunctions(Stage primaryStage, boolean setting, Parent template){
        Pane closeBtn = (Pane) template.lookup("#closeWin");
        Pane minWin = (Pane) template.lookup("#minWin");
        Pane settWin = (Pane) template.lookup("#settWin");
        Pane dBar = (Pane) template.lookup("#dragBar");

        closeBtn.setOnMouseClicked(event -> primaryStage.close());
        closeBtn.setOnMouseEntered(event -> closeBtn.setBackground(new Background(new BackgroundFill(Color.web("#ff333380"), CornerRadii.EMPTY, Insets.EMPTY))));
        closeBtn.setOnMouseExited(event -> closeBtn.setBackground(Background.EMPTY));

        minWin.setOnMouseEntered(event -> minWin.setBackground(new Background(new BackgroundFill(Color.web("#00000040"), CornerRadii.EMPTY, Insets.EMPTY))));
        minWin.setOnMouseClicked(event -> {
            minWin.setBackground(Background.EMPTY);
            AnimationTimer timer = new AnimationTimer() {
                int count = 2;

                @Override
                public void handle(long now) {
                    if (--count <= 0) {
                        primaryStage.setIconified(true);
                        this.stop();
                    }
                }
            };
            timer.start();
        });
        minWin.setOnMouseExited(event -> minWin.setBackground(Background.EMPTY));

        dBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        dBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        settWin.setOnMouseEntered(event -> settWin.setBackground(new Background(new BackgroundFill(Color.web("#00000040"), CornerRadii.EMPTY, Insets.EMPTY))));
        settWin.setOnMouseExited(event -> settWin.setBackground(Background.EMPTY));
        if(!setting){
            settWin.setOnMouseClicked(event -> {
                try {
                    primaryStage.setScene(this.SceneSet(0, primaryStage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public Scene menuScene(Stage primaryStage)  throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource("template.fxml"));
        GridPane gridPane = (GridPane) template.lookup("#root");
        defFunctions(primaryStage, false, template);
        ImageView logo = new ImageView();
        logo.setImage(new Image("resources/logoIn.png"));
        logo.setFitWidth(415);
        logo.setFitHeight(190);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);

        VBox options = new VBox();
        options.setSpacing(5);
        options.setPrefHeight(200);
        options.setPrefWidth(100);

        Map<String, Integer> buttonMap = new LinkedHashMap<>();
        buttonMap.put("Jednoosobowa", 2);
        buttonMap.put("Wieloosobowa", 3);
        buttonMap.put("Tablica Wyników", 4);

        for (Map.Entry<String, Integer> btn : buttonMap.entrySet()){

            BorderPane butt = new BorderPane();
            butt.setPrefWidth(90);
            butt.setPrefHeight(45);
            butt.getStyleClass().add("menuBtn");

            Text txt = new Text();
            txt.setText(btn.getKey());
            txt.setFill(Color.web("#f1f1f1"));

            butt.setCenter(txt);
            options.getChildren().add(butt);

            butt.setOnMouseEntered(event -> butt.setBackground(new Background(new BackgroundFill(Color.web("#00000060"), CornerRadii.EMPTY, Insets.EMPTY))));
            butt.setOnMouseExited(event -> butt.setBackground(new Background(new BackgroundFill(Color.web("#00000040"), CornerRadii.EMPTY, Insets.EMPTY))));
            butt.setOnMouseClicked(event -> {
                System.out.println("Brak sceny o danym numerze");
            });

        }

        gridPane.add(logo, 0,0);
        gridPane.add(options, 0, 1);

        return new Scene(template, 750, 500);
    }

}
