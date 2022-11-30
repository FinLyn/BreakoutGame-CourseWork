package project.chenlin_17.tools;

import project.chenlin_17.GameApplication;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
/**
 * This is the class that handles all the sound in the game.<br>
 * It is built for code reusing as well as maintainablity. If new music is needed, we just need to modify this file.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */

/**
 * The player is only allowed to hear the specific music defined in this class
 */
public enum SoundEffect {
    /**
     * Default music for background
     */
    BACKGROUND("Sounds/background.wav"),
    /**
     * Default music for button clicking
     */
    BUTTONCLICK ("Sounds/clickButton.wav"),
    /**
     * Default music for space theme playing
     */
    SPACE("Sounds/spaceBackground.wav"),
    /**
     * Default music for city theme playing
     */
    CITY("Sounds/cityBackground.wav"),
    /**
     * Default music for brick breaking
     */
    BROKEN("Sounds/cityEliminate.wav"),
    /**
     * Default music for going to next level
     */
    LEVELUP("Sounds/levelUp.wav"),
    /**
     * Default music for hitting the wall
     */
    WALLIMPACT("Sounds/wallImpact.wav");


    /**
     * Music player controller
     */
    private Clip m_Clip;
    /**
     * Music volume setter
     */
    private double m_Volume;

    /**
     * This sets the default music for each music event.
     * @param soundFileName music sound file path
     */
    SoundEffect(String soundFileName) {
        // sets the sound effect
        try {
            URL url = GameApplication.class.getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            m_Clip = AudioSystem.getClip();
            m_Clip.open(audioInputStream);
            m_Volume=0.5;
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function changes the music status to play without intervals.
     */
    public void setToLoop() {
        // sets whether or not the sound effect loops
        m_Clip.loop(m_Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * This function allows to get the player's change for volume.<br>
     * It is used by the index page.
     * @param volume expected volume number
     */
    public void setVolume(float volume) {
        m_Volume = volume;
    }

    /**
     * This function implements the volume change behaviour by calculating the volume that set by the player.
     */
    public void changeBGMVolume() {
        if (m_Clip!=null) {
            FloatControl gainControl = (FloatControl) m_Clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(m_Volume));
        }
    }

    /**
     * This function allows the player to hear the music.<br>
     * Its default playing status is set to playing in loops.
     */
    public void startplay() {
        FloatControl gainControl = (FloatControl) m_Clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(m_Volume));
        setToLoop();
        m_Clip.start();
    }

    /**
     * This function allows the player to hear the music.<br>
     * Its default playing status is just play once.
     */
    public void click() {
        m_Clip.setFramePosition(0);
        m_Clip.start();
    }

    /**
     * This function stops the music playing.
     */
    public void stop() {
        m_Clip.stop();
    }

    /**
     * This function gets the current volume status.<br>
     * It is used for visualizing volume bar in the index page.
     * @return volume status used for slider showing
     */
    public double getM_Volume() {
        return m_Volume;
    }
}
