package project.chenlin_17.tools;

import project.chenlin_17.Views.*;

import java.util.ArrayList;
/**
 * This is the helper class for building the world and pre character option view for the character chosen page.<br>
 * It aims at code reusing and uses the Factory Pattern.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
public class SceneFactory {
    /**
     * Player's choice for scene theme
     */
    private String m_Scene;

    /**
     * This constructor gets the scene theme from the player and store it for later usage.
     * @param scene player's choice for scene theme passed from the scene option page
     */
    public SceneFactory(String scene){
        m_Scene = scene;
    }

    /**
     * This function returns the worldBuilder for the chosen theme in order to pass to the character option page.<br>
     * If the player's selection is 'General', the general worldBuilder will be returned.<br>
     * In the other conditions, the advanced worldBuilder will be returned for building image purposes.
     * @return the world builder of the specific type
     */
    public WorldBuilder getBuilder(){
        return switch (m_Scene) {
            case "General" -> new GeneralBuilder();
            case "Space" -> new AdvancedBuilder(SceneOptions.Space);
            case "City" -> new AdvancedBuilder(SceneOptions.City);
            default -> new AdvancedBuilder(SceneOptions.Soccer);
        };
    }

    /**
     *  This function returns the character image and paddle set for the chosen theme in order to pass it to the character option page.<br>
     *  All the character images are set according to the themes. Different theme have different character image and its corresponding paddle set.
     * @return character images and its corresponding paddle image of the specific type
     */
    public ArrayList<CharacterOptions> getCharacterOptions() {
        ArrayList<CharacterOptions> characterOptions = new ArrayList<>();
        switch (m_Scene) {
            case "General" -> {
                characterOptions.add(CharacterOptions.GeneralSet1);
                characterOptions.add(CharacterOptions.GeneralSet2);
                characterOptions.add(CharacterOptions.GeneralSet3);
                characterOptions.add(CharacterOptions.GeneralSet4);
            }
            case "Space" -> {
                characterOptions.add(CharacterOptions.SpaceSet1);
                characterOptions.add(CharacterOptions.SpaceSet2);
                characterOptions.add(CharacterOptions.SpaceSet3);
                characterOptions.add(CharacterOptions.SpaceSet4);
            }
            case "City" -> {
                characterOptions.add(CharacterOptions.CitySet1);
                characterOptions.add(CharacterOptions.CitySet2);
                characterOptions.add(CharacterOptions.CitySet3);
                characterOptions.add(CharacterOptions.CitySet4);
            }
            default -> {
                characterOptions.add(CharacterOptions.SoccerSet1);
                characterOptions.add(CharacterOptions.SoccerSet2);
                characterOptions.add(CharacterOptions.SoccerSet3);
                characterOptions.add(CharacterOptions.SoccerSet4);
            }
        }
        return characterOptions;
    }

}
