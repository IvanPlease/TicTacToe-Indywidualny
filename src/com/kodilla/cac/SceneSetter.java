package com.kodilla.cac;

import com.opencsv.CSVWriter;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.glyphfont.Glyph;

import java.io.*;
import java.util.*;

public class SceneSetter {

    public static int randomGen(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();

    }

    private double xOffset = 0;
    private double yOffset = 0;
    private int layYVal = 30;
    private final int VGapV = 10;
    private int paneWidth = 360;
    private final String temFileName = "template.fxml";
    private final String dragIdName = "#dragBar";
    private final String backIcon = "long_arrow_left";
    private final String backButtonClassName = "backBtn";
    private final String glyphStyleClassName = "glyphStyle";
    private final String backBackButtonIdName = "backBackButton";
    private final String rootIdName = "#wholeWin";
    private final String rootBackIdName = "root";
    private final String backIdName = "#back";
    private final String backName = "back";
    private final String buttonClassName = "menuBtn";
    private final String leftBarClass = "leftBar";
    private final String rightBarClass = "rightBar";
    private final String textColor = "#f1f1f1";
    private final String buttonsIdName = "buttonsBlock";
    private final String logoIdName = "logoView";
    private final String exitPaneClass = "bigBlack";
    private final String gameBoardId = "gameBoard";
    private final String insideBoardId = "insidePane";
    private final String inPaneId = "inPane";
    private final String gPaneId = "GPane";
    private final String usernamePrompt = "Podaj nazwę użytkownika";
    private final String textAskClass = "textAsk";
    private final String tAskClass = "tAsk";
    private final String nicknameId = "nickName";
    private final String sizeId = "size";
    private final String diffPrompt = "Wybierz poziom trudności";
    private final String startPrompt = "Rozpocznij grę";
    private final String startMPrompt = "Wyszukaj rozgrywki";
    private final String nextBtnId = "nextBtn";
    private final String closeIdName = "#closeWin";
    private final String minIdName = "#minWin";
    private final String tttPaneClass = "tttPane";
    private final String borderNClass = "border-none";
    private final String borderTClass = "border-top";
    private final String borderRClass = "border-right";
    private final String borderLClass = "border-left";
    private final String borderBClass = "border-bottom";
    private final String borderRTClass = "border-right-top";
    private final String borderRBClass = "border-right-bottom";
    private final String borderLTClass = "border-left-top";
    private final String borderLBClass = "border-left-bottom";
    private final String sceneMissing = "Brak sceny o danym numerze";
    private final String exitBtnId = "exitBtn";
    private final String exitBtnIdName = "#exitBtn";
    private final String gridPaneClass = "miaWin";
    private final String endGame = "Czy na pewno chcesz zakonczyc gre?";
    private final String textFontClass = "font";
    private final String bacKBtnClass = "btnExit";
    private final String yesBtn = "Tak";
    private final String noBtn = "Nie";
    private final List<String> btnContent = new LinkedList<String>() {
        {
            add("Jednoosobowa");
            add("Wieloosobowa");
            add("Tablica Wyników");
        }
    };

    public Scene SceneSet(int sceneNr, Stage primaryStage) throws IOException {
        Scene tScene = null;
        switch (sceneNr) {
            case 1:
                tScene = menuScene(primaryStage);
                break;
            case 2:
                tScene = askScene(primaryStage);
                break;
            case 3:
                tScene = multiScene(primaryStage);
                break;
            case 4:
                tScene = scoreScene(primaryStage);
                break;
            default:
                System.out.println(sceneMissing);
                break;
        }
        return tScene;
    }

