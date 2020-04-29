package isaac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * ISAAC - Incredibly Smart And Amazing Calculator
 *
 * @author Petr Salavec, 2020
 */
public class ISAAC extends Application {

    //Shows the menu for ISAAC
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.setTitle("ISAAC");
        stage.getIcons().add(new Image("/icon.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
