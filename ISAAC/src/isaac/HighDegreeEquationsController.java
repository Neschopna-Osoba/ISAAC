package isaac;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
 * High Degree Equations Controller class
 *
 * @author Petr Salavec, 2020
 */
public class HighDegreeEquationsController implements Initializable {

    /**
     * This algorith can in theory solve equaiton of any degree. However due to
     * design and time limitations I have limited it here to maximum of 5th
     * degree. Reasons why I chose 5th degree are that there arent many
     * equations that go above 4th degree and because 6th degree equations tend
     * to have solutions in complex numbers, which this algorithm cant really
     * handle.
     */
    @FXML
    private TextField field5;
    @FXML
    private TextField field4;
    @FXML
    private TextField field3;
    @FXML
    private TextField field2;
    @FXML
    private TextField field1;
    @FXML
    private TextField field0;
    @FXML
    private TextField fieldResult;

    @FXML
    private void handleMenuButtonAction(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
        Scene scene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.setTitle("ISAAC");
    }

    @FXML
    private void handleCalcButton(ActionEvent event) throws IOException {
        ArrayList<Integer> arr = new ArrayList();

        //Add values of variable to ArrayList, check if empty, in that case assume that value is 0
        if (field0.getText() == null) {
            arr.add(0);
        } else {
            arr.add(Integer.valueOf(field0.getText()));
        }
        if (field1.getText() == null) {
            arr.add(0);
        } else {
            arr.add(Integer.valueOf(field1.getText()));
        }
        if (field2.getText() == null) {
            arr.add(0);
        } else {
            arr.add(Integer.valueOf(field2.getText()));
        }
        if (field3.getText() == null) {
            arr.add(0);
        } else {
            arr.add(Integer.valueOf(field3.getText()));
        }
        if (field4.getText().isEmpty()) {
            arr.add(0);
        } else {
            arr.add(Integer.valueOf(field4.getText()));
        }
        if (field5.getText().isEmpty()) {
            arr.add(0);
        } else {
            arr.add(Integer.valueOf(field5.getText()));
        }

        ArrayList solutions = new ArrayList();

        //Checks solutions from -100 to 100
        for (int i = -100; i <= 100; i++) {
            double solution = 0;
            //Puts the i in the equation as value of varialbe
            for (int j = 0; j < arr.size(); j++) {
                solution = solution + (arr.get(j) * Math.pow(i, j));
            }
            //If we have found solution - great
            if (solution == 0 && !solutions.contains(i)) {
                solutions.add(i);
                //If not, but we got "reasonably" close, try decimal values
            } else if (Math.abs(solution) < 5) {
                for (int k = -99; k < 100; k++) {
                    double solutionSmaller = 0;
                    double iSmaller = Double.valueOf(i) + Double.valueOf(k) / 100;
                    for (int j = 0; j < arr.size(); j++) {
                        solutionSmaller = solutionSmaller + (arr.get(j) * Math.pow(iSmaller, j));
                    }
                    if (solutionSmaller == 0 && !solutions.contains(iSmaller)) {
                        solutions.add(iSmaller);
                        //If we failed to find exact solution but are within 0.02 margin of error, we can safely assume that it is the solution
                    } else if (Math.abs(solutionSmaller) < 0.02 && !solutions.contains((double) Math.round(iSmaller * 10) / 10)) {
                        solutions.add((double) Math.round(iSmaller * 10) / 10);
                    }
                }
            }
        }

        //Formatting the results
        String resultScreen = "K = {";
        for (int i = 0; i < solutions.size(); i++) {
            if (i == solutions.size() - 1) {
                resultScreen = resultScreen + solutions.get(i);
            } else {
                resultScreen = resultScreen + solutions.get(i) + ", ";
            }
        }
        resultScreen = resultScreen + "}";

        //Showing results on screen
        fieldResult.setText(resultScreen);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
