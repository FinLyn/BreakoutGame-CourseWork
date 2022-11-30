package project.chenlin_17;

import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the application of the game.
 */
public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DirectorApp.getInstance().initialize(stage);
    }

    /**
     * This function sets the game to the current stage.
     * @param args application's running status
     */
    public static void main(String[] args) {
        launch();
    }
}