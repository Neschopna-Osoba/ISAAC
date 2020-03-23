package isaac;

import java.util.ArrayList;

import static isaac.UsefulMethods.cleanup;
import static isaac.UsefulMethods.add;
import static isaac.UsefulMethods.substract;
import static isaac.UsefulMethods.multiply;
import static isaac.UsefulMethods.divide;
import static isaac.UsefulMethods.power;

/**
 * Class in which equations with variables are solved
 *
 * @author Petr Salavec, 2020
 */
public class EquationSolver {

    //Global value of quadratic variable in equation
    static String quadVarValue = "0";
    //Global value of linear variable in equation
    static String varValue = "0";
    //Global value of absulute element in equation
    static String absValue = "0";

    //Solves the equation in given ArrayList, returns ArrayList with three values - (0) - linear variable; (1) - absolute element; (2) - quadratic variable
    private static void solve(ArrayList<String> arr) {

        //Local value of quadratic variable in equation
        String currQuadVarValue = "0";
        //Local value of linear variable in equation
        String currVarValue = "0";
        //Local value of absulute element in equation
        String currAbsValue = "0";

        //***DIVISION***
        //First we go through our equation and check for divisions
        for (int i = 0; i < arr.size(); i++) {
            if ("/".equals(arr.get(i))) {
                if ("x".equals(arr.get(i - 1))) {    // If we find variable before our division -> ex: x/2
                    if ("-".equals(arr.get(i - 2))) {      // If we find variable before our division -> ex: x/2
                        currVarValue = substract(currVarValue, divide("1", arr.get(i + 1)));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    } else if ("*".equals(arr.get(i - 2))) {    // If the variable is multiplied
                        if ("-".equals(arr.get(i - 4))) {   // If there is a - before the number that multiplies the variable -> ex: -2*x/2
                            currVarValue = substract(currVarValue, divide(arr.get(i - 3), arr.get(i + 1)));
                            arr = cleanup(arr, i - 4, i + 1);
                            i = -1;
                        } else {    // If there is not a - before the number
                            currVarValue = add(currVarValue, divide(arr.get(i - 3), arr.get(i + 1)));
                            arr = cleanup(arr, i - 4, i + 1);
                            i = -1;
                        }
                    } else {
                        currVarValue = add(currVarValue, divide("1", arr.get(i + 1)));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    }

                } else {    //If there is no variable it is just standart division -> ex: 4/2
                    if ("-".equals(arr.get(i - 2))) {
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
        //***POWER***
        //Second we check for any powers
        for (int i = 0; i < arr.size(); i++) {
            if ("^".equals(arr.get(i))) {
                if ("x".equals(arr.get(i - 1))) { //Check for varuable
                    if (!"2".equals(arr.get(i + 1))) { //If the variable in not squared, then we have a problem
                        System.err.println("Sorry, I cant handle different that quadratic variables :(");
                    } else { //Squared variable
                        if ("-".equals(arr.get(i - 2))) {
                            currQuadVarValue = substract(currQuadVarValue, "1");
                            arr = cleanup(arr, i - 2, i + 1);
                            i = -1;
                        } else if ("*".equals(arr.get(i - 2))) {
                            if ("-".equals(arr.get(i - 4))) {
                                currQuadVarValue = substract(currQuadVarValue, arr.get(i - 3));
                                arr = cleanup(arr, i - 4, i + 1);
                                i = -1;
                            } else {
                                currQuadVarValue = add(currQuadVarValue, arr.get(i - 3));
                                arr = cleanup(arr, i - 4, i + 1);
                                i = -1;
                            }

                        } else {
                            currQuadVarValue = add(currQuadVarValue, "1");
                            arr = cleanup(arr, i - 2, i + 1);
                            i = -1;
                        }
                    }
                } else { //It is just two numbers
                    currAbsValue = add(currAbsValue, power(arr.get(i - 1), arr.get(i + 1)));
                    arr = cleanup(arr, i - 1, i + 1);
                    i = -1;
                }
            }
        }

        //***MULTIPLICATION***
        //Third we check for any multiplication
        for (int i = 0; i < arr.size(); i++) {
            if ("*".equals(arr.get(i))) {
                if ("x".equals(arr.get(i + 1))) {
                    if ("-".equals(arr.get(i - 2))) {
                        currVarValue = substract(currVarValue, arr.get(i - 1));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;

                    } else {
                        currVarValue = add(currVarValue, arr.get(i - 1));
                        arr = cleanup(arr, i - 2, i + 1);
                        i = -1;
                    }
                } else {
                    if ("-".equals(arr.get(i - 2))) {
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

        //***ADDITION***
        //Fourth we check for any additions
        for (int i = 0; i < arr.size(); i++) {
            if ("+".equals(arr.get(i))) {
                if ("x".equals(arr.get(i + 1))) {
                    currVarValue = add(currVarValue, "1");
                    arr = cleanup(arr, i, i + 1);
                    i = -1;
                } else {
                    currAbsValue = add(currAbsValue, arr.get(i + 1));
                    arr = cleanup(arr, i, i + 1);
                    i = -1;
                }
            }
        }

        //***SUBSTRACTION***
        //Last but not least we check for substractions
        for (int i = 0; i < arr.size(); i++) {
            if ("-".equals(arr.get(i))) {
                if ("x".equals(arr.get(i + 1))) {
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
        arr.clear(); //Sometimes for some reason, there is left a blank spot in array, this makes sure, that the array will be empty
        arr.add(currVarValue); //Add local linear variable value to ArrayList
        arr.add(currAbsValue); //Add local absolute element value to ArrayList
        arr.add(currQuadVarValue); //Add local quadratic variable value to ArrayList
    }

    public static String solveLin(ArrayList<String> equation) {
        //This clears the global variables
        absValue = "0";
        varValue = "0";
        quadVarValue = "0";

        //Test
        System.out.println("Original equation: " + equation);

        ArrayList<String> eq = new ArrayList(); //New ArrayList which will contain only half of the equation
        int posOfEqu = 0; //Position of = sign

        for (int i = 0; i < equation.size(); i++) { //Find position of = sign in array
            if ("=".equals(equation.get(i))) {
                posOfEqu = i;
            }
        }

        for (int koeficient = 0; koeficient <= 1; koeficient++) { //Which side of equation are we currently on; 0 = left, 1 = right

            //Fills ArrayList eq with either left or right side of equation
            if (koeficient == 0) { //Add left side
                for (int i = 0; i < posOfEqu; i++) {
                    eq.add(equation.get(i));
                }
            } else { //Add right side
                eq.clear();
                if (!"+".equals(equation.get(posOfEqu + 1)) && !"-".equals(equation.get(posOfEqu + 1))) { //Add + to the beggining of the right side of equation
                    eq.add("+");
                }
                for (int i = posOfEqu + 1; i < equation.size(); i++) {
                    eq.add(equation.get(i));
                }
                //Since we have moved to the right side of equation, all of values of need to be multiplied by -1
                varValue = multiply(varValue, "-1");
                absValue = multiply(absValue, "-1");
                quadVarValue = multiply(quadVarValue, "-1");

            }

            //***BRACKETS***
            //We need to find brackets first and deal with them
            int leftBracket = 0; //Position of left bracket in the equation

            for (int i = 0; i < eq.size(); i++) {
                if ("(".equals(eq.get(i))) {
                    leftBracket = i;
                }
                if (")".equals(eq.get(i))) { //Here we create a new array with just the equation in the brackets and solve it
                    ArrayList<String> arr = new ArrayList();
                    for (int j = leftBracket + 1; j < i; j++) {
                        arr.add(eq.get(j));
                    }

                    solve(arr); //Array that will be returned will consist of three numbers in this order: (0)value of variable (1)absolute value (2) value of quad variable
                    eq = cleanup(eq, leftBracket, i); //Finally we remove the brackets from the original equation

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

            //Adding the local values to the global values, they might be zero and as such try catch block are used to make sure no funny bussiness happens
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
