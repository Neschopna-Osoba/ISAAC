package isaac;

import static isaac.UsefulMethods.isGonio;
import static isaac.UsefulMethods.isLog;
import static isaac.UsefulMethods.isSign;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JFrame;

/**
 * Graph View Controller
 *
 * @author Petr Salavec, 2020
 */
public class GraphViewController implements Initializable {

    boolean creatingNumber;
    boolean negative;
    int number;

    String equationOnScreen = "";
    String result = "";
    ArrayList<String> equation = new ArrayList();

    boolean lin = false;
    boolean quad = false;

    //Same methond as in Equation Controller
    private void numberCreation(String string) {
        if (creatingNumber) {
            creatingNumber = false;
            if (negative) {
                negative = false;
                number = number * -1;
            }

            equation.add(Integer.toString(number));
            if (("x".equals(string) || "(".equals(string) || isGonio(string) || isLog(string)) || "√".equals(string) && !isSign(equation.get(equation.size() - 1)) && !"".equals(string) && !"^".equals(string)) { //People often leave empty space between two elements --> 10x = 10 * x; 10(x+3) = 10 * (x+3)
                equation.add("*");
            }
            equation.add(string);
            number = 0;
        } else {

            try {
                if ("x".equals(equation.get(equation.size() - 1)) && !isSign(string) && !"".equals(string) && !"^".equals(string) && !")".equals(string)) { //x(7+3) = x * (7+3)
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
    private void handleSquaredButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "√";
        textField.setText(equationOnScreen);
        numberCreation("√");
    }

    @FXML
    private void handleNegButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + " -";
        textField.setText(equationOnScreen);

        negative = true;
    }

    @FXML
    private void handleSinButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "sin(";
        textField.setText(equationOnScreen);
        numberCreation("sin");
        numberCreation("(");

    }

    @FXML
    private void handleCosButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "cos(";
        textField.setText(equationOnScreen);
        numberCreation("cos");
        numberCreation("(");
    }

    @FXML
    private void handleTgButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "tg(";
        textField.setText(equationOnScreen);
        numberCreation("tg");
        numberCreation("(");
    }

    @FXML
    private void handleCotgButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "cotg(";
        textField.setText(equationOnScreen);
        numberCreation("cotg");
        numberCreation("(");
    }

    @FXML
    private void handleLog10ButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "log10(";
        textField.setText(equationOnScreen);
        numberCreation("log10");
        numberCreation("(");
    }

    @FXML
    private void handleLnButtonAction(ActionEvent event) {
        equationOnScreen = equationOnScreen + "ln(";
        textField.setText(equationOnScreen);
        numberCreation("ln");
        numberCreation("(");
    }

    @FXML
    private void handleGraphButtonAction(ActionEvent event) throws IOException {

        numberCreation("");

        if (!"-".equals(equation.get(0)) && !"+".equals(equation.get(0))) {
            equation.add(0, "+");
        }
        System.out.println(equation);
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); //centers the window BUT WHY
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
