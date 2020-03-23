/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this equationlate file, choose Tools | Templates
 * and open the equationlate in the editor.
 */
package isaac;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static isaac.EquationSolver.solveLin;
import static isaac.EquationSolver.isVariable;
import static isaac.EquationSolver.isNumber;
import static isaac.EquationSolver.isSign;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author petrs
 */
public class FXMLDocumentController implements Initializable {

    boolean creatingNumber;
    boolean negative;
    int number;

    String equationOnScreen = "";
    String result = "";
    ArrayList<String> equation = new ArrayList();

    boolean lin = false;
    boolean quad = false;

    private void numberCreation(String string) {
        if (creatingNumber) {
            creatingNumber = false;
            if (negative) {
                negative = false;
                number = number * -1;
            }

            equation.add(Integer.toString(number));
            if ((string == "x" || string == "(") && !isSign(equation.get(equation.size() - 1)) && string != "" && string != "^") { //People often leave empty space between two elements --> 10x = 10 * x; 10(x+3) = 10 * (x+3)
                equation.add("*");
            }
            equation.add(string);
            number = 0;
        } else {

            try {
                if (equation.get(equation.size() - 1) == "x" && !isSign(string) && string != "" && string != "^") { //x(7+3) = x * (7+3)
                    equation.add("*");
                }
                if (negative) {
                    equation.add("-1");
                    equation.add("*");
                }
                equation.add(string);
            } catch (Exception e) {
                equation.add(string);
            }
        }
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

    @FXML
    private TextField textField;

    @FXML
    private void handleNumButtonAction(ActionEvent event) {
        Button b = (Button) event.getSource();
        //Name of the button is buttonNumX -- number is 10th character (Its lazy I know)
        number = number * 10 + Character.getNumericValue(b.getId().charAt(9));
        if (!creatingNumber) {
            creatingNumber = true;
        }

        equationOnScreen = equationOnScreen + Character.toString(b.getId().charAt(9));
        textField.setText(equationOnScreen);

    }

    @FXML
    private void handlePlusButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "+";
        textField.setText(equationOnScreen);
        numberCreation("+");
    }

    @FXML
    private void handleMinusButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "-";
        textField.setText(equationOnScreen);
        numberCreation("-");
    }

    @FXML
    private void handleTimesButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "*";
        textField.setText(equationOnScreen);
        numberCreation("*");
    }

    @FXML
    private void handleDivisionButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "/";
        textField.setText(equationOnScreen);
        numberCreation("/");
    }

    @FXML
    private void handleEqualsButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "=";
        textField.setText(equationOnScreen);
        numberCreation("=");
    }

    @FXML
    private void handleDelButtonAction(ActionEvent event) {
        equationOnScreen = "";
        textField.setText(equationOnScreen);
        equation.clear();
    }

    @FXML
    private void handleBracketLButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "(";
        textField.setText(equationOnScreen);
        numberCreation("(");
    }

    @FXML
    private void handleBracketRButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + ")";
        textField.setText(equationOnScreen);
        numberCreation(")");
    }

    @FXML
    private void handleXButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "x";
        textField.setText(equationOnScreen);
        numberCreation("x");
    }

    @FXML
    private void handlePowerButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "^";
        textField.setText(equationOnScreen);
        numberCreation("^");
    }

    @FXML
    private void handleNegButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + " -";
        textField.setText(equationOnScreen);

        negative = true;
    }

    @FXML
    private void handleCalcButtonAction(ActionEvent event) {
        numberCreation("");

        if (equation.get(0) != "-" && equation.get(0) != "+") {
            equation.add(0, "+");
        }

        System.out.println("CHECK OF STRING ARRAY IN HANDLECALC:" + equation);
        result = solveLin(equation);
        textField.setText(result);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
