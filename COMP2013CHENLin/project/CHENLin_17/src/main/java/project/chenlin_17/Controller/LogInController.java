package project.chenlin_17.Controller;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.SoundEffect;

/**
 * This class is the controller of the log in page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */

public class LogInController {

    /**
     * Text field allows the player to input name
     */
    @FXML
   private TextField m_UserName;
    /**
     * Text field allows the player to input password
     */
    @FXML
    private TextField m_Password;
    /**
     * Store the username and its corresponding password read from the file
     */
    private HashMap<String,String> m_HashMap;
    /**
     * Alert window for informing the unexpected input
     */
    private Alert m_Alert;
    /**
     * The name for the file that stores the player's information
     */


    /**
     * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;
    /**
     * Defined file path for user's information
     */
    private final  String FILENAME = "src/main/resources/project/chenlin_17/ScoreRecords/userInfo.txt";
    /**
     * Default warning text when the name input is null
     */
    private final String NAMENULL = "User name can't be null";
    /**
     * Default warning text when the name input is null
     */
    private final String PWDNULL = "Password can't be null";
    /**
     * Default warning text when both the name and the password input is null
     */
    private final String BOTHNULL = "Please set your user name and password first";
    /**
     * Default warning text when the password input is incorrect
     */
    private final String PWDINCORRECT = "Incorrect Password";

    /**
     * This function sets the basic setting required for this scene. <br>
     * It contains the default value for the button clicking music.<br>
     * It contains the basic setting for warning alert dialog.<br>
     * It contains the intended file name set for read and write.<br>
     * It contains the hashmap initialization for the username ane their unique password.
     */
    public void initialize(){
        m_ButtonClicked = SoundEffect.BUTTONCLICK;
        m_Alert = createWarning("Warning","Action Required");
        m_HashMap = initializeHash();
    }

    /**
     * @return initialized hashmap with user name and corresponding password stored
     */
    private HashMap<String, String> initializeHash() {
        HashMap<String,String> hashMap = new HashMap<>();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(FILENAME));
            while((line =bufferedReader.readLine())!=null){
                String[] record = line.split(" ");
                hashMap.put(record[0],record[1]);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    /**
     * This function sets the default viewing for warning window.<br>
     * It is created for maintainability.
     * @param title stands for the warning title name
     * @param header stands for the warning header name
     * @return the newly created warning type that already had the title and header set.
     */
    private Alert createWarning(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        return alert;
    }

    /**
     * This function checks whether the username and the password are valid.<br>
     * If not valid, the warning alert will be shown. <br>
     * If is valid, the player will be directed to the index page with the username set.
     * @param actionEvent activated when the mouse is clicked
     */
    public void HandleSubmitButtonAction(ActionEvent actionEvent) {
            m_ButtonClicked.click();
            String name = m_UserName.getText();
            String password = m_Password.getText();
            String warningMessage;
        /*
        if the input is not null, then the validation process will be handled
         */
        if(!checkNull(name) &&!checkNull(password))
               checkStatus(name,password);
        else{
            if(checkNull(name) &&!checkNull(password))
                warningMessage = NAMENULL;
            else if(!checkNull(name) && checkNull(password))
                warningMessage = PWDNULL;
            else
                warningMessage= BOTHNULL;
            /*
            set corresponding warning message
            */
            m_Alert.setContentText(warningMessage);
            m_Alert.show();
        }
    }

    /**
     * This function checks whether the player has input text or not.
     * @param value the player's input box
     * @return the state of the judging result
     */
    private boolean checkNull(String value) {
        return Objects.equals(value, "");
    }

    /**
     * This function checks whether the name and the password are valid.<br>
     * If the name and the password are new, then it will be newly added into the file.<br>
     * If the name and the password corresponds, then the player will be directed to the index page with the user's name set.<br>
     * If the name and the password doesn't correspond, the warning message will be shown
     * @param name player name
     * @param password player stored password
     */

    private void checkStatus(String name,String password){
        if(!m_HashMap.containsKey(name))
        {
            m_HashMap.put(name,password);
            writeBack(name,password);
            /*
            go to index page with username set
             */
            DirectorApp.getInstance().setM_UserName(name);
            DirectorApp.getInstance().showStartScreen();
        }

        else{
            String truePassword = m_HashMap.get(name);
            if(truePassword.equals(password)){
                DirectorApp.getInstance().setM_UserName(name);
                DirectorApp.getInstance().showStartScreen();
            }
            else {
                /*
                show alert message
                 */
                m_Alert.setContentText(PWDINCORRECT);
                m_Alert.show();
            }
        }
    }

    /**
     * This function writes the newly added username to the existing file.<br>
     * There may exists file open exceptions.
     * @param name the user's input name
     * @param password the user's input password
     */
    private void writeBack(String name,String password){
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILENAME,true));
            bufferedWriter.write(name +" "+ password+"\n");
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
