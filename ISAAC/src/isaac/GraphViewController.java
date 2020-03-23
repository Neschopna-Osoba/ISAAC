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
import javax.swing.JFrame;

/**
 *
 * @author petrs
 */
public class GraphViewController implements Initializable {

    private static boolean isGonio(String s) {
        return s == "sin" || s == "cos" || s == "tg" || s == "cotg";
    }

    private static boolean isLog(String s) {
        return s == "log10" || s == "ln";
    }

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
            if ((string == "x" || string == "(" || isGonio(string) || isLog(string)) && !isSign(equation.get(equation.size() - 1)) && string != "" && string != "^") { //People often leave empty space between two elements --> 10x = 10 * x; 10(x+3) = 10 * (x+3)
                equation.add("*");
            }
            equation.add(string);
            number = 0;
        } else {

            try {
                if (equation.get(equation.size() - 1) == "x" && !isSign(string) && string != "" && string != "^" && string != ")") { //x(7+3) = x * (7+3)
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
    private void handleSinButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "sin";
        textField.setText(equationOnScreen);
        numberCreation("sin");
    }

    @FXML
    private void handleCosButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "cos";
        textField.setText(equationOnScreen);
        numberCreation("cos");
    }

    @FXML
    private void handleTgButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "tg";
        textField.setText(equationOnScreen);
        numberCreation("tg");
    }

    @FXML
    private void handleCotgButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "cotg";
        textField.setText(equationOnScreen);
        numberCreation("cotg");
    }

    @FXML
    private void handleLog10ButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "log10";
        textField.setText(equationOnScreen);
        numberCreation("log10");
    }

    @FXML
    private void handleLnButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "ln";
        textField.setText(equationOnScreen);
        numberCreation("ln");
    }

    @FXML
    private void handleGraphButtonAction(ActionEvent event) throws IOException {

        numberCreation("");

        if (equation.get(0) != "-" && equation.get(0) != "+") {
            equation.add(0, "+");
        }
        System.out.println(equation);
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); //centers the window BUT WHY WHY WHY
        frame.setTitle("Graph");
        MakeGraph graph = new MakeGraph(equation);
        frame.add(graph);
        frame.setVisible(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
