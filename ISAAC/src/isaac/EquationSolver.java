/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isaac;

import java.util.ArrayList;

/**
 *
 * @author petrs
 */
public class EquationSolver {

    private static String add(String s, String t) {
        return Double.toString(Double.valueOf(s) + Double.valueOf(t));
    }

    private static String substract(String s, String t) {
        return Double.toString(Double.valueOf(s) - Double.valueOf(t));
    }

    private static String multiply(String s, String t) {
        return Double.toString(Double.valueOf(s) * Double.valueOf(t));
    }

    private static String divide(String s, String t) {
        return Double.toString(Double.valueOf(s) / Double.valueOf(t));
    }

    private static String power(String s, String t) {
        double d = Double.valueOf(s);
        for (int i = 1; i < Integer.valueOf(t); i++) {
            d = d * Double.valueOf(s);
        }
        return Double.toString(d);
    }

    private static void vyhodnotVyraz(ArrayList<String> arr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static ArrayList<String> cleanup(ArrayList<String> arr, int i, int j) {
        for (int k = j; k >= i; k--) {
            arr.remove(k);
        }
        return arr;
    }

    static String quadVarValue = "0";
    static String varValue = "0";
    static String absValue = "0";

    public static Boolean isSign(String s) {
        if (s == "+" || s == "-" || s == "*" || s == "/" || s == "=") {

            return true;
        }
        return false;
    }

    public static Boolean isNumber(Character ch) {
        if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
            return true;
        }
        return false;
    }

    public static Boolean isVariable(Character ch) {
        if (ch == 'x') {
            return true;
        }
        return false;
    }

    public static Boolean isBracket(Character ch) {
        if (ch == '(' || ch == ')') {
            return true;
        }
        return false;
    }