    public Scene gameScene(Stage primaryStage, String nickname, String difficulty, int n) throws IOException {
        int tot = randomGen(1,2);
        GameRulesAi.State icon;
        if(tot == 1){
            icon = GameRulesAi.State.X;
        }else{
            icon = GameRulesAi.State.O;
        }
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane whole = (Pane) template.lookup(rootIdName);
        defFunctions(primaryStage, template, true);

        GameRulesAi gameRules = new GameRulesAi(difficulty, icon, n);
        Pane backBtn = (Pane) template.lookup(backIdName);
        backBtn.setOnMouseClicked(event -> {
            backBtn.setOnMouseClicked(null);
            exitMsg(primaryStage, template, whole, gameRules, nickname);
        });
        BorderPane gameBoard = new BorderPane();
        gameBoard.setId(gameBoardId);
        gameBoard.setLayoutY(layYVal);

        GridPane leftP = new GridPane();
        leftP.getStyleClass().add(leftBarClass);
        leftP.setVgap(5);
        leftP.setStyle("-fx-padding: 10 0 0 0");

        BorderPane namePane = new BorderPane();
        namePane.setPrefWidth(150);

        Text nickname2 = new Text();
        nickname2.getStyleClass().add("nicknameClass");
        nickname2.setWrappingWidth(140);
        nickname2.setFill(Color.web(textColor));
        nickname2.setText("Nazwa uzytkownika: " + nickname);

        BorderPane activeSymbol = new BorderPane();
        activeSymbol.setPrefWidth(150);

        Text stateIcon = new Text();
        stateIcon.getStyleClass().add("nicknameClass");
        stateIcon.setWrappingWidth(140);
        stateIcon.setFill(Color.web(textColor));
        stateIcon.setText("Aktywny symbol: " + gameRules.getIcon());

        BorderPane stateText = new BorderPane();
        stateText.setPrefWidth(150);

        Text stateTextTxt = new Text();
        stateTextTxt.getStyleClass().add("nicknameClass");
        stateTextTxt.setWrappingWidth(140);
        stateTextTxt.setFill(Color.web(textColor));
        stateTextTxt.setText("Stan Gry:");

        GridPane gameState = new GridPane();
        gameState.setPrefWidth(150);

        Text gameStateOne = new Text();
        gameStateOne.setId(String.valueOf(gameRules.getIcon()));
        gameStateOne.getStyleClass().add("nicknameClass");
        gameStateOne.setWrappingWidth(75);
        gameStateOne.setFill(Color.web(textColor));
        gameStateOne.setText(gameRules.getIcon() + ": " + gameRules.getScoreU());


        Text gameStateTwo = new Text();
        gameStateTwo.setId(String.valueOf(gameRules.getIconAi()));
        gameStateTwo.getStyleClass().add("nicknameClass");
        gameStateTwo.setWrappingWidth(75);
        gameStateTwo.setFill(Color.web(textColor));
        gameStateTwo.setText(gameRules.getIconAi() + ": " + gameRules.getScoreAI());


        namePane.setCenter(nickname2);
        activeSymbol.setCenter(stateIcon);
        stateText.setCenter(stateTextTxt);
        gameState.add(gameStateOne, 0,0);
        gameState.add(gameStateTwo, 1,0);
        leftP.add(namePane, 0,0);
        leftP.add(activeSymbol,0,1);
        leftP.add(stateText,0,2);
        leftP.add(gameState,0,3);

        GridPane gPane = new GridPane();
        gPane.setId(gPaneId);
        gPane.setAlignment(Pos.CENTER);

        createBoard(paneWidth, n, gPane, primaryStage, template, whole, gameRules, nickname);

        Pane rightBar = new Pane();
        rightBar.getStyleClass().add(rightBarClass);

        gameBoard.setLeft(leftP);
        gameBoard.setCenter(gPane);
        gameBoard.setRight(rightBar);

        whole.getChildren().add(gameBoard);

        return new Scene(template, 750, 500);

    }

    public Scene askScene(Stage primaryStage) throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane whole = (Pane) template.lookup(rootIdName);
        defFunctions(primaryStage, template, true);

