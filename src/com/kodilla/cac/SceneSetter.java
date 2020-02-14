package com.kodilla.cac;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.util.*;

public class SceneSetter {

    private double xOffset = 0;
    private double yOffset = 0;

    private String hoverBtnColor = "#00000040";
    private String hoverCloseBtnColor = "#00000040";
    private String hoverReturnBtnColor = "#00000060";
    private String temFileName = "template.fxml";
    private String dragIdName = "#dragBar";
    private String minIdName = "#minWin";
    private String closeIdName = "#closeWin";
    private String settIdName = "#settWin";
    private String rootIdName = "#root";
    private String backIcon = "long_arrow_left";
    private String sceneMissing = "Brak sceny o danym numerze";
    private String buttonClassName = "menuBtn";
    private String textColor = "#f1f1f1";
    private String backButtonClassName = "backBtn";
    private String glyphStyleClassName = "glyphStyle";
    private String backBackButtonIdName = "backBackButton";
    private String buttonsIdName = "buttonsBlock";
    private String logoIdName = "logoView";
    private List<String> btnContent = new LinkedList<String>() {
        {
            add("Jednoosobowa");
            add("Wieloosobowa");
            add("Tablica Wyników");
        }
    };

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
                System.out.println(sceneMissing);
                break;
        }
        return tScene;
    }

    public Scene settingsScene(Stage primaryStage) throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane dragBar = (Pane) template.lookup(dragIdName);
        defFunctions(primaryStage, true, template);

        Pane backBtn = new Pane();
        backBtn.getStyleClass().add(backButtonClassName);
        backBtn.setOnMouseEntered(event1 -> backBtn.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY)))); //do stałych
        backBtn.setOnMouseExited(event1 -> backBtn.setBackground(Background.EMPTY));
        backBtn.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(this.SceneSet(1, primaryStage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Glyph backArrow = new Glyph();
        backArrow.getStyleClass().add(glyphStyleClassName);
        backArrow.setIcon(backIcon);

        BorderPane borderPane = new BorderPane();
        borderPane.setId(backBackButtonIdName);
        borderPane.setCenter(backArrow);

        backBtn.getChildren().add(borderPane);
        dragBar.getChildren().add(backBtn);

        return new Scene(template, 750, 500);
    }

    public void defFunctions(Stage primaryStage, boolean setting, Parent template){
        Pane closeBtn = (Pane) template.lookup(closeIdName);
        Pane minWin = (Pane) template.lookup(minIdName);
        Pane settWin = (Pane) template.lookup(settIdName);
        Pane dBar = (Pane) template.lookup(dragIdName);

        closeBtn.setOnMouseClicked(event -> primaryStage.close());
        closeBtn.setOnMouseEntered(event -> closeBtn.setBackground(new Background(new BackgroundFill(Color.web(hoverCloseBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
        closeBtn.setOnMouseExited(event -> closeBtn.setBackground(Background.EMPTY));

        minWin.setOnMouseEntered(event -> minWin.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
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

        settWin.setOnMouseEntered(event -> settWin.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
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
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        GridPane gridPane = (GridPane) template.lookup(rootIdName);
        defFunctions(primaryStage, false, template);
        ImageView logo = new ImageView();
        logo.setId(logoIdName);
        logo.setFitWidth(415);
        logo.setFitHeight(190);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);

        VBox options = new VBox();
        options.setId(buttonsIdName);

        Map<String, Integer> buttonMap = new LinkedHashMap<>();
        for(String s:btnContent){
            buttonMap.put(s, btnContent.indexOf(s)+2);
        }

        for (Map.Entry<String, Integer> btn : buttonMap.entrySet()){

            BorderPane butt = new BorderPane();
            butt.getStyleClass().add(buttonClassName);

            Text txt = new Text();
            txt.setText(btn.getKey());
            txt.setFill(Color.web(textColor));
            butt.setCenter(txt);
            options.getChildren().add(butt);

            butt.setOnMouseEntered(event -> butt.setBackground(new Background(new BackgroundFill(Color.web(hoverReturnBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
            butt.setOnMouseExited(event -> butt.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
            butt.setOnMouseClicked(event -> System.out.println(sceneMissing + btn.getValue()));

        }

        gridPane.add(logo, 0,0);
        gridPane.add(options, 0, 1);

        return new Scene(template, 750, 500);
    }

}