    private static void solve(ArrayList<String> arr) {

        String currQuadVarValue = "0";
        String currVarValue = "0";
        String currAbsValue = "0";

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == "/") {
                if (arr.get(i - 1) == "x") {

                    if (arr.get(i - 2) == "-") { // -x/2
                        currVarValue = substract(currVarValue, divide("1", arr.get(i + 1)));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;

                    } else if (arr.get(i - 2) == "*") {
                        if (arr.get(i - 4) == "-") { // -4*x/2
                            currVarValue = substract(currVarValue, divide(arr.get(i - 3), arr.get(i + 1)));
                            arr = cleanup(arr, i - 4, i + 1);
                            i = -1;
                        } else { //+4*x/2
                            currVarValue = add(currVarValue, divide(arr.get(i - 3), arr.get(i + 1)));
                            arr = cleanup(arr, i - 4, i + 1);
                            i = -1;
                        }
                    } else { //+x/2
                        currVarValue = add(currVarValue, divide("1", arr.get(i + 1)));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    }

                } else { //4/2

                    if (arr.get(i - 2) == "-") {
                        currAbsValue = substract(currAbsValue, divide(arr.get(i - 1), arr.get(i + 1)));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    } else {
                        currAbsValue = add(currAbsValue, divide(arr.get(i - 1), arr.get(i + 1)));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    }

                }
            }

        }
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == "^") {
                if (arr.get(i - 1) == "x") { //Assume that variable is quadratic
                    if (!"2".equals(arr.get(i + 1))) {
                        System.err.println("Sorry, I cant handle different that quadratic variables :(");
                    } else {
                        if (arr.get(i - 2) == "-") {
                            currQuadVarValue = substract(currQuadVarValue, "1");
                            arr = cleanup(arr, i - 2, i + 1);
                            i = -1;
                        } else if (arr.get(i - 2) == "*") {
                            try {
                                if (arr.get(i - 4) == "-") {
                                    currQuadVarValue = substract(currQuadVarValue, arr.get(i - 3));
                                    arr = cleanup(arr, i - 4, i + 1);
                                    i = -1;
                                } else {
                                    currQuadVarValue = add(currQuadVarValue, arr.get(i - 3));
                                    arr = cleanup(arr, i - 4, i + 1);
                                    i = -1;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                currQuadVarValue = add(currQuadVarValue, arr.get(i - 3));
                                arr = cleanup(arr, i - 3, i + 1);
                                i = -1;
                            }
                        } else {
                            currQuadVarValue = add(currQuadVarValue, "1");
                            arr = cleanup(arr, i - 2, i + 1);
                            i = -1;
                        }
                    }
                } else {
                    currAbsValue = add(currAbsValue, power(arr.get(i - 1), arr.get(i + 1)));
                    arr = cleanup(arr, i - 1, i + 1);
                    i = -1;
                }
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == "*") {
                if (arr.get(i + 1) == "x") {
                    if (arr.get(i - 2) == "-") { // -2*x
                        currVarValue = substract(currVarValue, arr.get(i - 1));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;

                    } else {
                        currVarValue = add(currVarValue, arr.get(i - 1));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    }
                } else {
                    if (arr.get(i - 2) == "-") { // -2*3
                        currAbsValue = substract(currAbsValue, (multiply(arr.get(i - 1), arr.get(i + 1))));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    } else {
                        currAbsValue = add(currAbsValue, (multiply(arr.get(i - 1), arr.get(i + 1))));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    }
                }
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr);
            System.out.println(arr.get(i));
            if (arr.get(i) == "+") {
                if (arr.get(i + 1) == "x") {
                    currVarValue = add(currVarValue, "1");
                    arr = cleanup(arr, i, i + 1);
                    i = -1;
                } else {
                    System.out.println("tet3");
                    currAbsValue = add(currAbsValue, arr.get(i + 1));
                    arr = cleanup(arr, i, i + 1);
                    i = -1;
                }
            }
        }
        for (int i = 0; i < arr.size(); i++) {

            if (arr.get(i) == "-") {
                if (arr.get(i + 1) == "x") {
                    currVarValue = substract(currVarValue, "1");
                    arr = cleanup(arr, i, i + 1);
                    i = -1;
                } else {
                    currAbsValue = substract(currAbsValue, arr.get(i + 1));
                    arr = cleanup(arr, i, i + 1);
                    i = -1;
                }

            }
        }
        arr.clear(); //Sometimes for some reason, there is left a blank spot in array
        arr.add(currVarValue);
        arr.add(currAbsValue);
        arr.add(currQuadVarValue);
        System.out.println(currAbsValue + " test2");
    }

    public static String solveLin(ArrayList<String> equation) {
        absValue = "0";
        varValue = "0";
        quadVarValue = "0";
        System.out.println("Original equation: " + equation);

        ArrayList<String> eq = new ArrayList();
        int posOfEqu = 0; //Position of =

        for (int i = 0; i < equation.size(); i++) { //Find position of = sign in array
            if ("=".equals(equation.get(i))) {
                posOfEqu = i;
            }
        }

        for (int koeficient = 0; koeficient <= 1; koeficient++) { //Which side of equation are we currently on; 0 = left, 1 = right

            //Creates array eq with eqither left or right side of equation
            if (koeficient == 0) {
                for (int i = 0; i < posOfEqu; i++) {
                    eq.add(equation.get(i));
                }
            } else {
                eq.clear();
                if (!"+".equals(equation.get(posOfEqu + 1)) && !"-".equals(equation.get(posOfEqu + 1))) { // =4+3 >> =+4+3
                    eq.add("+");
                }
                for (int i = posOfEqu + 1; i < equation.size(); i++) {

                    eq.add(equation.get(i));
                }
                //Since we have moved to the right side, our values of need to be multiplied by -1
                varValue = multiply(varValue, "-1");
                absValue = multiply(absValue, "-1");
                quadVarValue = multiply(quadVarValue, "-1");

            }
            System.out.println(eq);

//*****BRACKETS*****
            int leftBracket = 0; //Position of left bracket in the array
            for (int i = 0; i < eq.size(); i++) {
                if ("(".equals(eq.get(i))) {
                    leftBracket = i;
                }

                if (")".equals(eq.get(i))) { //Here we create a new array with just the equation in the brackets and solve it
                    //Zavorky od leftBracket do i
                    ArrayList<String> arr = new ArrayList();
                    for (int j = leftBracket + 1; j < i; j++) {
                        arr.add(eq.get(j));
                    }

                    solve(arr); //array that will be returned will consist of three numbers in this order: (0)value of variable (1)absolute value (2) value of quad variable
                    eq = cleanup(eq, leftBracket, i); //Finally we remove the brackets from the original equations

                    if ("+".equals(eq.get(leftBracket - 1))) { //If there was a + before brackets we will simply add the bracket value to the global equation values
                        varValue = add(varValue, arr.get(0));//Brackets var Value
                        absValue = add(absValue, arr.get(1)); //Brackets abs Value
                        quadVarValue = add(quadVarValue, arr.get(2)); //Brackets quadratic variable value
                        eq.remove(leftBracket - 1); //Remove the + from equation
                    } else if ("-".equals(eq.get(leftBracket - 1))) { //Same as before just with -
                        varValue = substract(varValue, arr.get(0));
                        absValue = substract(absValue, arr.get(1));
                        quadVarValue = add(quadVarValue, arr.get(2));
                        eq.remove(leftBracket - 1);
                    } else if ("*".equals(eq.get(leftBracket - 1))) { //If there is a * before bracket it is bit more comlicated by still similar as before
                        if ("-".equals(eq.get(leftBracket - 3))) { // If there is a - before the operand ex:(-3*(....)), we multiply the bracket values and substract the from global
                            varValue = substract(varValue, multiply(eq.get(leftBracket - 2), arr.get(0)));
                            absValue = substract(absValue, multiply(eq.get(leftBracket - 2), arr.get(1)));
                            quadVarValue = substract(quadVarValue, multiply(eq.get(leftBracket - 2), arr.get(2)));
                            eq = cleanup(eq, leftBracket - 3, leftBracket - 1); //Clean up the whole multiplication before brackets
                        } else { //Same thing but with +
                            varValue = add(varValue, multiply(eq.get(leftBracket - 2), arr.get(0)));
                            absValue = add(absValue, multiply(eq.get(leftBracket - 2), arr.get(1)));
                            quadVarValue = add(quadVarValue, multiply(eq.get(leftBracket - 2), arr.get(2)));
                            eq = cleanup(eq, leftBracket - 3, leftBracket - 1);
                        }

                    }
                    /*else if ("/".equals(eq.get(leftBracket))) { //If b //NOT IMPLEMENTED TO DO
                        varValue = add(varValue, divide(arr.get(0), eq.get(leftBracket + 1)));
                        absValue = add(absValue, divide(arr.get(0), eq.get(leftBracket + 1)));
                        eq = cleanup(eq, leftBracket, leftBracket + 1);
                    }*/
                }
            }
            //Now that brackets have been solved we can solve the rest of equation
            solve(eq); //This will once again return three values (0)var (1)abs (2)quad

            try {
                varValue = add(varValue, eq.get(0));
            } catch (Exception e) {

            }
            try {
                absValue = add(absValue, eq.get(1));
            } catch (Exception e) {

            }
            try {
                quadVarValue = add(quadVarValue, eq.get(2));
            } catch (Exception e) {

            }
        }
        System.out.println(varValue);
        System.out.println(absValue);
        System.out.println(quadVarValue);

        if ("0.0".equals(quadVarValue)) { //If the equation is not quadtratic the quadVarValue remained zero
            if (Double.valueOf(varValue) < 0) { //If value of variable is smaller than 0, we multiply it and absolute value by -1 ex: -x=1 > x=-1
                varValue = multiply(varValue, "-1");
                absValue = multiply(absValue, "-1");
            }
            return "x =  " + multiply((divide(absValue, varValue)), "-1"); //Now we return "x = " and absolute value divided by variable value and multiplied by -1
        } else if (!"0.0".equals(quadVarValue)) { //If equation is quadratic
            double result1; //We usually get two results from quadratic equation, and most of the time they are not whole numbers
            double result2;
            double dis; //Discriminant (b*b - 4 * a * c) in our case: (varValue * varValue - 4 * quadVarValue * absValue)

            dis = (Double.valueOf(varValue) * Double.valueOf(varValue)) - 4 * (Double.valueOf(quadVarValue) * Double.valueOf(absValue));
            System.out.println(dis);
            if (dis > 0.0) { //In real numbers discriminant must be bigger or equal to zero

                result1 = (-(Double.valueOf(varValue)) + Math.sqrt(dis)) / (2 * Double.valueOf(quadVarValue)); //(-b * sqrt(Discriminant)) / 4a
                result2 = (-(Double.valueOf(varValue)) - Math.sqrt(dis)) / (2 * Double.valueOf(quadVarValue));

                return "x1 = " + Double.toString(result1) + "; x2 = " + Double.toString(result2); //return the results
            } else {
                return "Equation has no solution";
            }
        }

        return null;
    }
}
