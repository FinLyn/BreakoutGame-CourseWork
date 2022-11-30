package project.chenlin_17.Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import project.chenlin_17.GameApplication;

import java.io.IOException;
/**
 * This class is the controller of the scene option page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
public class Index {
    /**
     * This function leads the player to the index page.<br>
     * It is static since in each page there exists a button that allows the player to go back to the index page.
     * @param stage current game stage for showing
     */
    public static void load(Stage stage){
        try{
            Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/Index.fxml"));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
