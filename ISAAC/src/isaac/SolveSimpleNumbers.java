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
public class SolveSimpleNumbers {

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

    private static ArrayList<String> cleanup(ArrayList<String> arr, int i, int j) {
        for (int k = j; k >= i; k--) {
            arr.remove(k);
        }
        return arr;
    }

    public static String solveNum(ArrayList<String> equation) {

        if (equation.get(0).isEmpty()) { //Sometimes the array comes with first value as empty, dont really know why, so this is just an easy fix
            equation.remove(0);
        }

        int leftBracketPos = 0;

        while (equation.contains("(")) {
            for (int i = 0; i < equation.size(); i++) {
                if ("(".equals(equation.get(i))) {
                    leftBracketPos = i;
                } else if (")".equals(equation.get(i))) {
                    ArrayList<String> arr = new ArrayList();

                    for (int j = leftBracketPos + 1; j < i; j++) {
                        arr.add(equation.get(j));
                    }
                    solve(arr);
                    equation.add(leftBracketPos, arr.get(0));
                    cleanup(equation, leftBracketPos + 1, i + 1);
                    System.out.println(equation);
                }

            }
        }
        solve(equation);
        return equation.get(0);
    }

    private static void solve(ArrayList<String> arr) {

        for (int i = 0; i < arr.size(); i++) {
            if ("^".equals(arr.get(i))) {
                arr.add(i - 1, String.valueOf(Math.pow(Double.valueOf(arr.get(i - 1)), Double.valueOf(arr.get(i + 1)))));
                arr = cleanup(arr, i, i + 2);
                i = 0;
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            if ("/".equals(arr.get(i))) {
                arr.add(i - 1, divide(arr.get(i - 1), arr.get(i + 1)));
                arr = cleanup(arr, i, i + 2);
                i = 0;
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            if ("*".equals(arr.get(i))) {
                arr.add(i - 1, multiply(arr.get(i - 1), arr.get(i + 1)));
                arr = cleanup(arr, i, i + 2);
                i = 0;
                System.out.println(arr);
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            if ("+".equals(arr.get(i))) {
                arr.add(i - 1, add(arr.get(i - 1), arr.get(i + 1)));
                arr = cleanup(arr, i, i + 2);
                i = 0;
            }
        }
        for (int i = 0; i < arr.size(); i++) {
            if ("-".equals(arr.get(i))) {
                arr.add(i - 1, substract(arr.get(i - 1), arr.get(i + 1)));
                arr = cleanup(arr, i, i + 2);
                i = 0;
            }
        }
    }
}
