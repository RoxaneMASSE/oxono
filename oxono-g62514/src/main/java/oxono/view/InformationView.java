package oxono.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.*;

public class InformationView extends HBox {
    private Label playerPink;
    private Label playerBlack;
    private Label counterXPink;
    private Label counterOPink;
    private Label counterXBlack;
    private Label counterOBlack;
    private Label freeBoxes;
    private VBox playerPinkBox;
    private VBox playerBlackBox;
    private ImageView pinkXToken;
    private ImageView blackXToken;
    private ImageView pinkOToken;
    private ImageView blackOToken;
    private ImageView freeBoxesImage;
    private HBox playerPinkCounterX;
    private HBox playerBlackCounterX;
    private HBox playerPinkCounterO;
    private HBox playerBlackCounterO;
    private HBox freeBoxesBox;
    private Button AIButton;


    public InformationView() {
        this.playerPink = new Label("Player pink");
        this.playerBlack = new Label("Player black");
        this.counterXPink = new Label();
        this.counterOPink = new Label();
        this.counterXBlack = new Label();
        this.counterOBlack = new Label();
        this.AIButton = new Button("AI play");
        this.AIButton.setPrefSize(100,25);
        pinkXToken = new ImageView(Images.getImage(9));
        blackXToken = new ImageView(Images.getImage(11));
        pinkOToken = new ImageView(Images.getImage(10));
        blackOToken = new ImageView(Images.getImage(12));
        freeBoxesImage = new ImageView(Images.getImage(0));

        this.freeBoxes = new Label();
        playerPink.setFont(Font.font("Verdana", 24));
        Color c = Color.rgb(240,39,146);
        playerPink.setTextFill(c);
        playerBlack.setFont(Font.font("Verdana", 24));
        counterXPink.setFont(Font.font("Verdana", 24));
        counterOPink.setFont(Font.font("Verdana", 24));
        counterXBlack.setFont(Font.font("Verdana", 24));
        counterOBlack.setFont(Font.font("Verdana", 24));
        freeBoxes.setFont(Font.font("Verdana", 24));

        this.playerPinkCounterX = new HBox();
        this.playerPinkCounterO = new HBox();
        this.playerBlackCounterX = new HBox();
        this.playerBlackCounterO = new HBox();
        this.freeBoxesBox = new HBox();
        playerPinkCounterX.setAlignment(Pos.CENTER);
        playerBlackCounterO.setAlignment(Pos.CENTER);
        playerPinkCounterO.setAlignment(Pos.CENTER);
        playerBlackCounterX.setAlignment(Pos.CENTER);
        freeBoxesBox.setAlignment(Pos.CENTER);

        playerPinkCounterX.getChildren().addAll(counterXPink, pinkXToken);
        playerPinkCounterO.getChildren().addAll(counterOPink, pinkOToken);
        playerBlackCounterX.getChildren().addAll(counterXBlack, blackXToken);
        playerBlackCounterO.getChildren().addAll(counterOBlack, blackOToken);
        freeBoxesBox.getChildren().addAll(freeBoxes, freeBoxesImage);

        Label spacer = new Label();
        spacer.setMinHeight(25);
        spacer.setMaxHeight(25);

        //spacer.setMinHeight(AIButton.getHeight());
        //spacer.setMaxHeight(AIButton.getHeight());

        this.playerPinkBox = new VBox();
        this.playerBlackBox = new VBox();
        playerPinkBox.setSpacing(15);
        playerPinkBox.setAlignment(Pos.CENTER);
        playerBlackBox.setAlignment(Pos.CENTER);
        playerBlackBox.setSpacing(15);
        this.setSpacing(200);
        //playerPinkBox.getChildren().addAll(playerPink, playerPinkCounterX, playerPinkCounterO);
        playerPinkBox.getChildren().addAll(playerPink, playerPinkCounterX, playerPinkCounterO, AIButton);
        //playerBlackBox.getChildren().addAll(playerBlack, playerBlackCounterX, playerBlackCounterO);
        playerBlackBox.getChildren().addAll(playerBlack, playerBlackCounterX, playerBlackCounterO, spacer);
        this.getChildren().addAll(playerPinkBox, freeBoxesBox, playerBlackBox);
        playerPinkBox.setPadding(new Insets(0,0,20,0));
        playerBlackBox.setPadding(new Insets(0,0,20,0));

        this.setAlignment(Pos.CENTER);
    }

    public void setVisibleAIButton(Boolean visible) {
        AIButton.setVisible(visible);
    }

    public Button getAIButton() {
        return AIButton;
    }

    public void setBorder(String player) {
        if (player.equals("PINK")) {
            Color c = Color.rgb(240,39,146);
            String pinkColorCss = String.format("rgba(%d, %d, %d, %.2f);",
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255),
                    c.getOpacity());
            playerPinkBox.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 5;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 10;" +
                    "-fx-border-color: "+ pinkColorCss);
            playerBlackBox.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: none;");
        } else {
            playerBlackBox.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 5;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 10;" +
                    "-fx-border-color: black;");
            playerPinkBox.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: none;");
        }
    }

    public void setCounterXPink(int counterXPink) {
        this.counterXPink.setText(counterXPink + " ");
    }

    public void setCounterOPink(int counterOPink) {
        this.counterOPink.setText(counterOPink + " ");
    }

    public void setCounterXBlack(int counterXBlack) {
        this.counterXBlack.setText(counterXBlack + " ");
    }

    public void setCounterOBlack(int counterOBlack) {
        this.counterOBlack.setText(counterOBlack + " ");
    }

    public void setFreeBoxes(int freeBoxes) {
        this.freeBoxes.setText(freeBoxes + " ");
    }
}
