/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isaac;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author petrs
 */
public class LogarithmsViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField textA;
    @FXML
    private TextField textB;
    @FXML
    private TextField textX;

    @FXML
    private void handleCalcButton(ActionEvent event) throws IOException {
        if (textX.getText().isEmpty()) {
            double result = Math.log10(Double.valueOf(textB.getText())) / Math.log10(Double.valueOf(textA.getText()));
            textX.setText(String.valueOf(result));
        } else if (textB.getText().isEmpty()) {
            double result = Math.pow(Double.valueOf(textA.getText()), Double.valueOf(textX.getText()));
            textB.setText(String.valueOf(result));
        } else if (textA.getText().isEmpty()) {
            double result = Math.pow(Double.valueOf(textB.getText()), 1.0 / Double.valueOf(textX.getText()));
            textA.setText(String.valueOf(result));
        }

    }

    @FXML
    private void handleEButtonAction(ActionEvent event) throws IOException {
        textA.setText(String.valueOf(Math.E));
    }

    @FXML
    private void handleMenuButtonAction(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
        Scene scene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.setTitle("ISAAC");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
