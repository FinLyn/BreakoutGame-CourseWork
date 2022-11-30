package project.chenlin_17.tools;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * This class for the player's game record, which includes the name, score and created time.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */

public class Record {
    /**
     * Player name
     */
    private String m_Name;
    /**
     * Player score
     */
    private int m_Score;
    /**
     * Player time
     */
    private String m_Time;
    /**
     * BufferReader for file
     */
    private BufferedReader m_BufferReader;
    /**
     * BufferWriter for file
     */
    private FileWriter m_FileWritter;
    /**
     * File path or the file name
     */
    private static String FILE_SOURCE = "src/main/resources/project/chenlin_17/ScoreRecords/record.txt" ;

    /**
     * This constructor initializes the name and corresponding score for the record
     * @param name player name
     * @param score player score
     */
    public Record(String name, int score) {
        m_Name = name;
        m_Score = score;
        /*
        get the current time used for setting record
         */
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        m_Time = LocalDateTime.now().format(myFormat);
    }

    /**
     * This function allows the player to get his new game record being stored into the file.
     */
    public void updateRecord(){
        /*
        try to make a new buffer reader
         */
        m_BufferReader = makeReader(FILE_SOURCE);
        /*
        store the records read from the record file
         */
        ArrayList<String> writtenContent = readFile();
        try{
            writeBack(writtenContent);
        }catch (IOException e){
            System.out.println("Update failed");
        }

    }

    /**
     * This function helps write the new record back to the existing record file
     * @param writtenContent newly updated score lists with inserted record
     * @throws IOException if loading fails
     */
    private void writeBack(ArrayList<String> writtenContent) throws IOException {
        m_FileWritter = makeFileWriter(FILE_SOURCE);
        for(String file: writtenContent){
            m_FileWritter.write(file+"\n");
        }
        m_FileWritter.close();
    }

    /**
     * This is the helper function to make the buffer writer of the record file.
     * @param file record file path
     * @return newly built buffer writer
     */
    private FileWriter makeFileWriter(String file) {
        try{
            FileWriter fileWriter = new FileWriter(file);
            return fileWriter;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This function stores the current record list in order to make changes and insert the player's new record.
     * @return list that stores all the game record for all the player
     */
    private ArrayList<String> readFile() {
        ArrayList<String> content = new ArrayList<>();
        try{
            int number = 0;
            boolean found = false;
            String line;
            while((line=m_BufferReader.readLine())!=null) {
                String[] splitPoints = line.split(" ");
                //get the record score
                int score = Integer.parseInt(splitPoints[1]);
                content.add(line);
                // make the comparison
                if (m_Score > score && found ==false) {
                    content.add(number, toFormattedString());
                    found = true;
                }
                number++;
            }
            //The newly created user score
            if(!found) content.add(toFormattedString());
            return content;
            }
        catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    /**
     * This is the helper function to make the buffer reader of the record file.
     * @param file record file path
     * @return newly built buffer reader
     */
    private BufferedReader makeReader(String file) {
        try {
            FileReader fileReader = new FileReader(file);
            return new BufferedReader(fileReader);
        }
        catch (IOException e) {
            System.out.println("File invalid");
            return null;
        }
    }

    /**
     * This function gets the player name of the record.<br>
     * It is used for showing the player's name on the rank board.
     * @return player name
     */
    public String getName(){
        return m_Name;
    }
    /**
     * This function gets the player score of the record.<br>
     * It is used for showing the player's score on the rank board.
     * @return player score
     */
    public int getScore(){
        return m_Score;
    }
    /**
     * This function gets the record created time of the record.<br>
     * It is used for showing the time on the rank board.
     * @return record created time
     */
    public String getTime(){
        return m_Time;
    }

    /**
     * This function helps to store all the three values in an organized way
     * @return formatted record
     */
    public String toFormattedString(){
        return m_Name+" "+m_Score+" "+m_Time;
    }

}
