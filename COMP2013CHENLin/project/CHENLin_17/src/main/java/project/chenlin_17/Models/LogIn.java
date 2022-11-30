package project.chenlin_17.Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.chenlin_17.GameApplication;

import java.io.IOException;
/**
 * This class is the controller of the scene option page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
public class LogIn {
    /**
     * This function leads the player to the log in page.<br>
     * It is static since it is the log in page has the functionality of initializing the default stage.
     * @param stage current game stage for showing
     * @param height expected scene height that combines with the stage
     * @param width expected scene width that combines with the stage
     */
    public static void load(Stage stage,int width, int height){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("Fxml/LogIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
