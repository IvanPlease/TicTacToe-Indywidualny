package com.kodilla.cac;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private final String hoverBtnColor = "#00000040";
    private final String hoverCloseBtnColor = "#00000040";
    private final String hoverReturnBtnColor = "#00000060";
    private final String temFileName = "template.fxml";
    private final String dragIdName = "#dragBar";
    private final String backIcon = "long_arrow_left";
    private final String backButtonClassName = "backBtn";
    private final String glyphStyleClassName = "glyphStyle";
    private final String backBackButtonIdName = "backBackButton";
    private final List<String> btnContent = new LinkedList<String>() {
        {
            add("Jednoosobowa");
            add("Wieloosobowa");
            add("Tablica Wyników");
        }
    };

    public Scene SceneSet(int sceneNr, Stage primaryStage) throws IOException {
        Scene tScene = null;
        String sceneMissing = "Brak sceny o danym numerze";
        switch (sceneNr) {
            case 0:
                tScene = settingsScene(primaryStage);
                break;
            case 1:
                tScene = menuScene(primaryStage);
                break;
            case 2:
                tScene = gameScene(primaryStage);
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
        backBtn.setOnMouseEntered(event1 -> backBtn.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
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

    public Scene gameScene(Stage primaryStage) throws IOException {
        String temFileName2 = "soloplayer.fxml";
        Parent template = FXMLLoader.load(getClass().getResource(temFileName2));
        Pane dragBar = (Pane) template.lookup(dragIdName);
        defFunctions(primaryStage, true, template);

        Pane backBtn = new Pane();
        backBtn.getStyleClass().add(backButtonClassName);
        backBtn.setOnMouseEntered(event1 -> backBtn.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
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
        String closeIdName = "#closeWin";
        Pane closeBtn = (Pane) template.lookup(closeIdName);
        String minIdName = "#minWin";
        Pane minWin = (Pane) template.lookup(minIdName);
        String settIdName = "#settWin";
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
        String rootIdName = "#wholeWin";
        Pane whole = (Pane) template.lookup(rootIdName);
        GridPane gridPane = new GridPane();
        String rootBackIdName = "root";
        gridPane.setId(rootBackIdName);
        gridPane.setLayoutY(30);
        gridPane.setAlignment(Pos.TOP_CENTER);
        defFunctions(primaryStage, false, template);
        ImageView logo = new ImageView();
        String logoIdName = "logoView";
        logo.setId(logoIdName);
        logo.setFitWidth(415);
        logo.setFitHeight(190);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);

        VBox options = new VBox();
        String buttonsIdName = "buttonsBlock";
        options.setId(buttonsIdName);

        Map<String, Integer> buttonMap = new LinkedHashMap<>();
        for(String s:btnContent){
            buttonMap.put(s, btnContent.indexOf(s)+2);
        }

        for (Map.Entry<String, Integer> btn : buttonMap.entrySet()){

            BorderPane butt = new BorderPane();
            String buttonClassName = "menuBtn";
            butt.getStyleClass().add(buttonClassName);

            Text txt = new Text();
            txt.setText(btn.getKey());
            String textColor = "#f1f1f1";
            txt.setFill(Color.web(textColor));
            butt.setCenter(txt);
            options.getChildren().add(butt);

            butt.setOnMouseEntered(event -> butt.setBackground(new Background(new BackgroundFill(Color.web(hoverReturnBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
            butt.setOnMouseExited(event -> butt.setBackground(new Background(new BackgroundFill(Color.web(hoverBtnColor), CornerRadii.EMPTY, Insets.EMPTY))));
            butt.setOnMouseClicked(event -> {
                try {
                    primaryStage.setScene(this.SceneSet(btn.getValue(), primaryStage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

        gridPane.add(logo, 0,0);
        gridPane.add(options, 0, 1);
        whole.getChildren().add(gridPane);

        return new Scene(template, 750, 500);
    }

}
