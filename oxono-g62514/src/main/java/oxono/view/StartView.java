package oxono.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class StartView extends Dialog<List<Integer>>{

    public StartView() {
        this.setTitle("Congifuration of new game");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,150,10,10));
        TextField numberOfPlayer = new TextField();
        numberOfPlayer.setPrefWidth(80);
        numberOfPlayer.setPromptText("Number of players (1 or 2)");
        gridPane.add(new Label("Number of player (1 or 2) :"), 0, 0);
        gridPane.add(numberOfPlayer, 1, 0);
        gridPane.add(new Label("Size of the grid :"), 0, 2);

        gridPane.add(new Label("AI level :"), 0, 1);
        ObservableList<String> AILevels =
                FXCollections.observableArrayList(
                        "Facile",
                        "Difficile"
                );
        final ComboBox comboBoxAI = new ComboBox(AILevels);
        comboBoxAI.getSelectionModel().select(0);
        ObservableList<Integer> sizeOfGrid =
                FXCollections.observableArrayList(4, 6, 8);
        final ComboBox comboBoxSize = new ComboBox(sizeOfGrid);
        comboBoxSize.getSelectionModel().select(1);

        gridPane.add(comboBoxAI, 1, 1);
        gridPane.add(comboBoxSize, 1, 2);
        ButtonType configButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(configButtonType, ButtonType.CANCEL);

        Node okButton = this.getDialogPane().lookupButton(configButtonType);
        okButton.setDisable(true);
        numberOfPlayer.textProperty().addListener((observable, oldValue, newValue)  -> {
            okButton.setDisable(!(isNumeric(newValue) && Integer.parseInt(newValue) > 0 && Integer.parseInt(newValue) < 3));
        });
        this.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> numberOfPlayer.requestFocus());

        this.setResultConverter((dialogButton -> {
            if (dialogButton == configButtonType) {

                List<Integer> returnList = new ArrayList<>();
                returnList.add(Integer.parseInt(numberOfPlayer.getText()));
                returnList.add((Integer) comboBoxSize.getSelectionModel().getSelectedItem());
                returnList.add(comboBoxAI.getSelectionModel().getSelectedIndex() + 1);

                return returnList;
            }
            return null;
        }));

    }

    private boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
