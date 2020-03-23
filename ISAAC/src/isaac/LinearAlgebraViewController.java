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
 * Linear Algebra Controller
 *
 * @author Petr Salavec, 2020
 */
public class LinearAlgebraViewController implements Initializable {

    @FXML
    private TextField firstField;
    @FXML
    private TextField secondField;
    @FXML
    private TextField thirdField;
    @FXML
    private TextField textResult;

    @FXML
    private void handleCalcButton(ActionEvent event) {

        ArrayList<Double> arr1 = getNumbers(firstField.getText());
        ArrayList<Double> arr2 = getNumbers(secondField.getText());
        ArrayList<Double> arr3 = getNumbers(thirdField.getText());

        /**
         * Determinanty Cramerův algortimus, dokumentace!!
         */
        if (arr3.isEmpty()) { //2 Variables, x and y
            double divider = arr1.get(0) * arr2.get(1)
                    - arr1.get(1) * arr2.get(0);

            double x = arr1.get(2) * arr2.get(1)
                    - arr1.get(1) * arr2.get(2);

            double y = arr1.get(0) * arr2.get(2)
                    - arr1.get(2) * arr2.get(0);
            textResult.setText("x = " + x / divider + "; y = " + y / divider);

        } else { //3 Variables, x,y and z

            double divider = arr1.get(0) * arr2.get(1) * arr3.get(2)
                    + arr2.get(0) * arr3.get(1) * arr1.get(2)
                    + arr3.get(0) * arr1.get(1) * arr2.get(2)
                    - arr1.get(2) * arr2.get(1) * arr3.get(0)
                    - arr2.get(2) * arr3.get(1) * arr1.get(0)
                    - arr3.get(2) * arr1.get(1) * arr2.get(0);

            double x = arr1.get(3) * arr2.get(1) * arr3.get(2)
                    + arr2.get(3) * arr3.get(1) * arr1.get(2)
                    + arr3.get(3) * arr1.get(1) * arr2.get(2)
                    - arr1.get(2) * arr2.get(1) * arr3.get(3)
                    - arr2.get(2) * arr3.get(1) * arr1.get(3)
                    - arr3.get(2) * arr1.get(1) * arr2.get(3);
            double y = arr1.get(0) * arr2.get(3) * arr3.get(2)
                    + arr2.get(0) * arr3.get(3) * arr1.get(2)
                    + arr3.get(0) * arr1.get(3) * arr2.get(2)
                    - arr1.get(2) * arr2.get(3) * arr3.get(0)
                    - arr2.get(2) * arr3.get(3) * arr1.get(0)
                    - arr3.get(2) * arr1.get(3) * arr2.get(0);

            double z = arr1.get(0) * arr2.get(1) * arr3.get(3)
                    + arr2.get(0) * arr3.get(1) * arr1.get(3)
                    + arr3.get(0) * arr1.get(1) * arr2.get(3)
                    - arr1.get(3) * arr2.get(1) * arr3.get(0)
                    - arr2.get(3) * arr3.get(1) * arr1.get(0)
                    - arr3.get(3) * arr1.get(1) * arr2.get(0);
            textResult.setText("x = " + x / divider + "; y = " + y / divider + "; z = " + z / divider);

        }
    }

    private ArrayList<Double> getNumbers(String s) {
        //This is a bit different than in other classes as I found that the buttons were just really annoying to use with these equations
        ArrayList<Double> arr = new ArrayList();
        int number = 0;
        boolean positive = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'x') {
                if (number == 0) {
                    number = 1;
                }
                if (!positive) {
                    number = number * -1;
                    positive = true;
                }
                arr.add(Double.valueOf(number));
                number = 0;
            } else if (s.charAt(i) == 'y') {
                if (number == 0) {
                    number = 1;
                }
                if (!positive) {
                    number = number * -1;
                    positive = true;
                }
                arr.add(Double.valueOf(number));
                number = 0;
            } else if (s.charAt(i) == 'z') {
                if (number == 0) {
                    number = 1;
                }
                if (!positive) {
                    number = number * -1;
                    positive = true;
                }
                arr.add(Double.valueOf(number));
                number = 0;
            } else if (i == s.length() - 1) {
                number = number * 10 + Character.getNumericValue(s.charAt(i));
                if (!positive) {
                    number = number * -1;
                    positive = true;
                }
                arr.add(Double.valueOf(number));
            } else if (s.charAt(i) == '-') {
                positive = false;
            } else if (s.charAt(i) != '=' && s.charAt(i) != '+' && s.charAt(i) != ' ') {
                number = number * 10 + Character.getNumericValue(s.charAt(i));
            }

        }
        return arr;

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