        Pane backBtn = (Pane) template.lookup(backIdName);
        backBtn.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(this.SceneSet(1, primaryStage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        GridPane gameBoard = new GridPane();
        gameBoard.setId(gameBoardId);
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setLayoutY(layYVal);

        GridPane insidePane = new GridPane();
        insidePane.setId(insideBoardId);
        insidePane.setAlignment(Pos.CENTER);

        BorderPane name = new BorderPane();
        name.getStyleClass().add(textAskClass);

        Text nameText = new Text();
        nameText.setText(usernamePrompt);
        nameText.setFill(Color.web(textColor));

        name.setCenter(nameText);

        TextField textField = new TextField();
        textField.setId(nicknameId);
        textField.getStyleClass().add(textAskClass);

        BorderPane size = new BorderPane();
        size.getStyleClass().add(textAskClass);

        Text sizeText = new Text();
        sizeText.setText("Podaj rozmiar tablicy (Max: 7)");
        sizeText.setFill(Color.web(textColor));

        size.setCenter(sizeText);

        TextField textField2 = new TextField();
        textField2.setId(sizeId);
        textField2.getStyleClass().add(textAskClass);
        textField2.setText("3");

        BorderPane difT = new BorderPane();
        difT.getStyleClass().add(textAskClass);

        Text difText = new Text();
        difText.setText(diffPrompt);
        difText.setFill(Color.web(textColor));

        difT.setCenter(difText);

        HBox buttons = new HBox();
        buttons.getStyleClass().add(textAskClass);

        Map<String, String> radioButtons = new LinkedHashMap<>();
        radioButtons.put("E", "0");
        radioButtons.put("M", "1");
        radioButtons.put("H", "2");

        final ToggleGroup difficulty = new ToggleGroup();

        for(Map.Entry<String, String> radio : radioButtons.entrySet()){
            BorderPane borderPane1 = new BorderPane();
            RadioButton radioButton = new RadioButton();
            radioButton.setToggleGroup(difficulty);
            radioButton.setText(radio.getKey());
            radioButton.setUserData(radio.getValue());
            if(radio.getValue().equals("0")){
                radioButton.setSelected(true);
            }
            borderPane1.setCenter(radioButton);
            buttons.getChildren().add(borderPane1);
        }

        BorderPane nextBtn = new BorderPane();
        nextBtn.setId(nextBtnId);
        nextBtn.getStyleClass().add(textAskClass);

        BorderPane tAsk = new BorderPane();
        tAsk.getStyleClass().add(tAskClass);

        Text start = new Text();
        start.setText(startPrompt);
        start.setFill(Color.web(textColor));

        tAsk.setCenter(start);
        nextBtn.setCenter(tAsk);

        nextBtn.setOnMouseClicked(event -> {
            String nic = textField.getText();
            String diff = (String) difficulty.getSelectedToggle().getUserData();
            String n = textField2.getText();
            if(nic != null && !nic.isEmpty() && n != null && !n.isEmpty() &&  Integer.parseInt(n) > 0 && Integer.parseInt(n) < 8){
                try {
                    primaryStage.setScene(this.gameScene(primaryStage, nic, diff, Integer.parseInt(n)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        insidePane.add(name,0,0);
        insidePane.add(textField,0,1);
        insidePane.add(size,0,2);
        insidePane.add(textField2,0,3);
        insidePane.add(difT,0,4);
        insidePane.add(buttons,0,5);
        insidePane.add(nextBtn,0,6);

        gameBoard.getChildren().add(insidePane);

        whole.getChildren().add(gameBoard);

        return new Scene(template, 750, 500);
    }

    public Scene multiScene(Stage primaryStage) throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane whole = (Pane) template.lookup(rootIdName);
        defFunctions(primaryStage, template, true);

        Pane backBtn = (Pane) template.lookup(backIdName);
        backBtn.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(this.SceneSet(1, primaryStage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        GridPane gameBoard = new GridPane();
        gameBoard.setId(gameBoardId);
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setLayoutY(layYVal);

        GridPane insidePane = new GridPane();
        insidePane.setId(insideBoardId);
        insidePane.setAlignment(Pos.CENTER);

        BorderPane name = new BorderPane();
        name.getStyleClass().add(textAskClass);

        Text nameText = new Text();
        nameText.setText(usernamePrompt);
        nameText.setFill(Color.web(textColor));

        name.setCenter(nameText);

        TextField textField = new TextField();
        textField.setId(nicknameId);
        textField.getStyleClass().add(textAskClass);

        BorderPane size = new BorderPane();
        size.getStyleClass().add(textAskClass);

        BorderPane nextBtn = new BorderPane();
        nextBtn.setId(nextBtnId);
        nextBtn.getStyleClass().add(textAskClass);

        BorderPane tAsk = new BorderPane();
        tAsk.getStyleClass().add(tAskClass);

        Text start = new Text();
        start.setText(startMPrompt);
        start.setFill(Color.web(textColor));

        tAsk.setCenter(start);
        nextBtn.setCenter(tAsk);

        nextBtn.setOnMouseClicked(event -> {
            String nic = textField.getText();
            if(nic != null && !nic.isEmpty()){
                try {
                    DatabaseConnector dbConnector = new DatabaseConnector();
                    dbConnector.insertToDatabase("insert into `userQueue`(`username`) values ('"+nic+"')");
                    int userId = dbConnector.selectIDFromDatabase("select `id` from `userQueue` where `username`='"+nic+"'");
                    int rowCount = dbConnector.selectFromDatabase("select count(*) from `userQueue`");
                    int gameCount = dbConnector.selectFromDatabase("select count(*) from `gameStatus`");
                    if(rowCount%2==0){
                        int gameId = rowCount/2;
                        if(gameId > gameCount){
                            int[] uName = new int[2];
                            dbConnector.selectFromDatabase("select * from `userQueue` order by id desc limit 2;", uName);
                            for(int s:uName){
                                dbConnector.updateRowInDatabase("update `userQueue` set `gameId`="+gameId+" where `id`="+s);
                            }
                            dbConnector.insertToDatabase("insert into `gameStatus`(`user1`, `user2`) values ('"+uName[0]+"','"+uName[1]+"')");
                        }
                    }
                    Runnable runnable = () -> {
                        boolean end = false;
                        while(!end){
                            try {
                                boolean res = dbConnector.selectFromDatabaseForResult("select count(*) from `userQueue` where `id`=" + userId + " AND `gameId` is not null");
                                if (res) {
                                    System.out.println("Game ready");
                                    end = true;
                                } else {
                                    System.out.println("Game not ready!");
                                }
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Thread t = new Thread(runnable);
                    t.start();
                    t.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        insidePane.add(name,0,0);
        insidePane.add(textField,0,1);
        insidePane.add(size,0,2);
        insidePane.add(nextBtn,0,3);

        gameBoard.getChildren().add(insidePane);

        whole.getChildren().add(gameBoard);

        return new Scene(template, 750, 500);
    }



    public Scene gameScene(Stage primaryStage, int gameId, String nickname) throws IOException {
        int tot = randomGen(1,2);
        GameRulesAi.State icon;
        if(tot == 1){
            icon = GameRulesAi.State.X;
        }else{
            icon = GameRulesAi.State.O;
        }
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane whole = (Pane) template.lookup(rootIdName);
        defFunctions(primaryStage, template, true);
        Pane backBtn = (Pane) template.lookup(backIdName);
        backBtn.setOnMouseClicked(event -> backBtn.setOnMouseClicked(null));
        BorderPane gameBoard = new BorderPane();
        gameBoard.setId(gameBoardId);
        gameBoard.setLayoutY(layYVal);

        GridPane gPane = new GridPane();
        gPane.setId(gPaneId);
        gPane.setAlignment(Pos.CENTER);


        Pane rightBar = new Pane();
        rightBar.getStyleClass().add(rightBarClass);
        gameBoard.setCenter(gPane);
        gameBoard.setRight(rightBar);

        whole.getChildren().add(gameBoard);

        return new Scene(template, 750, 500);

    }

    public void defFunctions(Stage primaryStage, Parent template, boolean back){
        Pane dragBar = (Pane) template.lookup(dragIdName);
        Pane closeBtn = (Pane) template.lookup(closeIdName);
        Pane minWin = (Pane) template.lookup(minIdName);
        Pane dBar = (Pane) template.lookup(dragIdName);

        closeBtn.setOnMouseClicked(event -> primaryStage.close());

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

        dBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        dBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        if(back){
            Glyph backArrow = new Glyph();
            backArrow.getStyleClass().add(glyphStyleClassName);
            backArrow.setIcon(backIcon);

            BorderPane borderPane = new BorderPane();
            borderPane.setId(backBackButtonIdName);
            borderPane.setCenter(backArrow);

            Pane backBtn = new Pane();
            backBtn.getStyleClass().add(backButtonClassName);
            backBtn.setId(backName);

            backBtn.getChildren().add(borderPane);
            dragBar.getChildren().add(backBtn);
        }

    }

    public Scene menuScene(Stage primaryStage)  throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane whole = (Pane) template.lookup(rootIdName);
        GridPane gridPane = new GridPane();
        gridPane.setId(rootBackIdName);
        gridPane.setLayoutY(layYVal);
        gridPane.setAlignment(Pos.TOP_CENTER);
        defFunctions(primaryStage, template, false);
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

    public Scene scoreScene(Stage primaryStage)  throws IOException {
        Parent template = FXMLLoader.load(getClass().getResource(temFileName));
        Pane whole = (Pane) template.lookup(rootIdName);
        GridPane gridPane = new GridPane();
        gridPane.setId(gameBoardId);
        gridPane.setLayoutY(layYVal);
        gridPane.setAlignment(Pos.CENTER);

        Map<String, String> table = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("abc.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] score = line.split(",");
            table.put(score[0], score[1]);
        }
        String[] arrayKeys = table.keySet().toArray( new String[ table.size() ] );

        table.remove(table.get(arrayKeys[ arrayKeys.length - 1 ]) );

        GridPane insidePane = new GridPane();
        insidePane.setId(inPaneId);
        insidePane.setAlignment(Pos.CENTER);

        GridPane tableScore = new GridPane();
        tableScore.prefWidth(250);
        int mapIterator = 0;

        for(Map.Entry<String, String> sc : table.entrySet()){
            Pane t = new Pane();
            t.prefWidth(125);

            Text te = new Text();
            te.setWrappingWidth(125);
            te.setText(sc.getKey().substring(1,sc.getKey().length()-1));
            te.getStyleClass().add("nicknameClass");
            te.setFill(Color.web("#f1f1f1"));

            Pane t1 = new Pane();
            t1.prefWidth(125);

            Text te1 = new Text();
            te1.setWrappingWidth(125);
            te1.setText(sc.getValue().substring(1,sc.getValue().length()-1));
            te1.getStyleClass().add("nicknameClass");
            te1.setFill(Color.web("#f1f1f1"));

            t.getChildren().add(te);
            t1.getChildren().add(te1);
            tableScore.add(t, 0,mapIterator);
            tableScore.add(t1, 1,mapIterator);
            mapIterator++;
        }
        insidePane.add(tableScore,0,0);
        gridPane.add(insidePane,0,0);
        defFunctions(primaryStage, template, true);

        Pane backBtn = (Pane) template.lookup(backIdName);
        backBtn.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(this.SceneSet(1, primaryStage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        whole.getChildren().add(gridPane);

        return new Scene(template, 750, 500);
    }

    public void exitMsg(Stage primaryStage, Parent template, Pane whole, GameRulesAi gameRules, String nickname){
        BorderPane overPane = new BorderPane();
        overPane.getStyleClass().add(exitPaneClass);
        overPane.setId(exitBtnId);
        overPane.setLayoutY(layYVal);

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add(gridPaneClass);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(VGapV);

        BorderPane borderPane = new BorderPane();

        Text text = new Text();
        text.setText(endGame);
        text.getStyleClass().add(textFontClass);

        borderPane.setCenter(text);

        HBox hBox = new HBox();
        String[] s = {
                yesBtn, noBtn
        };
        for(String r: s){
            BorderPane borderPane1 = new BorderPane();
            borderPane1.getStyleClass().add(bacKBtnClass);
            Text text1 = new Text();
            text1.setText(r);
            text.getStyleClass().add(textFontClass);
            borderPane1.setCenter(text1);
            hBox.getChildren().add(borderPane1);
            switch (r){
                case yesBtn:
                    borderPane1.setOnMouseClicked(event -> {
                        try {
                            primaryStage.setScene(this.SceneSet(1, primaryStage));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case noBtn:
                    borderPane1.setOnMouseClicked(event -> {
                        BorderPane borderPane2 = (BorderPane) template.lookup(exitBtnIdName);
                        whole.getChildren().remove(borderPane2);
                        Pane b = (Pane) template.lookup(backIdName);
                        b.setOnMouseClicked(event1 -> {
                            b.setOnMouseClicked(null);
                            this.exitMsg(primaryStage, template, whole, gameRules, nickname);
                        });
                    });
            }
        }

        gridPane.add(borderPane, 0,0);
        gridPane.add(hBox, 0,1);

        overPane.setCenter(gridPane);
        whole.getChildren().add(overPane);
    }

    public void nextGameMsg(Stage primaryStage, Parent template, Pane whole, GameRulesAi gameRules, String nickname, int n){
        BorderPane overPane = new BorderPane();
        overPane.getStyleClass().add(exitPaneClass);
        overPane.setId(exitBtnId);
        overPane.setLayoutY(layYVal);

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add(gridPaneClass);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(VGapV);

        BorderPane borderPane = new BorderPane();

        Text text = new Text();
        text.setText("Chcesz rozpocząć nową rozgrywke?");
        text.getStyleClass().add(textFontClass);

        borderPane.setCenter(text);

        HBox hBox = new HBox();
        String[] s = {
                yesBtn, noBtn
        };
        for(String r: s){
            BorderPane borderPane1 = new BorderPane();
            borderPane1.getStyleClass().add(bacKBtnClass);
            Text text1 = new Text();
            text1.setText(r);
            text.getStyleClass().add(textFontClass);
            borderPane1.setCenter(text1);
            hBox.getChildren().add(borderPane1);
            switch (r){
                case yesBtn:
                    borderPane1.setOnMouseClicked(event -> {

                        BorderPane borderPane2 = (BorderPane) template.lookup(exitBtnIdName);
                        whole.getChildren().remove(borderPane2);
                        GridPane gPane = (GridPane) template.lookup("#" + gPaneId);
                        gPane.getChildren().clear();

                        createBoard(paneWidth, n, gPane, primaryStage, template, whole, gameRules, nickname);
                    });
                    break;
                case noBtn:
                    borderPane1.setOnMouseClicked(event -> {
                        try {
                            BorderPane borderPane2 = (BorderPane) template.lookup(exitBtnIdName);
                            whole.getChildren().remove(borderPane2);

                            FileWriter outputFile = new FileWriter("abc.csv", true);
                            CSVWriter writer = new CSVWriter(outputFile);
                            String[] data2 = { nickname, String.valueOf(gameRules.getScoreU())};
                            writer.writeNext(data2);

                            writer.close();
                            primaryStage.setScene(this.SceneSet(1, primaryStage));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
            }
        }

        gridPane.add(borderPane, 0,0);
        gridPane.add(hBox, 0,1);

        overPane.setCenter(gridPane);
        whole.getChildren().add(overPane);
    }

    public void createBoard(int paneWidth, int n, GridPane gPane, Stage primaryStage, Parent template, Pane whole, GameRulesAi gameRules, String nickname){

        int[][] squares = new int[n][n];

        squares[0][n-1] = 8;
        squares[n-1][0] = 5;
        squares[n-1][n-1] = 7;

        for(int s = 0;s<n;s++){
            for(int p = 0;p<n;p++){
                if(s==0){
                    if( p!=n-1){
                        squares[s][p] = 6;
                    }else{
                        squares[s][p] = 3;
                    }
                }else if(s==n-1){
                    if(p!=n-1){
                        squares[s][p] = 2;
                    }else{
                        squares[s][p] = 0;
                    }
                }else{
                    if(p!=n-1){
                        squares[s][p] = 6;
                    }else{
                        squares[s][p] = 3;
                    }
                }
            }
        }

        int wither = paneWidth / n;

        for(int s = 0;s<n;s++){
            for(int p = 0;p<n;p++){
                Pane square = new Pane();
                square.getStyleClass().add(tttPaneClass);
                square.setPrefWidth(wither);
                square.setPrefHeight(wither);
                switch (squares[s][p]){
                    case 0:
                        square.getStyleClass().add(borderNClass);
                        break;
                    case 1:
                        square.getStyleClass().add(borderTClass);
                        break;
                    case 2:
                        square.getStyleClass().add(borderRClass);
                        break;
                    case 3:
                        square.getStyleClass().add(borderBClass);
                        break;
                    case 4:
                        square.getStyleClass().add(borderLClass);
                        break;
                    case 5:
                        square.getStyleClass().add(borderRTClass);
                        break;
                    case 6:
                        square.getStyleClass().add(borderRBClass);
                        break;
                    case 7:
                        square.getStyleClass().add(borderLTClass);
                        break;
                    case 8:
                        square.getStyleClass().add(borderLBClass);
                        break;
                }
                gPane.add(square, p, s);
                int row = s;
                int col = p;
                square.setOnMouseClicked(event -> {
                    int result = gameRules.makeMove(row, col, gameRules.getIcon(), gPane);
                    if(result == 1){
                        System.out.println("Winner");
                        Text t = (Text) template.lookup("#"+gameRules.getIcon());
                        t.setText(gameRules.getIcon() + ": " + gameRules.getScoreU());
                        nextGameMsg(primaryStage, template, whole, gameRules, nickname, n);
                    }else if(result == 3){
                        System.out.println("Draw1");
                        nextGameMsg(primaryStage, template, whole, gameRules, nickname, n);
                    }else if(result != 2){
                        int re;
                        do{
                            re = gameRules.makeMove(gPane);
                            if(re == 3){
                                break;
                            }
                        }while(re == 2);
                        if(re == 1){
                            Text t = (Text) template.lookup("#"+gameRules.getIconAi());
                            t.setText(gameRules.getIconAi() + ": " + gameRules.getScoreAI());
                            nextGameMsg(primaryStage, template, whole, gameRules, nickname, n);
                        }else if(re == 3){
                            System.out.println("Draw1");
                            nextGameMsg(primaryStage, template, whole, gameRules, nickname, n);
                        }
                    }
                });
            }
        }
    }public void createBoard(int paneWidth, int n, GridPane gPane, Parent template, GameRulesAi gameRules){

        int[][] squares = new int[n][n];

        squares[0][n-1] = 8;
        squares[n-1][0] = 5;
        squares[n-1][n-1] = 7;

        for(int s = 0;s<n;s++){
            for(int p = 0;p<n;p++){
                if(s==0){
                    if( p!=n-1){
                        squares[s][p] = 6;
                    }else{
                        squares[s][p] = 3;
                    }
                }else if(s==n-1){
                    if(p!=n-1){
                        squares[s][p] = 2;
                    }else{
                        squares[s][p] = 0;
                    }
                }else{
                    if(p!=n-1){
                        squares[s][p] = 6;
                    }else{
                        squares[s][p] = 3;
                    }
                }
            }
        }

        int wither = paneWidth / n;

        for(int s = 0;s<n;s++){
            for(int p = 0;p<n;p++){
                Pane square = new Pane();
                square.getStyleClass().add(tttPaneClass);
                square.setPrefWidth(wither);
                square.setPrefHeight(wither);
                switch (squares[s][p]){
                    case 0:
                        square.getStyleClass().add(borderNClass);
                        break;
                    case 1:
                        square.getStyleClass().add(borderTClass);
                        break;
                    case 2:
                        square.getStyleClass().add(borderRClass);
                        break;
                    case 3:
                        square.getStyleClass().add(borderBClass);
                        break;
                    case 4:
                        square.getStyleClass().add(borderLClass);
                        break;
                    case 5:
                        square.getStyleClass().add(borderRTClass);
                        break;
                    case 6:
                        square.getStyleClass().add(borderRBClass);
                        break;
                    case 7:
                        square.getStyleClass().add(borderLTClass);
                        break;
                    case 8:
                        square.getStyleClass().add(borderLBClass);
                        break;
                }
                gPane.add(square, p, s);
                int row = s;
                int col = p;
                square.setOnMouseClicked(event -> {
                    int result = gameRules.makeMove(row, col, gameRules.getIcon(), gPane);
                    if(result == 1){
                        System.out.println("Winner");
                        Text t = (Text) template.lookup("#"+gameRules.getIcon());
                        t.setText(gameRules.getIcon() + ": " + gameRules.getScoreU());
                    }else if(result == 3){
                        System.out.println("Draw1");
                    }else if(result != 2){
                        int re;
                        do{
                            re = gameRules.makeMove(gPane);
                            if(re == 3){
                                break;
                            }
                        }while(re == 2);
                        if(re == 1){
                            Text t = (Text) template.lookup("#"+gameRules.getIconAi());
                            t.setText(gameRules.getIconAi() + ": " + gameRules.getScoreAI());
                        }else if(re == 3){
                            System.out.println("Draw1");
                        }
                    }
                });
            }
        }
    }

}
