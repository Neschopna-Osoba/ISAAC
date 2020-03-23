package isaac;

import java.util.ArrayList;

/**
 * Class full of useful methods used in this project
 *
 * @author Petr Salavec, 2020
 */
public class UsefulMethods {
    //Method that adds two strings as if they were numbers in double format, returns string

    public static String add(String s, String t) {
        return Double.toString(Double.valueOf(s) + Double.valueOf(t));
    }

    //Method that substracts two strings as if they were numbers in double format, returns string
    public static String substract(String s, String t) {
        return Double.toString(Double.valueOf(s) - Double.valueOf(t));
    }

    //Method that multiplies two strings as if they were numbers in double format, returns string
    public static String multiply(String s, String t) {
        return Double.toString(Double.valueOf(s) * Double.valueOf(t));
    }

    //Method that divides two strings as if they were numbers in double format, returns string
    public static String divide(String s, String t) {
        return Double.toString(Double.valueOf(s) / Double.valueOf(t));
    }

    //Method that raises the first string to the power of the second as if they were numbers in double format, returns string
    public static String power(String s, String t) {
        double d = Double.valueOf(s);
        for (int i = 1; i < Integer.valueOf(t); i++) {
            d = d * Double.valueOf(s);
        }
        return Double.toString(d);
    }

    //Method that removes elements from ArrayList between i and j, returns ArrayList
    public static ArrayList<String> cleanup(ArrayList<String> arr, int i, int j) {
        for (int k = j; k >= i; k--) {
            arr.remove(k);
        }
        return arr;
    }

    //Method that checks whether the string is a sing
    public static boolean isSign(String s) {
        return "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || "=".equals(s);
    }

    //Method that checks whether the string is a goniometric function
    public static boolean isGonio(String s) {
        return "sin".equals(s) || "cos".equals(s) || "tg".equals(s) || "cotg".equals(s);
    }

    //Method that checks whether the string is a logarithm
    public static boolean isLog(String s) {
        return "log10".equals(s) || "ln".equals(s);
    }

}
