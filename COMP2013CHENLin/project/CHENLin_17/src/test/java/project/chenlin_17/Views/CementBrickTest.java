package project.chenlin_17.Views;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CementBrickTest extends ApplicationTest {
    AnchorPane m_Root;
    Brick m_Cement;
    Group m_Group;
    @Start
    public void start(Stage stage) throws IOException {
        m_Root = new AnchorPane();
        Scene scene = new Scene(m_Root, 600, 650);
        stage.setScene(scene);
        stage.show();
    }
    private void initialize(){
        m_Group = new Group();
        m_Cement= new CementBrick(new Point2D(20,20), new Dimension2D(20,20) , m_Group);
    }
    @Test
    @DisplayName("Test whether the crack has been shown")
    @Order(1)
    void testUpdate(){
        initialize();
        ((CementBrick)m_Cement).setImpact(new Point2D(20,20),Crack.LEFT);
        Assertions.assertTrue(m_Group.getChildren().toString().contains("Path"),"Crack is expected to be shown");
    }

    @Test
    @DisplayName("Test whether repair works correctly")
    @Order(2)
    void testRepair() {
        initialize();
        Field broken = null;
        try {
            broken = Brick.class.getDeclaredField("m_Broken");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        broken.setAccessible(true);
        try {
            broken.set(m_Cement,true);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        m_Group.getChildren().remove(m_Cement.getM_BrickFace());
        m_Cement.repair();
        Assertions.assertEquals(false,m_Cement.isBroken());
    }

    @Test
    @DisplayName("Test isBroken")
    void testIsBroken() {
        initialize();
        Field broken = null;
        try {
            broken = Brick.class.getDeclaredField("m_Broken");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        broken.setAccessible(true);
        try {
            broken.set(m_Cement,true);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(true,m_Cement.isBroken());
    }



}