/**
 * This exports the module as package.
 */
module project.chenlin_17 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;

    opens project.chenlin_17 to javafx.fxml;
    exports project.chenlin_17;
    opens project.chenlin_17.Controller to javafx.fxml;
    exports project.chenlin_17.Controller;
    opens project.chenlin_17.Models to javafx.fxml;
    exports project.chenlin_17.Models;
    opens project.chenlin_17.tools to javafx.fxml;
    exports project.chenlin_17.tools;


}