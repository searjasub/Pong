package edu.neumont.lopez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller of the application in charge of launching the canvas
 */
public class Main extends Application {

    /**
     *  Argument to pass to JavaFX application
     * @param args Arguments to pass in
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    /**
     * Start the application
     * @param stage The stage we're on
     */
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("Canvas.fxml"));
        Parent root = loader.load();
        CanvasController controller = loader.getController();
        controller.init(stage);
        stage.setScene(new Scene(root));


    }
}
