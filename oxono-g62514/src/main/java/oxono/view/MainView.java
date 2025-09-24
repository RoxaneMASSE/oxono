package oxono.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oxono.controller.Controller;
import oxono.model.Game;
import oxono.model.Observer;
import oxono.model.Position;

import java.util.List;
import java.util.Optional;

public class MainView implements Observer {
    private MenuView menuView;
    private BoardView boardView;
    private Stage stage;
    private BorderPane root;
    private Controller controller;
    private StartView startView;
    private InformationView informationView;

    public MainView(Stage stage) {
        this.menuView = new MenuView();

        this.stage = stage;

        root = new BorderPane();
        root.setTop(menuView);

        stage.setTitle("OXONO");
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        menuView.getNewGame().setOnAction(actionEvent -> {
            startView = new StartView();
            List<Integer> startViewReturns = startView.showAndWait().orElse(null);
            if (startViewReturns != null) {
                menuView.setMenuIsInGame(true);
                this.boardView = new BoardView(startViewReturns.get(1), controller);
                root.setCenter(boardView);
                this.informationView = new InformationView(/*startView.getNumberOfPlayer()*/);
                if (startViewReturns.get(0) < 2) {
                    informationView.setVisibleAIButton(true);
                    informationView.getAIButton().setOnAction(e -> {
                        informationView.getAIButton().setDisable(true);
                        informationView.getAIButton().getParent().setCursor(Cursor.WAIT);

                        Task<Void> aiTask = new Task<>() {
                            @Override
                            protected Void call() {
                                // Effectuer l'action IA (opération longue)
                                controller.AIAction();
                                return null;
                            }

                            @Override
                            protected void succeeded() {
                                // Réinitialiser le curseur après l'action IA
                                System.out.println("ok");
                                Platform.runLater(() -> {
                                    informationView.getAIButton().getParent().setCursor(Cursor.DEFAULT);
                                });
                                //informationView.getAIButton().getParent().setCursor(Cursor.DEFAULT);
                            }

                            @Override
                            protected void failed() {
                                // Gérer les erreurs si l'action IA échoue
                                //informationView.getAIButton().getParent().setCursor(Cursor.DEFAULT);
                                Platform.runLater(() -> {
                                    informationView.getAIButton().getParent().setCursor(Cursor.DEFAULT);
                                });
                                System.out.println("Erreur pendant l'exécution de l'IA");
                                getException().printStackTrace();
                            }
                        };

                        // Exécuter la tâche dans un thread séparé
                        new Thread(aiTask).start();
                    });
                } else {
                    informationView.setVisibleAIButton(false);
                }
                root.setBottom(informationView);
                controller.actionStart(startViewReturns.get(0), startViewReturns.get(1), startViewReturns.get(2));
            }
        });
        menuView.getUndo().setOnAction(actionEvent -> {
            controller.undoAction();
        });
        menuView.getRedo().setOnAction(actionEvent -> {
            controller.redoAction();
        });
        menuView.getSurrender().setOnAction(actionEvent -> {
            showModal(new Stage(), controller.getGame().getWinner().getColor().toString(), true, true);
            disableGame();
        });
    }


    @Override
    public void update() {
        for (int i = 0; i < controller.getGame().getBoardWidth(); i++) {
            for (int j = 0; j < controller.getGame().getBoardHeight(); j++) {
                if (controller.getGame().getSymbolAt(new Position(i,j)).equals("X")) {
                    boardView.setImageView(Images.getImage(controller.getGame().getColorAt(new Position(i, j))), i, j);
                } else if (controller.getGame().getSymbolAt(new Position(i, j)).equals("O")){
                    boardView.setImageView(Images.getImage(controller.getGame().getColorAt(new Position(i, j)) + 3), i, j);
                } else {
                    boardView.setImageView(Images.getImage(0), i, j);
                }
            }
        }
        boardView.setPosXTotemX(controller.getGame().getPosTotemX().getRow());
        boardView.setPosYTotemX(controller.getGame().getPosTotemX().getCol());
        boardView.setPosXTotemO(controller.getGame().getPosTotemO().getRow());
        boardView.setPosYTotemO(controller.getGame().getPosTotemO().getCol());
        boardView.setAIPlayer(controller.getGame().getCurrentPlayer().isAiPlayer());
        if (controller.getGame().getGameState().toString().equals("MOVE")) {
            boardView.setMoveMode(true);
        } else {
            boardView.setMoveMode(false);
        }
        int[] counters = controller.getGame().getCounters();
        informationView.setCounterXPink(counters[0]);
        informationView.setCounterOPink(counters[1]);
        informationView.setCounterXBlack(counters[2]);
        informationView.setCounterOBlack(counters[3]);
        informationView.setFreeBoxes(controller.getGame().getFreeBoxes());
        if (controller.getGame().getCurrentPlayer().isAiPlayer()) {
            informationView.getAIButton().setDisable(false);
        } else {
            informationView.getAIButton().setDisable(true);
        }
        informationView.setBorder(controller.getGame().getCurrentPlayer().getColor().toString());
        if (controller.getGame().win() || controller.getGame().draw()) {
            showModal(new Stage(), controller.getGame().getWinner().getColor().toString(), controller.getGame().win(), false);
            disableGame();
        }
        menuView.getUndo().setDisable(controller.getGame().isUndoEmpty());
        menuView.getRedo().setDisable(controller.getGame().isRedoEmpty());

     }

    private void showModal(Stage ownerStage, String winner, Boolean win, Boolean surrender) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(ownerStage);
        Label message = new Label();
        Label surrenderMsg = new Label();
        if (win) {
            if (surrender) {
               surrenderMsg.setText("Abandon...");
            }
            modalStage.setTitle("Win");
            message.setText("Player " + winner + " wins");
        } else {
            modalStage.setTitle("Draw");
            message.setText("No winner");
        }

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> modalStage.close()); // Ferme la modale

        VBox modalLayout = new VBox(10, surrenderMsg, message, closeButton);
        modalLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene modalScene = new Scene(modalLayout, 250, 150);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    private void disableGame() {
        informationView.setDisable(true);
        boardView.setDisable(true);
        menuView.setMenuIsInGame(false);
    }
}
