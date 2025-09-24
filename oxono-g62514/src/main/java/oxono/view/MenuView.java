package oxono.view;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import oxono.controller.Controller;

public class MenuView extends MenuBar{
    private Menu menu;

    private MenuItem newGame;
    private MenuItem undo;
    private MenuItem redo;
    private MenuItem surrender;

    public MenuView() {
        this.menu = new Menu("Menu");
        this.newGame = new MenuItem("New game");
        this.undo = new MenuItem("Undo");
        this.redo  = new MenuItem("Redo");
        this.surrender  = new MenuItem("Surrender");
        menu.getItems().addAll(newGame, undo, redo, surrender);
        this.getMenus().add(menu);
        setMenuIsInGame(false);
    }

    public void setMenuIsInGame(Boolean isInGame) {
        if (isInGame) {
            newGame.setDisable(true);
            undo.setDisable(false);
            redo.setDisable(false);
            surrender.setDisable(false);
        } else {
            newGame.setDisable(false);
            undo.setDisable(true);
            redo.setDisable(true);
            surrender.setDisable(true);
        }
    }

    public MenuItem getNewGame() {
        return newGame;
    }

    public MenuItem getUndo() {
        return undo;
    }

    public MenuItem getRedo() {
        return redo;
    }

    public MenuItem getSurrender() {
        return surrender;
    }
}
