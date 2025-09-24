package oxono;

import javafx.application.Application;
import javafx.stage.Stage;
import oxono.controller.Controller;
import oxono.view.MainView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {
        MainView mainView = new MainView(primaryStage);
        Controller controller = new Controller(mainView);
        mainView.setController(controller);
        primaryStage.show();

    }


}
