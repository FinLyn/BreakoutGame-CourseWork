package project.chenlin_17.tools;

import javafx.scene.image.Image;
import project.chenlin_17.GameApplication;
/**
 * This is the helper class that binds the character image and paddle image together.<br>
 * If a specific character image is chosen, then the corresponding paddle image will be returned.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */

/**
 * Each constructor set have its defined value for character image and paddle image, since they are designed to bind with each other.<br>
 * The player is able to set the paddle image by choosing the character
 */
public enum CharacterOptions {
    /**
     * This binds the character and paddle option together for the city set1
     */
    CitySet1("Pictures/City/cityPerson1.png","Pictures/City/cityPlayer1.png"),
    /**
     * This binds the character and paddle option together for the city set2
     */
    CitySet2("Pictures/City/cityPerson2.png","Pictures/City/cityPlayer2.png"),
    /**
     * This binds the character and paddle option together for the city set3
     */
    CitySet3("Pictures/City/cityPerson3.png","Pictures/City/cityPlayer3.png"),
    /**
     * This binds the character and paddle option together for the city set4
     */
    CitySet4("Pictures/City/cityPerson4.png","Pictures/City/cityPlayer4.png"),
    /**
     * This binds the character and paddle option together for the game set1
     */
    SoccerSet1("Pictures/Soccer/soccerPerson1.png","Pictures/Soccer/soccerPlayer1.png"),
    /**
     * This binds the character and paddle option together for the game set2
     */
    SoccerSet2("Pictures/Soccer/soccerPerson2.png","Pictures/Soccer/soccerPlayer2.png"),
    /**
     * This binds the character and paddle option together for the game set3
     */
    SoccerSet3("Pictures/Soccer/soccerPerson3.png","Pictures/Soccer/soccerPlayer3.png"),
    /**
     * This binds the character and paddle option together for the game set4
     */
    SoccerSet4("Pictures/Soccer/soccerPerson4.png","Pictures/Soccer/soccerPlayer4.png"),
    /**
     * This binds the character and paddle option together for the space set1
     */
    SpaceSet1("Pictures/Space/spacePerson2.png","Pictures/Space/spacePlayer1.png"),
    /**
     * This binds the character and paddle option together for the space set2
     */
    SpaceSet2("Pictures/Space/spacePerson3.png","Pictures/Space/spacePlayer1.png"),
    /**
     * This binds the character and paddle option together for the space set3
     */
    SpaceSet3("Pictures/Space/spacePerson4.png","Pictures/Space/spacePlayer3.png"),
    /**
     * This binds the character and paddle option together for the space set4
     */
    SpaceSet4("Pictures/Space/spacePerson5.png","Pictures/Space/spacePlayer3.png"),
    /**
     * This binds the character and paddle option together for the general set1
     */
    GeneralSet1("Pictures/Bricks/brickSet4.png",null),
    /**
     * This binds the character and paddle option together for the general set2
     */
    GeneralSet2("Pictures/Bricks/brickSet1.png",null),
    /**
     * This binds the character and paddle option together for the general set3
     */
    GeneralSet3("Pictures/Bricks/brickSet2.png",null),
    /**
     * This binds the character and paddle option together for the general set4
     */
    GeneralSet4("Pictures/Bricks/brickSet3.png",null);
    /**
     * Image for character
     */
    private Image m_Person;
    /**
     * Image for paddle
     */
    private Image m_Player;

    /**
     * This constructor sets the person image and player image for the specific theme set.
     * @param person character's expected image
     * @param player paddle's expected image
     */

    CharacterOptions(String person,String player){
        m_Person = new Image(GameApplication.class.getResource(person).toExternalForm());
        if(player!=null)
            m_Player = new Image(GameApplication.class.getResource(player).toExternalForm());
    }

    /**
     * This function helps the initial scene building for character option page.
     * @return specific character image associated with the specific set
     */
    public Image getM_Person() {return m_Person;}

    /**
     * This function helps the initial scene building for character option page.
     * @return specific paddle image associated with the specific set
     */
    public Image getM_Player() {return m_Player;}
}